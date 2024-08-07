package com.example.Customer.dto;

public class RegisterCustomerResponse {
	private long id;
    private String userName;
    private String message;
    private String role;
 
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public RegisterCustomerResponse(long id,String userName, String message, String role) {
		super();
		this.id=id;
		this.userName = userName;
		this.message = message;
		this.role = role;
	}
	@Override
	public String toString() {
		return "RegisterCustomerResponse [userName=" + userName + ", message=" + message + ", role=" + role + "]";
	}
}
