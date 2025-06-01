package in.zidiolearning.expenseService;


import in.zidiolearning.analytics.dto.CategoryExpenseDTO;
import in.zidiolearning.analytics.dto.MonthlyExpenseDTO;
import in.zidiolearning.expenseRepository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ExpenseRepository expenseRepository;

    public List<MonthlyExpenseDTO> getMonthlyTrends(int year) {
        return expenseRepository.findMonthlyTrends(year);
    }

    public List<CategoryExpenseDTO> getCategoryBreakdown(int year) {
        return expenseRepository.findCategoryBreakdown(year);
    }
}
