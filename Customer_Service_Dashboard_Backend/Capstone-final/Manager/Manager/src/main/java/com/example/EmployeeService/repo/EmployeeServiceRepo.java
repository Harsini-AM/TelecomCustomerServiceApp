package com.example.EmployeeService.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.EmployeeService.dto.ManagerDto;
import com.example.EmployeeService.entity.Manager;

@Repository
public interface EmployeeServiceRepo extends JpaRepository<Manager,Long>{
	//Optional<Manager> findByUsername(String userName);
	public Optional<Manager> findByEmpId(long id);
	void deleteByEmpId(long id);
	Optional<Manager> findByUserName(String userName);
	boolean existsByUserName(String userName);
	
	 @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Manager m WHERE m.phone_no = :phone_no")
	 boolean existsByPhoneNoCustom(@Param("phone_no") long phone_no);
	
	@Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Manager m WHERE m.id = :empId")
    boolean existsByEmpId(@Param("empId") Long managerId);
	
	 @Query("SELECT new com.example.EmployeeService.dto.ManagerDto(m.empId, m.userName, m.fName, m.lName, m.city, m.state, m.phone_no, m.domain, m.password) FROM Manager m")
    List<ManagerDto> getManagers(PageRequest pageRequest);
	 
	 @Query("SELECT new com.example.EmployeeService.dto.ManagerDto(m.empId, m.userName, m.fName, m.lName, m.city, m.state, m.phone_no, m.domain, m.password) FROM Manager m WHERE m.id = :empId")
	 ManagerDto getManagerById(Long empId);
	 
	 
	 @Query("SELECT COUNT(m) FROM Manager m")
	 long countAllManagers();
	 
	 
	
}
