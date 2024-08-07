package com.example.outage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutageRepo  extends JpaRepository<Outage, Integer>{
	
}
