package com.saucedemo.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saucedemo.qa.base.PageBase;

public class ProductsPage extends PageBase {

	Logger logger = LoggerFactory.getLogger(ProductsPage.class);

	private static final String ADD_TO_CART_BUTTON_LABEL = "ADD TO CART";

	private static final int DOLLAR_SIGN_INDEX = 1;

	// Object repository for Products page
	@FindBy(className = "inventory_item_name")
	private List<WebElement> inventoryList;

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

	public ProductsPage(WebDriver driver) {
		super(driver);
	}

	public void printInventoryPrices() {
		for (WebElement item : inventoryPrices) {
			logger.info(item.getText());
			// System.out.println(item.getText());
		}
		System.out.println("Complete");
	}

	public void sortItems(String sortOption) {
		Select select = new Select(sortDropDown);
		select.selectByVisibleText(sortOption);
	}

	public void addToCart(int itemIndex) {
		if (addRemoveButtons.get(itemIndex).getText().equals(ADD_TO_CART_BUTTON_LABEL)) {
			addRemoveButtons.get(itemIndex).click();
			System.out.println("Item at index [" + itemIndex + "] added to cart\n");
		} else {
			System.out.println("\nItem at index [" + itemIndex + "] is already added to cart\n");
		}
	}

	public List<Double> getInventoryPrices() {
		List<Double> priceValues = new ArrayList<>();
		for (WebElement inventoryPrice : inventoryPrices) {
			priceValues.add(Double.valueOf(inventoryPrice.getText().substring(DOLLAR_SIGN_INDEX)));
		}
		return priceValues;
	}

	public int getTotalItemCount() {
		return addRemoveButtons.size();
	}

	public int getNumOfItemsOnCart() {
		return Integer.parseInt(itemsOnCart.getText());
	}

	public YourCartPage goToCart() {
		cart.click();
		return new YourCartPage(getDriver());

	}

}
