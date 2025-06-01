package in.zidiolearning.ExpenseEntity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import in.zidiolearning.Entity.User;
import in.zidiolearning.Enums.ExpenseStatus;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Double amount;

    private LocalDate expenseDate;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String invoiceFileName;  // Stores invoice file name/path

    // If you want, you can add User reference for ownership
    // @ManyToOne(fetch = FetchType.LAZY)
    // private User user;

    @Enumerated(EnumType.STRING)
    private ExpenseStatus status = ExpenseStatus.DRAFT;

    private String rejectionReason;
    private String rejectedBy;                
    private LocalDateTime rejectedAt;

    private LocalDateTime submittedAt;
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @Column(name = "current_approval_level", nullable = false)
    private Integer currentApprovalLevel = 0;

	
	}
    
    

