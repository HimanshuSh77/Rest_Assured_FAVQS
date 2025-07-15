package com.model.response;

public class AccountDetails {

	private String email;
	private int private_favorites_count;
	private String pro_expiration;

	// Getters and Setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPrivate_favorites_count() {
		return private_favorites_count;
	}

	public void setPrivate_favorites_count(int private_favorites_count) {
		this.private_favorites_count = private_favorites_count;
	}

	public String getPro_expiration() {
		return pro_expiration;
	}

	public void setPro_expiration(String pro_expiration) {
		this.pro_expiration = pro_expiration;
	}

}
