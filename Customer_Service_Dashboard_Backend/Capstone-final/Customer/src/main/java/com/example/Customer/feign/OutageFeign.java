package com.example.Customer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
 
import com.example.Customer.dto.Outage;
 
import java.util.List;
 
@FeignClient(name = "outage", url = "http://localhost:8089/outage")
public interface OutageFeign {
    @GetMapping("/get")
    List<Outage> getAllOutages();
}
