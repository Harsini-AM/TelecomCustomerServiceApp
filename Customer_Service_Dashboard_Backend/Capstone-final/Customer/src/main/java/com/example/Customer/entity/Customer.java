package com.example.Customer.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customer {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long id;
	    
	@Column(unique = true, nullable = false)
	private String userName;
	
    private String password;
    private String fName;
    private String lName;
    private String city;
    private String state;
    private Long phone_no;
    private String planType;
    private String planName;
    private String planDescription;
    
    
    @JsonManagedReference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    // Other getters and setters

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public long getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(Long phone_no) {
		this.phone_no = phone_no;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanDescription() {
		return planDescription;
	}
	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", userName=" + userName + ", password=" + password + ", fName=" + fName
				+ ", lName=" + lName + ", city=" + city + ", state=" + state + ", phone_no=" + phone_no + ", planType="
				+ planType + ", planName=" + planName + ", planDescription=" + planDescription + ", tickets=" + tickets
				+ "]";
	}
	
	
}
