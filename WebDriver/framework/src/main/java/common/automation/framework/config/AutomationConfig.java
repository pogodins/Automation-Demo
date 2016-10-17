package common.automation.framework.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.Reporter;

/**
 * The Class AutomationConfig.
 */
public class AutomationConfig {

	private static final String ENVIRONMENT = "$ENVIRONMENT$";
	private static final String PORTAL_NAME = "$PORTAL_NAME$"; 
	private static final String CONFIGURATION_PORTAL_URL = "automation." + ENVIRONMENT + "." + PORTAL_NAME + ".url";
	private static final String CONFIGURATION_ENV = "automation.env";
	private static final String CONFIGURATION_DBSTRING = "automation." + ENVIRONMENT + ".DBString";

	private static final String ROLE = "$ROLE$";
	private static final String CONFIGURATION_USER_ID = "automation." + ENVIRONMENT + "." + ROLE + "_id";
	private static final String CONFIGURATION_PASSWORD = "automation." + ENVIRONMENT + "." + ROLE + "_password";

	private static final String CONFIGURATION_MEMBER_NAME = "automation.member";
	private static final String CONFIGURATION_EVENT_NAME = "automation.event";
	private static final String CONFIGURATION_SUPPLIER_NAME = "automation.supplier";
	private static final String CONFIGURATION_EMAIL = "automation.email";
	private static final String CONFIGURATION_EMAIL_PASSWORD = "automation.emailPassword";
	private static final String CONFIGURATION_EVENT_NOEMAIL_NAME = "automation.event_no_email";

	private static final String CONFIGURATION_TIME_OUT = "automation.timeout";
	private static final String CONFIG_FILENAME = System.getProperty("user.dir") + "\\auto_config.txt";

	/** The config. */
	private static Properties config;
	
	
	static {
		loadConfig();		
	}

	public static void write(String key, String value){
		config.put(key, value);
		try {
			config.store(new FileOutputStream(CONFIG_FILENAME), null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static void setProperty(String key, String value) {
        config.setProperty(key,value);
    }
	
	/**
	 * Load config.
	 */
	private static void loadConfig() {
		config = new Properties();
		File localConfig = new File(CONFIG_FILENAME);
		if (localConfig.exists()) {
			InputStream inStream = null;
			try {
				inStream = new BufferedInputStream(new FileInputStream(localConfig));
				config.load(inStream);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						// empty
					}
				}
			}
		}
		else{
			Reporter.log(CONFIG_FILENAME + " NOT FOUND", true);
		}
	}
	
	/**
	 * Load config.
	 *
	 * @param fileName the file name
	 * @return the properties
	 */
	public static Properties loadConfig(String fileName) {
		Properties propFile;
		propFile = new Properties();
		File localConfig = new File(fileName);
		if (localConfig.exists()) {
			InputStream inStream = null;
			try {
				inStream = new BufferedInputStream(new FileInputStream(localConfig));
				propFile.load(inStream);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						// empty
					}
				}
			}
		}
		else{
			Reporter.log(fileName + " NOT FOUND", true);
		}
		return propFile;
	}
	
	public static String getPortalURL(String portalName) {
		return getProperty(CONFIGURATION_PORTAL_URL.replace(ENVIRONMENT, getProperty(CONFIGURATION_ENV)).replace(PORTAL_NAME, portalName));
	}
	
	public static String getDBConString() {
		return getProperty(CONFIGURATION_DBSTRING.replace(ENVIRONMENT, getProperty(CONFIGURATION_ENV)));
	}
	
	public static String getUserId(String role) {
		return getProperty(CONFIGURATION_USER_ID.replace(ENVIRONMENT, getProperty(CONFIGURATION_ENV)).replace(ROLE, role));
	}
	
	public static String getPassword(String role) {
		return getProperty(CONFIGURATION_PASSWORD.replace(ENVIRONMENT, getProperty(CONFIGURATION_ENV)).replace(ROLE, role));
	}
	
	public static String getMemberName() {
		return getProperty(CONFIGURATION_MEMBER_NAME);
	}
	
	public static String getEventName() {
		return getProperty(CONFIGURATION_EVENT_NAME);
	}
	
	public static String getSupplierName() {
		return getProperty(CONFIGURATION_SUPPLIER_NAME);
	}
	
	public static String getEmail() {
		return getProperty(CONFIGURATION_EMAIL);
	}
	
	public static String getEmailPassword() {
		return getProperty(CONFIGURATION_EMAIL_PASSWORD);
	}
	
	public static String getEventNoEmail() {
		return getProperty(CONFIGURATION_EVENT_NOEMAIL_NAME);
	}

	public static String getTimeout() {
		return getProperty(CONFIGURATION_TIME_OUT);
	}

	public static String getProperty(String key) {
		return config.getProperty(key);
	}
}
