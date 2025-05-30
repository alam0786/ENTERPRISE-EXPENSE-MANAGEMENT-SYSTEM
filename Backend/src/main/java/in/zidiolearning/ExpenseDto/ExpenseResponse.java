package in.zidiolearning.ExpenseDto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

import in.zidiolearning.ExpenseEntity.Category;

@Data
@Builder
public class ExpenseResponse {
    private Long id;
    private String title;
    private String description;
    private Double amount;
    private LocalDate expenseDate;
    private Category category;
    private String invoiceFileName;
}
