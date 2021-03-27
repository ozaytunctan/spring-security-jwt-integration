package com.tr.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.tr.app.security.entry.point.AuthenticationFailureEntryPoint;
import com.tr.app.security.jwt.filter.JwtAuthenticationFilter;
import com.tr.app.security.jwt.manager.JwtTokenManager;
import com.tr.app.security.provider.UserAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	 private JwtTokenManager tokenManager;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserAuthenticationProvider userAuthenticationProvider = new UserAuthenticationProvider();
		userAuthenticationProvider.setUserDetailsService(userDetailsService);
		userAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		auth.authenticationProvider(userAuthenticationProvider);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http = http.cors().and().csrf().disable();

		http = http//
				.sessionManagement()//
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

		http = http.exceptionHandling()//
				.authenticationEntryPoint(authenticationEntryPoint()).and();

		http = http.authorizeRequests()//
				.antMatchers("/v2/api-docs", "/api-docs", "/configuration/ui", "/configuration/security",
						"/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**")
				.permitAll()//
				.antMatchers(HttpMethod.POST, "/rest/api/v1/auth/login").permitAll()//
				.antMatchers(HttpMethod.POST, "/rest/api/v1/user/register").permitAll()//
				.anyRequest().authenticated().and()//
				.addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenManager));

	}

	@Bean
	@Order(1)
	public AuthenticationEntryPoint authenticationEntryPoint() {
		AuthenticationFailureEntryPoint failureEntryPoint = new AuthenticationFailureEntryPoint();
		return failureEntryPoint;
	}

}
