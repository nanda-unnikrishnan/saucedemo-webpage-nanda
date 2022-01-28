package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.utils.TestBase;

public class CheckoutCompleteTest extends TestBase {

	CheckoutCompletePage checkoutComplete;

	@Override
	@BeforeMethod
	public void setUp() {
		super.setUp();
		checkoutComplete = new LoginPage(getDriver())
				.login(AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
						AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME))
				.addToCart(0)
				.goToCart()
				.checkout()
				.enterYourInfo("firstname", "lastname", "postalCode")
				.continueCheckout()
				.clickOnFinish();
	}

	@Override
	@AfterMethod
	public void tearDown() {
		super.tearDown();
	}

	@Test
	public void CheckoutCompletePageTest() {
		assertEquals(checkoutComplete.getTitle(), "CHECKOUT: COMPLETE!");
	}
}
