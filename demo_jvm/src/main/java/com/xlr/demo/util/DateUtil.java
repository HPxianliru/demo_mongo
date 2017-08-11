package com.xlr.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.Assert;

public class DateUtil {
	public enum Formatter{
		/**
		 * yyyy-MM-dd
		 */
		YEAR_TO_DAY,
		/**
		 * yyyy-MM-dd HH
		 */
		YEAR_TO_HOUR,
		/**
		 * yyyy-MM-dd HH:mm
		 */
		YEAR_TO_MINUTE,
		/**
		 * yyyy-MM-dd HH:mm:ss
		 */
		YEAR_TO_SECOND,
		/**
		 * yyyy-MM-dd HH:mm:ss:sss
		 */
		YEAR_TO_MILLISECOND;
	}
	
	static Map<Formatter,DateTimeFormatter> formatterMap = new HashMap<Formatter,DateTimeFormatter>();
	
	private static final DateTimeFormatter YEAR_TO_DAY = DateTimeFormat.forPattern("yyyy-MM-dd");
	
	private static final DateTimeFormatter YEAR_TO_HOUR = DateTimeFormat.forPattern("yyyy-MM-dd HH");
	
	private static final DateTimeFormatter YEAR_TO_MINUTE = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
	
	private static final DateTimeFormatter YEAR_TO_SECOND = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	
	private static final DateTimeFormatter YEAR_TO_MILLISECOND= DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss:sss");

	static{
		formatterMap.put(Formatter.YEAR_TO_DAY, YEAR_TO_DAY);
		formatterMap.put(Formatter.YEAR_TO_HOUR, YEAR_TO_HOUR);
		formatterMap.put(Formatter.YEAR_TO_MINUTE, YEAR_TO_MINUTE);
		formatterMap.put(Formatter.YEAR_TO_SECOND, YEAR_TO_SECOND);
		formatterMap.put(Formatter.YEAR_TO_MILLISECOND, YEAR_TO_MILLISECOND);

	}
	
	/**
	 * 根据formatter指定的日期格式，日期转换字符串
	 * @param date
	 * @param formatter
	 * @return
	 */
	public final static String toFormatString(Date date,Formatter formatter){
		Assert.notNull(date);
		Assert.notNull(formatter);
		return formatterMap.get(formatter).print(new DateTime(date.getTime()));
	}
	
	/**
	 * 根据formatter指定的日期格式，字符串转换日期
	 * @param date
	 * @param formatter
	 * @return Date
	 */
	public final static Date toFormatDate(String date,Formatter formatter){
		return DateTime.parse(date, formatterMap.get(formatter)).toDate();
	}
	
	/**
	 * 根据formatter指定的日期格式，字符串转换日期
	 * @param date
	 * @param formatter
	 * @return Date
	 */
	public final static DateTime toFormatDateTime(String date){
		DateTimeFormatter f = DateTimeFormat.forPattern("yyyy-MM-dd").withZoneUTC();  
	    return f.parseDateTime(date);
	}
	
	/**
	 * 增加year年，返回一个new Date。
	 * @param date
	 * @param year
	 * @return
	 */
	public final static Date addYear(Date date,int year){
		return new DateTime(date.getTime()).plusYears(year).toDate();
	}
	
	/**
	 * 增加月，返回一个new date
	 * @param date
	 * @param month
	 * @return
	 */
	public final static Date addMonth(Date date,int month){
		return new DateTime(date.getTime()).plusMonths(month).toDate();
	}
	
	/**
	 * 增加日，返回一个new date
	 * @param date
	 * @param day
	 * @return
	 */
	public final static Date addDay(Date date,int day){
		return new DateTime(date.getTime()).plusDays(day).toDate();
	}
	
	/**
	 * 增加小时，返回一个new date
	 * @param date
	 * @param hour
	 * @return
	 */
	public final static Date addHour(Date date,int hour){
		return new DateTime(date.getTime()).plusHours(hour).toDate();
	}
	
