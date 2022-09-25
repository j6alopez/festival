package com.festival.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.festival.helper.StackTraceHelper;
import com.festival.model.Consumption;
import com.festival.model.Dispenser;
import com.festival.model.FestivalPrice;

public class ResponseHandler {
	
	public static String generateResponseAllTimes(List <Consumption> consumptionsList, Dispenser dispenser) {
        	
		int totalConsumedTime=0;
		BigDecimal totalConsumedPrice = BigDecimal.ZERO;

		ObjectMapper mapper = new ObjectMapper();
        ObjectNode mainNode  = mapper.createObjectNode();      
        ArrayNode arrayUsagesNode = mapper.createArrayNode();		        
		
        
        // each entry in map is a Json
        
		for(Consumption consumption : consumptionsList ) {
			
			ObjectNode dispenserNode = mapper.createObjectNode();
			
			BigDecimal consumedPrice = BigDecimal.ZERO;
			BigDecimal consumedTime  = BigDecimal.valueOf(consumption.getConsumedSeconds());
			
			consumedPrice = consumedTime.multiply(FestivalPrice.getValue());
			totalConsumedPrice = totalConsumedPrice.add(consumedPrice);			
			
			dispenserNode.put("opened_at",consumption.getTapInstantOpened());
			dispenserNode.put("closed_at",consumption.getTapInstantClosed());
			dispenserNode.put("flow_volume",dispenser.getFlow_volume());
			dispenserNode.put("total_spent",consumedPrice);
			dispenserNode.put("currency",FestivalPrice.getCurrency());
			
			arrayUsagesNode.add(dispenserNode);
					
		}
		
		mainNode.put("amount", totalConsumedPrice);
		mainNode.set("usages", arrayUsagesNode);
		
		
		try {
			return mapper.writerWithDefaultPrettyPrinter()
//						 .withRootName("data")
						 .writeValueAsString(mainNode);
			
		} catch (JsonProcessingException e) {

			return StackTraceHelper.stakTraceToString(e);
		}

            
    }

	public static String generateResponseDispenserCreated(Dispenser dispenser) {
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectnode = mapper.createObjectNode();

		objectnode.put("id", dispenser.getId());
		objectnode.put("flow_volume", dispenser.getFlow_volume());
        
		try {
			return mapper.writerWithDefaultPrettyPrinter()
					 .withoutRootName()
					 .writeValueAsString(objectnode);
		} catch (JsonProcessingException e) {
			
			return StackTraceHelper.stakTraceToString(e);
		}
        
	}
	public static String generateResponseDispenserStatus(Dispenser dispenser, String timeStamp) {
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectnode = mapper.createObjectNode();
		
		String status = dispenser.isCurrent_opened() ? "opened" : "closed";
		
        objectnode.put("status", status);
        objectnode.put("updated_at",timeStamp) ;
        
		try {
			return mapper.writerWithDefaultPrettyPrinter()
					 .withoutRootName()
					 .writeValueAsString(objectnode);
		} catch (JsonProcessingException e) {
			
			return StackTraceHelper.stakTraceToString(e);
		}
        
	}	
}
