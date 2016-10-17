package automation.locators;

public class CheckoutPageLocator {
	public final static String firstNameXpath = "//input[@name = 'FirstName']";
	public final static String lastNameXpath = "//input[@name = 'LastName']";
	public final static String addressXpath = "//input[@name = 'Address']";
	public final static String cityXpath = "//input[@name = 'City']";
	public final static String stateXpath = "//input[@name = 'State']";
	public final static String postalCodeXpath = "//input[@name = 'PostalCode']";
	public final static String countryXpath = "//input[@name = 'Country']";
	public final static String phoneXpath = "//input[@name = 'Phone']";
	public final static String emailXpath = "//input[@name = 'Email']";
	public final static String promoCodeXpath = "//input[@name = 'PromoCode']";
	public final static String submitOrderButtonpath = "//input[(@type='submit') and (@value = 'Submit Order')]";
	
	public final static String pageHeaderXpath = "//h2[text() = 'Address And Payment']";
	public final static String checkoutCompleteXpath = "//h2[text() = 'Checkout Complete']";
	public final static String orderNumberMessageXpath = "//p[starts-with(text(), 'Thanks for your order! Your order number is:')]";
}