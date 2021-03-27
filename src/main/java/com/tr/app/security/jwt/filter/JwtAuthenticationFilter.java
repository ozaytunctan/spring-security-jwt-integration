package com.tr.app.security.jwt.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.tr.app.security.jwt.manager.JwtTokenManager;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

	private JwtTokenManager tokenManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenManager tokenManager) {
		super(authenticationManager);
		this.tokenManager = tokenManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			String token = this.tokenManager.getHeaderToken(request);

			//token yok ise spring security bırakılıyor.
			if (!StringUtils.hasText(token)) {
				chain.doFilter(request, response);
				return;
			}

			//token var ve valid ise
			if (tokenManager.validateToken(token)) {

				String principal = tokenManager.getUsernameFromToken(token);
				
				List<SimpleGrantedAuthority> roles = tokenManager.getRolesFromToken(token);

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						principal, null, roles);

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
				chain.doFilter(request, response);
			} else {
				
				if (isBasicAuthRequest(request)) {
					SecurityContextHolder.clearContext();
					chain.doFilter(request, response);
					return;
				}
				
				prepareInvalidAuthResponse(response);
				
				return;
			}

		} catch (ExpiredJwtException | BadCredentialsException ex) {
			prepareInvalidAuthResponse(response);
		} catch (Exception ex) {
			prepareInvalidAuthResponse(response);
		}
	}

	private boolean isBasicAuthRequest(HttpServletRequest request) {
		String data = request.getHeader(HttpHeaders.AUTHORIZATION);
		return (StringUtils.hasText(data) && data.startsWith("Basic "));
	}

	private void prepareInvalidAuthResponse(HttpServletResponse response) {
		SecurityContextHolder.clearContext();
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("INVALID-AUTH", "Invalid authentication attempt!");
	}

}
