package com.example.Customer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
 
@Entity
public class FeedBack {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long feedbackId;
	
	private long rating;
	private long customer_id;
	public long getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(long feedbackId) {
		this.feedbackId = feedbackId;
	}
	public long getRating() {
		return rating;
	}
	public void setRating(long rating) {
		this.rating = rating;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	@Override
	public String toString() {
		return "FeedBack [feedbackId=" + feedbackId + ", rating=" + rating + ", customer_id=" + customer_id + "]";
	}


 
}