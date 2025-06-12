package in.zidiolearning.EmailNotificationrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.zidiolearning.Emailnotification.EmailNotification;

@Repository
public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {
}