package com.xlr.demo;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Testaa {
	public static void main(String[] args) {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();  
		long millisecond = runtime.getStartTime();
	    Date date = new Date(millisecond);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss SSS a");
	    System.out.println("毫秒["+millisecond+"]对应日期时间字符串：" + format.format(date));
	}
}
