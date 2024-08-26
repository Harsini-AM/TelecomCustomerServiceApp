package com.example.EmployeeService.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EmployeeService.entity.Admin;

 
@Repository
public interface AdminRepo extends JpaRepository<Admin, Long>{
	Optional<Admin> findByUserName(String userName);
 
}
