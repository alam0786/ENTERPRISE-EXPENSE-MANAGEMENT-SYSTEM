package in.zidiolearning.ExpenseDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import in.zidiolearning.ExpenseEntity.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExpenseRequest {
    private String title;
    private String description;
    private Double amount;
    private LocalDate expenseDate;
    private Category category;
}
