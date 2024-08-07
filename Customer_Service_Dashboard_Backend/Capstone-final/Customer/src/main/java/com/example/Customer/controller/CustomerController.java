package com.example.Customer.controller;

import java.time.LocalDate;	
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Customer.dao.CustomerdaoImpl;
import com.example.Customer.dto.LoginRequest;
import com.example.Customer.dto.RegisterCustomerRequest;
import com.example.Customer.dto.RegisterCustomerResponse;
import com.example.Customer.dto.TicketResponse;
import com.example.Customer.dto.UpdateCustomerRequest;
import com.example.Customer.dto.UpdateCustomerResponse;
import com.example.Customer.entity.Customer;
import com.example.Customer.entity.FAQs;
import com.example.Customer.entity.FeedBack;
import com.example.Customer.entity.Ticket;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerdaoImpl customerService;
    
    //deva
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return customerService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterCustomerResponse> register(@RequestBody RegisterCustomerRequest registerRequest) {
        return customerService.register(registerRequest);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<UpdateCustomerResponse> updateCustomer(@PathVariable String username, @RequestBody UpdateCustomerRequest updateRequest) {
        return customerService.updateCustomer(username, updateRequest);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String username) {
        return customerService.findByUserName(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/createTicket")
    public ResponseEntity<?> createTicket(@RequestParam long id,@RequestBody Ticket ticket) {
        return customerService.createTicket(id, ticket);
    }
    
    @GetMapping("/ticket/{customerId}")
    public ResponseEntity<?> getAllTicketsByCustomerId(@PathVariable long customerId) {
        return customerService.getAllTicketsByCustomerId(customerId);
    }
    
    @GetMapping("/ticket/chart/{customerId}")
    public ResponseEntity<Map<String, Long>> getTicketByStatus(@PathVariable Long customerId) {
        Map<String, Long> ticketCounts = customerService.getTicketByStatus(customerId);
        return ResponseEntity.ok(ticketCounts);
    }
    
    

   
    //harsini
    @GetMapping("/getTickets")
    public ResponseEntity<?> getTickets(){
    	return new ResponseEntity<>(customerService.getAllTickets(),HttpStatus.OK);
    }
    
    
    @GetMapping("/responseAverage/{managerId}")
    public ResponseEntity<?> getResponseAverageByManagerId(@PathVariable Long managerId){
    	Map<Long, Double> result = customerService.calculateTop5RepWiseAverageResponseTime(managerId);
    	System.out.println("result is "+result);
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    
  
    @GetMapping("/resolutionAverage/{managerId}")
    public ResponseEntity<?> getResolutionAverageByManagerId(@PathVariable Long managerId){
    	Map<Long, Double> result = customerService.calculateTop5RepWiseAverageResolutionTime(managerId);
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/statusCounts/{managerId}")
    public ResponseEntity<Map<String, Long>> getTicketCountsByStatus(@PathVariable Long managerId) {
        Map<String, Long> ticketCounts = customerService.getTicketCountsByStatus(managerId);
        return ResponseEntity.ok(ticketCounts);
    }
    
    
    @GetMapping("/averageResponseTime/{repId}")
    public ResponseEntity<Double> getAverageResponseTimeByRepId(@PathVariable Long repId) {
        double averageResponseTime = customerService.getAverageResponseTimeByRepId(repId);
        return ResponseEntity.ok(averageResponseTime);
    }
 
    @GetMapping("/averageResolutionTime/{repId}")
    public ResponseEntity<Double> getAverageResolutionTimeByRepId(@PathVariable Long repId) {
        double averageResolutionTime = customerService.getAverageResolutionTimeByRepId(repId);
        return ResponseEntity.ok(averageResolutionTime);
    }
    
    
    @GetMapping("/statusCountsForRep/{repId}")
    public ResponseEntity<?> getTicketCountsByStatusForRep(@PathVariable Long repId) {
        try {
            Map<String, Long> ticketCounts = customerService.getTicketCountsByStatusForRep(repId);
            return ResponseEntity.ok(ticketCounts);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error in fetching data");
        }
    }
    
    @GetMapping("/weeklyResponseTime/{repId}")
    public Map<String, Float> getAverageResponseTime(@PathVariable Long repId) {
        LocalDate date = LocalDate.now();
        return customerService.getAverageResponseTimeByDayOfWeek(repId, date);
    }
    
    
    @GetMapping("/weeklyResolutionTime/{repId}")
    public Map<String, Float> getAverageResolutionTime(@PathVariable Long repId) {
        LocalDate date = LocalDate.now();
        return customerService.getAverageResolutionTimeByDayOfWeek(repId, date);
    }

	public void setCustomerService(CustomerdaoImpl customerService2) {
		this.customerService = customerService2;
		
	}
	
	@GetMapping("/ticketCount/{repId}")
	public ResponseEntity<Long> getCount(@PathVariable Long repId) {
		return new ResponseEntity<>(this.customerService.getTicketCount(repId),HttpStatus.OK);
	}
	
	
	@GetMapping("/ticketCountForManager/{managerId}")
	public ResponseEntity<Long> getCountManager(@PathVariable Long managerId) {
		return new ResponseEntity<>(this.customerService.getTicketCountOfManager(managerId),HttpStatus.OK);
	}
		
		//phani
    
    @GetMapping("/faqs")
	public List<FAQs> getAllFAQs(){
		return customerService.findAll();
	}
	@GetMapping("/faqs/domain/{domainName}")
	public List<FAQs> getFAQsByDomain(@PathVariable String domain){
		return customerService.findByDomain(domain);
	}
	@PostMapping("/faqs/add")
	public ResponseEntity<String> addFAQ(@RequestBody FAQs faq) {
        try {
        	customerService.addFAQ(faq);
            return ResponseEntity.status(HttpStatus.CREATED).body("FAQ added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add FAQ: " + e.getMessage());
        }
    }
	
	//thilak
	@GetMapping("/getTicketsByEmpId/{empId}")
    public ResponseEntity<?> getTicketsByEmpId(@PathVariable("empId") long empId) {
    	System.out.println("Method called");
    	List<Ticket> tickets = customerService.getTicketsByEmpId(empId);
        return new ResponseEntity<>(tickets,HttpStatus.OK);
    }
	
	@PutMapping("/updateTicket/{ticketId}")
	public ResponseEntity<TicketResponse> updateTicket(@PathVariable long ticketId, @RequestBody Ticket updatedTicket) {
        TicketResponse response = customerService.updateTicket(ticketId, updatedTicket);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/countByDomain")
    public List<Object[]> countByDomain() {
        return customerService.countByDomain();
    }
	
    @GetMapping("/countCustomersByLocation")
    public List<Object[]> countCustomersByLocation(){
    	return customerService.countCustomersByLocation();
    }
    
    @GetMapping("/getCustomerRating")
    public Double getCustomerRating() {
        return customerService.getCustomerRating();
    }
    
    @PostMapping("/addFeedback")
    public FeedBack addFeedback(FeedBack feedback) {
    	return customerService.addFeedback(feedback);
    }
    
    


 	
    
}