package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.saucedemo.qa.utils.TestBase;
import com.saucedemo.qa.utils.TestUtils;

public class LoginFunctionalityTest extends TestBase {

	LoginPage loginPage;

	@Override
	@BeforeMethod
	public void setUp() {
		super.setUp();
		loginPage = new LoginPage(getDriver());
	}

	@Test(dataProvider = "getSuccessfulLoginData")
	public void testSuccessfulLogin(String username, String password) {
		assertEquals(loginPage.login(username, password)
				.getTitle(), "PRODUCTS");
	}

	@Test(dataProvider = "getFailedLoginData")
	public void testFailedLogin(String username, String password, String errorText) {
		assertNull(loginPage.login(username, password));
		assertTrue(loginPage.isErrorMessagePresent());
		assertEquals(loginPage.getErrorText(), errorText);
	}

	@DataProvider
	private Object[][] getSuccessfulLoginData() {
		return TestUtils.getTestData("LoginSuccessful");
	}

	@DataProvider
	private Object[][] getFailedLoginData() {
		return TestUtils.getTestData("LoginFailure");
	}

	@Override
	@AfterMethod
	public void tearDown() {
		super.tearDown();
	}

}
