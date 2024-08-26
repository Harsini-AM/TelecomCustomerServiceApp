package com.example.EmployeeService.dto;

public class ManagerDeletionRequest {
	private String oldManagerId;
    private String newManagerId;

    // Getters and setters
    public String getOldManagerId() {
        return oldManagerId;
    }

    public void setOldManagerId(String oldManagerId) {
        this.oldManagerId = oldManagerId;
    }

    public String getNewManagerId() {
        return newManagerId;
    }

    public void setNewManagerId(String newManagerId) {
        this.newManagerId = newManagerId;
    }
}
