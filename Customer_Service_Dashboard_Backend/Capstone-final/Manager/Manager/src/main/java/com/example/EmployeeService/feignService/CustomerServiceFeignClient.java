package com.example.EmployeeService.feignService;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.EmployeeService.dto.Ticket;

import java.util.List;
import java.util.Map;


@FeignClient(name = "Customer", url = "http://localhost:8088") 
public interface CustomerServiceFeignClient {

    @GetMapping("/customer/responseAverage/{managerId}")
    ResponseEntity<Map<Long, Double>> getResponseAverageByManagerId(@PathVariable Long managerId);
    
    @GetMapping("/customer/resolutionAverage/{managerId}")
    ResponseEntity<Map<Long, Double>> getResolutionAverageByManagerId(@PathVariable Long managerId);

    @GetMapping("/customer/statusCounts/{managerId}")
    ResponseEntity<Map<String, Long>> getTicketCountsByStatus(@PathVariable Long managerId);

    @GetMapping("/customer/averageResponseTime/{repId}")
    ResponseEntity<Double> getAverageResponseTimeByRepId(@PathVariable Long repId);

    @GetMapping("/customer/averageResolutionTime/{repId}")
    ResponseEntity<Double> getAverageResolutionTimeByRepId(@PathVariable Long repId);

    @GetMapping("/customer/statusCountsForRep/{repId}")
    ResponseEntity<Map<String, Long>> getTicketCountsByStatusForRep(@PathVariable Long repId);

    @GetMapping("/customer/weeklyResponseTime/{repId}")
    Map<String, Float> getAverageResponseTime(@PathVariable Long repId);

    @GetMapping("/customer/weeklyResolutionTime/{repId}")
    Map<String, Float> getAverageResolutionTime(@PathVariable Long repId);
    
    @GetMapping("/customer/getTicketsByEmpId/{empId}")
	List<Ticket> getTicketsByEmpId(@PathVariable("empId") long empId);
    
    @GetMapping("/customer/ticketCount/{repId}")
    Long getTicketCount(@PathVariable("repId") long repId);
    
    @GetMapping("/customer/ticketCountForManager/{managerId}")
    Long getCountManager(@PathVariable Long managerId);
}
