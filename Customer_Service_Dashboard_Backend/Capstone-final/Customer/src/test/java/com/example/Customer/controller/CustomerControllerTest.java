package com.example.Customer.controller;
 
import com.example.Customer.CustomerApplication;
import com.example.Customer.dto.*;
import com.example.Customer.entity.Customer;
import com.example.Customer.entity.Ticket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
 
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
 
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {
 
    @Autowired
    private TestRestTemplate restTemplate;
 
    private Customer customer;
    private Ticket ticket;
    
    ObjectMapper objectMapper = new ObjectMapper();
 
    @BeforeEach 
    void setUp() {
        customer = new Customer();
        customer.setUserName("testUser");
        customer.setPassword("password");
        customer.setfName("FirstName");
        customer.setlName("LastName");
        customer.setCity("City");
        customer.setState("State");
        customer.setPhone_no(1234567890L);
        customer.setPlanType("Basic");
        customer.setPlanName("Basic Plan");
        customer.setPlanDescription("Basic plan description");
 
        ticket = new Ticket();
        ticket.setDomain("network");
        ticket.setDescription("Issue description");
        ticket.setEmpId(1L);
        ticket.setManagerId(1L);
        ticket.setStatus("open");
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setResponseTime(2.5f);
        ticket.setResolutionTime(3.5f);
        ticket.setCustomer(customer);
 
        customer.setTickets(Collections.singletonList(ticket));
    }
 
    @Test
    void testGetTickets() {
        ResponseEntity<?> response = restTemplate.getForEntity("/customer/getTickets", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
 
    @Test
    void testGetResponseAverageByManagerId() {
    	Map<String,Double> result = new HashMap<String,Double>();
    	result.put("14", 30.0);
    	String json="";
    	try {
            json = objectMapper.writeValueAsString(result);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ResponseEntity<?> response = restTemplate.getForEntity("/customer/responseAverage/26", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(json, response.getBody());
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
        ResponseEntity<?> response = restTemplate.getForEntity("/customer/resolutionAverage/26", String.class);
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
        ResponseEntity<?> response = restTemplate.getForEntity("/customer/statusCounts/26", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(json,response.getBody());
    }
 
    @Test
    void testGetAverageResponseTimeByRepId() {
    	Double result = 50.0;
        ResponseEntity<?> response = restTemplate.getForEntity("/customer/averageResponseTime/28", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(result, response.getBody());
    }
 
    @Test
    void testGetAverageResolutionTimeByRepId() {
    	Double result = 20.0;
        ResponseEntity<?> response = restTemplate.getForEntity("/customer/averageResolutionTime/28", String.class);
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
        ResponseEntity<?> response = restTemplate.getForEntity("/customer/statusCountsForRep/28", String.class);
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
        ResponseEntity<?> response = restTemplate.getForEntity("/customer/weeklyResponseTime/28", String.class);
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
        ResponseEntity<?> response = restTemplate.getForEntity("/customer/weeklyResolutionTime/28", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(json,response.getBody());
    }
}
