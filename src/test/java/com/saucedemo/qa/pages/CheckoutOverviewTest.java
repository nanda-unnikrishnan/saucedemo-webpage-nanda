package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.base.InventorySortOrder;
import com.saucedemo.qa.base.PageTitle;
import com.saucedemo.qa.base.TestBase;
import com.saucedemo.qa.utils.TestUtils;

public class CheckoutOverviewTest extends TestBase {

	CheckoutOverviewPage checkoutOverviewPage;

	@Override
	@BeforeMethod
	public void setUp() {
		super.setUp();

	}

	@Test(description = "Verify Checkout Overview Page title ", priority = 1)
	public void testPageTitle() {
		checkoutOverviewPage = new LoginPage(getDriver())
				.login(AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
						AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME))
				.addToCart(0)
				.proceedToYourCartPage()
				.proceedToYourInfoPage()
				.enterYourInfo("sampleFirstName", "sampleLastName", "samplePostalCode")
				.proceedToCheckoutOverviewPage();

		assertEquals(checkoutOverviewPage.getTitle(), PageTitle.CHECKOUT_OVERVIEW_PAGE_TITLE.getTitleText());
	}

	@Test(description = "Verify cheapest and second costliest items are added in cart")
	public void testCheckoutOverview_ItemsAddedArePresentInCheckout_CheapestAndSecondCostliest() {

		ProductsPage productsPage = new LoginPage(getDriver()).login(
				AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
				AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME));

		productsPage.sortItems(InventorySortOrder.BY_PRICE_LOW_TO_HIGH.getDescription())
				.addToCart(0) // add cheapest
				.sortItems(InventorySortOrder.BY_PRICE_HIGH_TO_LOW.getDescription())
				.addToCart(1); // add second costliest
		assertEquals(productsPage.getNumOfItemsOnCart(), 2, "Mismatch in cart item count");

		checkoutOverviewPage = productsPage.proceedToYourCartPage()
				.proceedToYourInfoPage()
				.enterYourInfo("sampleFirstName", "sampleLastName", "samplePostalCode")
				.proceedToCheckoutOverviewPage();

		List<String> expectedItemNames = productsPage.getInventoryNames();
		assertEquals(checkoutOverviewPage.getInventoryNames(), expectedItemNames);
	}

	@Test(description = "Verify the item total displayed as the sum of individual item prices")
	public void testItemTotal() {

		checkoutOverviewPage = new LoginPage(getDriver())
				.login(AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
						AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME))
				.addToCart(0)
				.proceedToYourCartPage()
				.proceedToYourInfoPage()
				.enterYourInfo("sampleFirstName", "sampleLastName", "samplePostalCode")
				.proceedToCheckoutOverviewPage();

		assertEquals(checkoutOverviewPage.calculateTotalPrice(), checkoutOverviewPage.getItemTotal());
	}

	@DataProvider
	private Object[][] getTestItemData() {
		// This can also be modified to accept data from excel data file
		Object[][] data = { { "Sauce Labs Backpack", "$29.99" }, { "Sauce Labs Bike Light", "$9.99" } };
		return data;
	}

	@Test(description = "Verify items added to cart in ProductsPage appear in Checkout Overview Page", dataProvider = "getTestItemData")
	public void testCheckoutOverview_ItemsAddedInProductsPageArePresentInCheckoutOverview(String title, String price) {
		ProductsPage productsPage = new LoginPage(getDriver()).login(
				AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
				AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME));
		checkoutOverviewPage = productsPage.addToCart(title, price)
				.proceedToYourCartPage()
				.proceedToYourInfoPage()
				.enterYourInfo("sampleFirstName", "sampleLastName", "samplePostalCode")
				.proceedToCheckoutOverviewPage();

		List<String> expectedItemNames = productsPage.getItemsAddedToCart();
		assertEquals(checkoutOverviewPage.getInventoryNames(), expectedItemNames);
	}

	@Test(description = "Verify items present in Your Cart Page appear in Checkout Overview Page", dataProvider = "getTestItemData")
	public void testCheckoutOverview_ItemsPresentInYourCartPageArePresentInCheckoutOverview(String title,
			String price) {
		YourCartPage yourCartPage = new LoginPage(getDriver())
				.login(AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
						AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME))
				.addToCart(title, price)
				.proceedToYourCartPage();
		List<String> expectedItemNames = yourCartPage.getInventoryNames();

		checkoutOverviewPage = yourCartPage.proceedToYourInfoPage()
				.enterYourInfo("sampleFirstName", "sampleLastName", "samplePostalCode")
				.proceedToCheckoutOverviewPage();

		assertEquals(checkoutOverviewPage.getInventoryNames(), expectedItemNames);
	}

	@AfterMethod
	@Override
	public void tearDown(ITestResult result) {
		TestUtils.takeScreenshot(getDriver(), getScreenshotName(result));
		super.tearDown(result);
	}

}
