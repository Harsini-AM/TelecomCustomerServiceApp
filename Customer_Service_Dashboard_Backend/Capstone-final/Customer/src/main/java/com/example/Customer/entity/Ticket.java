package com.example.Customer.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long TicketId;
	
	 private String domain;
	 private String description;
	 private long empId;
	 private long managerId;
	 private String status;
	 private LocalDateTime createdAt;
	 private float responseTime;
	 private float resolutionTime;
	 private
	 String empUserName;
	 
	 public String getEmpUserName() {
		return empUserName;
	}

	public void setEmpUserName(String empUserName) {
		this.empUserName = empUserName;
	}

	@JsonBackReference
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "customer_id")
	 private Customer customer;
	  
	// Other getters and setters

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
	 
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

	@Override
	public String toString() {
		return "Ticket [TicketId=" + TicketId + ", domain=" + domain + ", description=" + description + ", empId="
				+ empId + ", managerId=" + managerId + ", status=" + status + ", createdAt=" + createdAt
				+ ", responseTime=" + responseTime + ", resolutionTime=" + resolutionTime + ", customer=" + customer
				+ "]";
	}
		
}
