package common.automation.framework.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class HTTPHelper {
    
    public static boolean isResourceAvailableByUrl(String resourceUrl) {
    	if (WebDriverHelper.getBrowserName().equalsIgnoreCase("internet explorer")){
    		//Reporter.log("WARNING : URL check does not work in IE", true);
    		return true;
    	}
    	try {
    		URL obj = new URL(resourceUrl);
    		HttpURLConnection con;
    		if (resourceUrl.startsWith("https")){
    			con = (HttpsURLConnection) obj.openConnection();
    		} else {
    			con = (HttpURLConnection) obj.openConnection();
    		}
    		con.setRequestMethod("GET");
    		con.setRequestProperty("Cookie", formCookie());
    		con.setInstanceFollowRedirects(false);
    		int responseCode = con.getResponseCode();
    		//Reporter.log("Response Code : " + responseCode, true);
    		return responseCode==200;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
    
    private static String formCookie() {
    	String resultCookie = "";
    	WebDriver driver = WebDriverHelper.getWebDriver();
		Set<Cookie> testCookie = driver.manage().getCookies();
		//Reporter.log(testCookie.toString(), true);
		Iterator<Cookie> iter= testCookie.iterator();
		while(iter.hasNext()){
			Cookie tmpC = iter.next();
			if (!tmpC.getPath().equals("/service-provider")){
				resultCookie+= tmpC.getName() + "=" + tmpC.getValue() +"; ";
			}
		}
		resultCookie = resultCookie.substring(0, resultCookie.lastIndexOf(";"));
		//Reporter.log(resultCookie, true);
		//JavascriptExecutor executor = (JavascriptExecutor)driver;
		//String cookie = executor.executeScript("return document.cookie;").toString();
		return resultCookie;
    }
}
