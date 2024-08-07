package com.example.Customer.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.example.Customer.entity.FAQs;
 
public interface FAQRepo extends JpaRepository<FAQs, Long> {
 
	List<FAQs> findByDomain(String domain);
}
