package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.pages.LoginPage;
import com.saucedemo.qa.pages.ProductsPage;
import com.saucedemo.qa.utils.TestBase;

public class LoginPageTest extends TestBase {

	LoginPage loginPage;

	ProductsPage productsPage;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp();
		loginPage = new LoginPage(getDriver());
	}

	@Test
	public void testSuccessfulLogin() throws Exception {
		productsPage = loginPage.login(AppConfig.getConfigValue("username"), AppConfig.getConfigValue("password"));
		assertEquals(productsPage.getTitle(), "PRODUCTS");
	}

	@Override
	@AfterClass
	public void tearDown() {
		super.tearDown();
	}

}
