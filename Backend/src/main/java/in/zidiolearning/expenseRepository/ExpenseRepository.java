package in.zidiolearning.expenseRepository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.zidiolearning.ExpenseEntity.Expense;
import in.zidiolearning.analytics.dto.CategoryExpenseDTO;
import in.zidiolearning.analytics.dto.MonthlyExpenseDTO;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
		@Query("SELECT e.category, SUM(e.amount) FROM Expense e WHERE YEAR(e.expenseDate) = :year GROUP BY e.category")
		List<MonthlyExpenseDTO> findMonthlyTrends(@Param("year") int year);

		@Query("SELECT e.category, SUM(e.amount) FROM Expense e WHERE YEAR(e.expenseDate) = :year GROUP BY e.category")
		List<CategoryExpenseDTO> findCategoryBreakdown(@Param("year") int year);

	
}
