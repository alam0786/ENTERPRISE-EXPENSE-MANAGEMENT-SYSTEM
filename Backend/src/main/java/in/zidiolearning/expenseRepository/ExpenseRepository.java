package in.zidiolearning.expenseRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.zidiolearning.ExpenseEntity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	List<Expense> findByEmail(String email);

}