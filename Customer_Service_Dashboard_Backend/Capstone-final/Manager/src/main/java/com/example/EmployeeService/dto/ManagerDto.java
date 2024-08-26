package com.example.EmployeeService.dto;

public class ManagerDto {
	private long managerId;
	private long empId;
	private String userName;
	private String fName;
	private String lName;
	private String city;
	private String state;
	private long phone_no;
	private String domain;
	private String password;
	private boolean PasswordChanged;
	
	
	
	public ManagerDto(long empId, String userName, String fName, String lName, String city, String state,
			long phone_no, String domain, String password) {
		super();
		this.empId = empId;
		this.userName = userName;
		this.fName = fName;
		this.lName = lName;
		this.city = city;
		this.state = state;
		this.phone_no = phone_no;
		this.domain = domain;
		this.password = password;
	}
	
	

	



	public long getManagerId() {
		return managerId;
	}
	public void setManagerId(long managerId) {
		this.managerId = managerId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getFirstName() {
		return fName;
	}
	public void setFirstName(String firstName) {
		this.fName = firstName;
	}
	public String getLastName() {
		return lName;
	}
	public void setLastName(String lastName) {
		this.lName = lastName;
	}
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
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
	
	public boolean isPasswordChanged() {
		return PasswordChanged;
	}



	public void setPasswordChanged(boolean passwordChanged) {
		PasswordChanged = passwordChanged;
	}



	@Override
	public String toString() {
		return "ManagerDto [managerId=" + managerId + ", empId=" + empId + ", userName=" + userName + ", fName=" + fName
				+ ", lName=" + lName + ", city=" + city + ", state=" + state + ", phone_no=" + phone_no + ", domain="
				+ domain + ", password=" + password + "]";
	}
	
	
	
	
}
