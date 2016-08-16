package org.ppl.io;

/**
 * http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeClass {
	public static TimeClass config = null;

	public static TimeClass getInstance() {

		if (config == null) {
			config = new TimeClass();
		}
		return config;
	}

	/**
	 * 
	 * @param "yyyy_MM_dd"
	 * @return
	 */
	public String TimeFormat(String format) {
		
		Date nowTime = new Date();

		SimpleDateFormat time = new SimpleDateFormat(format);

		return time.format(nowTime).toString();
	}
	
	public String TimeStamptoDate(Long TimeStamp, String format) {
		if(TimeStamp<0 || format == null) return "";
		
		java.util.Date dateTime=new java.util.Date((long)TimeStamp*1000);

		SimpleDateFormat time = new SimpleDateFormat(format);

		return time.format(dateTime).toString();
	}

	/**
	 * 
	 * @param TimeStamp for php dfy
	 * @return
	 */
	public int DayForYear(Long TimeStamp) {
		int dfy = Integer.valueOf(TimeStamptoDate(TimeStamp, "yyyyD"));
		return (dfy-1);
	}

	public Long DateToTimeStamp(String tsStr) {
		//String tsStr = "2014-10-02 23:59:59";
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

        java.util.Date date;
		try {
			date = format.parse(tsStr);
			return date.getTime()/1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (long) 0;
	}
	
	public long time() {
		return System.currentTimeMillis() / 1000;
	}

	public List<String> DayTaltoMon(int year, int DayTal) {
		int monthTOday[] = new int[13];
		int Day = DayTal+1;
		monthTOday[0]=0;
		List<String> ResultSet = new ArrayList<String>();			
		if (year % 4 == 0) {
			monthTOday[1] = 31;
			monthTOday[2] = 60;
			monthTOday[3] = 91;
			monthTOday[4] = 121;
			monthTOday[5] = 152;
			monthTOday[6] = 182;
			monthTOday[7] = 213;
			monthTOday[8] = 244;
			monthTOday[9] = 274;
			monthTOday[10] = 305;
			monthTOday[11] = 335;
			monthTOday[12] = 366;
		} else {
			monthTOday[1] = 31;
			monthTOday[2] = 59;
			monthTOday[3] = 90;
			monthTOday[4] = 120;
			monthTOday[5] = 151;
			monthTOday[6] = 181;
			monthTOday[7] = 212;
			monthTOday[8] = 243;
			monthTOday[9] = 273;
			monthTOday[10] = 304;
			monthTOday[11] = 334;
			monthTOday[12] = 365;
		}
		int i=0;
		int day=0;
		
		for ( ; i< monthTOday.length; i++) {
			
			if(Day<=monthTOday[i]){				
				day = Day-monthTOday[i-1];				
				ResultSet.add(String.format("%02d", i));
				ResultSet.add(String.format("%02d", day));
				return ResultSet;			
			}
		}
		ResultSet.add(String.format("%02d", i+1));
		ResultSet.add(String.format("%02d", Day));
		return ResultSet;
	}
}
