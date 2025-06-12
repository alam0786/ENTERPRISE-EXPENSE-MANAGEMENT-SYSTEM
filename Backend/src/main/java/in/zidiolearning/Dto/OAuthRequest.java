package in.zidiolearning.Dto;

import lombok.Data;

@Data
public class OAuthRequest {
    public String provider;
    public String providerId;
    public String name;
    public String email;
    public String role;
}
