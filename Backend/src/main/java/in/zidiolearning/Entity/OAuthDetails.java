package in.zidiolearning.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oauth_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String provider; // GOOGLE, GITHUB
	
    @Column(name = "provider_id", nullable = false)
	private String providerId;
   
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
	private User user;
}