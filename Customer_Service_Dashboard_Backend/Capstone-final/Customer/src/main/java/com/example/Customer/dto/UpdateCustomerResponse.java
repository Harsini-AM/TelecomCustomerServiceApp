package com.example.Customer.dto;

public class UpdateCustomerResponse {
	private long id;
    private String userName;
    private String fName;
    private String lName;
    private String city;
    private String state;
    private long phone_no;
    private String planType;
    private String planName;
    private String planDescription;

    // Constructor

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

	public UpdateCustomerResponse(long id, String userName, String fName, String lName, String city, String state,
			long phone_no, String planType, String planName, String planDescription) {
		super();
		this.id = id;
		this.userName = userName;
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
		return "UpdateCustomerResponse [id=" + id + ", userName=" + userName + ", fName=" + fName + ", lName=" + lName
				+ ", city=" + city + ", state=" + state + ", phone_no=" + phone_no + ", planType=" + planType
				+ ", planName=" + planName + ", planDescription=" + planDescription + "]";
	}   
}

