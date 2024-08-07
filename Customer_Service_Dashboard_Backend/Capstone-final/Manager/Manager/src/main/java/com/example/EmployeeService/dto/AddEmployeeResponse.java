package com.example.EmployeeService.dto;

public class AddEmployeeResponse {
	private String message;
	private String userName;
	private String password;
	
	
	
	
	public AddEmployeeResponse(String message, String userName, String password) {
		super();
		this.message = message;
		this.userName = userName;
		this.password = password;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "AddEmployeeResponse [userName=" + userName + ", password=" + password + "]";
	}
	
	

}
