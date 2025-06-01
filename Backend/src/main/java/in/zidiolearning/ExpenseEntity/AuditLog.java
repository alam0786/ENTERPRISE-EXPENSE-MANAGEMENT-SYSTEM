package in.zidiolearning.ExpenseEntity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action; // e.g., CREATED, APPROVED, REJECTED

    private String performedBy;

    private LocalDateTime timestamp;

    private String details;
}
