package com.fithub.smart.model.dto;

public class User {
	private int userId;
	private String id;
	private String name;
	private String password;
	private String email;
	private String phoneNum;
	private String img;
	
	public User() {
	}
	
	public User(int userId, String id, String name, String password, String email, String phoneNum, String img) {
		super();
		this.userId = userId;
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.phoneNum = phoneNum;
		this.img = img;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", id=" + id + ", name=" + name + ", password=" + password + ", email="
				+ email + ", phoneNum=" + phoneNum + ", img=" + img + "]";
	}

	
	
}
