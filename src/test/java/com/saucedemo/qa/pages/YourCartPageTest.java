package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.base.PageBase;
import com.saucedemo.qa.base.PageTitle;
import com.saucedemo.qa.base.TestBase;

public class YourCartPageTest extends TestBase {

	PageBase yourCartPage;

	@Override
	@BeforeMethod
	public void setUp() {
		super.setUp();
	}

	@Test(priority = 1)
	public void testPageTitle() {
		yourCartPage = new LoginPage(getDriver())
				.login(AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
						AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME))
				.addToCart(0)
				.proceedToYourCartPage();

		assertEquals(yourCartPage.getTitle(), PageTitle.YOUR_CART_PAGE_TITLE.getTitleText());
	}

	@Test
	public void testItemsInCart() {
		ProductsPage productsPage = new LoginPage(getDriver()).login(
				AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
				AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME));

		yourCartPage = productsPage.addToCart("Sauce Labs Bike Light", "$9.99")
				.addToCart("Sauce Labs Backpack", "$29.99")
				.proceedToYourCartPage();

		List<String> expectedItemNames = productsPage.getItemsAddedToCart();
		assertEquals(yourCartPage.getInventoryNames(), expectedItemNames);
	}

}
