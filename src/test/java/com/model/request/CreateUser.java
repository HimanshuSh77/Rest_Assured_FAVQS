package com.model.request;

public class CreateUser {

	private User user;

	public CreateUser(String login, String email, String password) {
		this.user = new User(login, email, password);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static class User {
		private String login;
		private String email;
		private String password;

		public User() {
		}

		public User(String login, String email, String password) {
			this.login = login;
			this.email = email;
			this.password = password;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
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
	}
}