package automation.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import automation.page.AlbumPage;
import automation.page.BasePage;
import automation.page.CartPage;
import automation.page.CheckoutPage;
import automation.page.GenrePage;
import automation.page.HomePage;
import automation.page.LoginPage;
import common.automation.framework.util.WebDriverHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MusicStoreTest {
	static WebDriver driver;
	
	static HomePage homePage;
	static LoginPage loginPage;
	static GenrePage genrePage;
	static AlbumPage albumPage;
	static CartPage cartPage;
	static CheckoutPage checkoutPage;
	
	static int albumsCount = 0;
	static float purchaseSum = 0;
	static String albumName = "";
	static float albumPrice = 0;
	
	
	
	@Given("^I navigate to the Music Store in '(.+)' browser$")
	public void given_I_navigate_to_the_music_store_application(String browserName){
		driver = new WebDriverHelper().createWebDriver(browserName);
		driver.get("http://localhost:8089/");
	}
	
	@When("^I try to login as admin$")
	public void when_I_try_to_login_as_admin(){
		homePage = PageFactory.initElements(WebDriverHelper.getWebDriver(), HomePage.class);
		loginPage = homePage.goToLoginPage();
		loginPage.login("spogodin", "Lohika12#");
	}
	
	@Then("^I should not see any errors$")
	public void then_I_should_not_see_errors(){
		Assert.assertFalse("User login failed", loginPage.isErrorMessageDisplayed());
	}
	
	@Given("^'(.+)' genre exists in the list$")
	public void given_genre_exists_in_the_list(String genre){
		albumName = "";
		Assert.assertTrue(genre + " genre does not exist in he list", homePage.genreExistsInTheList(genre));
	}
	
	@When("^I add to cart '(.+)' album from '(.+)' genre$")
	public void when_I_add_an_album_to_cart(String album, String genre){
		albumPrice = 0;
		genrePage = homePage.selectGenre(genre);
		
		int albumIndex;
		if(album.equals("random")){
			int albumsQty = genrePage.getQtyOfAlbums();
			albumIndex = (int) (Math.random()*albumsQty);
		}
		else{
			albumIndex = Integer.valueOf(album);
		}
		albumPage = genrePage.selectAlbum(albumIndex);
		albumPrice = albumPage.getAlbumPrice();
		albumName = albumPage.getAlbumName();
		cartPage = albumPage.addToCart();
		
		albumsCount++;
		purchaseSum += albumPrice;
	}
	
	@Then("^I should see '(.+)' page header$")
	public void then_I_should_see_page_header(String pageHeader){
		Assert.assertEquals("This is not Cart page", pageHeader, BasePage.getPageHeader());
	}
	
	@Then("^Album should be added to the cart$")
	public void then_I_should_see_album_in_the_cart(){
		Assert.assertTrue("Album has not been added to the cart", cartPage.isAlbumAdded(albumName));
	}
	
	
	@Given("^Music Store application is opened$")
	public void music_Store_application_is_opened() {
		Assert.assertTrue("Music Store application is not opened", BasePage.isMusicStoreHeaderPresent());
	}

	@When("^I click on main banner$")
	public void i_click_on_main_banner() {
		homePage = homePage.navigateToMainPage();
	}

	@Then("^I should see home page$")
	public void i_should_see_home_page() {
	    Assert.assertTrue("Promotion banner or albums list are not displayed on home page", homePage.isHomePageAlbumsListDisplayed());
	}

	@When("^I navigate to cart$")
	public void i_navigate_to_cart() {
		cartPage = homePage.goToCart();
	}

	@Then("^Total sum should be correct$")
	public void total_sum_should_be_correct() {
		Assert.assertEquals(purchaseSum, cartPage.getTotalSum(), 0.01);
	}

	@Given("^Cart page is opened$")
	public void cart_page_is_opened() {
		Assert.assertEquals("This is not Cart page", "Review your cart:", BasePage.getPageHeader());
	}

	@When("^I remove first album from the cart$")
	public void i_remove_first_album_from_the_cart() {
		cartPage.removeFirstAlbum();
	}

	@Then("^Album should be removed from the list$")
	public void album_should_be_removed_from_the_list() {
	    Assert.assertEquals("Album has not been removed", albumsCount - 1, cartPage.getAlbumsCount());
	}

	@When("^I click 'Checkout' button$")
	public void i_click_Checkout_button() {
		checkoutPage = cartPage.checkout();
	}

	@Given("^Address And Payment page is opened$")
	public void address_And_Payment_page_is_opened() {
		Assert.assertEquals("This is not Address And Payment page", "Address And Payment", BasePage.getPageHeader());
	}

	@When("^I submit order with shipping details:$")
	public void when_I_submit_order(List<Map<String, String>> dataTable) {
		Map<String, String> shippingDetails = dataTable.get(0);
		checkoutPage.submitOrder(shippingDetails.get("First Name"),
						shippingDetails.get("Last Name"),
						shippingDetails.get("Address"),
						shippingDetails.get("City"),
						shippingDetails.get("State"),
						shippingDetails.get("Postal Code"),
						shippingDetails.get("Country"),
						shippingDetails.get("Phone"),
						shippingDetails.get("Email"),
						shippingDetails.get("Promo Code"));
	}
	
	@Then("^Order number should be displayed$")
	public void order_number_should_be_displayed() {
		Assert.assertTrue("Order number is not displayed", checkoutPage.isOrderNumberDisplayed());
	}
}