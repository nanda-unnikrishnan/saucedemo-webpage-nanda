package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.base.PageTitle;
import com.saucedemo.qa.base.TestBase;
import com.saucedemo.qa.utils.TestUtils;

public class YourInfoPageTest extends TestBase {

	YourInfoPage yourInfoPage;

	@Override
	@BeforeMethod
	public void setUp() {
		super.setUp();
		yourInfoPage = new LoginPage(getDriver())
				.login(AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
						AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME))
				.addToCart(0)
				.proceedToYourCartPage()
				.proceedToYourInfoPage();
	}

	@Test(priority = 1)
	public void testPageTitle() {
		assertEquals(yourInfoPage.getTitle(), PageTitle.YOUR_INFO_PAGE_TITLE.getTitleText());
	}

	@DataProvider
	private Object[][] getValidDataEntries() {
		return TestUtils.getTestData("UserInfoValid");
	}

	@DataProvider
	private Object[][] getInvalidDataEntries() {
		return TestUtils.getTestData("UserInfoInvalid");
	}

	@Test(dataProvider = "getValidDataEntries")
	public void testValidDataEntry(String firstname, String lastname, String postalCode) {
		yourInfoPage.enterYourInfo(firstname, lastname, postalCode);

		assertEquals(yourInfoPage.getFirstNameValue(), firstname);
		assertEquals(yourInfoPage.getLastNameValue(), lastname);
		assertEquals(yourInfoPage.getPostalCodeValue(), postalCode);
		assertEquals(yourInfoPage.proceedToCheckoutOverviewPage()
				.getTitle(), "CHECKOUT: OVERVIEW");
	}

	@Test(dataProvider = "getInvalidDataEntries")
	public void testIncorrectDataEntry(String firstname, String lastname, String postalCode, String errorText) {
		yourInfoPage.enterYourInfo(firstname, lastname, postalCode);
		yourInfoPage.proceedToCheckoutOverviewPage();
		assertTrue(yourInfoPage.isErrorMessagePresent());
		assertEquals(yourInfoPage.getErrorText(), errorText);
	}

}
