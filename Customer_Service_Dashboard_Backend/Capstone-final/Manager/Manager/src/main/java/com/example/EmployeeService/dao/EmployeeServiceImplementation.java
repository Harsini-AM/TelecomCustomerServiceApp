package com.example.EmployeeService.dao;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.EmployeeService.dto.AddEmployeeResponse;
import com.example.EmployeeService.dto.ChangePasswordRequest;
import com.example.EmployeeService.dto.EmployeeResponse;
import com.example.EmployeeService.dto.LoginRequest;
import com.example.EmployeeService.dto.LoginResponse;
import com.example.EmployeeService.dto.ManagerDto;
import com.example.EmployeeService.dto.RepresentativeDto;
import com.example.EmployeeService.dto.UpdateEmployeeRequest;
import com.example.EmployeeService.dto.UpdateEmployeeResponse;
import com.example.EmployeeService.entity.Admin;
import com.example.EmployeeService.entity.Manager;
import com.example.EmployeeService.entity.Representative;
import com.example.EmployeeService.exception.DuplicateEntryException;
import com.example.EmployeeService.exception.EmployeeNotFoundException;
import com.example.EmployeeService.exception.InvalidRoleException;
import com.example.EmployeeService.exception.ManagerHasRepresentativesException;
import com.example.EmployeeService.exception.ManagerNotFoundException;
import com.example.EmployeeService.exception.UnauthorizedAccessException;
import com.example.EmployeeService.feignService.CustomerServiceFeignClient;
import com.example.EmployeeService.repo.AdminRepo;
import com.example.EmployeeService.repo.EmployeeServiceRepo;
import com.example.EmployeeService.repo.RepresentativeRepo;
import com.example.EmployeeService.service.PasswordService;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;

@Service
public class EmployeeServiceImplementation{
	
	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private EmployeeServiceRepo repo;
	
	@Autowired
	private RepresentativeRepo repRepo;
	
	@Autowired
	private CustomerServiceFeignClient customerServiceFeignClient;

	@Autowired
	private PasswordService passwordService;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;

	public String test() {
		return "Welcome";
	}

	
	public ManagerDto getByManagerId(long id) {
        try {
            ManagerDto manager = repo.getManagerById(id);
            if (manager == null) {
                throw new ManagerNotFoundException("Manager not found with ID: " + id);
            }
            return manager;
        } catch (ManagerNotFoundException e) {
            throw e; 
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while fetching the manager", e);
        }
    }
	
	
	public RepresentativeDto getByRepId(long id) {
        try {
            RepresentativeDto rep = repRepo.getRepresentativeById(id);
            if (rep == null) {
                throw new EmployeeNotFoundException("Representative not found with ID: " + id);
            }
            return rep;
        } catch (EmployeeNotFoundException e) {
            throw e; 
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while fetching the representative", e);
        }
    }

	@Transactional
	public ResponseEntity<?> addManager(Manager manager) {
	    try {
	        String userPassword = passwordService.generatePassword();
	        String password = passwordEncoder.encode(userPassword);
	        String username = manager.getUserName();
	        manager.setPassword(password);
	        System.out.println("changed emp " + manager);
	        
	        if (repRepo.existsByUserName(manager.getUserName()) || repRepo.existsByPhoneNoCustom(manager.getPhone_no())) {
	            throw new DuplicateEntryException("Duplicate entry detected for manager: " + manager.getUserName() + " or phone number: " + manager.getPhone_no());
	        }

	        repo.save(manager);

	        AddEmployeeResponse response = new AddEmployeeResponse("Employee added successfully", username, userPassword);
	        return ResponseEntity.ok(response);
	    } catch (DataIntegrityViolationException ex) {
	        throw new DuplicateEntryException("Duplicate entry detected for manager: " + manager.getUserName() + " or phone number: " + manager.getPhone_no());
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to add manager", e);
	    }
	}

	

