package in.zidiolearning.expenseRepository;


import org.springframework.data.jpa.repository.JpaRepository;

import in.zidiolearning.ExpenseEntity.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
