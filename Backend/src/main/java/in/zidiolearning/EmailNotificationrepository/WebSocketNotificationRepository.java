package in.zidiolearning.EmailNotificationrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.zidiolearning.Emailnotification.WebSocketNotification;

@Repository
public interface WebSocketNotificationRepository extends JpaRepository<WebSocketNotification, Long> {
}