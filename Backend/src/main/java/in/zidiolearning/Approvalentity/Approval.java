package in.zidiolearning.Approvalentity;

import java.time.LocalDateTime;
import java.util.Set;

import in.zidiolearning.Entity.Role;
import in.zidiolearning.Entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approval")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Approval 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Long expenseid;
	
	private Long approvalid;
	
	private String level;
	
	private String decision;
	
	private LocalDateTime decisionDate;
	
	private String remarks;
	
}
