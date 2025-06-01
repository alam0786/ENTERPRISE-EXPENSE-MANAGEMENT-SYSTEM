package in.zidiolearning.ExpenseDto;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationPublisher {

    private final SimpMessagingTemplate template;

    public void publishStatusChange(Long expenseId, String status) {
        StatusMessage message = new StatusMessage(expenseId, status);
        template.convertAndSend("/topic/status", message);
    }
}
