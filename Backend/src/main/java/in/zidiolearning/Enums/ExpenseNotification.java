package in.zidiolearning.Enums;

public class ExpenseNotification {
    private Long expenseId;
    private ExpenseStatus status;

    public ExpenseNotification(Long expenseId, ExpenseStatus status) {
        this.expenseId = expenseId;
        this.status = status;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }
}
