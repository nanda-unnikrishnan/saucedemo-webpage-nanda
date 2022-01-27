package com.saucedemo.qa.integration;

import static org.testng.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.pages.CheckoutCompletePage;
import com.saucedemo.qa.pages.CheckoutOverviewPage;
import com.saucedemo.qa.pages.LoginPage;
import com.saucedemo.qa.pages.ProductsPage;
import com.saucedemo.qa.pages.YourCartPage;
import com.saucedemo.qa.pages.YourInfoPage;
import com.saucedemo.qa.utils.InventorySortOrder;
import com.saucedemo.qa.utils.TestBase;

public class UserCheckoutJourneyTest extends TestBase {

	LoginPage loginPage;
	ProductsPage productsPage;
	YourCartPage yourCartPage;
	YourInfoPage yourInfoPage;
	CheckoutOverviewPage checkoutOverviewPage;
	CheckoutCompletePage checkoutCompletePage;

	@Override
	@BeforeMethod
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testUserCheckoutJourney_SecondCostliestAndCheapestItem_Success() {
		verifyLoginComplete();

		// Retrieve prices before sorting
		List<Double> pricesBeforeSorting = productsPage.getInventoryPrices();
		Collections.sort(pricesBeforeSorting, Collections.reverseOrder());

		// Sort items on the page
		productsPage.sortItems(InventorySortOrder.BY_PRICE_HIGH_TO_LOW.getDescription());
		List<Double> pricesAfterSorting = productsPage.getInventoryPrices();
		assertEquals(pricesAfterSorting, pricesBeforeSorting, "Mismatch in sorted prices");

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
		verifyLoginComplete();

		// Retrieve prices before sorting
		List<Double> pricesBeforeSorting = productsPage.getInventoryPrices();
		Collections.sort(pricesBeforeSorting, Collections.reverseOrder());

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

	private void initateCheckout() {
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
	}

	private void verifyLoginComplete() {
		loginPage = new LoginPage(getDriver());
		productsPage = loginPage.login(AppConfig.getConfigValue("username"), AppConfig.getConfigValue("password"));
		assertEquals(productsPage.getTitle(), "PRODUCTS", "Unable to reach 'Products' page");
	}

	@Override
	@AfterMethod
	public void tearDown() {
		super.tearDown();
	}

}
