package common.automation.framework.util;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;




/**
 * The Class SessionUtility.
 */
public class SessionUtility {
	/** The session test map. */
	private static Map<String, Object> sessionTestMap = new ConcurrentHashMap<String, Object>();

	public static Object getValue(String key) {
		return sessionTestMap.get(key);
	}	
	
	public static void setValue(String key,Object object) {
		sessionTestMap.put(key, object);
	}

	public static void removeValue(String key) {
		sessionTestMap.remove(key);
	}
	
	/** The Constant CurrnetDate. */
	public static final String CurrnetDate="";
	
	public static String GetCurrentDateTime(){
		int day, month, year;
	      int second, minute, hour;
	      java.util.GregorianCalendar date = new java.util.GregorianCalendar();
	 
	      day = date.get(Calendar.DAY_OF_MONTH);
	      month = date.get(Calendar.MONTH);
	      year = date.get(Calendar.YEAR);
	 
	      second = date.get(Calendar.SECOND);
	      minute = date.get(Calendar.MINUTE);
	      hour = date.get(Calendar.HOUR);
	 
//	      Reporter.log("Current date is  "+day+"/"+(month+1)+"/"+year+ ":" +hour+":"+minute+":"+second, true);
		return day+"/"+(month+1)+"/"+year+ ":" +hour+":"+minute+":"+second;
}
	
}
