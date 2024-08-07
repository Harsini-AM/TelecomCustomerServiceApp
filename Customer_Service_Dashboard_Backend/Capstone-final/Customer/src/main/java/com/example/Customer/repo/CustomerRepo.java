package com.example.Customer.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Customer.entity.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>{
	 Optional<Customer> findByUserName(String userName);
	 
	 @Query("SELECT u.city, COUNT(u) FROM Customer u GROUP BY u.city")
		List<Object[]> countCustomersByLocation();
}
