package com.narasimham.springbootsecuritydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	public AuthenticationSuccessHandler csuccessHandler;
	
	public SecurityConfiguration() {
		super();
	}

	@Autowired
	public SecurityConfiguration(AuthenticationSuccessHandler successHandler) {
		super();
		this.csuccessHandler = successHandler;
	}

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		System.out.println("in DaoAuthenticationProvider");
		return daoAuthenticationProvider;
	}

	//need two more methods
	//authentication manager builder

	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		System.out.println("in SecurityFilterChain...");
		http

		//CSRF Protection
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(
				(authorize) ->authorize

				//White-list URLS
				.requestMatchers("/").permitAll()
				.requestMatchers("/**").permitAll()
				
				//Authorize Requests
				//ROLE_USER
				.requestMatchers("/tenant/**").hasAuthority("ROLE_USER")
				//.requestMatchers("/tenant/index").hasAnyAuthority("ROLE_USER")
				
				//ROLE_STAFF
				.requestMatchers("/staff/**").hasAuthority("ROLE_STAFF")
				
				//ROLE_ADMIN
				.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated()
				);

		http.formLogin(
				form -> form
				
				.loginPage("/login")
				.loginProcessingUrl("/login")
				// Need to remove defaultsuccess url for role based access landing page
				//.defaultSuccessUrl("/tenant/index")
				
				//add successhandler
				.successHandler(csuccessHandler)
				.failureUrl("/login-fail")
				.permitAll()
				
				);

		http.authenticationProvider(daoAuthenticationProvider());
		
		http.logout(
				logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.permitAll());

//		http.authenticationProvider(daoAuthenticationProvider());
		
		return http.build();
	}

//	private AuthenticationSuccessHandler successHandler() {
//		SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
//		simpleUrlAuthenticationSuccessHandler.setDefaultTargetUrl("/");
//		return simpleUrlAuthenticationSuccessHandler;
//	}

	@Bean
	public SecurityContextRepository securityContextRepository() {
		return new DelegatingSecurityContextRepository(
				new RequestAttributeSecurityContextRepository(),
				new HttpSessionSecurityContextRepository()
				);
	}

	@Bean
	WebSecurityCustomizer customizeWebSecurity() {
		return (web)-> web.ignoring().requestMatchers("/img/**","/css/**","/js/**");
	}
}