package shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public  class BaseMethod {
	
	public static int getWeekDay(Date day) {
		 Calendar cal = Calendar.getInstance();
		  cal.setTime(day);
		  return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public static Date getDateFromStartAndWeekday(Date startDay, int weekDay) {
		 Calendar cal = Calendar.getInstance();
		  cal.setTime(startDay);
		int start=  cal.get(Calendar.DAY_OF_WEEK);
		if(start-weekDay >0 || start-weekDay <0) {
		 cal.add(Calendar.DAY_OF_MONTH, start-weekDay);
		}
		
		return cal.getTime();
	}
		  
		
	
	
	
	public static Date convertDate(String day) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			return dateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Date convertDateFull(String day) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			return dateFormat.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Calendar  convertStringToCalendar(String day) {
		Date date= convertDate(day);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}
	
	public static int customCompareDate(Date date1, Date date2) {
        Calendar cal1 = toCalendar(date1);
        Calendar cal2 = toCalendar(date2);

        int yearComparison = Integer.compare(cal1.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
        if (yearComparison != 0) {
            return yearComparison;
        }

        int monthComparison = Integer.compare(cal1.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
        if (monthComparison != 0) {
            return monthComparison;
        }

        return Integer.compare(cal1.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
    }
	
	public static Calendar toCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
		}
	
	
}
