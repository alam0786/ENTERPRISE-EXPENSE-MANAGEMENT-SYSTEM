package in.zidiolearning.NotificationService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.zidiolearning.EmailNotificationrepository.EmailNotificationRepository;
import in.zidiolearning.EmailNotificationrepository.WebSocketNotificationRepository;
import in.zidiolearning.Emailnotification.EmailNotification;
import in.zidiolearning.Emailnotification.WebSocketNotification;

@Service
public class NotificationService {
	@Autowired
	private EmailNotificationRepository emailRepo;
	@Autowired
	private WebSocketNotificationRepository webSocketRepo;

	public void sendEmail(String to, String subject, String body) {
		EmailNotification email = new EmailNotification();
		email.setToEmail(to);
		email.setSubject(subject);
		email.setBody(body);
		email.setSentAt(LocalDateTime.now());
		email.setSuccess(true); // simulate
		emailRepo.save(email);
	}

	public void sendWebSocket(Long userId, String message) {
		WebSocketNotification note = new WebSocketNotification();
		note.setUserId(userId);
		note.setMessage(message);
		note.setSentAt(LocalDateTime.now());
		webSocketRepo.save(note);
	}
}