	@Transactional
	public ResponseEntity<?> addRepresentative(RepresentativeDto rep) {
	String userPassword = passwordService.generatePassword();
    String password = passwordEncoder.encode(userPassword);
    String username = rep.getUsername();
	Optional<Manager> manager = repo.findByEmpId(rep.getManagerId());
	
	if(repo.existsByUserName(rep.getUserName()) || repo.existsByPhoneNoCustom(rep.getPhone_no())) {
    	throw new DuplicateEntryException("Duplicate entry detected for manager: " + rep.getUserName() + " or phone number: " + rep.getPhone_no());
    }
	if(manager.isPresent()) {
		
        
        rep.setPassword(password);
        System.out.println("changed emp " + rep);
        Representative newRep = new Representative();
        newRep.setfName(rep.getfName());
        newRep.setlName(rep.getlName());
        newRep.setCity(rep.getCity());
        newRep.setState(rep.getState());
        newRep.setDomain(rep.getDomain());
        newRep.setManager(manager.get());
        newRep.setUsername(rep.getUsername());
        newRep.setPassword(password);
        newRep.setNumberOfTickets(rep.getNo_of_tickets());
        newRep.setPhone_no(rep.getPhone_no());
        

        try {
                repRepo.save(newRep);

        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateEntryException("Duplicate entry detected for manager: " + rep.getUsername() + " or phone number: " + rep.getPhone_no());
        }catch (Exception e) {
	        throw new RuntimeException("Failed to add representative");
	    }
        
	}
	else {
		return new ResponseEntity<>("Existing Manager required!", HttpStatus.NOT_FOUND); 
	}
	
	AddEmployeeResponse response = new AddEmployeeResponse("Employee added successfully", username, userPassword);
		
	return new ResponseEntity<>(response, HttpStatus.OK);
	
	
}
	

	public List<ManagerDto> getManagers() {
	    try {
	        return repo.getManagers(null);
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to fetch managers", e);
	    }
	}


