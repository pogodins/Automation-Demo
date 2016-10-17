package automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import automation.locators.LoginPageLocator;
import automation.utils.Logger;
import common.automation.framework.util.UIUtils;

public class LoginPage extends BasePage {
	@FindBy(xpath = LoginPageLocator.usernameXpath)
	private WebElement username;
	@FindBy(xpath = LoginPageLocator.passwordXpath)
	private WebElement password;
	@FindBy(xpath = LoginPageLocator.loginButtonXpath)
	private WebElement loginButton;
	
	public LoginPage(){
		try{
			UIUtils.waitTillElementAppear(By.xpath(LoginPageLocator.loginPageHeaderXpath));
		}
		catch(Exception e){
			String errorMsg = "This is not Login page, current page is: " + driver.getTitle();
			Logger.getLogger().error(errorMsg, new IllegalStateException(errorMsg));
		}
	}
	
	public void login(String userID, String userPassword){
		UIUtils.edit(username, userID);
		UIUtils.edit(password, userPassword);
		UIUtils.click(loginButton);
	}
	
	public boolean isErrorMessageDisplayed(){
		return UIUtils.doesElementExistBy(By.xpath(LoginPageLocator.errorMessageXpath));
	}
}