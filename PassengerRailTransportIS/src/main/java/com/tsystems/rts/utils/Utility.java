package com.tsystems.rts.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
	
	public static Date convertString(String str) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		return format.parse(str);
	}
	
}
