package automation.locators;

public class GenrePageLocator extends BasePageLocator {
	public final  static String GENRE = "$GENRE$";
	public final  static String INDEX = "$INDEX$";
	
	public final static String genreHederXpath = "//div[@class = 'genre']";
	public final static String albumXpath = "//ul[@id = 'album-list']/li";
	public final static String albumByIndexLikXpath = "//ul[@id = 'album-list']/li[" + INDEX + "]/a";
}