	@Transactional
	public ResponseEntity<?> updateManager(long id, ManagerDto managerDto) {
	    Manager update = null;
	    System.out.println("in service " + managerDto);
	    
	    try {
	        Optional<Manager> managerOptional = repo.findByEmpId(id); 

	        if(managerOptional.isPresent()) {
	            update = managerOptional.get();
	            update.setfName(managerDto.getFirstName());
	            update.setlName(managerDto.getLastName());
	            update.setDomain(managerDto.getDomain());
	            update.setPhone_no(managerDto.getPhone_no());
	            update.setCity(managerDto.getCity());
	            update.setState(managerDto.getState());

	            repo.save(update);
	            
	            System.out.println("updated value " + update);
	            
	            return new ResponseEntity<>(update, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Manager not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (DataIntegrityViolationException ex) {
	        throw new DuplicateEntryException("Duplicate entry detected for manager: " + (update != null ? update.getUserName() : "unknown") + " or phone number: " + (update != null ? update.getPhone_no() : "unknown"));
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to update manager: " + e.getMessage());
	    }
	}


	
	@Transactional
	public ResponseEntity<?> updateRepresentative(long id, RepresentativeDto newData) {
	    Representative update = null;
	    
	    try {
	        Optional<Representative> repOptional = repRepo.findById(id); 
	        
	        if (repOptional.isPresent()) {
	            update = repOptional.get();
	            
	            update.setfName(newData.getfName());
	            update.setlName(newData.getlName());
	            update.setDomain(newData.getDomain());
	            update.setPhone_no(newData.getPhone_no());
	            update.setCity(newData.getCity());
	            update.setState(newData.getState());
	            update.setUserName(newData.getUserName()); 
	            update.setPassword(newData.getPassword());
	            update.setNumberOfTickets(newData.getNo_of_tickets());
	            Optional<Manager> newManager = repo.findByEmpId(newData.getManagerId());
	            if(newManager.isPresent()) update.setManager(newManager.get());
	            

	            repRepo.save(update);
	            
	            System.out.println("updated value " + update);
	            
	            return new ResponseEntity<>(update, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Representative not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (DataIntegrityViolationException ex) {
	        throw new DuplicateEntryException("Duplicate entry detected for representative: " + (update != null ? update.getUserName() : "unknown") + " or phone number: " + (update != null ? update.getPhone_no() : "unknown"));
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to update representative: " + e.getMessage());
	    }
	}
		
	public List<RepresentativeDto> getRepresentatives() {
	    try {
	        return repRepo.getRepresentatives(null);
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to fetch representatives", e);
	    }
	}

		
	@Transactional
	public String deleteManager(long id) {
	    try {
	        Optional<Manager> manager = repo.findByEmpId(id);

	        if (manager.isPresent()) {
	            if (!manager.get().getRepresentatives().isEmpty()) {
	                throw new ManagerHasRepresentativesException("Cannot delete manager " + manager.get().getfName()+ " because they have associated representatives.");
	            }
	            repo.deleteById(id);
	            return "Manager removed successfully";
	        } else {
	            throw new ManagerNotFoundException("Manager " + manager.get().getfName() + " not found.");
	        }
	    } catch (ManagerHasRepresentativesException | ManagerNotFoundException e) {
	        throw e; 
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to delete manager", e);
	    }
	}

	
	@Transactional
	public String deleteRepresentative(long id) {
	    try {
	        Optional<Representative> rep = repRepo.findByEmpId(id);

	        if (rep.isPresent()) {
	            repRepo.deleteById(id);
	            return "Representative removed successfully";
	        } else {
	            throw new EmployeeNotFoundException("Representative " + rep.get().getfName() + " not found.");
	        }
	    } catch (EmployeeNotFoundException e) {
	        throw e; 
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to delete representative", e);
	    }
	}


	  @Transactional
		public ResponseEntity<?> promoteEmployee(long id, RepresentativeDto data) {
		    try {
		    	System.out.println("Promotion");
		        Optional<Representative> repOptional = repRepo.findByEmpId(id);
		        
		        if (repOptional.isPresent()) {
		            Representative rep = repOptional.get();

		            // Create a new manager using the data from the representative
		            Manager manager = new Manager(
		                rep.getEmpId(),
		                data.getfName(),
		                data.getlName(),
		                data.getPhone_no(),
		                data.getCity(),
		                data.getState(),
		                data.getUserName(),
		                data.getPassword(),
		                data.getDomain(),
		                false,
		                null 
		            );

		            // Save the new manager to the manager repository
		            repo.save(manager);
		            repRepo.delete(rep);

		            return new ResponseEntity<>("Representative promoted to Manager successfully", HttpStatus.OK);
		        } else {
		            return new ResponseEntity<>("Representative not found", HttpStatus.NOT_FOUND);
		        }
		    }
		    catch (Exception e) {
		        throw new RuntimeException("Failed to promote representative: " + e.getMessage());
		    }
		}


		@Transactional
		public ResponseEntity<?> depromoteEmployee(long id, ManagerDto managerDto) {
		    try {
		        Optional<Manager> managerOpt = repo.findByEmpId(id);

		        if (managerOpt.isEmpty()) {
		            throw new ManagerNotFoundException("Manager " + managerOpt.get().getfName() + " not found.");
		        }

		        Manager manager = managerOpt.get();

		        // Check if the manager has any representatives
		        if (!manager.getRepresentatives().isEmpty()) {
		            throw new ManagerHasRepresentativesException("Cannot depromote manager " + manager.getfName() + " because they have associated representatives.");
		        }

		        // Deleting the manager
		        repo.deleteByEmpId(id);

		        // Create a new representative
		        Representative newRep = new Representative();
		        newRep.setfName(managerDto.getfName());
		        newRep.setlName(managerDto.getlName());
		        newRep.setCity(managerDto.getCity());
		        newRep.setState(managerDto.getState());
		        newRep.setDomain(managerDto.getDomain());
		        newRep.setUserName(managerDto.getUserName());
		        newRep.setPassword(managerDto.getPassword());
		        newRep.setPhone_no(managerDto.getPhone_no());
		        newRep.setPasswordChanged(false);

		        Optional<Manager> newManagerOpt = repo.findByEmpId(managerDto.getManagerId());
		        if (newManagerOpt.isEmpty()) {
		            return new ResponseEntity<>("Existing Manager required!", HttpStatus.NOT_FOUND);
		        }
		        newRep.setManager(newManagerOpt.get());

		        try {
		            // Save the new representative
		            repRepo.save(newRep);
		        } catch (DataIntegrityViolationException ex) {
		            throw new DuplicateEntryException("Duplicate entry detected for manager: " + managerDto.getUserName() + " or phone number: " + managerDto.getPhone_no());
		        }

		        return new ResponseEntity<>("Depromotion successful!", HttpStatus.OK);
		    } catch (ManagerNotFoundException | ManagerHasRepresentativesException | DuplicateEntryException e) {
		        throw e;
		    } catch (Exception e) {
		        throw new RuntimeException("Failed to depromote employee", e);
		    }
		}

		
	
	    
	   //Harsini - chart data - data from customer
		public ResponseEntity<Map<Long, Double>> getResponseAverageByManagerId(Long managerId) {
	        try {
	            return customerServiceFeignClient.getResponseAverageByManagerId(managerId);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to get response average by manager ID: " + managerId, e);
	        }
	    }
		
	    public ResponseEntity<Map<Long, Double>> getResolutionAverageByManagerId(Long managerId) {
	        try {
	            return customerServiceFeignClient.getResolutionAverageByManagerId(managerId);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to get resolution average by manager ID: " + managerId, e);
	        }
	    }

	    public ResponseEntity<Map<String, Long>> getTicketCountsByStatus(Long managerId) {
	        try {
	            return customerServiceFeignClient.getTicketCountsByStatus(managerId);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to get ticket counts by status for manager ID: " + managerId, e);
	        }
	    }

	    public ResponseEntity<Double> getAverageResponseTimeByRepId(Long repId) {
	        try {
	            ResponseEntity<Double> double1 =  customerServiceFeignClient.getAverageResponseTimeByRepId(repId);
	            System.out.println(double1.getBody());
	            return double1;
	            
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to get average response time by representative ID: " + repId, e);
	        }
			  
		}

	    public ResponseEntity<Double> getAverageResolutionTimeByRepId(Long repId) {
	        try {
	            return customerServiceFeignClient.getAverageResolutionTimeByRepId(repId);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to get average resolution time by representative ID: " + repId, e);
	        }
	    }

	    public ResponseEntity<Map<String, Long>> getTicketCountsByStatusForRep(Long repId) {
	        try {
	            return customerServiceFeignClient.getTicketCountsByStatusForRep(repId);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to get ticket counts by status for representative ID: " + repId, e);
	        }
	    }

	    public Map<String, Float> getAverageResponseTime(Long repId) {
	        try {
	            return customerServiceFeignClient.getAverageResponseTime(repId);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to get average response time for representative ID: " + repId, e);
	        }
	    }

	    public Map<String, Float> getAverageResolutionTime(Long repId) {
	        try {
	            return customerServiceFeignClient.getAverageResolutionTime(repId);
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to get average resolution time for representative ID: " + repId, e);
	        }
	    }

	    public List<Representative> getRepsByManagerId(Long managerId) {
	        try {
	            Optional<Manager> manager = repo.findByEmpId(managerId);

	            if (manager.isPresent()) {
	                return manager.get().getRepresentatives();
	            } else {
	                throw new ManagerNotFoundException("Manager with id " + managerId + " not found.");
	            }
	        } catch (ManagerNotFoundException e) {
	            throw e; // rethrow custom exceptions to be handled by global exception handler
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to get representatives by manager ID: " + managerId, e);
	        }
	    }
	    
	    
	    public Long getTicketCount(Long repId) {
	    	return customerServiceFeignClient.getTicketCount(repId);
	    }
	    
	    public Long getTicketsByManagerId(long managerId) {
	    	return customerServiceFeignClient.getCountManager(managerId);
	    }
	   
	    
	    //deva
	    
	    public ResponseEntity<?> admin(@RequestBody LoginRequest loginRequest) {
	        try {
	            Optional<Admin> adminOpt = adminRepo.findByUserName(loginRequest.getUserName());
	            if (adminOpt.isPresent()) {
	                Admin admin = adminOpt.get();
	                if (passwordEncoder.matches(loginRequest.getPassword(), admin.getPassword())) {
	                    LoginResponse loginResponse = new LoginResponse(
	                            admin.getEmpId(),
	                            admin.getUserName(),
	                            "Login Successful",
	                            "admin",
	                            true
	                    );
	                    return ResponseEntity.ok(loginResponse);
	                } else {
	                    throw new UnauthorizedAccessException("Invalid Credentials");
	                }
	            }
	            throw new UnauthorizedAccessException("Unauthorized Access");
	        } catch (UnauthorizedAccessException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new InternalServerErrorException("An error occurred while processing the request.", e);
	        }
	    }
	 
	    public ResponseEntity<?> login(LoginRequest loginRequest) {
	        try {
	            Optional<Manager> managerOpt = repo.findByUserName(loginRequest.getUserName());
	            if (managerOpt.isPresent()) {
	                Manager manager = managerOpt.get();
	                if (passwordEncoder.matches(loginRequest.getPassword(), manager.getPassword())) {
	                    LoginResponse loginResponse = new LoginResponse(
	                            manager.getEmpId(),
	                            manager.getUserName(),
	                            "Login Successful",
	                            "manager",
	                            manager.getPasswordChanged()
	                    );
	                    return ResponseEntity.ok(loginResponse);
	                } else {
	                    throw new UnauthorizedAccessException("Invalid Credentials");
	                }
	            }
	 
	            Optional<Representative> representativeOpt = repRepo.findByUserName(loginRequest.getUserName());
	            if (representativeOpt.isPresent()) {
	                Representative representative = representativeOpt.get();
	                if (passwordEncoder.matches(loginRequest.getPassword(), representative.getPassword())) {
	                    LoginResponse loginResponse = new LoginResponse(
	                            representative.getEmpId(),
	                            representative.getUserName(),
	                            "Login Successful",
	                            "representative",
	                            representative.getPasswordChanged()
	                    );
	                    return ResponseEntity.ok(loginResponse);
	                } else {
	                    throw new UnauthorizedAccessException("Invalid Credentials");
	                }
	            }
	 
	            throw new UnauthorizedAccessException("Unauthorized Access");
	        } catch (UnauthorizedAccessException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new InternalServerErrorException("An error occurred while processing the request.", e);
	        }
	    }
	 
	 
	    public ResponseEntity<?> changePassword(String username, String role, ChangePasswordRequest changePasswordRequest) {
	        try {
	            if ("manager".equals(role)) {
	                Optional<Manager> managerOpt = repo.findByUserName(username);
	                if (managerOpt.isPresent()) {
	                    Manager manager = managerOpt.get();
	                    if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), manager.getPassword())) {
	                        manager.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
	                        manager.setPasswordChanged(true);
	                        repo.save(manager);
	                        return ResponseEntity.ok("Password changed successfully");
	                    } else {
	                        throw new UnauthorizedAccessException("Current password is incorrect");
	                    }
	                } else {
	                    throw new EmployeeNotFoundException("Manager not found with username: " + username);
	                }
	            } else if ("representative".equals(role)) {
	                Optional<Representative> representativeOpt = repRepo.findByUserName(username);
	                if (representativeOpt.isPresent()) {
	                    Representative representative = representativeOpt.get();
	                    if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), representative.getPassword())) {
	                        representative.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
	                        representative.setPasswordChanged(true);
	                        repRepo.save(representative);
	                        return ResponseEntity.ok("Password changed successfully");
	                    } else {
	                        throw new UnauthorizedAccessException("Current password is incorrect");
	                    }
	                } else {
	                    throw new EmployeeNotFoundException("Representative not found with username: " + username);
	                }
	            }
	            throw new InvalidRoleException("Invalid role specified");
	        } catch (UnauthorizedAccessException | EmployeeNotFoundException | InvalidRoleException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new InternalServerErrorException("An error occurred while processing the request.", e);
	        }
	    }
	 
