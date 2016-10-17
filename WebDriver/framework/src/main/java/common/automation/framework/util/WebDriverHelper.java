package common.automation.framework.util;

import java.io.File;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import common.automation.framework.config.AutomationConstants;

public class WebDriverHelper {
	private static WebDriver webDriver	= null;
	
	public WebDriver createWebDriver(String webDriverType) {
		if(AutomationConstants.INTERNET_EXPLORER.equalsIgnoreCase(webDriverType)){
			String systemOSBit = System.getProperty("os.arch");
			
			if(systemOSBit.contains("64")) {
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + File.separator + "lib" + File.separator + "IEDriverServer-64.exe");	
			}
			else {
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + File.separator + "lib" + File.separator + "IEDriverServer-32.exe");
		    }
			webDriver = new InternetExplorerDriver();  			
		}else if(AutomationConstants.FIREFOX.equalsIgnoreCase(webDriverType)){
			webDriver = new FirefoxDriver();
		}else if(AutomationConstants.CHROME.equalsIgnoreCase(webDriverType)){
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "lib" + File.separator + "chromedriver.exe");
			DesiredCapabilities dc=new DesiredCapabilities();
			dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour.ACCEPT);
			webDriver =	new ChromeDriver(dc);
		}
		webDriver.manage().window().maximize();
		
		return webDriver;
	}

	public static WebDriver getWebDriver() { 
		return webDriver;
	}
	
	public static String getBrowserName() { 
		Capabilities caps = ((RemoteWebDriver) getWebDriver()).getCapabilities();
		return caps.getBrowserName();
	}
	
	public static String getBrowserVersion() { 
		Capabilities caps = ((RemoteWebDriver) getWebDriver()).getCapabilities();
		return caps.getVersion();
	}
}
