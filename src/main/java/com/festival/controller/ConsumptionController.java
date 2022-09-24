package com.festival.controller;

import java.time.Instant;
import java.util.Date;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.festival.model.Consumption;
import com.festival.model.Dispenser;
import com.festival.repository.IConsumptionRepository;
import com.festival.repository.IDispenserRepository;

@RestController
public class ConsumptionController {
	
	@Autowired
	private IConsumptionRepository consumptionRepository;
	
	@Autowired
	private IDispenserRepository dispenserRepository;

	@PostMapping(value = "/dispensers/tracktime/{id}")
	
	public String setConsumption(@PathVariable Integer id, @RequestHeader(HttpHeaders.DATE) String headerDate) {
		
		if (dispenserRepository.existsById(id)) {
			
			Dispenser dispenser = dispenserRepository.findById(id).get(); 
			Consumption consumption;
			
			if (dispenser.isCurrent_opened()) { 

				dispenser.setCurrent_opened(false);
				consumption = consumptionRepository.consumptionOpened(id);
				consumption.setTapInstantClosed(headerDate);
				consumption.setEnded(true);

				
			}else {
				consumption = new Consumption(id);			
				dispenser.setCurrent_opened(true);
				consumption.setTapInstantOpened(headerDate);			
			}
			
			dispenserRepository.save(dispenser);
			consumptionRepository.save(consumption);
			
		}						

		return "TO BE MODIFIED";
	}

}
