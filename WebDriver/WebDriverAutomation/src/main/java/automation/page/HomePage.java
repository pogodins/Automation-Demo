package automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import automation.locators.HomePageLocator;
import automation.utils.Logger;
import common.automation.framework.util.UIUtils;
import common.automation.framework.util.WebDriverHelper;

public class HomePage extends BasePage {
	@FindBy(xpath = HomePageLocator.adminLinkXpath)
	private WebElement adminLink;
	@FindBy(xpath = HomePageLocator.cartLinkXpath)
	private WebElement cartLink;
	@FindBy(xpath = HomePageLocator.homePageLinkXpath)
	private WebElement homePageLink;
		
	public HomePage (){
		try{
			UIUtils.waitTillElementAppear(By.xpath(HomePageLocator.promotionXpah));
		}
		catch(Exception e){
			String errorMsg = "This is not Home page, current page is: " + driver.getTitle();
			Logger.getLogger().error(errorMsg, new IllegalStateException(errorMsg));
		}
	}
	
	public boolean isHomePageAlbumsListDisplayed(){
		return UIUtils.doesElementExistBy(By.xpath(HomePageLocator.albumsListXpath));
	}
	
	public LoginPage goToLoginPage() {
		UIUtils.click(adminLink);
		return PageFactory.initElements(WebDriverHelper.getWebDriver(), LoginPage.class);
	}
	
	public boolean genreExistsInTheList(String genre){
		return UIUtils.doesElementExistBy(By.xpath(HomePageLocator.genreXpath.replace(HomePageLocator.GENRE, genre)));
	}
	
	public GenrePage selectGenre(String genre){
		UIUtils.click(By.xpath(HomePageLocator.genreXpath.replace(HomePageLocator.GENRE, genre)));
		return PageFactory.initElements(WebDriverHelper.getWebDriver(), GenrePage.class);
	}
	
	public HomePage navigateToMainPage(){
		UIUtils.click(homePageLink);
		return PageFactory.initElements(WebDriverHelper.getWebDriver(), HomePage.class);
	}
	
	public CartPage goToCart(){
		UIUtils.click(cartLink);
		return PageFactory.initElements(WebDriverHelper.getWebDriver(), CartPage.class);
	}
}