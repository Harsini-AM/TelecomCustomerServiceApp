package com.example.Customer.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Customer.Exception.CustomerNotFoundException;
import com.example.Customer.Exception.NoRepresentativeFoundException;
import com.example.Customer.Exception.TicketCreationException;
import com.example.Customer.Exception.UsernameAlreadyExistsException;
import com.example.Customer.dto.EmployeeResponse;
import com.example.Customer.dto.LoginRequest;
import com.example.Customer.dto.LoginResponse;
import com.example.Customer.dto.Outage;
import com.example.Customer.dto.RegisterCustomerRequest;
import com.example.Customer.dto.RegisterCustomerResponse;
import com.example.Customer.dto.TicketResponse;
import com.example.Customer.dto.UpdateCustomerRequest;
import com.example.Customer.dto.UpdateCustomerResponse;
import com.example.Customer.entity.Customer;
import com.example.Customer.entity.FAQs;
import com.example.Customer.entity.FeedBack;
import com.example.Customer.entity.Ticket;
import com.example.Customer.feign.EmployeeFeign;
import com.example.Customer.feign.OutageFeign;
import com.example.Customer.repo.CustomerRepo;
import com.example.Customer.repo.FAQRepo;
import com.example.Customer.repo.FeedbackRepo;
import com.example.Customer.repo.TicketRepo;

@Service
public class CustomerdaoImpl{

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private EmployeeFeign employeeClient;
    
    @Autowired
	private FAQRepo repo;
    
    @Autowired
    private OutageFeign outageClient;
    
    
    
    @Autowired
    private FeedbackRepo repo1;
    //deva
    public Optional<Customer> findByUserName(String userName) {
        return customerRepo.findByUserName(userName);
    }
 
