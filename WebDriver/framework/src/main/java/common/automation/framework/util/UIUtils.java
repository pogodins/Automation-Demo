package common.automation.framework.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import common.automation.framework.config.AutomationConfig;

public class UIUtils {
	public static void navigateToURL(String url){
		WebDriverHelper.getWebDriver().navigate().to(url);
	}
	
	public static void waitForPageToLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(WebDriverHelper.getWebDriver(), 30);
		wait.until(pageLoadCondition);
	}
	
	public static void uploadFile(String elementXpath, String path){
		String browserName = WebDriverHelper.getBrowserName();
		String browserVersion = WebDriverHelper.getBrowserVersion();
		WebDriverHelper.getWebDriver().findElement(By.xpath(elementXpath)).sendKeys(path);
		if (browserName.equals("internet explorer") && browserVersion.startsWith("8")){
			GraphicalUIUtility.pause(3000);
			StringSelection ss = new StringSelection(path);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			
			try {
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			//For debug in downgraded IE
			GraphicalUIUtility.pause(10000);
			browserRefresh();
		}
		Reporter.log("Uploaded file from location: " + path, true);
	}

	public static void click(By elementRef, boolean ajaxExpected){
		if(ajaxExpected){
			By loaderRef = By.xpath("//div[contains(@class, 'ajax-overlay')]");
			waitTillElementDisappear(loaderRef);
		}
		click(elementRef);
	}
	
	public static void click(By elementRef){
		WebElement element = null;
		try{
			//Reporter.log("Locating element", true);
			element = (new WebDriverWait(WebDriverHelper.getWebDriver(), 30)).until(ExpectedConditions.visibilityOfElementLocated(elementRef));
			element = (new WebDriverWait(WebDriverHelper.getWebDriver(), 30)).until(ExpectedConditions.elementToBeClickable(element));
		}
		catch (TimeoutException e){
			Assert.fail("Unable to locate element " + elementRef.toString());
		}
		try{
			//Reporter.log("Clicking element", true);
			element.click();
		}
		catch (WebDriverException e){
			GraphicalUIUtility.pause(5000);
			click(elementRef);
		}
		//Reporter.log("Clicked", true);
	}

	public static void edit(WebElement element, String value){
		if(element.getTagName().equalsIgnoreCase("select")){
			Select select = new Select(element);
			select.selectByVisibleText(value);
		}
		else{
			element.clear();
			element.sendKeys(value);
			/*
			if(!element.getText().equals(value)){
				GraphicalUIUtility.pause(500);
				edit(element, value);
			}*/
		}
	}

	public static void editBy(By elementRef, String value){
		WebElement element = null;
		try{
			element = (new WebDriverWait(WebDriverHelper.getWebDriver(), 30)).until(ExpectedConditions.visibilityOfElementLocated(elementRef));
		}
		catch (TimeoutException e){
			Assert.fail("Unable to locate element " + elementRef.toString());
		}
		edit(element, value);
	}
	
	public static void click(WebElement element, boolean ajaxExpected){
		if(ajaxExpected){
			By loaderRef = By.xpath("//div[contains(@class, 'ajax-overlay')]");
			waitTillElementDisappear(loaderRef);
		}
		click(element);
	}
	
	public static void click(WebElement element){
		element = (new WebDriverWait(WebDriverHelper.getWebDriver(), 30)).until(ExpectedConditions.visibilityOf(element));
		element = (new WebDriverWait(WebDriverHelper.getWebDriver(), 30)).until(ExpectedConditions.elementToBeClickable(element));
		try{
			element.click();
		}
		catch (WebDriverException e){
			GraphicalUIUtility.pause(5000);
			click(element);
		}
	}
	
	public static String getElementsTextBy(By elementRef){
		WebElement element = (new WebDriverWait(WebDriverHelper.getWebDriver(), 30)).until(ExpectedConditions.presenceOfElementLocated(elementRef));
		return getElementsText(element);
	}

	public static String getElementsText(WebElement element){
		new WebDriverWait(WebDriverHelper.getWebDriver(), 30).until(ExpectedConditions.visibilityOf(element));
		return element.getText();
	}
	
	public static void browserRefresh(){
		WebDriver driver = WebDriverHelper.getWebDriver();
		driver.navigate().refresh();
	}
	
	public static boolean doesElementExistBy(By elementRef){
		boolean present;
		try {
			WebDriverHelper.getWebDriver().findElement(elementRef);
			present = true;
		} catch (NoSuchElementException e) {
			present = false;
		}
		return present;
		/*
		List<WebElement> elementsList = WebDriverHelper.getWebDriver().findElements(elementRef);
		if((elementsList.size()>0) && (elementsList.get(0).isDisplayed())){
				return true;
		}
		else {
			return false;
		}*/
	}
	
	public static int getValueFromElementsText(By elementRef) {
		String str = getElementsTextBy(elementRef);
		str = str.replaceAll("[^0-9]+", "");
		if (str.length() > 0) {
			return (Integer.parseInt(str));
		} else {
			return (0);
		}
	}
	
	public static void waitTillElementDisappear(final WebElement element){
		try{
			(new WebDriverWait(WebDriverHelper.getWebDriver(), 2)).until(ExpectedConditions.visibilityOf(element));
		} catch(WebDriverException e){
		}
		(new WebDriverWait(WebDriverHelper.getWebDriver(), 30)).until(ExpectedConditions.not(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver arg0) {
				try{
					return element.isDisplayed();
				}catch(NoSuchElementException e){
					return false;
				}
			}
		}));
	}
	
	public static void waitTillElementDisappear(By elementRef){
		try{
			(new WebDriverWait(WebDriverHelper.getWebDriver(), 1)).until(ExpectedConditions.visibilityOfElementLocated(elementRef));
		}catch(WebDriverException e){
		}
		(new WebDriverWait(WebDriverHelper.getWebDriver(), 60)).until(ExpectedConditions.invisibilityOfElementLocated(elementRef));
	}
	
	public static void waitTillElementAppear(WebElement element){
		(new WebDriverWait(WebDriverHelper.getWebDriver(), 30)).until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void waitTillElementAppear(By elementRef){
		(new WebDriverWait(WebDriverHelper.getWebDriver(), 30)).until(ExpectedConditions.presenceOfElementLocated(elementRef));
	}
	
	public static void executeJavaScript(final String javaScript, Object... object) {
		((JavascriptExecutor)WebDriverHelper.getWebDriver()).executeScript(javaScript, object);
	}
	
	public static void waitForAjax() {
		waitForAjax(750);
	}

	/**
	 * Wait for ajax.
	 *
	 * @param delay the delay
	 */
	public static void waitForAjax(int delay) {
		long timespan = 0;
		long maxTimeWait = Long.parseLong(AutomationConfig.getTimeout());
		long increment = 1000;
		Reporter.log("Waiting for ajax to complete", true);
		while (timespan<maxTimeWait) {
			JavascriptExecutor exec = (JavascriptExecutor)WebDriverHelper.getWebDriver();
			boolean ajaxIsComplete = (Boolean) exec.executeScript("return jQuery.active == 0");
			if (ajaxIsComplete) break;
			try {
				Thread.sleep(increment);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timespan += increment;
			if (timespan > maxTimeWait) {
				Reporter.log("Wait for AJAX timed out after waiting for #{timeout} seconds", true);
				throw new RuntimeException("Wait for AJAX Operation timed out after " + maxTimeWait + " seconds");
			}
		}
		sleep(delay);
	}
	
	public static void dragAndDrop (final String fromXpath,final String toXpath) {

		Actions builder = new Actions(WebDriverHelper.getWebDriver()); 
		WebElement fromWebElement = null;
		WebElement toWebElement = null;

		GraphicalUIUtility.waitFor(new GraphicalUICondition() {
			public boolean testConditions() {
				return doesElementExistBy(By.xpath(fromXpath));
			}
		});
		fromWebElement = WebDriverHelper.getWebDriver().findElement(By.xpath(fromXpath));

		GraphicalUIUtility.waitFor(new GraphicalUICondition() {
			public boolean testConditions() {
				return doesElementExistBy(By.xpath(toXpath));
			}
		});
		toWebElement = WebDriverHelper.getWebDriver().findElement(By.xpath(toXpath));

		Action dragAndDrop = builder.clickAndHold(fromWebElement)       
		.moveToElement(toWebElement)     
		.release(toWebElement)       
		.build();   
		dragAndDrop.perform();
	}
	
	public static void sleep(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void mouseOver(By elementRef){
		Actions action = new Actions(WebDriverHelper.getWebDriver());
		WebElement we = WebDriverHelper.getWebDriver().findElement(elementRef);
		action.moveToElement(we).build().perform();
	}
	
	public static void scrollToElement(WebElement element){
		((JavascriptExecutor) WebDriverHelper.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
	}
}
