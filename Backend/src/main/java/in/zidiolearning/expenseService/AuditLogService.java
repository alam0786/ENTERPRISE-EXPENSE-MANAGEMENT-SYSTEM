package in.zidiolearning.expenseService;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import in.zidiolearning.ExpenseEntity.AuditLog;
import in.zidiolearning.expenseRepository.AuditLogRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void log(String action, String performedBy, String details) {
        AuditLog log = AuditLog.builder()
                .action(action)
                .performedBy(performedBy)
                .timestamp(LocalDateTime.now())
                .details(details)
                .build();

        auditLogRepository.save(log);
    }
}
