package in.zidiolearning.expenseService;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import in.zidiolearning.Enums.ExpenseNotification;
import in.zidiolearning.Enums.ExpenseStatus;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyExpenseStatusChange(Long expenseId, ExpenseStatus status) {
        messagingTemplate.convertAndSend("/topic/expenses", new ExpenseNotification(expenseId, status));
    }
}
