package automation.locators;

public class LoginPageLocator {
	public final static String usernameXpath = "//input[@id = 'UserName']";
	public final static String passwordXpath = "//input[@id = 'Password']";
	public final static String loginButtonXpath = "//input[(@type='submit') and (@value = 'Log On')]";
	
	public final static String resetPwdLaterLinkXpath = "//a[text() = \"I'll reset my password later\"]";
	public final static String loginPageHeaderXpath = "//h2[text() = 'Log On']";
	public final static String errorMessageXpath = "//div[@class = 'validation-summary-errors']";
}