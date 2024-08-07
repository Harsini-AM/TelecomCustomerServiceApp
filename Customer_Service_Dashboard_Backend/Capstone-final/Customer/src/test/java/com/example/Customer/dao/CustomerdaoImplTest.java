package com.example.Customer.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.Customer.CustomerApplication;
import com.example.Customer.entity.Ticket;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerdaoImplTest {

	 @Autowired
	  private CustomerdaoImpl customerdao;
	 
	  @Test
	  void testGetTicketsByManagerId() {
	       List<Ticket> tickets = customerdao.getTicketsByManagerId(26L);
	        assertEquals(1, tickets.size());
	    }
	  
	  @Test
	  void testGetTicketsByManagerIdWhenNotPresent() {
	       List<Ticket> tickets = customerdao.getTicketsByManagerId(29L);
	        assertEquals(0, tickets.size());
	    }
	 
	  

	    @Test
	    void testCalculateTop5RepWiseAverageResponseTime() {
	    	Map<Long, Double> result = new HashMap<>();
	    	result.put(14L,30.0);
	        Map<Long, Double> top5ResponseTimes = customerdao.calculateTop5RepWiseAverageResponseTime(26L);
	        assertEquals(1, top5ResponseTimes.size());
	        assertEquals(30.0, top5ResponseTimes.get(14L));
	    }

	    @Test
	    void testCalculateTop5RepWiseAverageResolutionTime() {
	    	Map<Long, Double> result = new HashMap<>();
	    	result.put(14L,90.0);
	        Map<Long, Double> top5ResolutionTimes = customerdao.calculateTop5RepWiseAverageResolutionTime(1L);
	        assertEquals(1, top5ResolutionTimes.size());
	        assertEquals(90.0, top5ResolutionTimes.get(14L));
	    }

	    @Test
	    void testGetTicketCountsByStatus() {
	    	Map<String,Long> result = new HashMap<String,Long>();
	    	result.put("OPEN", 1L);

	        Map<String, Long> ticketCounts = customerdao.getTicketCountsByStatus(26L);
	        assertEquals(result, ticketCounts);
	       
	    }

	    @Test
	    void testGetAverageResponseTimeByRepId() {
	        double averageResponseTime = customerdao.getAverageResponseTimeByRepId(18L);
	        assertEquals(20.0, averageResponseTime);
	    }

	    @Test
	    void testGetAverageResolutionTimeByRepId() {
	        double averageResolutionTime = customerdao.getAverageResolutionTimeByRepId(18L);
	        assertEquals(80.0, averageResolutionTime);
	    }

	 

	    @Test
	    void testGetTicketCountsByStatusForRep() {
	    	Map<String,Long> result = new HashMap<String,Long>();
	    	result.put("OPEN", 1L);

	        Map<String, Long> ticketCounts = customerdao.getTicketCountsByStatusForRep(28L);
	        assertEquals(result, ticketCounts);
	    }

	    @Test
	    void testGetAverageResponseTimeByDayOfWeek() {
	        LocalDate currentDate = LocalDate.now();
	        Map<String,Float> result = new HashMap<String,Float>();
	    	result.put("FRIDAY", 50f);
	        Map<String, Float> avgResponseTimes = customerdao.getAverageResponseTimeByDayOfWeek(28L, currentDate);
	        assertEquals(result, avgResponseTimes);
	    }

	    @Test
	    void testGetAverageResolutionTimeByDayOfWeek() {
	    	LocalDate currentDate = LocalDate.now();
	        Map<String,Float> result = new HashMap<String,Float>();
	    	result.put("FRIDAY", 20f);
	        LocalDate currentDate1 = LocalDate.now();
	        Map<String, Float> avgResolutionTimes = customerdao.getAverageResolutionTimeByDayOfWeek(28L, currentDate1);
	        assertEquals(result, avgResolutionTimes);
	    }
	    
	    @Test
	    void testGetAverageResponseTimeByDayOfWeekIfRepNotFound() {
	        LocalDate currentDate = LocalDate.now();
	        Map<String,Float> result = new HashMap<String,Float>();
	        Map<String, Float> avgResponseTimes = customerdao.getAverageResponseTimeByDayOfWeek(1L, currentDate);
	        assertEquals(result, avgResponseTimes);
	    }

	    @Test
	    void testGetAverageResolutionTimeByDayOfWeekIfRepNotFound() {
	    	LocalDate currentDate = LocalDate.now();
	        Map<String,Float> result = new HashMap<String,Float>();
	        LocalDate currentDate1 = LocalDate.now();
	        Map<String, Float> avgResolutionTimes = customerdao.getAverageResolutionTimeByDayOfWeek(1L, currentDate1);
	        assertEquals(result, avgResolutionTimes);
	    }

	    @Test
	    void testGetAllTickets() {
	        List<Ticket> tickets = customerdao.getAllTickets();
	        assertEquals(7, tickets.size());
	    }
	  
	  


}
