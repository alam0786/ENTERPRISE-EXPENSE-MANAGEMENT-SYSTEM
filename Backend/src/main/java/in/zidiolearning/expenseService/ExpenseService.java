package in.zidiolearning.expenseService;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.zidiolearning.ExpenseDto.CreateExpenseRequest;
import in.zidiolearning.ExpenseDto.ExpenseResponse;
import in.zidiolearning.ExpenseDto.UpdateExpenseRequest;
import in.zidiolearning.ExpenseEntity.Expense;
import in.zidiolearning.expenseRepository.ExpenseRepository;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    // Directory where invoice files are saved
    private final Path uploadDir = Paths.get("uploads/invoices");

    public ExpenseResponse createExpense(CreateExpenseRequest request, MultipartFile invoice) throws IOException {
        Expense expense = Expense.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .expenseDate(request.getExpenseDate())
                .category(request.getCategory())
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

    private ExpenseResponse mapToResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .expenseDate(expense.getExpenseDate())
                .category(expense.getCategory())
                .invoiceFileName(expense.getInvoiceFileName())
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
}
