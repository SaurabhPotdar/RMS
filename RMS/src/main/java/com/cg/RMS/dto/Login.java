package com.cg.rms.dto;

public class Login {
	
	private String role;
	private int userId;
	
	public Login(String role, int userId) {
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
