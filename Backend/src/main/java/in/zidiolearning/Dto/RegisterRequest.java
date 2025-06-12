package in.zidiolearning.Dto;

import lombok.Data;

@Data
public class RegisterRequest {
	private String name;
	private String email;
	private String password;
	public String roleName; // e.g. "ADMIN", "MANAGER", "EMPLOYEE"
}
