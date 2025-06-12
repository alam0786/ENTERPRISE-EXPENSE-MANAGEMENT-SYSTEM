package in.zidiolearning.ExpenseEntity;

import java.time.LocalDate;

import in.zidiolearning.Enum.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "expense")
@Data
public class Expense {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	
	private Double amount;
	private String category;
	private String description;

	private String email;
	private LocalDate date;
	private String filepath;

	@Enumerated(EnumType.STRING)
	private Status status;
}
