package com.saucedemo.qa.integration;

import static org.testng.Assert.*;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.pages.*;
import com.saucedemo.qa.utils.InventorySortOrder;
import com.saucedemo.qa.utils.TestBase;

public class UserCheckoutJourneyTest extends TestBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserCheckoutJourneyTest.class);

	private LoginPage loginPage;

	private ProductsPage productsPage;

	private YourCartPage yourCartPage;

	private YourInfoPage yourInfoPage;

	private CheckoutOverviewPage checkoutOverviewPage;

	private CheckoutCompletePage checkoutCompletePage;

	@Override
	@BeforeMethod
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testUserCheckoutJourney_SecondCostliestAndCheapestItem_Success() {
		verifyLoginSuccess(AppConfig.getConfigValue("username"), AppConfig.getConfigValue("password"));

		// Retrieve prices before sorting
		List<Double> pricesBeforeSorting = productsPage.getInventoryPrices();
		Collections.sort(pricesBeforeSorting, Collections.reverseOrder());

		// Sort items on the page
		productsPage.sortItems(InventorySortOrder.BY_PRICE_HIGH_TO_LOW.getDescription());
		List<Double> pricesAfterSorting = productsPage.getInventoryPrices();
		assertEquals(pricesAfterSorting, pricesBeforeSorting, "Mismatch in sorted prices");

		LOGGER.info("Sorted items, add items to cart");
		// Add second costliest item
		productsPage.addToCart(1);
		// Add cheapest item
		productsPage.addToCart(productsPage.getTotalItemCount() - 1);
		assertEquals(productsPage.getNumOfItemsOnCart(), 2, "Mismatch in cart item count");

		initateCheckout();
		verifyCheckoutComplete();
	}

	@Test
	public void testUserCheckoutJourney_ThirdCostliestAndCheapestItem_Success() {
		verifyLoginSuccess(AppConfig.getConfigValue("username"), AppConfig.getConfigValue("password"));

		// Retrieve prices before sorting
		List<Double> pricesBeforeSorting = productsPage.getInventoryPrices();
		Collections.sort(pricesBeforeSorting, Collections.reverseOrder());

		LOGGER.info("Sorted items, add items to cart");
		// Sort items on the page
		productsPage.sortItems(InventorySortOrder.BY_PRICE_HIGH_TO_LOW.getDescription());
		List<Double> pricesAfterSorting = productsPage.getInventoryPrices();
		assertEquals(pricesAfterSorting, pricesBeforeSorting, "Mismatch in sorted prices");

		// Add third costliest item
		productsPage.addToCart(2);
		// Add cheapest item
		productsPage.addToCart(productsPage.getTotalItemCount() - 1);
		assertEquals(productsPage.getNumOfItemsOnCart(), 2, "Mismatch in cart item count");

		initateCheckout();

		verifyCheckoutComplete();
	}

	@Test
	public void testUserCheckoutJourney_LoginFail_ThenSuccess() {
		verifyLoginFailure("test", "test");
		verifyLoginSuccess(AppConfig.getConfigValue("username"), AppConfig.getConfigValue("password"));
	}

	private void initateCheckout() {
		LOGGER.info("Proceeding to checkout");
		yourCartPage = productsPage.goToCart();
		assertEquals(yourCartPage.getTitle(), "YOUR CART", "Unable to reach 'Cart' page");

		yourInfoPage = yourCartPage.checkout();
		assertEquals(yourInfoPage.getTitle(), "CHECKOUT: YOUR INFORMATION", "Unable to reach 'Your Info' page");

		yourInfoPage.enterYourInfo("Test", "User", "TU1 1TU");
		assertEquals(yourInfoPage.getFirstNameValue(), "Test");
		assertEquals(yourInfoPage.getLastNameValue(), "User");
		assertEquals(yourInfoPage.getPostalCodeValue(), "TU1 1TU");
	}

	private void verifyCheckoutComplete() {
		checkoutOverviewPage = yourInfoPage.continueCheckout();
		assertEquals(checkoutOverviewPage.getTitle(), "CHECKOUT: OVERVIEW");
		assertEquals(checkoutOverviewPage.calculateTotalPrice(), checkoutOverviewPage.getItemTotal());

		checkoutCompletePage = checkoutOverviewPage.clickOnFinish();
		assertEquals(checkoutCompletePage.getTitle(), "CHECKOUT: COMPLETE!");
		LOGGER.info("Checkout complete");
	}

	private void verifyLoginSuccess(String username, String password) {
		loginPage = new LoginPage(getDriver());
		productsPage = loginPage.login(username, password);
		assertEquals(productsPage.getTitle(), "PRODUCTS", "Unable to reach 'Products' page");
		LOGGER.info("Logged in user. Now in Products page");
	}

	private void verifyLoginFailure(String username, String password) {
		loginPage = new LoginPage(getDriver());
		assertNull(loginPage.login(username, password));
		LOGGER.info("Login failure");
	}

	@Override
	@AfterMethod
	public void tearDown() {
		super.tearDown();
	}

}
