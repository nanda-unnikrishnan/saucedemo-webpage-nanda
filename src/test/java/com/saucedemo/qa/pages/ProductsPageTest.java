package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.pages.LoginPage;
import com.saucedemo.qa.pages.ProductsPage;
import com.saucedemo.qa.pages.YourCartPage;
import com.saucedemo.qa.utils.TestBase;

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
		productsPage.printInventoryPrices();

		productsPage.sortItems("Price (high to low)");

		System.out.println("\nInventory List After Sorting\n");
		productsPage.printInventoryPrices();

		// Placeholder for validations on whether items are sorted correctly.

	}

	@Test(priority = 2)
	public void testAddItems() {

		// Add second costliest item
		productsPage.addToCart(1);
		// Add cheapest item
		productsPage.addToCart(productsPage.getTotalItemCount() - 1);
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
