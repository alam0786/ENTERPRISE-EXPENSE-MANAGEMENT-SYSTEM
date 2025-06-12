package in.zidiolearning.ExpenseController;

import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import in.zidiolearning.ExpenseEntity.Expense;
import in.zidiolearning.expenseService.ExpenseService;
import in.zidiolearning.Enum.Status;

@RestController
@RequestMapping("/api/expenses")
public class Expensecontroller {

	@Autowired
	private ExpenseService expenseService;

	@PostMapping("/create")
	public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
		return ResponseEntity.ok(expenseService.createExpense(expense));
	}

	@GetMapping("/user/{email}")
	public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable String email) {
		return ResponseEntity.ok(expenseService.getExpensesByUser(email));
	}

	@PutMapping("/status/{id}")
	public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam Status status) {

		expenseService.updateStatus(id, status);
		return ResponseEntity.ok("Expense status updated successfully");
	}
}
