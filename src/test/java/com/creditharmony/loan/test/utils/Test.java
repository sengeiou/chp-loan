package com.creditharmony.loan.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	
	@org.junit.Test
	public void dateStr(){

		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMdd");
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd");
		try {
			Date date = formatter.parse("20160513");
			System.out.println(df.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
