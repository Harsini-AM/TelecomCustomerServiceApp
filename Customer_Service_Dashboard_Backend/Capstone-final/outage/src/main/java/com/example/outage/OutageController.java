package com.example.outage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;


@RestController
@RequestMapping("/outage")
@CrossOrigin(origins = "http://localhost:3000")
public class OutageController {
	@Autowired
	OutageRepo repo;
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<String>("Welcome",HttpStatus.OK);
	}
	
	@GetMapping("/get")
    public List<Outage> getAllItems() {
        return repo.findAll();
    }
	

	@PostMapping("/addOutage")
	public ResponseEntity<Outage> addCustomer(@RequestBody Outage cus){
		Outage customer = repo.save(cus);
		return new ResponseEntity<Outage>(customer,HttpStatus.OK);
	}
}