	    public ResponseEntity<?> getEmployeeDetails(String username, String role) {
	    	RepresentativeDto repdto = new RepresentativeDto();
	        try {
	            if ("manager".equals(role)) {
	                Optional<Manager> managerOpt = repo.findByUserName(username);
	                return managerOpt.map(ResponseEntity::ok)
	                        .orElseThrow(() -> new EmployeeNotFoundException("Manager not found with username: " + username));
	            } else if ("representative".equals(role)) {
	                Optional<Representative> representativeOpt = repRepo.findByUserName(username);
		            Representative rep = representativeOpt.get();
		            repdto.setCity(rep.getCity());
		            repdto.setState(rep.getState());
		            repdto.setDomain(rep.getDomain());
		            repdto.setEmpId(rep.getEmpId());
		            repdto.setManagerId(rep.getManager().getEmpId());
		            repdto.setfName(rep.getfName());
		            repdto.setlName(rep.getlName());
		            repdto.setPhone_no(rep.getPhone_no());
		            repdto.setPasswordChanged(rep.getPasswordChanged());
		            repdto.setUsername(rep.getUserName());
		            return new ResponseEntity<>(repdto,HttpStatus.OK);
	            }
	            throw new InvalidRoleException("Invalid role specified");
	        } catch (EmployeeNotFoundException | InvalidRoleException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new InternalServerErrorException("An error occurred while processing the request.", e);
	        }
	    }
	    public ResponseEntity<?> updateEmployee(String username, String role, UpdateEmployeeRequest updateRequest) {
	        try {
	            if ("representative".equals(role)) {
	                Optional<Representative> representativeOpt = repRepo.findByUserName(username);
	                if (representativeOpt.isPresent()) {
	                    Representative representative = representativeOpt.get();
	                    representative.setfName(updateRequest.getfName());
	                    representative.setlName(updateRequest.getlName());
	                    representative.setState(updateRequest.getState());
	                    representative.setCity(updateRequest.getCity());
	                    representative.setPhone_no(updateRequest.getPhone_no());
	                    Representative savedRep = repRepo.save(representative);
	                    UpdateEmployeeResponse response = new UpdateEmployeeResponse(
	                            savedRep.getEmpId(),
	                            savedRep.getUserName(),
	                            savedRep.getfName(),
	                            savedRep.getlName(),
	                            savedRep.getState(),
	                            savedRep.getCity(),
	                            savedRep.getPhone_no()
	                    );
	                    return ResponseEntity.ok(response);
	                } else {
	                    throw new EmployeeNotFoundException("Representative not found with username: " + username);
	                }
	            } else if ("manager".equals(role)) {
	                Optional<Manager> managerOpt = repo.findByUserName(username);
	                if (managerOpt.isPresent()) {
	                    Manager manager = managerOpt.get();
	                    manager.setfName(updateRequest.getfName());
	                    manager.setlName(updateRequest.getlName());
	                    manager.setState(updateRequest.getState());
	                    manager.setCity(updateRequest.getCity());
	                    manager.setPhone_no(updateRequest.getPhone_no());
	                    Manager savedMan = repo.save(manager);
	                    UpdateEmployeeResponse response = new UpdateEmployeeResponse(
	                            savedMan.getEmpId(),
	                            savedMan.getUserName(),
	                            savedMan.getfName(),
	                            savedMan.getlName(),
	                            savedMan.getState(),
	                            savedMan.getCity(),
	                            savedMan.getPhone_no()
	                    );
	                    return ResponseEntity.ok(response);
	                } else {
	                    throw new EmployeeNotFoundException("Manager not found with username: " + username);
	                }
	            }
	            throw new InvalidRoleException("Invalid role specified");
	        } catch (EmployeeNotFoundException | InvalidRoleException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new InternalServerErrorException("An error occurred while processing the request.", e);
	        }
	    }
	 
