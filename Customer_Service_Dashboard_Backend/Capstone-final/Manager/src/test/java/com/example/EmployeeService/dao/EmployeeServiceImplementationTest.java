package com.example.EmployeeService.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.EmployeeService.EmployeeApplication;
import com.example.EmployeeService.controller.EmployeeController;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EmployeeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeServiceImplementationTest {

	
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
	    void testCalculateTop5RepWiseAverageResponseTime() {
	    	Map<Long, Double> result = new HashMap<>();
	    	result.put(14L,30.0);
	        Map<Long, Double> top5ResponseTimes = (Map<Long, Double>) empService.getResponseAverageByManagerId(26L);
	        assertEquals(1, top5ResponseTimes.size());
	        //assertEquals(30.0, top5ResponseTimes.get(14L));
	    }

	    @Test
	    void testCalculateTop5RepWiseAverageResolutionTime() {
	    	Map<Long, Double> result = new HashMap<>();
	    	result.put(14L,90.0);
	    	ResponseEntity<?> top5ResolutionTimes = empService.getResolutionAverageByManagerId(1L);
	        assertEquals(result, top5ResolutionTimes.getBody());
	        //assertEquals(90.0, top5ResolutionTimes.get(14L));
	    }

	    @Test
	    void testGetTicketCountsByStatus() {
	    	Map<String,Long> result = new HashMap<String,Long>();
	    	result.put("OPEN", 1L);

	        ResponseEntity<?> ticketCounts =  empService.getTicketCountsByStatus(28L);
	        assertEquals(result, ticketCounts);
	       
	    }

	    @Test
	    void testGetAverageResponseTimeByRepId() {
	    	Double result = 20.0;
	        ResponseEntity<Double> averageResponseTime = empService.getAverageResponseTimeByRepId(18L);
	        System.out.println(averageResponseTime.getBody());
	        assertEquals(result, averageResponseTime.getBody());
	    }

	    @Test
	    void testGetAverageResolutionTimeByRepId() {
	        ResponseEntity<?> averageResolutionTime = empService.getAverageResolutionTimeByRepId(18L);
	        assertEquals(80.0, averageResolutionTime.getBody());
	    }

	 

	    @Test
	    void testGetTicketCountsByStatusForRep() {
	    	Map<String,Long> result = new HashMap<String,Long>();
	    	result.put("OPEN", 1L);
	        Map<String, Long> ticketCounts =  (Map<String, Long>) empService.getTicketCountsByStatusForRep(28L);
	        assertEquals(result, ticketCounts);
	    }

	    @Test
	    void testGetAverageResponseTimeByDayOfWeek() {
	        Map<String,Float> result = new HashMap<String,Float>();
	    	result.put("FRIDAY", 50f);
	        Map<String, Float> avgResponseTimes = empService.getAverageResponseTime(28L);
	        assertEquals(result, avgResponseTimes);
	    }

	    @Test
	    void testGetAverageResolutionTimeByDayOfWeek() {
	    	
	        Map<String,Float> result = new HashMap<String,Float>();
	    	result.put("FRIDAY", 20f);
	       
	        Map<String, Float> avgResolutionTimes = empService.getAverageResolutionTime(28L);
	        assertEquals(result, avgResolutionTimes);
	    }
	    
	    @Test
	    void testGetAverageResponseTimeByDayOfWeekIfRepNotFound() {
	        
	        Map<String,Float> result = new HashMap<String,Float>();
	        Map<String, Float> avgResponseTimes = empService.getAverageResponseTime(1L);
	        assertEquals(result, avgResponseTimes);
	    }

	    @Test
	    void testGetAverageResolutionTimeByDayOfWeekIfRepNotFound() {
	    	
	        Map<String,Float> result = new HashMap<String,Float>();
	        Map<String, Float> avgResolutionTimes = empService.getAverageResolutionTime(1L);
	        assertEquals(result, avgResolutionTimes);
	    }

}


