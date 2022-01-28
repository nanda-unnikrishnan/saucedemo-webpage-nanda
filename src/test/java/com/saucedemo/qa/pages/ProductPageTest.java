package com.saucedemo.qa.pages;

import static org.testng.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.saucedemo.qa.base.AppConfig;
import com.saucedemo.qa.base.InventorySortOrder;
import com.saucedemo.qa.base.PageTitle;
import com.saucedemo.qa.base.TestBase;

public class ProductPageTest extends TestBase {

	ProductsPage productsPage;

	@Override
	@BeforeMethod
	public void setUp() {
		super.setUp();
		productsPage = new LoginPage(getDriver()).login(AppConfig.getConfigValue(STANDARD_USERNAME_CONFIG_NAME),
				AppConfig.getConfigValue(STANDARD_PASSWORD_CONFIG_NAME));
	}

	@Test(priority = 1)
	public void testPageTitle() {
		assertEquals(productsPage.getTitle(), PageTitle.PRODUCTS_PAGE_TITLE.getTitleText());
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
		assertEquals(actualPricesAfterSorting, sortedPrices, "Prices not sorted from high to low.");
	}

	@Test
	public void testSortOrder_NameAToZ() {
		// Retrieve prices before changing the default sort on the page
		List<String> namesFromPage = productsPage.getInventoryNames();
		Collections.sort(namesFromPage);

		// Sort items on the page
		productsPage.sortItems(InventorySortOrder.BY_NAME_A_TO_Z.getDescription());
		List<String> actualNamesAfterSorting = productsPage.getInventoryNames();
		assertEquals(actualNamesAfterSorting, namesFromPage, "Products not sorted by name(A to Z).");
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

}
