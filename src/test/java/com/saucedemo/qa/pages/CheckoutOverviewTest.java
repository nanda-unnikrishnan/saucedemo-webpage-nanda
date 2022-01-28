package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;

import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.base.InventorySortOrder;
import com.saucedemo.qa.utils.TestBase;

public class CheckoutOverviewTest extends TestBase {

	CheckoutOverviewPage checkoutOverviewPage;

	@Override
	@BeforeMethod
	public void setUp() {
		super.setUp();

	}

	@Test(description = "Verify cheapest and second costliest items added in cart")
	public void testCheckoutOverview_ItemsAddedArePresentInCheckout_Multiple() {
		ProductsPage productsPage = new LoginPage(getDriver()).login(
				AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
				AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME));

		checkoutOverviewPage = productsPage.sortItems(InventorySortOrder.BY_PRICE_LOW_TO_HIGH.getDescription())
				.addToCart(0) // add cheapest
				.sortItems(InventorySortOrder.BY_PRICE_HIGH_TO_LOW.getDescription())
				.addToCart(1) // add second costliest
				.goToCart()
				.checkout()
				.enterYourInfo("test", "test", "test")
				.continueCheckout();

		Map<String, String> expectedItemsAndPrices = productsPage.getMapOfCartItemsToPrice();

		assertEquals(checkoutOverviewPage.getItemNamesToPricesMap(), expectedItemsAndPrices);
	}

	@Test(description = "Verify title and total amount matches items shown")
	public void testCheckoutOverview() {
		checkoutOverviewPage = new LoginPage(getDriver())
				.login(AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
						AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME))
				.addToCart(0)
				.goToCart()
				.checkout()
				.enterYourInfo("test", "test", "test")
				.continueCheckout();

		assertEquals(checkoutOverviewPage.getTitle(), "CHECKOUT: OVERVIEW");
		assertEquals(checkoutOverviewPage.calculateTotalPrice(), checkoutOverviewPage.getItemTotal());
	}

	@Test(description = "Verify items added in ProductsPage appear in Checkout", dataProvider = "getTestItemData")
	public void testCheckoutOverview_ItemsAddedArePresentInCheckout(String title, String price) {
		ProductsPage productsPage = new LoginPage(getDriver()).login(
				AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
				AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME));
		checkoutOverviewPage = productsPage.addToCart(title, price)
				.goToCart()
				.checkout()
				.enterYourInfo("test", "test", "test")
				.continueCheckout();

		Map<String, String> expectedItemsAndPrices = productsPage.getMapOfCartItemsToPrice();
		assertEquals(checkoutOverviewPage.getItemNamesToPricesMap(), expectedItemsAndPrices);
	}

	@DataProvider
	private Object[][] getTestItemData() {
		// This can also be modified to accept data from excel data file
		Object[][] data = { { "Sauce Labs Backpack", "$29.99" }, { "Sauce Labs Bike Light", "$9.99" } };
		return data;
	}

	@Override
	@AfterMethod
	public void tearDown() {
		super.tearDown();
	}

}
