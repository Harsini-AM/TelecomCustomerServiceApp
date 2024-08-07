package com.example.EmployeeService.dto;

public class RepresentativeDto {
	private long empId;
    private String fName;
    private String lName;
    private long phone_no;
    private String city;
    private String state;
    private String userName;
    private String password;
    private String domain;
    private long no_of_tickets;
    private boolean passwordChanged;
    private long managerId;
    private String role;
    
    public RepresentativeDto() {
    	
    }
    
	public RepresentativeDto(long empId, String fName, String lName, long phone_no, String city, String state, String userName,
			String password, String domain, long no_of_tickets, boolean passwordChanged, long managerId) {
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
		this.no_of_tickets = no_of_tickets;
		this.passwordChanged = passwordChanged;
		this.managerId = managerId;
	}

	
	public RepresentativeDto(long empId, String fName, String lName, long phone_no, String city, String state,
			String userName, String password, String domain, long no_of_tickets, boolean passwordChanged,
			long managerId, String role) {
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
		this.no_of_tickets = no_of_tickets;
		this.passwordChanged = passwordChanged;
		this.managerId = managerId;
		this.role = role;
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
	public String getUsername() {
		return userName;
	}
	public void setUsername(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getNo_of_tickets() {
		return no_of_tickets;
	}
	public void setNo_of_tickets(long no_of_tickets) {
		this.no_of_tickets = no_of_tickets;
	}
	public boolean isPasswordChanged() {
		return passwordChanged;
	}
	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public long getEmpId() {
		return empId;
	}

	public void setEmpId(long empId) {
		this.empId = empId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "RepresentativeDto [empId=" + empId + ", fName=" + fName + ", lName=" + lName + ", phone_no=" + phone_no
				+ ", city=" + city + ", state=" + state + ", userName=" + userName + ", password=" + password
				+ ", domain=" + domain + ", no_of_tickets=" + no_of_tickets + ", passwordChanged=" + passwordChanged
				+ ", managerId=" + managerId + ", role=" + role + "]";
	}

	
    
	

   
}