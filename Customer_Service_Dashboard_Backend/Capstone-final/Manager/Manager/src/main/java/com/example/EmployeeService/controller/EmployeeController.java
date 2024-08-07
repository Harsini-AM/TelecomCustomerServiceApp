package com.example.EmployeeService.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.example.EmployeeService.dao.EmployeeServiceImplementation;
import com.example.EmployeeService.dto.ChangePasswordRequest;
import com.example.EmployeeService.dto.EmployeeResponse;
import com.example.EmployeeService.dto.LoginRequest;
import com.example.EmployeeService.dto.LoginResponse;
import com.example.EmployeeService.dto.ManagerDeletionRequest;
import com.example.EmployeeService.dto.ManagerDto;
import com.example.EmployeeService.dto.RepresentativeDto;
import com.example.EmployeeService.dto.Ticket;
import com.example.EmployeeService.dto.UpdateEmployeeRequest;
import com.example.EmployeeService.entity.Admin;
import com.example.EmployeeService.entity.Manager;
import com.example.EmployeeService.entity.Representative;
import com.example.EmployeeService.feignService.CustomerServiceFeignClient;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	
	@Autowired
	public EmployeeServiceImplementation empService;
	
	@Autowired
	private CustomerServiceFeignClient customerServiceFeignClient;
	
	@Autowired
	private RestTemplate restTemp;

	
	@PostMapping("/addManager")
	public ResponseEntity<?> addEmployee(@RequestBody Manager rep){
		
		return new ResponseEntity<>(empService.addManager(rep), HttpStatus.OK);
	}
	
	@PostMapping("/addRepresentative")
	public ResponseEntity<?> addRepresentative(@RequestBody RepresentativeDto rep){
		return empService.addRepresentative(rep);
	}
	
	@GetMapping("/getManagers")
	public ResponseEntity<List<ManagerDto>> getAllManagers(){
		List<ManagerDto> managers = empService.getManagers();
		return new ResponseEntity<>(managers, HttpStatus.OK);
	}
	
	@GetMapping("/getRepresentatives")
	public ResponseEntity<?> getAllRepresentatives(){
		List<RepresentativeDto> reps = empService.getRepresentatives();
		return new ResponseEntity<>(reps, HttpStatus.OK);
	}
	
	
	@GetMapping("/getManager/{id}")
	public ResponseEntity<ManagerDto> getById(@PathVariable int id){
		ManagerDto manager= empService.getByManagerId(id);
		return new ResponseEntity<>(manager,HttpStatus.OK);
	}
	
	@GetMapping("/getRepresentative/{id}")
	public ResponseEntity<RepresentativeDto> getRepById(@PathVariable int id){
		RepresentativeDto rep= empService.getByRepId(id);
		return new ResponseEntity<>(rep,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteManager/{id}")
	public ResponseEntity<?> deleteManager(@PathVariable long id){
		String response = empService.deleteManager(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/deleteRepresentative/{id}")
	public ResponseEntity<?> deleteRepresentative(@PathVariable long id){
		String response = empService.deleteRepresentative(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@PutMapping("updateManager/{id}")
    public ResponseEntity<?> updateManager(@PathVariable long id, @RequestBody ManagerDto newData) {
		System.out.println(newData);
		return empService.updateManager(id, newData);
		
    }
	
	
	@PutMapping("updateRepresentative/{id}")
    public ResponseEntity<?> updateRepresentative(@PathVariable long id, @RequestBody RepresentativeDto newData) {
		System.out.println(newData);
		return empService.updateRepresentative(id, newData);
		
    }
	
	@PutMapping("promoteEmployee/{id}")
	public ResponseEntity<?> promoteEmployee(@PathVariable long id, @RequestBody RepresentativeDto newData) {
		System.out.println(newData);
		return empService.promoteEmployee(id, newData);
		
    }
	
	@PutMapping("depromoteEmployee/{id}")
	public ResponseEntity<?> depromoteEmployee(@PathVariable long id, @RequestBody ManagerDto newData) {
		System.out.println(newData);
		return empService.depromoteEmployee(id, newData);
		
    }
		
	
	
    // harsini TicketClient - charts (Manager)
    @GetMapping("/responseAverage/{managerId}")
    public ResponseEntity<?> getResponseAverageByManagerId(@PathVariable Long managerId){
    	return empService.getResponseAverageByManagerId(managerId);
    }
	
    
    @GetMapping("/resolutionAverage/{managerId}")
    public ResponseEntity<Map<Long, Double>> getResolutionAverageByManagerId(@PathVariable Long managerId) {
        return empService.getResolutionAverageByManagerId(managerId);
    }

    @GetMapping("/statusCounts/{managerId}")
    public ResponseEntity<Map<String, Long>> getTicketCountsByStatus(@PathVariable Long managerId) {
        return empService.getTicketCountsByStatus(managerId);
    }

    @GetMapping("/averageResponseTime/{repId}")
    public ResponseEntity<Double> getAverageResponseTimeByRepId(@PathVariable Long repId) {
        return empService.getAverageResponseTimeByRepId(repId);
    }

    @GetMapping("/averageResolutionTime/{repId}")
    public ResponseEntity<Double> getAverageResolutionTimeByRepId(@PathVariable Long repId) {
        return empService.getAverageResolutionTimeByRepId(repId);
    }

    @GetMapping("/statusCountsForRep/{repId}")
    public ResponseEntity<Map<String, Long>> getTicketCountsByStatusForRep(@PathVariable Long repId) {
        return empService.getTicketCountsByStatusForRep(repId);
    }

    @GetMapping("/weeklyResponseTime/{repId}")
    public Map<String, Float> getAverageResponseTime(@PathVariable Long repId) {
        return empService.getAverageResponseTime(repId);
    }

    @GetMapping("/weeklyResolutionTime/{repId}")
    public Map<String, Float> getAverageResolutionTime(@PathVariable Long repId) {
        return empService.getAverageResolutionTime(repId);
    }
    
    
    @GetMapping("/getRepByManagerId/{managerId}")
    public ResponseEntity<List<Representative>> getRepresentative(@PathVariable Long managerId){
    	return new ResponseEntity<>(empService.getRepsByManagerId(managerId), HttpStatus.OK);
    }
    
    @GetMapping("/ticketCount/{repId}")
	public ResponseEntity<Long> getCount(@PathVariable Long repId) {
		return new ResponseEntity<>(this.empService.getTicketCount(repId),HttpStatus.OK);
	}
    
    @GetMapping("/getCountManager/{managerId}")
    public ResponseEntity<Long> getTicketsForManager(@PathVariable int managerId) {
        return ResponseEntity.ok(this.empService.getTicketsByManagerId(managerId));
    }
    
    
    //deva
    
    @PostMapping("/admin")
    public ResponseEntity<?> admin(@RequestBody LoginRequest loginRequest) {
        return empService.admin(loginRequest);
    }
 
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return empService.login(loginRequest);
    }
 
    @PutMapping("/changepassword/{userName}")
    public ResponseEntity<?> changePassword(@PathVariable String userName, @RequestParam String role, @RequestBody ChangePasswordRequest changePasswordRequest) {
        return empService.changePassword(userName, role, changePasswordRequest);
    }
 
    @GetMapping("/{userName}")
    public ResponseEntity<?> getEmployeeDetails(@PathVariable String userName, @RequestParam String role) {
        return empService.getEmployeeDetails(userName, role);
    }
 
    @PutMapping("/update/{userName}")
    public ResponseEntity<?> updateEmployee(@PathVariable String userName, @RequestParam String role, @RequestBody UpdateEmployeeRequest updateRequest) {
        return empService.updateEmployee(userName, role, updateRequest);
    }
    
    @GetMapping("/leastTicketRepresentative")
    public ResponseEntity<EmployeeResponse> getLeastTicketRepresentative(@RequestParam String domain) {
        return ResponseEntity.ok(empService.getLeastTicketRepresentative(domain));
    }
    
    @GetMapping("/getTicketsByEmpId/{empId}")
    public ResponseEntity<List<Ticket>> getTickets(@PathVariable int empId) {
        return ResponseEntity.ok(customerServiceFeignClient.getTicketsByEmpId(empId));
    }
    
    @GetMapping("/getManagerCount")
    public ResponseEntity<Long> getManagerCount(){
    	Long count = empService.getManagerCount();
    	return new ResponseEntity<>(count,HttpStatus.OK);
    }
    
    @GetMapping("/getRepCount")
    public ResponseEntity<Long> getRepCount(){
    	Long count = empService.getRepCount();
    	return new ResponseEntity<>(count,HttpStatus.OK);
    }
    
    
    
    
    @GetMapping("/paginationManager/{offset}/{pageSize}")
    public ResponseEntity<List<ManagerDto>> getManagerWithPagination(@PathVariable int offset,@PathVariable int pageSize){
    	List<ManagerDto> managers =  empService.getManagersWithPagination(offset, pageSize);
    	return new ResponseEntity<>(managers,HttpStatus.OK);
    }
    
    @GetMapping("/paginationRepresentative/{offset}/{pageSize}")
    public List<RepresentativeDto> getRepresentativeWithPagination(@PathVariable int offset,@PathVariable int pageSize){
    	List<RepresentativeDto> allManagers = empService.findRepsWithPagination(offset, pageSize);
    	return allManagers;
    }
    
    
    
    
    
    
}
