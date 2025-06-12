package in.zidiolearning.Notificationcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.zidiolearning.EmailNotificationdto.EmailNotificationDto;
import in.zidiolearning.EmailNotificationdto.WebSocketNotificationDto;
import in.zidiolearning.NotificationService.NotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	// Send Email Notification
	@PostMapping("/email")
	public ResponseEntity<String> sendEmail(@RequestBody EmailNotificationDto emailDto) {
		notificationService.sendEmail(emailDto.toEmail, emailDto.subject, emailDto.body);
		return ResponseEntity.ok("Email sent successfully");
	}

	// Send WebSocket Notification (just store)
	@PostMapping("/websocket")
	public ResponseEntity<String> sendWebSocket(@RequestBody WebSocketNotificationDto wsDto) {
		notificationService.sendWebSocket(wsDto.userId, wsDto.message);
		return ResponseEntity.ok("WebSocket notification stored successfully");
	}
}
