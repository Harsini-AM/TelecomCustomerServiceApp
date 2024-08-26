package com.example.EmployeeService.dto;

public class LoginResponse {
	private long empId;
	private String userName;
    private String message;
    private String role;
    private boolean PasswordChanged;
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
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
	public boolean isPasswordChanged() {
		return PasswordChanged;
	}
	public void setPasswordChanged(boolean passwordChanged) {
		PasswordChanged = passwordChanged;
	}
	public LoginResponse(long empId, String userName, String message, String role, boolean passwordChanged) {
		super();
		this.empId = empId;
		this.userName = userName;
		this.message = message;
		this.role = role;
		PasswordChanged = passwordChanged;
	}
	@Override
	public String toString() {
		return "LoginResponse [empId=" + empId + ", userName=" + userName + ", message=" + message + ", role=" + role
				+ ", PasswordChanged=" + PasswordChanged + "]";
	}   
}
