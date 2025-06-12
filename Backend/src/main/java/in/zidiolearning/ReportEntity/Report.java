package in.zidiolearning.ReportEntity;

import java.time.LocalDate;
import java.util.Set;

import in.zidiolearning.Entity.Role;
import in.zidiolearning.Entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String reportTye; // Monthly, Yearly
	
	private String fileUrl; //PDF or Excel
	
	private LocalDate genrateDate;
	
	private Long userId; // optional, for user-specific reports
	
}
