package com.festival.controller;

import java.util.List;
import com.festival.model.Dispenser;
import com.festival.repository.IDispenserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
	public String createDispenser(@RequestBody Dispenser dispenser) {
		
		boolean dispenserExists = dispenserRepository.existsById(dispenser.getId());

		if (dispenserExists){
			
			return "object already exists";
		}
		
		dispenserRepository.save(dispenser);
		
		return dispenser.getId().toString();
		
	}
	
	@DeleteMapping(value = "/{id}")
	
	public String deleteDispenser(@PathVariable Integer id) {
		
		Dispenser dispenser = dispenserRepository.findById(id).get();
		dispenserRepository.delete(dispenser);
		
		return "{ 'message' : deleted}";
		
	}
	

}
