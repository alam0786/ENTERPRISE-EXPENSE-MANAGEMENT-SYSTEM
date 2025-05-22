package in.zidiolearning.JwtFilter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{

	private final JwtTokenProvider jwtTokenProvider;
	
	public OAuth2LoginSuccessHandler(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider=jwtTokenProvider;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response
			,Authentication authentication)throws IOException, java.io.IOException{
		OAuth2User oAuth2User=(OAuth2User)authentication.getPrincipal();
		String email=oAuth2User.getAttribute("email");
		
		// Generate JWT token for the OAuth user
		UserDetails userDetails=(UserDetails) authentication.getPrincipal();
		String token=jwtTokenProvider.generateToken(userDetails);
		
		// Return token in response header or redirect with token
		response.addHeader("Authorization", "Bearer "+token);
		
		// Redirect or write response
		response.sendRedirect("/loginSuccess?Token="+token);
	}
	
}
