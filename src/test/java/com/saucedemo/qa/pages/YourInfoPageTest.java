package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.pages.CheckoutCompletePage;
import com.saucedemo.qa.pages.CheckoutOverviewPage;
import com.saucedemo.qa.pages.LoginPage;
import com.saucedemo.qa.pages.ProductsPage;
import com.saucedemo.qa.pages.YourCartPage;
import com.saucedemo.qa.pages.YourInfoPage;
import com.saucedemo.qa.utils.TestBase;

public class YourInfoPageTest extends TestBase {

	LoginPage loginPage;
	ProductsPage productsPage;
	YourCartPage yourCartPage;
	YourInfoPage yourInfoPage;
	CheckoutOverviewPage checkoutOverviewPage;
	CheckoutCompletePage checkoutCompletePage;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp();
		loginPage = new LoginPage(getDriver());
		productsPage = loginPage.login(AppConfig.getConfigValue("username"), AppConfig.getConfigValue("password"));
		productsPage.sortItems("Price (high to low)");
		// Add second costliest item
		productsPage.addToCart(1);
		// Add cheapest item
		productsPage.addToCart(productsPage.getTotalItemCount() - 1);
		yourCartPage = productsPage.goToCart();
		yourInfoPage = yourCartPage.checkout();
	}

	@Test(priority = 1)
	public void testDataEntry() {
		yourInfoPage.enterYourInfo("Test", "User", "TU1 1TU");
		assertEquals(yourInfoPage.getFirstNameValue(), "Test");
		assertEquals(yourInfoPage.getLastNameValue(), "User");
		assertEquals(yourInfoPage.getPostalCodeValue(), "TU1 1TU");
	}

	@Test(priority = 2)
	public void testCheckoutOverview() {
		checkoutOverviewPage = yourInfoPage.continueCheckout();
		assertEquals(checkoutOverviewPage.getTitle(), "CHECKOUT: OVERVIEW");
		assertEquals(checkoutOverviewPage.calculateTotalPrice(), checkoutOverviewPage.getItemTotal());
	}

	@Test(priority = 3)
	public void testCheckoutCompletion() {
		checkoutCompletePage = checkoutOverviewPage.clickOnFinish();
		assertEquals(checkoutCompletePage.getTitle(), "CHECKOUT: COMPLETE!");
	}

	@Override
	@AfterClass
	public void tearDown() {
		// super.tearDown();
	}

}
