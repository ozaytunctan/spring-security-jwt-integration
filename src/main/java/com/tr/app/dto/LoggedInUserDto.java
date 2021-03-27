package com.tr.app.dto;

import java.io.Serializable;
import java.util.List;

public class LoggedInUserDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9131088064282586200L;
	
	

	private Long userId;

	private String token;
	
	private String role;

	


	public LoggedInUserDto() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public LoggedInUserDto(Long userId, String token) {
		super();
		this.userId = userId;
		this.token = token;
	}
	
	public LoggedInUserDto(Long userId, String token, String role) {
		super();
		this.userId = userId;
		this.token = token;
		this.role = role;
	}

	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	
	
	
}
