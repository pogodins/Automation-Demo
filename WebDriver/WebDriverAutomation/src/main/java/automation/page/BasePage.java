package automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import automation.locators.BasePageLocator;
import common.automation.framework.util.UIUtils;
import common.automation.framework.util.WebDriverHelper;

public class BasePage {
	protected WebDriver driver = WebDriverHelper.getWebDriver();
	
	public BasePage(){
		UIUtils.waitForPageToLoad();
	}
	public static String getPageHeader(){
		return UIUtils.getElementsTextBy(By.xpath(BasePageLocator.pageHeaderXpath));
	}
	
	public static boolean isMusicStoreHeaderPresent(){
		return UIUtils.doesElementExistBy(By.xpath(BasePageLocator.applicationHeader));
	}
}