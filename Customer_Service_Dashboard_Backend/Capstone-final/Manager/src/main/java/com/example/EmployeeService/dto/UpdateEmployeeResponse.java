package com.example.EmployeeService.dto;

public class UpdateEmployeeResponse {
	private long empId;
    private String userName;
    private String fName;
    private String lName;
    private String city;
    private String state;
    private long phone_no;
	
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public long getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(Long phone_no) {
		this.phone_no = phone_no;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public UpdateEmployeeResponse(long empId, String userName, String fName, String lName, String city, String state,
			long phone_no) {
		super();
		this.empId = empId;
		this.userName = userName;
		this.fName = fName;
		this.lName = lName;
		this.city = city;
		this.state = state;
		this.phone_no = phone_no;
	}
	@Override
	public String toString() {
		return "UpdateManagerResponse [empId=" + empId + ", userName=" + userName + ", fName=" + fName + ", lName="
				+ lName + ", city=" + city + ", state=" + state + ", phone_no=" + phone_no + "]";
	}
}

