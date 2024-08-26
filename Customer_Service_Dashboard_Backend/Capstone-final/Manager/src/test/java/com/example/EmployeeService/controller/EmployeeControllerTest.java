package com.example.EmployeeService.controller;

import static org.assertj.core.api.Assertions.setRemoveAssertJRelatedElementsFromStackTrace;	
import static org.junit.jupiter.api.Assertions.*;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hc.core5.http.HttpEntity;
import
org.springframework.http.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.example.EmployeeService.EmployeeApplication;
import com.example.EmployeeService.dao.EmployeeServiceImplementation;
import com.example.EmployeeService.dto.AddEmployeeResponse;
import com.example.EmployeeService.dto.ManagerDto;
import com.example.EmployeeService.dto.RepresentativeDto;
import com.example.EmployeeService.entity.Manager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



import com.example.EmployeeService.entity.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EmployeeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {
	
	 @Autowired
	 private TestRestTemplate restTemplate;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	 @Autowired
	 private EmployeeController employeeController;
	 @Autowired
	 private EmployeeServiceImplementation empService;

	   
	    @BeforeEach
	    void setUp() {
	        employeeController = new EmployeeController();
	        employeeController.empService = empService;
	    }
	       

	    @Test
	    public void testAddManager() {
	        Manager manager = new Manager();
	        //Manager manager= new Manager();
	        manager.setUserName("User6@LIT.com");
	        manager.setPassword("password");
	        manager.setfName("FirstName");
	        manager.setlName("LastName");
	        manager.setCity("City");
	        manager.setState("State");
	        manager.setPhone_no(1888823458L);
	        manager.setDomain("network");
	        // Set fields for the manager object
	        ResponseEntity<?> response = restTemplate.postForEntity("/employee/addManager", manager, AddEmployeeResponse.class);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());

	    }
	    
	    @Test
	    public void testAddManagerIfAlreadyPresent() {
	        Manager manager = new Manager();
	        //Manager manager= new Manager();
	        manager.setUserName("User1@LIT.com");
	        manager.setPassword("password");
	        manager.setfName("FirstName");
	        manager.setlName("LastName");
	        manager.setCity("City");
	        manager.setState("State");
	        manager.setPhone_no(1234567890L);
	        manager.setDomain("network");
	        // Set fields for the manager object
	        ResponseEntity<?> response = restTemplate.postForEntity("/employee/addManager", manager, String.class);
	        assertNotEquals(HttpStatus.OK, response.getStatusCode());
	        //assertNotNull(response.getBody());
	        
	    }

	    @Test
	    public void testAddRepresentative() {
	        RepresentativeDto rep = new RepresentativeDto();
	        rep.setUserName("User3@LIT.com");
		       rep.setPassword("password");
		       rep.setfName("FirstName");
		       rep.setlName("LastName");
		       rep.setCity("City");
		       rep.setState("State");
		       rep.setPhone_no(1234667890L);
		       rep.setDomain("network");
		       rep.setManagerId(12);
	        // Set fields for the representativeDto object
	        ResponseEntity<?> response = restTemplate.postForEntity("/employee/addRepresentative",rep,AddEmployeeResponse.class);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }
	    
	    
	    @Test
	    public void testAddRepresentativeIfNotPresent() {
	        RepresentativeDto rep = new RepresentativeDto();
	        rep.setUserName("User3@LIT.com");
		       rep.setPassword("password");
		       rep.setfName("FirstName");
		       rep.setlName("LastName");
		       rep.setCity("City");
		       rep.setState("State");
		       rep.setPhone_no(1234667890L);
		       rep.setDomain("network");
		       rep.setManagerId(12);
	        // Set fields for the representativeDto object
	        ResponseEntity<?> response = restTemplate.postForEntity("/employee/addRepresentative",rep,String.class);
	        assertNotEquals(HttpStatus.OK, response.getStatusCode());
	        
	    }

	    @Test
	    public void testGetAllManagers() {
	        ResponseEntity<List> response = restTemplate.getForEntity("/employee/getManagers",List.class);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        //assertNotNull(response.getBody());
	        //assertEquals(2, response.getBody().size());
	    }
	    
	    @Test
	    public void testGetAllRepresentatives() {
	        ResponseEntity<List> response = restTemplate.getForEntity("/employee/getRepresentatives",List.class);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        //assertNotNull(response.getBody());
	        //assertEquals(2, response.getBody().size());
	    }
	    
	    @Test
	    void testGetManagerById() {
	        Long managerId = 39L;
	        ResponseEntity<ManagerDto> response = restTemplate.getForEntity("/employee/getManager/" + managerId, ManagerDto.class);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }
	    @Test
	    void testGetManagerByIdIfNotPresent() {
	        Long managerId = 19L;
	        ResponseEntity<?> response = restTemplate.getForEntity("/employee/getManager/" + managerId, String.class);
	        //assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }
	    
	    
	    

	    @Test
	    void testGetRepresentativeById() {
	        Long representativeId = 21L;
	        ResponseEntity<RepresentativeDto> response = restTemplate.getForEntity("/employee/getRepresentative/" + representativeId, RepresentativeDto.class);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }

	    
	    @Test
	    void testGetRepresentativeByIdIfNotPresent() {
	        Long representativeId = 19L;
	        ResponseEntity<?> response = restTemplate.getForEntity("/employee/getRepresentative/" + representativeId, String.class);
	        //assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	    }
	   
	    @Test
	    void updateManager() {
	        ManagerDto updateRequest = new ManagerDto(77, "harsini@LIT.com","Harsini","A M","Madurai","TN",0, "1234567893","harsini123");
	        updateRequest.setfName("test@test.com");
	 
	        restTemplate.put("/employee/updateManager/"+77, updateRequest);
	    }
	    
	    
	    @Test
	    void updateRepresentative() {
	        RepresentativeDto updateRequest = new RepresentativeDto();
	        updateRequest.setfName("test@test.com");
	 
	        restTemplate.put("/employee/updateManager/"+36, updateRequest);
	    }
	    
	    @Test
	    void getListOfRepresentativeByManagerId() {
	    	ResponseEntity<List> response = restTemplate.getForEntity("/employee/getRepByManagerId/"+39,List.class);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	    	
	    }
	    

	  
   
 
    @Test
    void testGetResolutionAverageByManagerId() {
    	Map<String,Double> result = new HashMap<String,Double>();
    	result.put("14", 90.0);
    	String json="";
    	try {
            json = objectMapper.writeValueAsString(result);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ResponseEntity<?> response = restTemplate.getForEntity("/employee/resolutionAverage/26", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(json, response.getBody());
    }
 
    @Test
    void testGetTicketCountsByStatus() {
    	Map<String,Long> result = new HashMap<String,Long>();
    	result.put("OPEN", 1L);
    	String json="";
    	try {
            json = objectMapper.writeValueAsString(result);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ResponseEntity<?> response = restTemplate.getForEntity("/employee/statusCounts/26", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(json,response.getBody());
    }
 
    @Test
    void testGetAverageResponseTimeByRepId() {
    	Double result = 50.0;
        ResponseEntity<?> response = restTemplate.getForEntity("/employee/averageResponseTime/28", Double.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(result, response.getBody());
    }
 
    @Test
    void testGetAverageResolutionTimeByRepId() {
    	Double result = 20.0;
        ResponseEntity<?> response = restTemplate.getForEntity("/employee/averageResolutionTime/28", Double.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(result, response.getBody());
    }
 
    @Test
    void testGetTicketCountsByStatusForRep() {
    	Map<String,Long> result = new HashMap<String,Long>();
    	result.put("OPEN", 1L);
    	
    	String json="";
    	try {
            json = objectMapper.writeValueAsString(result);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ResponseEntity<?> response = restTemplate.getForEntity("/employee/statusCountsForRep/28", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(json,response.getBody());
    }
 
    @Test
    void testGetAverageResponseTime() {
    	Map<String,Double> result = new HashMap<String,Double>();
    	result.put("FRIDAY", 50.0);
    	
    	String json="";
    	try {
            json = objectMapper.writeValueAsString(result);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ResponseEntity<?> response = restTemplate.getForEntity("/employee/weeklyResponseTime/28", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(json,response.getBody());
    }
 
    @Test
    void testGetAverageResolutionTime() { 
    	Map<String,Double> result = new HashMap<String,Double>();
    	result.put("FRIDAY", 20.0);
    	String json="";
    	try {
            json = objectMapper.writeValueAsString(result);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ResponseEntity<?> response = restTemplate.getForEntity("/employee/weeklyResolutionTime/28", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(json,response.getBody());
    }

}
