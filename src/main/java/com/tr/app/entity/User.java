package com.tr.app.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tr.app.common.enums.UserRole;
import com.tr.app.entity.base.BaseEntity;

@Entity
@Table(name = "kullanici")
@SequenceGenerator(name = "idGenerator", allocationSize = 60, initialValue = 1, sequenceName = "SQ_USER")
public class User extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 898415776539706658L;

	private String email;

	private String userName;

	private String password;

	private String role = UserRole.DEFAULT.getDisplayName();

	private String firstName;

	private String lastName;

	public User() {
		super(0l);
	}

	public User(Long id) {
		super(id);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

}
