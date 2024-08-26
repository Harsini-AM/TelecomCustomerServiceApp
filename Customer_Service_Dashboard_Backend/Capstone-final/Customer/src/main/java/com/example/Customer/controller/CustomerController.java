package com.example.Customer.controller;

import java.time.LocalDate;		
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerdaoImpl customerService;
    
    
    public void setCustomerService(CustomerdaoImpl customerService2) {
		this.customerService = customerService2;
		
	}
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
    public ResponseEntity<?> getTickets() {
        logger.info("Fetching all tickets");
        return new ResponseEntity<>(customerService.getAllTickets(), HttpStatus.OK);
    }

    @GetMapping("/responseAverage/{managerId}")
    public ResponseEntity<?> getResponseAverageByManagerId(@PathVariable Long managerId) {
        logger.info("Calculating response average for managerId: {}", managerId);
        Map<Long, Double> result = customerService.calculateTop5RepWiseAverageResponseTime(managerId);
        logger.info("Response average result for managerId {}: {}", managerId, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/resolutionAverage/{managerId}")
    public ResponseEntity<?> getResolutionAverageByManagerId(@PathVariable Long managerId) {
        logger.info("Calculating resolution average for managerId: {}", managerId);
        Map<Long, Double> result = customerService.calculateTop5RepWiseAverageResolutionTime(managerId);
        logger.info("Resolution average result for managerId {}: {}", managerId, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/statusCounts/{managerId}")
    public ResponseEntity<Map<String, Long>> getTicketCountsByStatus(@PathVariable Long managerId) {
        logger.info("Fetching ticket counts by status for managerId: {}", managerId);
        Map<String, Long> ticketCounts = customerService.getTicketCountsByStatus(managerId);
        logger.info("Ticket counts for managerId {}: {}", managerId, ticketCounts);
        return ResponseEntity.ok(ticketCounts);
    }

    @GetMapping("/averageResponseTime/{repId}")
    public ResponseEntity<Double> getAverageResponseTimeByRepId(@PathVariable Long repId) {
        logger.info("Calculating average response time for repId: {}", repId);
        double averageResponseTime = customerService.getAverageResponseTimeByRepId(repId);
        logger.info("Average response time for repId {}: {}", repId, averageResponseTime);
        return ResponseEntity.ok(averageResponseTime);
    }

    @GetMapping("/averageResolutionTime/{repId}")
    public ResponseEntity<Double> getAverageResolutionTimeByRepId(@PathVariable Long repId) {
        logger.info("Calculating average resolution time for repId: {}", repId);
        double averageResolutionTime = customerService.getAverageResolutionTimeByRepId(repId);
        logger.info("Average resolution time for repId {}: {}", repId, averageResolutionTime);
        return ResponseEntity.ok(averageResolutionTime);
    }

    @GetMapping("/statusCountsForRep/{repId}")
    public ResponseEntity<?> getTicketCountsByStatusForRep(@PathVariable Long repId) {
        logger.info("Fetching ticket counts by status for repId: {}", repId);
        try {
            Map<String, Long> ticketCounts = customerService.getTicketCountsByStatusForRep(repId);
            logger.info("Ticket counts for repId {}: {}", repId, ticketCounts);
            return ResponseEntity.ok(ticketCounts);
        } catch (RuntimeException e) {
            logger.error("Error fetching ticket counts for repId {}: {}", repId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error in fetching data");
        }
    }

    @GetMapping("/weeklyResponseTime/{repId}")
    public Map<String, Float> getAverageResponseTime(@PathVariable Long repId) {
        logger.info("Calculating weekly average response time for repId: {}", repId);
        LocalDate date = LocalDate.now();
        Map<String, Float> responseTimes = customerService.getAverageResponseTimeByDayOfWeek(repId, date);
        logger.info("Weekly average response time for repId {}: {}", repId, responseTimes);
        return responseTimes;
    }

    @GetMapping("/weeklyResolutionTime/{repId}")
    public Map<String, Float> getAverageResolutionTime(@PathVariable Long repId) {
        logger.info("Calculating weekly average resolution time for repId: {}", repId);
        LocalDate date = LocalDate.now();
        Map<String, Float> resolutionTimes = customerService.getAverageResolutionTimeByDayOfWeek(repId, date);
        logger.info("Weekly average resolution time for repId {}: {}", repId, resolutionTimes);
        return resolutionTimes;
    }

    

    @GetMapping("/ticketCount/{repId}")
    public ResponseEntity<Long> getCount(@PathVariable Long repId) {
        logger.info("Fetching ticket count for repId: {}", repId);
        Long count = customerService.getTicketCount(repId);
        logger.info("Ticket count for repId {}: {}", repId, count);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/ticketCountForManager/{managerId}")
    public ResponseEntity<Long> getCountManager(@PathVariable Long managerId) {
        logger.info("Fetching ticket count for managerId: {}", managerId);
        Long count = customerService.getTicketCountOfManager(managerId);
        logger.info("Ticket count for managerId {}: {}", managerId, count);
        return new ResponseEntity<>(count, HttpStatus.OK);
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