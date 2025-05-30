package in.zidiolearning.ExpenseDto;


import lombok.Data;

import java.time.LocalDate;

import in.zidiolearning.ExpenseEntity.Category;

@Data
public class UpdateExpenseRequest {
    private String title;
    private String description;
    private Double amount;
    private LocalDate expenseDate;
    private Category category;
}
