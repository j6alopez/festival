package com.festival.helper;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceHelper {

	public static String stakTraceToString(Exception e) {
		
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);			
	    e.printStackTrace(printWriter);
	    
	    return printWriter.toString();
		
	}
}
