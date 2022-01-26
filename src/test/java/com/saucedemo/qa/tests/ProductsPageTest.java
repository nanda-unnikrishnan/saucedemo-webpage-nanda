package com.saucedemo.qa.tests;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.pages.LoginPage;
import com.saucedemo.qa.pages.ProductsPage;
import com.saucedemo.qa.pages.YourCartPage;

public class ProductsPageTest extends TestBase {

	LoginPage loginPage;
	ProductsPage productsPage;
	YourCartPage yourCartPage;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp();
		loginPage = new LoginPage(getDriver());
		productsPage = loginPage.login(AppConfig.getConfigValue("username"), AppConfig.getConfigValue("password"));
	}

	@Test(priority = 1)
	public void testSortOrderChange() {

		System.out.println("\nInventory List with default sort\n");
		productsPage.displayInventory();

		productsPage.sortBy("Price (high to low)");

		System.out.println("\nInventory List After Sorting\n");
		productsPage.displayInventory();

		// Placeholder for validations on whether items are sorted correctly.

	}

	@Test(priority = 2)
	public void testAddItems() {

		productsPage.addToCart();
		int itemsAdded = productsPage.getNumOfItemsOnCart();
		assertEquals(itemsAdded, 2);
	}

	@Test(priority = 3)
	public void testNavigationToCart() {
		yourCartPage = productsPage.goToCart();
		assertEquals(yourCartPage.getTitle(), "YOUR CART");
	}

	@Override
	@AfterClass
	public void tearDown() {
		super.tearDown();
	}

}
