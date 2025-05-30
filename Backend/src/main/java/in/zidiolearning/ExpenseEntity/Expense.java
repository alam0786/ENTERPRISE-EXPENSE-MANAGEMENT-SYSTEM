package in.zidiolearning.ExpenseEntity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    
}
