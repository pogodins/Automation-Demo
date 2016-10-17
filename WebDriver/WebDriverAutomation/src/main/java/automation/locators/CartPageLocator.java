package automation.locators;

public class CartPageLocator {
	public final static String checkoutXpath = "//a[text() = 'Checkout >>']";
	public final static String totalSumXpath = "//td[@id = 'cart-total']";
	
	public final static String pageHeaderXpath = "//h3[contains(., 'Review your cart:')]";
	public final static String removeFromCartLinkXpath = "//a[text() = 'Remove from cart']";
	public final static String ALBUM_NAME = "$ALBUM_NAME$";
	public final static String albumDetailsLinkXpath = "//tr[starts-with(@id, 'row-')]/descendant::a[contains(@href, 'Store/Details') and (text() = '"+ ALBUM_NAME + "')]";
}