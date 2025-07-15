package com.model.response;

public class GetUserResponsePayload {

	private String login;
	private String pic_url;
	private int public_favorites_count;
	private int followers;
	private int following;
	private boolean pro;
	private AccountDetails account_details;

	// Getters and Setters
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}

	public int getPublic_favorites_count() {
		return public_favorites_count;
	}

	public void setPublic_favorites_count(int public_favorites_count) {
		this.public_favorites_count = public_favorites_count;
	}

	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	public boolean isPro() {
		return pro;
	}

	public void setPro(boolean pro) {
		this.pro = pro;
	}

	public AccountDetails getAccount_details() {
		return account_details;
	}

	public void setAccount_details(AccountDetails account_details) {
		this.account_details = account_details;
	}

}
