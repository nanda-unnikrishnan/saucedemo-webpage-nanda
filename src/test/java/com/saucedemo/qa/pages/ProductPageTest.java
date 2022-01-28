package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.base.InventorySortOrder;
import com.saucedemo.qa.utils.TestBase;

public class ProductPageTest extends TestBase {

	ProductsPage productsPage;

	@Override
	@BeforeMethod
	public void setUp() {
		super.setUp();
		productsPage = new LoginPage(getDriver()).login(AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
				AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME));
	}

	@Test
	public void testSortOrder_PriceHighToLow() {
		// Retrieve prices before changing the default sort on the page
		List<Double> pricesBeforeSorting = productsPage.getInventoryPrices();
		Collections.sort(pricesBeforeSorting, Collections.reverseOrder());
		List<Double> sortedPrices = pricesBeforeSorting;

		// Sort items on the page
		productsPage.sortItems(InventorySortOrder.BY_PRICE_HIGH_TO_LOW.getDescription());
		List<Double> actualPricesAfterSorting = productsPage.getInventoryPrices();
		assertEquals(actualPricesAfterSorting, sortedPrices, "Mismatch in sorted prices");
	}

	@Test
	public void testSortOrder_NameAToZ() {
		// Retrieve prices before changing the default sort on the page
		List<String> namesFromPage = productsPage.getInventoryNames();
		Collections.sort(namesFromPage);

		// Sort items on the page
		productsPage.sortItems(InventorySortOrder.BY_NAME_A_TO_Z.getDescription());
		List<String> actualNamesAfterSorting = productsPage.getInventoryNames();
		assertEquals(actualNamesAfterSorting, namesFromPage, "Mismatch in sorted names");
	}

	@Test
	public void testAddedItemsAppearInCartBadge() {
		productsPage.sortItems(InventorySortOrder.BY_PRICE_HIGH_TO_LOW.getDescription());

		// Add second costliest item
		productsPage.addToCart(1);
		// Add cheapest item
		productsPage.addToCart(productsPage.getTotalItemCount() - 1);

		assertEquals(productsPage.getNumOfItemsOnCart(), 2, "Mismatch in cart item count");
	}

	@Override
	@AfterMethod
	public void tearDown() {
		super.tearDown();
	}

}
