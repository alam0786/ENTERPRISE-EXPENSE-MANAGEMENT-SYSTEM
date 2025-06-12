package in.zidiolearning.expenseService;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.zidiolearning.Enum.Status;
import in.zidiolearning.ExpenseEntity.Expense;
import in.zidiolearning.expenseRepository.ExpenseRepository;

@Service
public class ExpenseService {
	@Autowired
	private ExpenseRepository expenseRepository;

	public Expense createExpense(Expense expense) {
		expense.setStatus(Status.PENDING);
		return expenseRepository.save(expense);
	}

	public List<Expense> getExpensesByUser(String email) {
		return expenseRepository.findByEmail(email);
	}

	public void updateStatus(Long expenseId, Status status) {
		Expense exp = expenseRepository.findById(expenseId).orElseThrow();
		exp.setStatus(status);
		expenseRepository.save(exp);
	}
}
