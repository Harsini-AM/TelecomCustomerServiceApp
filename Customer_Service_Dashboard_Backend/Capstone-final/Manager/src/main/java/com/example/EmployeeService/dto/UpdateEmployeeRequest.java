package com.example.EmployeeService.dto;

public class UpdateEmployeeRequest {
    private String fName;
    private String lName;
    private String city;
    private String state;
    private long phone_no;
    
	
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
	public void setPhone_no(long phone_no) {
		this.phone_no = phone_no;
	}
	public UpdateEmployeeRequest(String fName, String lName, String city, String state, long phone_no) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.city = city;
		this.state = state;
		this.phone_no = phone_no;
	}
	@Override
	public String toString() {
		return "UpdateEmployeeRequest [fName=" + fName + ", lName=" + lName + ", city=" + city + ", state=" + state
				+ ", phone_no=" + phone_no + "]";
	}   
}

