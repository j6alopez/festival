package com.festival.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	@GetMapping(value = "/allTime")
	@ResponseBody
	public String getAlltimes() {
		
		int totalComulativeConsumed=0;
		List <Consumption> consumptionsList = consumptionRepository.findAll();
		HashMap<Integer, Integer> consumptionsMap = new HashMap<Integer, Integer>();
		
		for (Dispenser dispenser : dispenserRepository.findAll()) {
			
			consumptionsMap.put(dispenser.getId(), 0);
		}
		
		for (Consumption consumption : consumptionsList) {
			
			totalComulativeConsumed += consumption.getConsumedSeconds();
			
			if (consumptionsMap.containsKey(consumption.getId())){
				
				int cumulativeConsumed = consumptionsMap.get(consumption.getId()) + consumption.getConsumedSeconds();
				
				consumptionsMap.put(consumption.getId(),cumulativeConsumed );
				
			}else {
				
				consumptionsMap.put(consumption.getId(),consumption.getConsumedSeconds());
				
			}
					
		}

		String json =  ResponseHandler.generateResponseAllTimes(consumptionsMap);
		return json;
	}	

	@PostMapping(value = "/tracktime/{id}")
	
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
