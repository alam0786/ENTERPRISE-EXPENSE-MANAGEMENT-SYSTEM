package in.zidiolearning.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Month;

@Data
@AllArgsConstructor
public class MonthlyExpenseDTO {
    private Month month;
    private BigDecimal totalAmount;
}
