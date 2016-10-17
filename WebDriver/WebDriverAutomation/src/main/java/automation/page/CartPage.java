package automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import automation.locators.CartPageLocator;
import automation.utils.Logger;
import common.automation.framework.util.UIUtils;
import common.automation.framework.util.WebDriverHelper;

public class CartPage extends BasePage {
	@FindBy(xpath = CartPageLocator.checkoutXpath)
	private WebElement checkout;
	@FindBy(xpath = CartPageLocator.totalSumXpath)
	private WebElement totalSum;
	
	public CartPage (){
		try{
			UIUtils.waitTillElementAppear(By.xpath(CartPageLocator.pageHeaderXpath));
		}
		catch(Exception e){
			String errorMsg = "This is not Cart page";
			Logger.getLogger().error(errorMsg, new IllegalStateException(errorMsg));
		}
	}
	
	public CheckoutPage checkout(){
		UIUtils.click(checkout);
		return PageFactory.initElements(WebDriverHelper.getWebDriver(), CheckoutPage.class);
	}
	
	public void removeFirstAlbum(){
		WebElement removeFromCartLink = WebDriverHelper.getWebDriver().findElements(By.xpath(CartPageLocator.removeFromCartLinkXpath)).get(0);
		UIUtils.click(removeFromCartLink);
	}
	
	public int getAlbumsCount(){
		return WebDriverHelper.getWebDriver().findElements(By.xpath(CartPageLocator.removeFromCartLinkXpath)).size();
	}
	
	public float getTotalSum(){
		String totalSumText = UIUtils.getElementsText(totalSum).trim();
		return Float.valueOf(totalSumText);
	}

	public boolean isAlbumAdded(String album) {
		return UIUtils.doesElementExistBy(By.xpath(CartPageLocator.albumDetailsLinkXpath.replace(CartPageLocator.ALBUM_NAME, album)));
	}
}