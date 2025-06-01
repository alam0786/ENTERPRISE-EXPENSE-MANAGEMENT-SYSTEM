package in.zidiolearning.ExpenseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusMessage {
    private Long expenseId;
    private String status;
}
