package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.pages.LoginPage;
import com.saucedemo.qa.pages.ProductsPage;
import com.saucedemo.qa.pages.YourCartPage;
import com.saucedemo.qa.pages.YourInfoPage;
import com.saucedemo.qa.utils.TestBase;

public class YourCartPageTest extends TestBase {

	LoginPage loginPage;
	ProductsPage productsPage;
	YourCartPage yourCartPage;
	YourInfoPage yourInfoPage;

	@Override
	@BeforeTest
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
	}

	@Test
	public void testCheckout() {
		yourInfoPage = yourCartPage.checkout();
		assertEquals(yourInfoPage.getTitle(), "CHECKOUT: YOUR INFORMATION");
	}

	@Override
	@AfterTest
	public void tearDown() {
		super.tearDown();
	}

}
