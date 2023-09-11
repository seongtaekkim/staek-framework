package com.staekframework.test.User;

public class User {

	private final String id;
	private final String name;
	private final String password;

	public User(String id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}


	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
