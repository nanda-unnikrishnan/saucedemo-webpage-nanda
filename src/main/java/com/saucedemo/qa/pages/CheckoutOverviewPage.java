package com.saucedemo.qa.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.saucedemo.qa.base.PageBase;

public class CheckoutOverviewPage extends PageBase {

	public CheckoutOverviewPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@class='inventory_item_price']")
	List<WebElement> inventoryPrices;

	@FindBy(xpath = "//div[@class='summary_subtotal_label']")
	WebElement itemTotal;

	public double calculateTotalPrice() {

		double calculatedPrice = 0.0;
		for (int i = 0; i < inventoryPrices.size(); i++) {
			calculatedPrice = calculatedPrice + (Double.parseDouble(inventoryPrices.get(i).getText().substring(1)));
		}

		return calculatedPrice;
	}

	public double getItemTotal() {
		return Double.parseDouble(itemTotal.getText().substring("Item total: $".length()));
	}

}
