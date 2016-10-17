package automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import automation.locators.CheckoutPageLocator;
import automation.utils.Logger;
import common.automation.framework.util.UIUtils;

public class CheckoutPage extends BasePage {
	@FindBy(xpath = CheckoutPageLocator.firstNameXpath)
	private WebElement firstName;
	@FindBy(xpath = CheckoutPageLocator.lastNameXpath)
	private WebElement lastName;
	@FindBy(xpath = CheckoutPageLocator.addressXpath)
	private WebElement address;
	@FindBy(xpath = CheckoutPageLocator.cityXpath)
	private WebElement city;
	@FindBy(xpath = CheckoutPageLocator.stateXpath)
	private WebElement state;
	@FindBy(xpath = CheckoutPageLocator.postalCodeXpath)
	private WebElement postalCode;
	@FindBy(xpath = CheckoutPageLocator.countryXpath)
	private WebElement country;
	@FindBy(xpath = CheckoutPageLocator.phoneXpath)
	private WebElement phone;
	@FindBy(xpath = CheckoutPageLocator.emailXpath)
	private WebElement email;
	@FindBy(xpath = CheckoutPageLocator.promoCodeXpath)
	private WebElement promoCode;
	@FindBy(xpath = CheckoutPageLocator.submitOrderButtonpath)
	private WebElement submitOrderButton;
	
	public CheckoutPage (){
		try{
			UIUtils.waitTillElementAppear(By.xpath(CheckoutPageLocator.pageHeaderXpath));
		}
		catch(Exception e){
			String errorMsg = "This is not Checkout page";
			Logger.getLogger().error(errorMsg, new IllegalStateException(errorMsg));
		}
	}
	
	public void submitOrder(String firstNameValue, String lastNameValue, String addressValue, String cityValue, String stateValue,
			String postalCodeValue, String countryValue, String phoneValue, String emailAddressValue, String promoCodeValue){
		
		UIUtils.edit(firstName, firstNameValue);
		UIUtils.edit(lastName, lastNameValue);
		UIUtils.edit(address, addressValue);
		UIUtils.edit(city, cityValue);
		UIUtils.edit(state, stateValue);
		UIUtils.edit(postalCode, postalCodeValue);
		UIUtils.edit(country, countryValue);
		UIUtils.edit(phone, phoneValue);
		UIUtils.edit(email, emailAddressValue);
		UIUtils.edit(promoCode, promoCodeValue);
		
		UIUtils.click(submitOrderButton);
	}
	
	public boolean isCheckoutCompleted(){
		return UIUtils.doesElementExistBy(By.xpath(CheckoutPageLocator.checkoutCompleteXpath));
	}
	
	public boolean isOrderNumberDisplayed(){
		return UIUtils.doesElementExistBy(By.xpath(CheckoutPageLocator.orderNumberMessageXpath));
	}
}