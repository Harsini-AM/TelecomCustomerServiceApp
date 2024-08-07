package com.example.Customer.dto;

public class RegisterCustomerRequest {
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
	public Long getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(Long phone_no) {
		this.phone_no = phone_no;
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

	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public RegisterCustomerRequest() {
		
	}
	public RegisterCustomerRequest(String userName, String password, String fName, String lName, String city,
			String state, Long phone_no, String planName, String planDescription) {
		super();
		this.userName = userName;
		this.password = password;
		this.fName = fName;
		this.lName = lName;
		this.city = city;
		this.state = state;
		this.phone_no = phone_no;
		this.planName = planName;
		this.planDescription = planDescription;
	}
	@Override
	public String toString() {
		return "RegisterCustomerRequest [userName=" + userName + ", password=" + password + ", fName=" + fName
				+ ", lName=" + lName + ", city=" + city + ", state=" + state + ", phone_no=" + phone_no + ", planName="
				+ planName + ", planDescription=" + planDescription + "]";
	}
    
}