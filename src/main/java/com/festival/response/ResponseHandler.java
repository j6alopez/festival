package com.festival.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.festival.helper.StackTraceHelper;
import com.festival.model.Dispenser;
import com.festival.model.FestivalPrice;

public class ResponseHandler {
	
	public static String generateResponseAllTimes(HashMap<Integer, Integer> consumptionsMap) {
        	
		Iterator <Entry<Integer,Integer>> iterator = consumptionsMap.entrySet().iterator();
		int totalConsumedTime=0;
		BigDecimal totalConsumedPrice = BigDecimal.ZERO;

		ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();	
        ArrayNode arrayNodeTotal = mapper.createArrayNode();	        
		
        
        // each entry in map is a Json
        
		while(iterator.hasNext()) {
			
			ObjectNode dispenserNode = mapper.createObjectNode();
			Entry<Integer,Integer> entry = iterator.next();
			
			BigDecimal consumedPrice = BigDecimal.ZERO;
			BigDecimal consumedTime  = BigDecimal.valueOf(entry.getValue());
			
			consumedPrice = consumedTime.multiply(FestivalPrice.getValue());
			totalConsumedPrice = totalConsumedPrice.add(consumedPrice);
			totalConsumedTime += entry.getValue();
			
			
			dispenserNode.put("dispenser_id",entry.getKey());
			dispenserNode.put("time_consumed",consumedTime);
			dispenserNode.put("price_value",consumedPrice);
			dispenserNode.put("price_currency",FestivalPrice.getCurrency());
			
			arrayNode.add(dispenserNode);
					
		}
		
		ObjectNode totalNode = mapper.createObjectNode();
		
		totalNode.put("dispenser_id","all_dispensers");
		totalNode.put("total_time_consumed",totalConsumedTime);
		totalNode.put("price_value",totalConsumedPrice);
		totalNode.put("price_currency",FestivalPrice.getCurrency());
					
		arrayNodeTotal.add(totalNode);
		
		try {
			return mapper.writerWithDefaultPrettyPrinter()
//						 .withRootName("data")
						 .writeValueAsString(arrayNode);
			
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
