package in.zidiolearning.Dto;


import lombok.Data;

@Data
public class AuthResponse {
	private String message;
    private String token;
    private String name;
    private String email;
    private String role;
	public AuthResponse(String message, String token,String name) {
		super();
		this.message = message;
		this.token = token;
		this.name=name;
	}
	
	public AuthResponse(String message, String token, String name, String email, String role) {
		super();
		this.message = message;
		this.token = token;
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public AuthResponse(String message, String token) {
		super();
		this.message = message;
		this.token = token;
	}
    
    
   
}
