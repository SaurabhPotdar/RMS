package com.cg.rms.dto;

public class Login {
	
	private String role;
	private long userId;
	
	public Login(String role, long userId) {
		super();
		this.role = role;
		this.userId = userId;
	}

	public Login() {
		super();
	}

	public String getRole() {
		return role;
	}

	public long getUserId() {
		return userId;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	

}
