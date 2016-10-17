package automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import automation.locators.GenrePageLocator;
import automation.utils.Logger;
import common.automation.framework.util.UIUtils;
import common.automation.framework.util.WebDriverHelper;

public class GenrePage extends BasePage {
	
	public GenrePage (){
		try{
			UIUtils.waitTillElementAppear(By.xpath(GenrePageLocator.genreHederXpath));
		}
		catch(Exception e){
			String errorMsg = "This is not Genre page";
			Logger.getLogger().error(errorMsg, new IllegalStateException(errorMsg));
		}
	}
	
	public int getQtyOfAlbums(){
		return driver.findElements(By.xpath(GenrePageLocator.albumXpath)).size();
	}
	
	public AlbumPage selectAlbum(int albumIndex){
		String albumIndexString = String.valueOf(albumIndex);
		
		UIUtils.click(By.xpath(GenrePageLocator.albumByIndexLikXpath.replace(GenrePageLocator.INDEX, albumIndexString)));
		return PageFactory.initElements(WebDriverHelper.getWebDriver(), AlbumPage.class);
	}
}