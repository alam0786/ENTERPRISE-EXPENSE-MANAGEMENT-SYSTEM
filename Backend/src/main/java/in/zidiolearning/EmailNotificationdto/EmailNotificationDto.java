package in.zidiolearning.EmailNotificationdto;

import lombok.Data;

@Data
public class EmailNotificationDto {
    public String toEmail;
    public String subject;
    public String body;
}
