package com.staekframework.test.User;

import java.util.Objects;

public class User {

	private final String id;
	private final String name;
	private final String password;

	private final String price;

	public User(String id, String name, String password, String price) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.price = price;
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

	public String getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", price=" + price +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(price, user.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, password, price);
	}
}
