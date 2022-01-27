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

		LOGGER.info("Sorted items, add items to cart.");
		// Add second costliest item
		productsPage.addToCart(1);
		// Add cheapest item
		productsPage.addToCart(productsPage.getTotalItemCount() - 1);
		assertEquals(productsPage.getNumOfItemsOnCart(), 2, "Mismatch in cart item count");

		initateCheckout("Test", "User", "TU1 1TU");
		proceedToCheckoutOverviewPage();
		proceedToCheckoutCompletePage();
	}

	@Test
	public void testUserCheckoutJourney_CustomItemAddition_Success() {
		verifyLoginSuccess(AppConfig.getConfigValue("username"), AppConfig.getConfigValue("password"));

		LOGGER.info("Add items to cart.");
		productsPage.addToCart("Test.allTheThings() T-Shirt (Red)", "$15.99");
		assertEquals(productsPage.getNumOfItemsOnCart(), 1, "Mismatch in cart item count");

		initateCheckout("Test", "User", "TU1 1TU");

		proceedToCheckoutOverviewPage();
		assertTrue(checkoutOverviewPage.doesItemExistInCheckout("Test.allTheThings() T-Shirt (Red)", "$15.99"),
				"Selected item not included in checkout");
		assertFalse(checkoutOverviewPage.doesItemExistInCheckout("Sauce Labs Bike Light", "$9.99"),
				"Unselected item included in checkout");

		proceedToCheckoutCompletePage();
	}

	@Test
	public void testUserCheckoutJourney_LoginFail_ThenSuccess() {
		verifyLoginFailure("test", "test");
		verifyLoginSuccess(AppConfig.getConfigValue("username"), AppConfig.getConfigValue("password"));
	}

	private void initateCheckout(String firstname, String lastname, String postCode) {
		LOGGER.info("Proceeding to checkout.");
		yourCartPage = productsPage.goToCart();
		assertEquals(yourCartPage.getTitle(), "YOUR CART", "Unable to reach 'Cart' page");

		yourInfoPage = yourCartPage.checkout();
		assertEquals(yourInfoPage.getTitle(), "CHECKOUT: YOUR INFORMATION", "Unable to reach 'Your Info' page");

		yourInfoPage.enterYourInfo(firstname, lastname, postCode);
		assertEquals(yourInfoPage.getFirstNameValue(), firstname);
		assertEquals(yourInfoPage.getLastNameValue(), lastname);
		assertEquals(yourInfoPage.getPostalCodeValue(), postCode);
	}

	private void proceedToCheckoutCompletePage() {
		checkoutCompletePage = checkoutOverviewPage.clickOnFinish();
		assertEquals(checkoutCompletePage.getTitle(), "CHECKOUT: COMPLETE!");
		LOGGER.info("Checkout complete");
	}

	private void proceedToCheckoutOverviewPage() {
		checkoutOverviewPage = yourInfoPage.continueCheckout();
		assertEquals(checkoutOverviewPage.getTitle(), "CHECKOUT: OVERVIEW");
		assertEquals(checkoutOverviewPage.calculateTotalPrice(), checkoutOverviewPage.getItemTotal());

	}

	private void verifyLoginSuccess(String username, String password) {
		loginPage = new LoginPage(getDriver());
		productsPage = loginPage.login(username, password);
		assertEquals(productsPage.getTitle(), "PRODUCTS", "Unable to reach 'Products' page");
		LOGGER.info("Logged in user. Now in Products page.");
	}

	private void verifyLoginFailure(String username, String password) {
		loginPage = new LoginPage(getDriver());
		assertNull(loginPage.login(username, password));
		LOGGER.info("Login failure.");
	}

	@Override
	@AfterMethod
	public void tearDown() {
		super.tearDown();
	}

}
