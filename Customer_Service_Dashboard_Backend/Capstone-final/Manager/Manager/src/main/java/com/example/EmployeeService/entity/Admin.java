package com.example.EmployeeService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Admin {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long empId;
	@Column(unique = true, nullable = false)
    private String userName;
	
    private String password;
 
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
 
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
	@Override
	public String toString() {
		return "Admin [empId=" + empId + ", userName=" + userName + ", password=" + password + "]";
	}
}
