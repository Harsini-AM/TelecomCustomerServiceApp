package com.example.EmployeeService.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Representative {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long empId;
	private String fName;
	private String lName;
	@Column(unique=true)
	private long phone_no;
	private String city;
	private String state;
	private String userName;
	private String password;
	private String domain;
	private long NumberOfTickets;
	private boolean PasswordChanged;
	
	@JsonBackReference
	@ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    // Getters and Setters

    
    public Representative() {
    	
    }

    public Representative(long empId, String fName, String lName, long phone_no, String city, String state,
			String username, String password, String domain, long NumberOfTickets, boolean passwordChanged,
			Manager manager) {
		super();
		this.empId = empId;
		this.fName = fName;
		this.lName = lName;
		this.phone_no = phone_no;
		this.city = city;
		this.state = state;
		this.userName = username;
		this.password = password;
		this.domain = domain;
		this.NumberOfTickets = NumberOfTickets;
		PasswordChanged = passwordChanged;
		this.manager = manager;
	}
    public Manager getManager() {
        return manager;
    }

	public void setManager(Manager manager) {
        this.manager = manager;
    }
	
	public long getEmpId() {
		return empId;
	}


	public void setEmpId(long repId) {
		this.empId = repId;
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

	public long getPhone_no() {
		return phone_no;
	}


	public void setPhone_no(long phone_no) {
		this.phone_no = phone_no;
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


//	public int getManagerId() {
//		return managerId;
//	}
//
//
//	public void setManagerId(int managerId) {
//		this.managerId = managerId;
//	}


	public String getDomain() {
		return domain;
	}


	public void setDomain(String domain) {
		this.domain = domain;
	}
	


	public long getNumberOfTickets() {
		return NumberOfTickets;
	}
	public void setNumberOfTickets(long numberOfTickets) {
		NumberOfTickets = numberOfTickets;
	}
	public String getUsername() {
		return userName;
	}


	public void setUsername(String username) {
		this.userName = username;
	}


	public boolean getPasswordChanged() {
		return PasswordChanged;
	}


	public void setPasswordChanged(boolean passwordChanged) {
		PasswordChanged = passwordChanged;
	}

	@Override
	public String toString() {
		return "Representative [repId=" + empId + ", fName=" + fName + ", lName=" + lName +  ", phone_no=" + phone_no + ", city=" + city + ", state=" + state + ", username=" + userName
				+ ", password=" + password + ", managerId=" + manager.getEmpId() + ", domain=" + domain + "no_of_tickets=" + NumberOfTickets + ", PasswordChanged=" + PasswordChanged + "]";
	}



}
