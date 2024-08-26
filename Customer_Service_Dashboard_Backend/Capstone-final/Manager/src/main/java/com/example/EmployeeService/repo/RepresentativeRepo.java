package com.example.EmployeeService.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.EmployeeService.dto.RepresentativeDto;
import com.example.EmployeeService.entity.Representative;

import jakarta.transaction.Transactional;

@Repository
public interface RepresentativeRepo extends JpaRepository<Representative,Long>{
	//public Representative findByManagerId(int id);
	public Representative findByEmpId(int id);
	
	Optional<Representative> findByUserName(String userName);
	
	boolean existsByUserName(String userName);
	
	 @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Representative r WHERE r.phone_no = :phone_no")
	 boolean existsByPhoneNoCustom(@Param("phone_no") long phone_no);
    
    @Query(value = "SELECT * FROM Representative r WHERE r.domain = :domain ORDER BY r.number_of_tickets ASC LIMIT 1", nativeQuery = true)
    Representative findFirstByDomainOrderByNumberOfTicketsAsc(@Param("domain") String domain);
	
	//Optional<Representative> findByuserName(String userName);
	
	Optional<Representative> findByEmpId(long empId);
	
	void deleteByEmpId(long empId);
	
	@Query("SELECT new com.example.EmployeeService.dto.RepresentativeDto(r.empId,r.fName, r.lName, r.phone_no, r.city, r.state, r.userName, r.password, r.domain, r.NumberOfTickets, r.PasswordChanged, r.manager.empId) FROM Representative r")
    List<RepresentativeDto> getRepresentatives(PageRequest pageRequest);
	
	@Query("SELECT new com.example.EmployeeService.dto.RepresentativeDto(r.empId,r.fName, r.lName, r.phone_no, r.city, r.state, r.userName, r.password, r.domain, r.NumberOfTickets, r.PasswordChanged, r.manager.empId) FROM Representative r WHERE r.id = :empId")
    RepresentativeDto getRepresentativeById(Long empId);
	
	 @Query("SELECT COUNT(r) FROM Representative r")
	 long countAllRepresentatives();
}

