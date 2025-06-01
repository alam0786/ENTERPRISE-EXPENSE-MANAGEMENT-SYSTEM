package in.zidiolearning.ExpenseDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import in.zidiolearning.Entity.User;
import in.zidiolearning.Enums.ExpenseStatus;
import in.zidiolearning.ExpenseEntity.Category;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    private Long id;
    private String title;
    private String description;
    private Double amount;
    private LocalDate expenseDate;
    private Category category;
    private String invoiceFileName;
    private ExpenseStatus status;
    private String rejectionReason;
    private LocalDateTime submittedAt;
    private User createdBy;
}
