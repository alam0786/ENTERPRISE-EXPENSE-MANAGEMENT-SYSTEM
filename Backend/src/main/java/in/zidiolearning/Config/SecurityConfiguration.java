package in.zidiolearning.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.zidiolearning.JwtFilter.JwtAuthenticationFilter;
import in.zidiolearning.JwtFilter.OAuth2LoginSuccessHandler;
import in.zidiolearning.Services.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration 
{

	private final JwtAuthenticationFilter jwtAuthfilter;
	
	private final OAuth2UserService<OAuth2UserRequest,OAuth2User> oAuth2UserService;
	
	private final CustomOAuth2UserService customOAth2UserService;
	
	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessfulHandler;
	
	public SecurityConfiguration(JwtAuthenticationFilter jwtAuthfilter,OAuth2UserService<OAuth2UserRequest,OAuth2User> oAuth2UserService,
			CustomOAuth2UserService customOAth2UserService,OAuth2LoginSuccessHandler oAuth2LoginSuccessfulHandler) {
		this.jwtAuthfilter=jwtAuthfilter;
		this.oAuth2UserService=oAuth2UserService;
		this.customOAth2UserService=customOAth2UserService;
		this.oAuth2LoginSuccessfulHandler=oAuth2LoginSuccessfulHandler;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf->csrf.disable())
			.authorizeHttpRequests(auth->auth
					.requestMatchers("/api/auth/**","/auth2/**").permitAll()
					.anyRequest().authenticated()
					)
			.oauth2Login(oauth2->oauth2
					.userInfoEndpoint(userinfo->userinfo
							.userService(customOAth2UserService))
					.successHandler(oAuth2LoginSuccessfulHandler))
			.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtAuthfilter, UsernamePasswordAuthenticationFilter.class);
		 return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
