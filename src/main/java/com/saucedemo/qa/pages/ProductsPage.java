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

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsPage.class);

	private static final String ADD_TO_CART_BUTTON_LABEL = "ADD TO CART";

	private final List<String> itemsAddedToCart;

	// Object repository for Products page

	@FindBy(className = "product_sort_container")
	private WebElement sortDropDown;

	@FindBy(xpath = "//a[@class='shopping_cart_link']")
	private WebElement cartLink;

	@FindBy(xpath = "//div[@class='inventory_list']//div//button")
	private List<WebElement> addRemoveButtons;

	@FindBy(xpath = "//span[@class='shopping_cart_badge']")
	private WebElement itemsOnCartBadge;

	public ProductsPage(WebDriver driver) {
		super(driver);
		itemsAddedToCart = new ArrayList<>();
	}

	public ProductsPage sortItems(String sortOption) {
		Select select = new Select(sortDropDown);
		select.selectByVisibleText(sortOption);
		return this;
	}

	public ProductsPage addToCart(int itemIndex) {

		// Verify if the item is not already added to cart
		if (addRemoveButtons.get(itemIndex)
				.getText()
				.equals(ADD_TO_CART_BUTTON_LABEL)) {
			addRemoveButtons.get(itemIndex)
					.click();
			itemsAddedToCart.add(inventoryNames.get(itemIndex)
					.getText());
			LOGGER.info("Item at index [{}] added to cart", itemIndex);
		} else {
			LOGGER.info("Item at index [{}] is already added to cart", itemIndex);
		}
		return this;
	}

	public ProductsPage addToCart(String title, String price) {

		for (int index = 0; index < inventoryNames.size(); index++) {
			if (addRemoveButtons.get(index)
					.getText()
					.equals(ADD_TO_CART_BUTTON_LABEL)
					&& inventoryNames.get(index)
							.getText()
							.equals(title)
					&& inventoryPrices.get(index)
							.getText()
							.equals(price)) {
				addRemoveButtons.get(index)
						.click();
				itemsAddedToCart.add(inventoryNames.get(index)
						.getText());
				LOGGER.info("Item with title:[{}] and price:[{}] added to cart", title, price);
				break;
			}
		}
		return this;
	}

	public YourCartPage proceedToYourCartPage() {
		cartLink.click();
		return new YourCartPage(getDriver());

	}

	// Getter methods

	public int getTotalItemCount() {
		return addRemoveButtons.size();
	}

	public int getNumOfItemsOnCart() {
		return Integer.parseInt(itemsOnCartBadge.getText());
	}

	public List<String> getItemsAddedToCart() {
		return itemsAddedToCart;
	}

}
