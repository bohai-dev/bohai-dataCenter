package com.bohai.dataCenter.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateFormatterUtil {
	
	public static Date getDate(String dateStr) throws ParseException{
		
		if(StringUtils.isEmpty(dateStr)){
			return null;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(dateStr);
	}
	
	public static Date getDateyyyyMMdd(String dateStr) throws ParseException{
		
		if(StringUtils.isEmpty(dateStr)){
			return null;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.parse(dateStr);
	}
	
	public static String getCurrentDateStr(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return simpleDateFormat.format(new Date());
	}
	
	
	public static String getDateStr(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return simpleDateFormat.format(date);
	}
	
	public static String getDateStryyyyMMdd(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		return simpleDateFormat.format(date);
	}

}
