package in.zidiolearning.analytics.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryExpenseDTO {
    private String category;
    private BigDecimal totalAmount;
}
