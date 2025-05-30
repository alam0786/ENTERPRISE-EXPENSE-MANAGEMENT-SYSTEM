package in.zidiolearning.expenseController;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import in.zidiolearning.ExpenseDto.CreateExpenseRequest;
import in.zidiolearning.ExpenseDto.ExpenseResponse;
import in.zidiolearning.ExpenseDto.UpdateExpenseRequest;
import in.zidiolearning.expenseService.ExpenseService;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ExpenseResponse> createExpense(
            @RequestPart("expense") String expenseJson,
            @RequestPart(value = "invoice", required = false) MultipartFile invoice
    ) throws IOException {
        CreateExpenseRequest request = objectMapper.readValue(expenseJson, CreateExpenseRequest.class);
        ExpenseResponse response = expenseService.createExpense(request, invoice);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable("id") Long id,
            @RequestPart("expense") String expenseJson,
            @RequestPart(value = "invoice", required = false) MultipartFile invoice
    ) throws IOException {
        UpdateExpenseRequest request = objectMapper.readValue(expenseJson, UpdateExpenseRequest.class);
        ExpenseResponse response = expenseService.updateExpense(id, request, invoice);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN')")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok("Deleted expense with id: " + id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN')")
    public ResponseEntity<ExpenseResponse> getExpense(@PathVariable("id") Long id) {
        ExpenseResponse response = expenseService.getExpense(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("list")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN')")
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        List<ExpenseResponse> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }
}
