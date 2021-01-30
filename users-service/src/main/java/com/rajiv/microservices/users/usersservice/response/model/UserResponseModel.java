package com.rajiv.microservices.users.usersservice.response.model;

import java.util.List;

public class UserResponseModel {
	private String firstName;
	private String lastName;
	private String email;
	private String userId;
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

	public List<AlbumsResponseModel> getAlbumsRes() {
		return albumsRes;
	}

	public void setAlbumsRes(List<AlbumsResponseModel> albumsRes) {
		this.albumsRes = albumsRes;
	}
	
}
