package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.base.PageTitle;
import com.saucedemo.qa.base.TestBase;
import com.saucedemo.qa.utils.TestUtils;

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
				.proceedToYourCartPage()
				.proceedToYourInfoPage()
				.enterYourInfo("sampleFirstName", "sampleLastName", "samplePostalCode")
				.proceedToCheckoutOverviewPage()
				.proceedToCheckoutCompletePage();
	}

	@Test
	public void testPageTitle() {
		assertEquals(checkoutComplete.getTitle(), PageTitle.CHECKOUT_COMPLETE_PAGE_TITLE.getTitleText());
	}

	@AfterMethod
	@Override
	public void tearDown(ITestResult result) {
		TestUtils.takeScreenshot(getDriver(), getScreenshotName(result));
		super.tearDown(result);
	}

}
