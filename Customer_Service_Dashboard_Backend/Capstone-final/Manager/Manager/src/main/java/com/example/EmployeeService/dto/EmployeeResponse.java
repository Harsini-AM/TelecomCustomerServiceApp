package com.example.EmployeeService.dto;

public class EmployeeResponse {
	private long empId;
    private long managerId;
    private String userName;
    
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public long getManagerId() {
		return managerId;
	}
	public void setManagerId(long managerId) {
		this.managerId = managerId;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	
    
}
	@Override
	public String toString() {
		return "EmployeeResponse [empId=" + empId + ", managerId=" + managerId + ", userName=" + userName + "]";
	}
}
