package com.example.Customer.dto;

import java.time.LocalTime;

public class Outage {
	
	
	private int outage_id;
	private LocalTime start_time;
	private LocalTime end_time;
	private String location;
	public int getOutage_id() {
		return outage_id;
	}
	public void setOutage_id(int outage_id) {
		this.outage_id = outage_id;
	}
	public LocalTime getStart_time() {
		return start_time;
	}
	public void setStart_time(LocalTime start_time) {
		this.start_time = start_time;
	}
	public LocalTime getEnd_time() {
		return end_time;
	}
	public void setEnd_time(LocalTime end_time) {
		this.end_time = end_time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
 
}