	    @Transactional
	    public EmployeeResponse getLeastTicketRepresentative(String domain) {
	        try {
	            Representative leastTicketRepresentative = repRepo.findFirstByDomainOrderByNumberOfTicketsAsc(domain);
	            if (leastTicketRepresentative == null) {
	                throw new EmployeeNotFoundException("No representatives found for domain: " + domain);
	            }
	 
	            // Increase the number of tickets by 1
	            leastTicketRepresentative.setNumberOfTickets(leastTicketRepresentative.getNumberOfTickets() + 1);
	 
	            // Save the updated representative back to the database
	            repRepo.save(leastTicketRepresentative);
	 
	            EmployeeResponse response = new EmployeeResponse();
	            response.setEmpId(leastTicketRepresentative.getEmpId());
	            response.setManagerId(leastTicketRepresentative.getManager().getEmpId());
	            response.setUserName(leastTicketRepresentative.getUserName());
	 
	            return response;
	        } catch (EmployeeNotFoundException ex) {
	            throw ex;
	        } catch (Exception e) {
	            throw new InternalServerErrorException("An error occurred while processing the request.", e);
	        }
	        
	        
	        
	        
	    }
	    
	    //pagination
	    
	     
		public List<ManagerDto> getManagersWithPagination(int offset, int pageSize){   
	    	 List<ManagerDto> managers= repo.getManagers(PageRequest.of(offset, pageSize));
	    	 return managers;
	     }
		
		public List<RepresentativeDto> findRepsWithPagination(int offset, int pageSize){
	    	 List<RepresentativeDto> all = repRepo.getRepresentatives(PageRequest.of(offset, pageSize));
	    	 return all;
	    }
	    
		public Long getManagerCount() {
			long count = repo.countAllManagers();
			System.out.println(count);
			return count;
		}
		
		public Long getRepCount() {
			long count = repRepo.countAllRepresentatives();
			System.out.println(count);
			return count;
		}


	
	    
	   
		
}