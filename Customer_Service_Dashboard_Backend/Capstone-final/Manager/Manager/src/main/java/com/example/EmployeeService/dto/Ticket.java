package com.example.EmployeeService.dto;

import java.time.LocalDateTime;

public class Ticket {	
	
	 private long TicketId;
	
	 private String domain;
	 private String description;
	 private long empId;
	 private long managerId;
	 private String empUserName;
	 private String status;
	 private LocalDateTime createdAt;
	 private float responseTime;
	 private float resolutionTime;
	
	public long getTicketId() {
		return TicketId;
	}
	public void setTicketId(long ticketId) {
		TicketId = ticketId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public float getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(float responseTime) {
		this.responseTime = responseTime;
	}
	public float getResolutionTime() {
		return resolutionTime;
	}
	public void setResolutionTime(float resolutionTime) {
		this.resolutionTime = resolutionTime;
	}
	
	public String getEmpUserName() {
		return empUserName;
	}
 
	public void setEmpUserName(String empUserName) {
		this.empUserName = empUserName;
	}
	@Override
	public String toString() {
		return "Ticket [TicketId=" + TicketId + ", domain=" + domain + ", description=" + description + ", empId="
				+ empId + ", managerId=" + managerId + ", empUserName=" + empUserName + ", status=" + status
				+ ", createdAt=" + createdAt + ", responseTime=" + responseTime + ", resolutionTime=" + resolutionTime
				+ "]";
	}
 
 
		
}
 