package com.festival.controller;

import java.util.List;
import com.festival.model.Dispenser;
import com.festival.repository.IDispenserRepository;
import com.festival.response.ResponseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "dispensers")
public class DispenserController {
	
	@Autowired
	private IDispenserRepository dispenserRepository;
	
	@GetMapping(value = "/")
	public String getGreeting() {
		
		return "Welcome to API";
	}
	
	@GetMapping
	public List<Dispenser> getDispensers( ) {
		 return dispenserRepository.findAll();
	}	
	
	@PostMapping	
	public ResponseEntity<String> createDispenser(@RequestBody Dispenser dispenser) {
		
		try {
			dispenser = dispenserRepository.save(dispenser);

			String json = ResponseHandler.generateResponseDispenserCreated(dispenser);
			return ResponseEntity.status(HttpStatus.CREATED).body(json);
			
		}catch (Exception e) {
			
			String json = "Unexpected API error";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(json);
			
		}
	}
	

}
