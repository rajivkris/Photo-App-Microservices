package com.rajiv.microservices.users.usersservice.request.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRequestModel {

	@NotBlank
	@Size(min = 2, message = "First name should have a minimum of length 2")
	private String firstName;

	@NotBlank
	@Size(min = 2, message = "First name should have a minimum of length 2")
	private String lastName;

	@NotBlank
	@Email
	private String email;

	private String userId;

	@NotBlank
	@Size(min = 8, max = 16, message = "The password must have a length between 8 and 16")
	private String password;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
