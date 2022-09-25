package com.festival.model;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public final class FestivalPrice {
	

	private static final String currency = "EUR";
	private static final BigDecimal  value = new BigDecimal("1.51");

	
	public static String getCurrency() {
		return currency;
	}
	
	public static BigDecimal getValue() {
		return value;
	}
	

}
