package in.zidiolearning.expenseService;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.zidiolearning.Entity.Role;
import in.zidiolearning.Entity.User;
import in.zidiolearning.Enums.ExpenseStatus;
import in.zidiolearning.Exception.ResourceNotFoundException;
import in.zidiolearning.ExpenseDto.CreateExpenseRequest;
import in.zidiolearning.ExpenseDto.ExpenseResponse;
import in.zidiolearning.ExpenseDto.NotificationPublisher;
import in.zidiolearning.ExpenseDto.UpdateExpenseRequest;
import in.zidiolearning.ExpenseEntity.Expense;
import in.zidiolearning.Repository.UserRepository;
import in.zidiolearning.expenseRepository.ExpenseRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;
    private final NotificationPublisher notificationPublisher;
    private final EmailService emailService;
    

    // Directory where invoice files are saved
    private final Path uploadDir = Paths.get("uploads/invoices");
    
    BigDecimal approvalLimit = new BigDecimal("1000");

    public ExpenseResponse createExpense(CreateExpenseRequest request, MultipartFile invoice) throws IOException {
    	 // ✅ Get currently logged-in username from SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
    	
     // ✅ Fetch user from DB
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        
    	Expense expense = Expense.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .expenseDate(request.getExpenseDate())
                .category(request.getCategory())
                .createdBy(user)
                .currentApprovalLevel(0)
                .build();

        if (invoice != null && !invoice.isEmpty()) {
            String fileName = saveInvoiceFile(invoice);
            expense.setInvoiceFileName(fileName);
        }

        Expense saved = expenseRepository.save(expense);

        return mapToResponse(saved);
    }

    public ExpenseResponse updateExpense(Long id, UpdateExpenseRequest request, MultipartFile invoice) throws IOException {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setTitle(request.getTitle());
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setCategory(request.getCategory());

        if (invoice != null && !invoice.isEmpty()) {
            String fileName = saveInvoiceFile(invoice);
            expense.setInvoiceFileName(fileName);
        }

        Expense updated = expenseRepository.save(expense);

        return mapToResponse(updated);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public ExpenseResponse getExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return mapToResponse(expense);
    }

    public List<ExpenseResponse> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ExpenseResponse mapToResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .expenseDate(expense.getExpenseDate())
                .category(expense.getCategory())
                .invoiceFileName(expense.getInvoiceFileName())
                .status(expense.getStatus())
                .rejectionReason(expense.getRejectionReason())
                .submittedAt(expense.getSubmittedAt())
                .createdBy(expense.getCreatedBy())
                .build();
    }

    private String saveInvoiceFile(MultipartFile file) throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String originalFileName = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalFileName;
        Path targetLocation = uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
    
    public Expense approveExpense(Long expenseId, String approverUsername) {
    	
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        User approver = userRepository.findByUsername(approverUsername)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Role role = approver.getRole(); // Assume enum: EMPLOYEE, MANAGER, FINANCE, ADMIN
        auditLogService.log("APPROVED", approverUsername, "Expense ID: " + expense.getId());
    	emailService.sendApprovalEmail(expense.getCreatedBy().getEmail(), expense.getTitle(), "APPROVED");
    	notificationPublisher.publishStatusChange(expense.getId(), "APPROVED");


        switch (role) {
            case ROLE_MANAGER:
                if (expense.getStatus() != ExpenseStatus.SUBMITTED) throw new IllegalStateException("Only SUBMITTED expenses can be approved by Manager.");
                expense.setStatus(ExpenseStatus.MANAGER_APPROVED);
                break;
            case ROLE_FINANCE:
                if (expense.getStatus() != ExpenseStatus.MANAGER_APPROVED) throw new IllegalStateException("Only MANAGER_APPROVED expenses can be approved by Finance.");
                expense.setStatus(ExpenseStatus.FINANCE_APPROVED);
                break;
            case ROLE_ADMIN:
                if (expense.getStatus() != ExpenseStatus.FINANCE_APPROVED) throw new IllegalStateException("Only FINANCE_APPROVED expenses can be approved by Admin.");
                expense.setStatus(ExpenseStatus.ADMIN_APPROVED);
                break;
            default:
                throw new AccessDeniedException("Unauthorized to approve.");
        }

        return expenseRepository.save(expense);
    }

    
    public Expense submitExpense(Long expenseId, String username) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        if (!expense.getCreatedBy().getUsername().equals(username)) {
            throw new AccessDeniedException("Not allowed to submit this expense");
        }

        expense.setStatus(ExpenseStatus.SUBMITTED);
        expense.setSubmittedAt(LocalDateTime.now());

        // Auto approve if amount is low
        BigDecimal amount = BigDecimal.valueOf(expense.getAmount());
        if (amount.compareTo(approvalLimit) <= 0) {
            expense.setStatus(ExpenseStatus.MANAGER_APPROVED);
        }

        return expenseRepository.save(expense);
    }

    public Expense rejectExpense(Long expenseId, String rejectedBy, String reason) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));

        // Example: update the expense status to REJECTED
        expense.setStatus(ExpenseStatus.REJECTED);

        // Optionally, track who rejected and reason
        expense.setRejectedBy(rejectedBy);
        expense.setRejectionReason(reason);
        expense.setRejectedAt(LocalDateTime.now());

        // Save changes
        return expenseRepository.save(expense);
    }

    
}
