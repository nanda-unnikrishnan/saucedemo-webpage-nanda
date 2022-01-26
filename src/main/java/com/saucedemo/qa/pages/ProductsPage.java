package com.saucedemo.qa.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.saucedemo.qa.base.PageBase;

public class ProductsPage extends PageBase {

	// Object repository for Products page
	@FindBy(className = "inventory_item_name")
	List<WebElement> inventoryList;

	@FindBy(className = "product_sort_container")
	WebElement sortDropDown;

	@FindBy(xpath = "//a[@class='shopping_cart_link']")
	WebElement cart;

	@FindBy(xpath = "//div[@class='inventory_list']//div//button")
	List<WebElement> addRemoveButtons;

	@FindBy(xpath = "//span[@class='shopping_cart_badge']")
	WebElement itemsOnCart;

	public ProductsPage(WebDriver driver) {
		super(driver);
	}

	public void displayInventory() {
		for (WebElement item : inventoryList) {
			System.out.println(item.getText());
		}
	}

	public List<WebElement> sortBy(String sortOption) {

		Select select = new Select(sortDropDown);
		select.selectByVisibleText(sortOption);
		return inventoryList;
	}

	public void addToCart() {

		// This method currently adds the second most expensive and the cheapest items
		// to the cart.
		// This can later be modified to select items based on inputs

		int numOfItems = addRemoveButtons.size();

		if (addRemoveButtons.get(1).getText().equals("ADD TO CART")) {
			addRemoveButtons.get(1).click();
			System.out.println("\nSecond costliest item added to cart\n");
		} else {
			System.out.println(addRemoveButtons.get(1).getText());
			System.out.println("\nSecond costliest item is already added to cart\n");
		}

		if (addRemoveButtons.get(numOfItems - 1).getText().equals("ADD TO CART")) {
			addRemoveButtons.get(numOfItems - 1).click();
			System.out.println("\nCheapest item added to cart\n");
		} else {
			System.out.println("\nCheapest item is already added to cart\n");
		}

	}

	public int getNumOfItemsOnCart() {
		return Integer.parseInt(itemsOnCart.getText());
	}

	public YourCartPage goToCart() {

		cart.click();
		return new YourCartPage(getDriver());

	}

}
