package com.example.Customer.feign;
import com.example.Customer.dto.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Manager", url = "http://localhost:8086")
public interface EmployeeFeign {

 @GetMapping("/employee/leastTicketRepresentative")
 EmployeeResponse getLeastTicketRepresentative(@RequestParam String domain);
}
