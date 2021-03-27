package com.tr.app.common.enums;

public enum UserRole {
	USER,
	ADMIN;
	
	public static UserRole DEFAULT=UserRole.USER;
	
	private UserRole() {
		// TODO Auto-generated constructor stub
	}
	
	
	public String getDisplayName() {
		return this.name();
	}
	
	
}
