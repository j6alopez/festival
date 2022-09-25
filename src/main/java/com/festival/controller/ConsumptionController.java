package com.festival.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.festival.helper.StackTraceHelper;
import com.festival.model.Consumption;
import com.festival.model.Dispenser;
import com.festival.repository.IConsumptionRepository;
import com.festival.repository.IDispenserRepository;
import com.festival.response.ResponseHandler;

@RestController
@RequestMapping(value = "dispensers")
public class ConsumptionController {
	
	@Autowired
	private IConsumptionRepository consumptionRepository;
	
	@Autowired
	private IDispenserRepository dispenserRepository;
	@GetMapping(value = "/{id}/spending")
	@ResponseBody
	public  ResponseEntity<String>  getSpendings(@PathVariable Integer id) {
		
		int totalComulativeConsumed=0;
		
		try {
			List <Consumption> consumptionsList = consumptionRepository.getDispenserConsumptions(id);
			
			if (!dispenserRepository.existsById(id)) {
				
				String json = "Requested dispenser does not exist";
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
			}
			
			Dispenser dispenser = dispenserRepository.findById(id).get();
			

			String json =  ResponseHandler.generateResponseAllTimes(consumptionsList,dispenser);
			return ResponseEntity.status(HttpStatus.OK).body(json);
		} catch (Exception e) {
			String json = StackTraceHelper.stakTraceToString(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(json);
		}
	}	

	@PutMapping(value = "/{id}/status")
	
	public ResponseEntity<String> setConsumption(@PathVariable Integer id, @RequestHeader(HttpHeaders.DATE) String headerDate) {
				

		if (dispenserRepository.existsById(id)) {
			
			try {
				Dispenser dispenser = dispenserRepository.findById(id).get(); 
				Consumption consumption;
				
				if (dispenser.isCurrent_opened()) { 

					dispenser.setCurrent_opened(false);
					consumption = consumptionRepository.getConsumptionOpened(id);
					consumption.setTapInstantClosed(headerDate);
					consumption.setEnded(true);

					
				}else {
					consumption = new Consumption(id);			
					dispenser.setCurrent_opened(true);
					consumption.setTapInstantOpened(headerDate);			
				}
				
				dispenser = dispenserRepository.save(dispenser);
				consumptionRepository.save(consumption);
				
				String json =ResponseHandler.generateResponseDispenserStatus(dispenser, Instant.now().toString());
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(json);
				
			} catch (Exception e) {
				
				String json = StackTraceHelper.stakTraceToString(e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected API error");
			}
		}
		else {

			String json = "Dispenser not created";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(json);
		}
	}

}
