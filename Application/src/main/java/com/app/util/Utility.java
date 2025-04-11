package com.app.util;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utility {
	
	private static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"); 

	public static OffsetDateTime getCurrentTime() {		
		OffsetDateTime date = OffsetDateTime.now(ZoneId.systemDefault());	
		return formatDate(date);      
	}

	public static OffsetDateTime formatDate(OffsetDateTime date) {
		String formattedDate = date.format(outputFormatter);    
		date =OffsetDateTime.parse(formattedDate,outputFormatter);
		System.out.println(date);
		return date;
	}
	
	private static String getDateString(OffsetDateTime date) {
		String formattedDate = date.format(outputFormatter);    
		date =OffsetDateTime.parse(formattedDate,outputFormatter);
		System.out.println(date);
		return date.toString();
	}

}