	/**
	 * 增加分，返回一个new date
	 * @param date
	 * @param minutes
	 * @return
	 */
	public final static Date addMinute(Date date,int minute){
		return new DateTime(date.getTime()).plusMinutes(minute) .toDate();
	}
	
	
	/**
	 * 增加秒，返回一个new date
	 * @param date
	 * @param minutes
	 * @return
	 */
	public final static Date addSecond(Date date,int second){
		return new DateTime(date.getTime()).plusSeconds(second) .toDate();
	}
	
	/**
	 * 获取两个date之间的差值（精确到日）
	 * @param start
	 * @param end
	 * @return Period
	 */
	
	public final static Period getPeriod(Date start,Date end){
		Assert.notNull(start);
		Assert.notNull(end);
		Interval interval = new Interval(new DateTime(start.getTime()),new DateTime(end.getTime()));
		
		return interval.toPeriod();
	}

	/**
	 * 获取两个datetime之间的差值
	 * @param start
	 * @param end
	 * @return Period
	 */
	
	public final static Period getPeriod(DateTime start,DateTime end){
		Assert.notNull(start);
		Assert.notNull(end);
		Interval interval = new Interval(start,end);
		return interval.toPeriod();
	}
	
	/**
	 * 获取start与nowTI相差天数
	 * @param start
	 * @return days
	 */
	
	public final static long getDays(String start,long nowTime){
		Assert.notNull(start);
		long from = 0;
		try {
			from = new SimpleDateFormat("yyyy-MM-dd").parse(start).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (nowTime - from)/(1000*60*60*24);
	}
	
	public final static String compareDate(Date start,Date first){
		
		    String dateStr = "";
		    if(first.after(start)){//[1023910000]请检查您的起保日期或初登日期，起保日期必须大于初登日期
	 			dateStr = "1023910000";
	 		}
	 		return dateStr;
	}
	
	/**
	 * 获取两个date之间相差天数
	 * @param start
	 * @param end
	 * @return
	 */
	public final static long getDurationDays(Date start,Date end){
		Assert.notNull(start);
		Assert.notNull(end);
		Interval interval = new Interval(new DateTime(start.getTime()),new DateTime(end.getTime()));
		return interval.toDuration().getStandardDays();
	}
    
    /** 
 	 * 时间转换str-->date工具
 	 * @param antDateStr
 	 * @return
 	 */
 	public static Date getStrToDate(String antDateStr) {
 		Date date = null;
 		try {
 			int length = antDateStr.length();
 			SimpleDateFormat sdf = new SimpleDateFormat();
 			if (length==8) {
 				sdf = new SimpleDateFormat("yyyyMMdd");
 			}else if (length==10) {
 				sdf = new SimpleDateFormat("yyyy-MM-dd");
 			}else if (length==13) {
 				sdf = new SimpleDateFormat("yyyy-MM-dd HH");
 			}else if (length==14) {
 				sdf = new SimpleDateFormat("yyyyMMddHHmmss");
 			}else if (length==19) {
 				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 			}else {
 				return null;
 			}
 			date = sdf.parse(antDateStr);
 		} catch (ParseException e) {
 			e.printStackTrace();
 			return null;
 		}
         return date;
     }
 	
 	//当前毫秒转换 当前时间 
 	public static String minDate(long millisecond){
	    Date date = new Date(millisecond);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    System.out.println("毫秒["+millisecond+"]对应日期时间字符串：" + format.format(date));
		return format.format(date);
 	}
 	
 	public static void main(String[] args) {

		try {
			Date first = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2007-04-19 00:00:01");
			Date start = new SimpleDateFormat("yyyy-MM-dd").parse("2007-04-19");
	 		if(first.getTime()>start.getTime()){
	 			System.out.println(true);
	 		}else if(first.getTime()==start.getTime()){
	 			System.out.println("==");
	 		}
		} catch (Exception e) {
			e.printStackTrace();
		}
 		
	}
}
