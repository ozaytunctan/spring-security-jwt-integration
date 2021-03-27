package com.tr.app.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class CreateUserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7519939290340114775L;

	private Long id;

	@NotNull
	private String email;

	@NotNull
	private String password;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	private String role;

	public CreateUserDto() {
		this(0l);
	}

	public CreateUserDto(Long id) {
		super();
		this.id = id;
	}

	public CreateUserDto(String email, String password) {
		this();
		this.email = email;
		this.password = password;
	}

	public CreateUserDto(String email, String password, String firstName, String lastName) {
		this(email, password);
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public CreateUserDto(Long id, String email, String password, String firstName, String lastName,
			String role) {
		this(email, password, firstName, lastName);
		this.id = id;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
