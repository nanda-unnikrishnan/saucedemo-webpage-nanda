package com.saucedemo.qa.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saucedemo.qa.base.PageBase;

public class ProductsPage extends PageBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsPage.class);

	private static final String ADD_TO_CART_BUTTON_LABEL = "ADD TO CART";

	private static final int DOLLAR_SIGN_INDEX = 1;

	// Object repository for Products page
	@FindBy(className = "inventory_item_name")
	private List<WebElement> inventoryNames;

	@FindBy(xpath = "//div[@class='inventory_item_price']")
	List<WebElement> inventoryPrices;

	@FindBy(className = "product_sort_container")
	private WebElement sortDropDown;

	@FindBy(xpath = "//a[@class='shopping_cart_link']")
	private WebElement cart;

	@FindBy(xpath = "//div[@class='inventory_list']//div//button")
	private List<WebElement> addRemoveButtons;

	@FindBy(xpath = "//span[@class='shopping_cart_badge']")
	private WebElement itemsOnCart;

	private final Map<String, String> mapOfCartItemsToPrice;

	public ProductsPage(WebDriver driver) {
		super(driver);
		mapOfCartItemsToPrice = new HashMap<>();
	}

	public void printInventoryPrices() {
		for (WebElement item : inventoryPrices) {
			LOGGER.debug(item.getText());
		}
		LOGGER.debug("Complete");
	}

	public ProductsPage sortItems(String sortOption) {
		Select select = new Select(sortDropDown);
		select.selectByVisibleText(sortOption);
		return this;
	}

	public ProductsPage addToCart(int itemIndex) {
		if (addRemoveButtons.get(itemIndex)
				.getText()
				.equals(ADD_TO_CART_BUTTON_LABEL)) {
			addRemoveButtons.get(itemIndex)
					.click();
			mapOfCartItemsToPrice.put(inventoryNames.get(itemIndex)
					.getText(),
					inventoryPrices.get(itemIndex)
							.getText());
			LOGGER.info("Item at index [{}] added to cart", itemIndex);
		} else {
			LOGGER.info("Item at index [{}] is already added to cart", itemIndex);
		}
		return this;
	}

	public ProductsPage addToCart(String title, String price) {
		for (int index = 0; index < inventoryNames.size(); index++) {
			if (inventoryNames.get(index)
					.getText()
					.equals(title)
					&& inventoryPrices.get(index)
							.getText()
							.equals(price)) {
				addRemoveButtons.get(index)
						.click();
				mapOfCartItemsToPrice.put(inventoryNames.get(index)
						.getText(),
						inventoryPrices.get(index)
								.getText());
				LOGGER.info("Item with title:[{}] and price:[{}] added to cart", title, price);
				break;
			}
		}
		return this;
	}

	public List<Double> getInventoryPrices() {
		List<Double> priceValues = new ArrayList<>();
		for (WebElement inventoryPrice : inventoryPrices) {
			priceValues.add(Double.valueOf(inventoryPrice.getText()
					.substring(DOLLAR_SIGN_INDEX)));
		}
		return priceValues;
	}

	public List<String> getInventoryNames() {
		List<String> inventoryNamesValues = new ArrayList<>();
		for (WebElement element : inventoryNames) {
			inventoryNamesValues.add(element.getText());
		}
		return inventoryNamesValues;
	}

	public int getTotalItemCount() {
		return addRemoveButtons.size();
	}

	public int getNumOfItemsOnCart() {
		return Integer.parseInt(itemsOnCart.getText());
	}

	public Map<String, String> getMapOfCartItemsToPrice() {
		return mapOfCartItemsToPrice;
	}

	public YourCartPage goToCart() {
		cart.click();
		return new YourCartPage(getDriver());

	}

}
