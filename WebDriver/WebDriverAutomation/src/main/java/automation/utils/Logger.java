package automation.utils;

public class Logger {
	private static org.apache.log4j.Logger logger;
	
	public static void setupLogger(String className){
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "warn");
		logger = org.apache.log4j.Logger.getLogger(className);
	}
	
	public static org.apache.log4j.Logger getLogger(){
		return logger;
	}
	
	public static void info(String message){
		logger.info(message);
	}
	
	public static void error(String message){
		logger.error(message);
	}
}