    public void saveCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepo.save(customer);
    }
 
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        try {
            Optional<Customer> customer = customerRepo.findByUserName(loginRequest.getUserName());
            if (customer.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), customer.get().getPassword())) {
                Customer foundCustomer = customer.get();
                
                // Outage check
                String customerLocation = foundCustomer.getCity(); // Assuming city is used for outage check
                System.out.println(customerLocation);
                List<Outage> outages = outageClient.getAllOutages();
                for (Outage outage : outages) {
                	System.out.println((outage.getLocation().equalsIgnoreCase(customerLocation)) &&(LocalTime.now().isBefore(outage.getEnd_time()) && (LocalTime.now().isAfter(outage.getStart_time()))));
                    if ((outage.getLocation().equalsIgnoreCase(customerLocation)) &&
                        (LocalTime.now().isBefore(outage.getEnd_time()) && (LocalTime.now().isAfter(outage.getStart_time())))) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                            new LoginResponse(0,foundCustomer.getUserName(), "Your location is currently experiencing an outage. Please try again after " + outage.getEnd_time(),"customer")
                        );
                    }
                }
 
                LoginResponse loginResponse = new LoginResponse(foundCustomer.getId(), foundCustomer.getUserName(), "Login Successful", "customer");
                return ResponseEntity.ok(loginResponse);
            } else {
                throw new CustomerNotFoundException("Username doesn't exist. Create an account to proceed.");
            }
        } catch (CustomerNotFoundException ex) {
            throw ex;
        }
    }
 
    public ResponseEntity<RegisterCustomerResponse> register(RegisterCustomerRequest registerRequest) {
        try {
            if (findByUserName(registerRequest.getUserName()).isPresent()) {
                throw new UsernameAlreadyExistsException("Username already exists");
            } else {
                Customer customer = new Customer();
                customer.setUserName(registerRequest.getUserName());
                customer.setPassword(registerRequest.getPassword());
                customer.setfName(registerRequest.getfName());
                customer.setlName(registerRequest.getlName());
                customer.setCity(registerRequest.getCity());
                customer.setState(registerRequest.getState());
                customer.setPhone_no(registerRequest.getPhone_no());
                customer.setPlanType(registerRequest.getPlanType());
                customer.setPlanName(registerRequest.getPlanName());
                customer.setPlanDescription(registerRequest.getPlanDescription());
                saveCustomer(customer);
                return ResponseEntity.ok(new RegisterCustomerResponse(customer.getId(), customer.getUserName(), "Registered successfully", "customer"));
            }
        } catch (UsernameAlreadyExistsException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while processing the registration request.", e);
        }
    }
 
    public ResponseEntity<UpdateCustomerResponse> updateCustomer(String userName, UpdateCustomerRequest updateRequest) {
        try {
            Optional<Customer> existingCustomer = findByUserName(userName);
            if (existingCustomer.isPresent()) {
                Customer customer = existingCustomer.get();
                customer.setfName(updateRequest.getfName());
                customer.setlName(updateRequest.getlName());
                customer.setCity(updateRequest.getCity());
                customer.setState(updateRequest.getState());
                customer.setPhone_no(updateRequest.getPhone_no());
                customer.setPlanType(updateRequest.getPlanType());
                customer.setPlanName(updateRequest.getPlanName());
                customer.setPlanDescription(updateRequest.getPlanDescription());
                customerRepo.save(customer);
                UpdateCustomerResponse response = new UpdateCustomerResponse(
                    customer.getId(),
                    customer.getUserName(),
                    customer.getfName(),
                    customer.getlName(),
                    customer.getCity(),
                    customer.getState(),
                    customer.getPhone_no(),
                    customer.getPlanType(),
                    customer.getPlanName(),
                    customer.getPlanDescription()
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new CustomerNotFoundException("Customer not found with username: " + userName);
            }
        } catch (CustomerNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while processing the update request.", e);
        }
    }
 
    public ResponseEntity<?> createTicket(long id, Ticket ticket) {
        try {
            EmployeeResponse employee = employeeClient.getLeastTicketRepresentative(ticket.getDomain());
            if (employee == null) {
                throw new NoRepresentativeFoundException("No representative available for the specified domain");
            }
 
            Optional<Customer> customerOpt = customerRepo.findById(id);
            if (customerOpt.isPresent()) {
                Customer customer = customerOpt.get();
                ticket.setEmpId(employee.getEmpId());
                ticket.setManagerId(employee.getManagerId());
                ticket.setCustomer(customer);
                ticket.setStatus("OPEN");
                ticket.setCreatedAt(LocalDateTime.now());
                ticket.setEmpUserName(employee.getUserName());
                ticketRepo.save(ticket);
                return ResponseEntity.ok("Ticket created successfully");
            } else {
                throw new CustomerNotFoundException("Customer not found with ID: " + id);
            }
        } catch (NoRepresentativeFoundException | CustomerNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the ticket.", e);
        }
    }
 
    public ResponseEntity<?> getAllTicketsByCustomerId(long customerId) {
        try {
            List<Ticket> tickets = ticketRepo.findAllByCustomerId(customerId);
            if (tickets != null && !tickets.isEmpty()) {
                return ResponseEntity.ok(tickets);
            } else {
                throw new CustomerNotFoundException("No tickets found for customer with ID: " + customerId);
            }
        } catch (CustomerNotFoundException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving tickets.", e);
        }
    }
 
    public Map<String, Long> getTicketByStatus(Long customerId) {
        try {
            List<Object[]> results = ticketRepo.countTicketsStatus(customerId);
            Map<String, Long> ticketCounts = new HashMap<>();
 
            for (Object[] result : results) {
                String status = (String) result[0];
                Long count = (Long) result[1];
                ticketCounts.put(status, count);
            }
 
            if (ticketCounts.isEmpty()) {
                throw new TicketCreationException("No tickets found for customer with ID: " + customerId);
            }
 
            return ticketCounts;
        } catch (TicketCreationException ex) {
            throw ex;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving ticket counts by status.", e);
        }
    }



    
    //harsini  
    public List<Ticket> getTicketsByManagerId(Long managerId) {
        try {
            return ticketRepo.findByManagerId(managerId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching tickets by manager ID", e);
        }
    }

    public double calculateAverageResponseTime(Long managerId) {
        try {
        	
            List<Ticket> tickets = getTicketsByManagerId(managerId);
            return tickets.stream()
                          .mapToDouble(Ticket::getResponseTime)
                          .average()
                          .orElse(0.0);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating average response time", e);
        }
    }

    public double getAverageResponseTimeByManagerId(Long managerId) {
        try {
            List<Ticket> tickets = getTicketsByManagerId(managerId);
            //System.out.println(t);
            return tickets.stream()
                          .mapToDouble(Ticket::getResponseTime)
                          .average()
                          .orElse(0.0);
        } catch (Exception e) {
            throw new RuntimeException("Error getting average response time by manager ID", e);
        }
    }

    public double getAverageResolutionTimeByManagerId(Long managerId) {
        try {
            List<Ticket> tickets = getTicketsByManagerId(managerId);
            return tickets.stream()
                          .mapToDouble(Ticket::getResolutionTime)
                          .average()
                          .orElse(0.0);
        } catch (Exception e) {
            throw new RuntimeException("Error getting average resolution time by manager ID", e);
        }
    }

    public Map<Long, Double> calculateTop5RepWiseAverageResponseTime(Long managerId) {
        try {
            List<Ticket> tickets = getTicketsByManagerId(managerId);
            Map<Long, Double> repAvgResponseTime = tickets.stream()
                    .collect(Collectors.groupingBy(Ticket::getEmpId, Collectors.averagingDouble(Ticket::getResponseTime)));
            Map<Long,Double> sorted =  repAvgResponseTime.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .limit(5)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));
            return sorted;
        } catch (Exception e) {
            throw new RuntimeException("Error calculating top 5 representative-wise average response time", e);
        }
    }


		
    public Map<Long,Double> calculateTop5RepWiseAverageResolutionTime(Long managerId){
        try {
            List<Ticket> tickets = getTicketsByManagerId(managerId);
            
            // Calculate average resolution time for each representative
            Map<Long, Double> repAvgResolutionTime = tickets.stream()
                    .collect(Collectors.groupingBy(Ticket::getEmpId, Collectors.averagingDouble(Ticket::getResolutionTime)));
            
            // Sort the representatives by their average resolution time and take the top 5
            Map<Long,Double> sorted =  repAvgResolutionTime.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .limit(5)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));
            
            System.out.println(sorted);
            return sorted;
        } catch (Exception e) {
            // Handle exception or rethrow
            throw new RuntimeException("Error calculating top 5 representative-wise average resolution time", e);
        }
    }

    public Map<String, Long> getTicketCountsByStatus(Long managerId) {
        try {
            List<Object[]> results = ticketRepo.countTicketsByStatus(managerId);
            Map<String, Long> ticketCounts = new HashMap<>();

            for (Object[] result : results) {
                String status = (String) result[0];
                Long count = (Long) result[1];
                ticketCounts.put(status, count);
            }
            System.out.println(ticketCounts.size());

            return ticketCounts;
        } catch (Exception e) {
            throw new RuntimeException("Error getting ticket counts by status", e);
        }
    }

    public double getAverageResponseTimeByRepId(Long repId) {
        try {
            Optional<Double> result = ticketRepo.findAverageResponseTimeByRepId(repId);
            return result.orElse(0.0);
        } catch (Exception e) {
            throw new RuntimeException("Error getting average response time by representative ID", e);
        }
    }

    public double getAverageResolutionTimeByRepId(Long repId) {
        try {
            Optional<Double> result = ticketRepo.findAverageResolutionTimeByRepId(repId);
            return result.orElse(0.0);
        } catch (Exception e) {
            throw new RuntimeException("Error getting average resolution time by representative ID", e);
        }
    }

    public List<Ticket> getTicketsByRepId(Long repId) {
        try {
            return ticketRepo.findByEmpId(repId);
        } catch (Exception e) {
            throw new RuntimeException("Error getting tickets by representative ID", e);
        }
    }
    

		
		
		
		
    public Map<String, Long> getTicketCountsByStatusForRep(Long repId) {
        try {
            List<Object[]> results = ticketRepo.countTicketsByStatusForRep(repId);
            Map<String, Long> ticketCounts = new HashMap<>();

            for (Object[] result : results) {
                String status = (String) result[0];
                Long count = (Long) result[1];
                ticketCounts.put(status, count);
            }

            return ticketCounts;
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            throw new RuntimeException("Error getting ticket counts by status for representative: " + e.getMessage(), e);
        }
    }

		public Map<String, Float> getAverageResponseTimeByDayOfWeek(Long repId, LocalDate currentDate) {
		    try {
		        LocalDateTime startDate = currentDate.atStartOfDay().minusDays(7);
		        List<Object[]> results = ticketRepo.findResponseTimesForRep(repId, startDate);

		        // Collect response times by day
		        Map<LocalDate, List<Float>> responseTimesByDay = new HashMap<>();
		        for (Object[] result : results) {
		            LocalDate createdAt = ((LocalDateTime) result[0]).toLocalDate();
		            float responseTime = ((Number) result[1]).floatValue();

		            responseTimesByDay
		                .computeIfAbsent(createdAt, k -> new ArrayList<>())
		                .add(responseTime);
		        }
		        System.out.println(responseTimesByDay);

		        // Calculate average response times and store in a sorted map
		        Map<LocalDate, Float> avgResponseTimesByDate = responseTimesByDay.entrySet().stream()
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                entry -> (float) entry.getValue().stream().mapToDouble(Float::floatValue).average().orElse(0.0)
		            ));

		        // Sort the map by LocalDate
		        Map<LocalDate, Float> sortedAvgResponseTimesByDate = avgResponseTimesByDate.entrySet().stream()
		            .sorted(Map.Entry.comparingByKey())
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (e1, e2) -> e1,
		                LinkedHashMap::new
		            ));

		        // Convert sorted map to day-of-week format
		        Map<String, Float> avgResponseTimesByDayOfWeek = new LinkedHashMap<>();
		        for (Map.Entry<LocalDate, Float> entry : sortedAvgResponseTimesByDate.entrySet()) {
		            avgResponseTimesByDayOfWeek.put(entry.getKey().getDayOfWeek().name(), entry.getValue());
		        }

		        return avgResponseTimesByDayOfWeek;
		    } catch (Exception e) {
		        throw new RuntimeException("Error getting average response time by day of week for representative", e);
		    }
		}

		public Map<String, Float> getAverageResolutionTimeByDayOfWeek(Long repId, LocalDate currentDate) {
		    try {
		        LocalDateTime startDate = currentDate.atStartOfDay().minusDays(7);
		        List<Object[]> results = ticketRepo.findResolutionTimesForRep(repId, startDate);

		        // Collect resolution times by day
		        Map<LocalDate, List<Float>> resolutionTimesByDay = new HashMap<>();
		        for (Object[] result : results) {
		            LocalDate createdAt = ((LocalDateTime) result[0]).toLocalDate();
		            float resolutionTime = ((Number) result[1]).floatValue();

		            resolutionTimesByDay
		                .computeIfAbsent(createdAt, k -> new ArrayList<>())
		                .add(resolutionTime);
		        }
		        System.out.println(resolutionTimesByDay);

		        // Calculate average resolution times
		        Map<LocalDate, Float> avgResolutionTimesByDate = resolutionTimesByDay.entrySet().stream()
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                entry -> (float) entry.getValue().stream().mapToDouble(Float::floatValue).average().orElse(0.0)
		            ));

		        // Sort the map by LocalDate
		        Map<LocalDate, Float> sortedAvgResolutionTimesByDate = avgResolutionTimesByDate.entrySet().stream()
		            .sorted(Map.Entry.comparingByKey())
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (e1, e2) -> e1,
		                LinkedHashMap::new
		            ));

		        // Convert sorted map to day-of-week format
		        Map<String, Float> avgResolutionTimesByDayOfWeek = new LinkedHashMap<>();
		        for (Map.Entry<LocalDate, Float> entry : sortedAvgResolutionTimesByDate.entrySet()) {
		            avgResolutionTimesByDayOfWeek.put(entry.getKey().getDayOfWeek().name(), entry.getValue());
		        }

		        return avgResolutionTimesByDayOfWeek;
		    } catch (Exception e) {
		        throw new RuntimeException("Error getting average resolution time by day of week for representative", e);
		    }
		}

		
		public long getTicketCount(Long repId) {
			return ticketRepo.getCountByRepId(repId);
		}
		
		public long getTicketCountOfManager(Long managerId) {
			return ticketRepo.getCountByManagerId(managerId);
		}
		
		public List<Ticket> getAllTickets(){
		    try {
		        return ticketRepo.findAll();
		    } catch (Exception e) {
		        throw new RuntimeException("Error getting all tickets", e);
		    }
		}
	
		
		
		//phani
		
		public List<FAQs> findAll(){
			return repo.findAll();
		}
		public List<FAQs> findByDomain(String domain){
			return repo.findByDomain(domain);
		}
		public FAQs addFAQ(FAQs faq) {
	        return repo.save(faq);
	    }
    
		
		//thilak
		public List<Ticket> getTicketsByEmpId(long id){
	    	List<Ticket> tickets = ticketRepo.getTicketsByEmpId(id);  	
	    	return tickets;
	    }
		
		public TicketResponse updateTicket(long ticketId, Ticket updatedTicket) {
		    // Check if the ticket with the given ID exists
		    Ticket existingTicket = ticketRepo.findById(ticketId).orElse(null);
		    if (existingTicket != null) {
		        // Update the fields of the existing ticket with the values from the updated ticket
		        existingTicket.setDescription(updatedTicket.getDescription());
		        existingTicket.setStatus(updatedTicket.getStatus());
		        // Update other fields as needed
		        if(existingTicket.getStatus().equals("in-progress")) {
		            float currentTime = LocalDateTime.now().getHour() * 3600 + LocalDateTime.now().getMinute() * 60 + LocalDateTime.now().getSecond();
		            float createdAt = existingTicket.getCreatedAt().getHour() *3600 + existingTicket.getCreatedAt().getMinute() * 60 + existingTicket.getCreatedAt().getSecond();
		            float responseTime = Math.abs(createdAt - currentTime);
		            existingTicket.setResponseTime(responseTime);
		        }
		        if(existingTicket.getStatus().equals("close")) {
		            float currentTime = LocalDateTime.now().getHour() * 3600 + LocalDateTime.now().getMinute() * 60 + LocalDateTime.now().getSecond();
		            float createdAt = existingTicket.getCreatedAt().getHour() * 3600 + existingTicket.getCreatedAt().getMinute() * 60 + existingTicket.getCreatedAt().getSecond();
		            float resolutionTime = Math.abs(createdAt - currentTime);
		            existingTicket.setResolutionTime(resolutionTime);
		        }
		        // Save the updated ticket
		        ticketRepo.save(existingTicket);
		        
		        // Get the customer userName
		        String customerUserName = existingTicket.getCustomer().getUserName();
		        
		        // Return the custom response object
		        return new TicketResponse(existingTicket, customerUserName);
		        
		    } else {
		        return null; // Ticket with the given ID not found
		    }
		}
		
		
		//charanya
//		public List<Object[]> getCountByLocation() {
//	        return ticketRepo.countUsersByLocation();
//	    }
		
		public List<Object[]> countByDomain() {
	        return ticketRepo.countByDomain();
	    }
		
		
		public List<Object[]> countCustomersByLocation() {
			// TODO Auto-generated method stub
			return customerRepo.countCustomersByLocation();
		}
		
		 public Double getCustomerRating() {
		        
				return repo1.getCustomerRating();
		    }
		 
		 public FeedBack addFeedback(FeedBack feedback) {
			 return repo1.save(feedback);
		 }
}




