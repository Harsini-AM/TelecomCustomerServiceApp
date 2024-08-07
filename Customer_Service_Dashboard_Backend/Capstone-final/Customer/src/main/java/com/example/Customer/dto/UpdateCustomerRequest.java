package com.example.Customer.dto;

public class UpdateCustomerRequest {
    private String fName;
    private String lName;
    private String city;
    private String state;
    private long phone_no;
    private String planType;
    private String planName;
    private String planDescription;
    
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
	public UpdateCustomerRequest(String fName, String lName, String city, String state, long phone_no, String planType,
			String planName, String planDescription) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.city = city;
		this.state = state;
		this.phone_no = phone_no;
		this.planType = planType;
		this.planName = planName;
		this.planDescription = planDescription;
	}
	@Override
	public String toString() {
		return "UpdateCustomerRequest [fName=" + fName + ", lName=" + lName + ", city=" + city + ", state=" + state
				+ ", phone_no=" + phone_no + ", planType=" + planType + ", planName=" + planName + ", planDescription="
				+ planDescription + "]";
	}
}