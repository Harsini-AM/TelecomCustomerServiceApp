package com.example.Customer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Customer.entity.FeedBack;
 
public interface FeedbackRepo extends JpaRepository<FeedBack, Long> {
 
	@Query("SELECT (AVG(u.rating)/5)*100 FROM FeedBack u")         
	Double getCustomerRating();
}
