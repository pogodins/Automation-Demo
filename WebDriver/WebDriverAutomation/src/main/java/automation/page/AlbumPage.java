package automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import automation.locators.AlbumPageLocator;
import automation.utils.Logger;
import common.automation.framework.util.UIUtils;
import common.automation.framework.util.WebDriverHelper;

public class AlbumPage extends BasePage{
	@FindBy(xpath = AlbumPageLocator.addToCartXpath)
	private WebElement addToCart;
	@FindBy(xpath = AlbumPageLocator.priceXpath)
	private WebElement price;
	@FindBy(xpath = AlbumPageLocator.albumNameXpath)
	private WebElement albumName;
	
	public AlbumPage (){
		try{
			UIUtils.waitTillElementAppear(By.xpath(AlbumPageLocator.albumDetailsXpath));
		}
		catch(Exception e){
			String errorMsg = "This is not Album page";
			Logger.getLogger().error(errorMsg, new IllegalStateException(errorMsg));
		}
	}
	
	public CartPage addToCart(){
		UIUtils.click(addToCart);
		return PageFactory.initElements(WebDriverHelper.getWebDriver(), CartPage.class);
	}
	
	public String getAlbumName() {
		return UIUtils.getElementsText(albumName);
	}
	
	public float getAlbumPrice(){
		String priceElementText = UIUtils.getElementsText(price);
		String price = priceElementText.replace("Price:", "").trim();
		return Float.valueOf(price);
	}
}