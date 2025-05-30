package in.zidiolearning.expenseRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.zidiolearning.ExpenseEntity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
