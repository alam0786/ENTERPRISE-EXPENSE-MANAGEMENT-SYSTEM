package in.zidiolearning.Dto;


import in.zidiolearning.Entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
