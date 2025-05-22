package in.zidiolearning.JwtFilter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.zidiolearning.Services.CustomUserDetailsService;

import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{

	private final JwtTokenProvider jwtTokenProvider;
	
	private final CustomUserDetailsService customUserDetailsService;
	
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,CustomUserDetailsService customUserDetailsService) {
		this.jwtTokenProvider=jwtTokenProvider;
		this.customUserDetailsService=customUserDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token=parseJwt(request);
		if(token!=null && jwtTokenProvider.validateJwtToken(token)) {
			String username=jwtTokenProvider.getUsernameFromjwt(token);
			UserDetails userDetails=customUserDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAouth=request.getHeader("Authorization");
		if(StringUtils.hasText(headerAouth) && headerAouth.startsWith("Bearer ")) {
			return headerAouth.substring(7);
		}
		return null;
	}
	
}
