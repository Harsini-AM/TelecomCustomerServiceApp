package com.example.EmployeeService.entity;

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
public class Manager {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long empId;
	private String fName;
	private String lName;
	private long phone_no;
	private String city;
	private String state;
	private String userName;
	private String password;
	private String domain;
	private boolean PasswordChanged;

	@JsonManagedReference
	 @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
	   private List<Representative> representatives;

	    public List<Representative> getRepresentatives() {
	        return representatives;
	    }

	    public void setRepresentatives(List<Representative> representatives) {
	        this.representatives = representatives;
	  }
	    
	    
	   public Manager() {}
	
	
	public Manager(long empId, String fName, String lName, long phone_no, String city, String state,
				String userName, String password, String domain, boolean passwordChanged,
				List<Representative> representatives) {
			super();
			this.empId = empId;
			this.fName = fName;
			this.lName = lName;
			this.phone_no = phone_no;
			this.city = city;
			this.state = state;
			this.userName = userName;
			this.password = password;
			this.domain = domain;
			PasswordChanged = passwordChanged;
			this.representatives = representatives;
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
	
	
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public boolean getPasswordChanged() {
		return PasswordChanged;
	}
	public void setPasswordChanged(boolean passwordChanged) {
		PasswordChanged = passwordChanged;
	}

	@Override
	public String toString() {
		return "Manager [EmpId=" + empId + ", fName=" + fName + ", lName=" + lName 
				+ ", phone_no=" + phone_no + ", city=" + city + ", state=" + state + ", userName=" + userName + ", password=" + password + ", domain=" + domain + ", PasswordChanged="
				+ PasswordChanged + "]";
	}
	
	
	
	
	
	

}
