package com.rajiv.microservices.users.usersservice.dto;

import java.io.Serializable;
import java.util.List;

import com.rajiv.microservices.users.usersservice.response.model.AlbumsResponseModel;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String email;
	private String userId;
	private String encrptPassword;
	private String password;
	private List<AlbumsResponseModel> albumsRes;

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

	public String getEncrptPassword() {
		return encrptPassword;
	}

	public void setEncrptPassword(String encrptPassword) {
		this.encrptPassword = encrptPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<AlbumsResponseModel> getAlbumsRes() {
		return albumsRes;
	}

	public void setAlbumsRes(List<AlbumsResponseModel> albumsRes) {
		this.albumsRes = albumsRes;
	}
}
