package automation.locators;

public class HomePageLocator {
	public final static String adminLinkXpath = "//ul[@id = 'navlist']/li/a[text() = 'Admin']";
	public final static String cartLinkXpath = "//ul[@id = 'navlist']/li/a[starts-with(text(), 'Cart')]";
	public final static String homePageLinkXpath = "//div[@id = 'header']/h1/a";
	
	public final static String promotionXpah = "//div[@id = 'promotion']";
	public final static String GENRE = "$GENRE$"; 
	public final static String genreXpath = "//ul[@id = 'categories']/li/a[text() = '" + GENRE + "']";
	public final static String albumsListXpath = "//div[(@id = 'main') and (div[@id = 'promotion']) and (ul[@id =  'album-list'])]";
}