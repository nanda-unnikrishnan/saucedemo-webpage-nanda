package com.saucedemo.qa.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.saucedemo.qa.base.PageBase;

public class CheckoutOverviewPage extends PageBase {

	private static final int DOLLAR_SIGN_INDEX = 1;

	public CheckoutOverviewPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(className = "inventory_item_name")
	private List<WebElement> inventoryNames;

	@FindBy(xpath = "//div[@class='inventory_item_price']")
	List<WebElement> inventoryPrices;

	@FindBy(xpath = "//div[@class='summary_subtotal_label']")
	WebElement itemTotal;

	@FindBy(id = "finish")
	WebElement finishButton;

	public double calculateTotalPrice() {

		double calculatedPrice = 0.0;
		for (int i = 0; i < inventoryPrices.size(); i++) {
			calculatedPrice = calculatedPrice + (Double.parseDouble(inventoryPrices.get(i)
					.getText()
					.substring(DOLLAR_SIGN_INDEX)));
		}

		return calculatedPrice;
	}

	public double getItemTotal() {
		return Double.parseDouble(itemTotal.getText()
				.substring("Item total: $".length()));
	}

	public CheckoutCompletePage clickOnFinish() {
		finishButton.click();
		return new CheckoutCompletePage(getDriver());
	}

	public Map<String, String> getItemNamesToPricesMap() {
		Map<String, String> itemNamesToPricesMap = new HashMap<>();
		for (int index = 0; index < inventoryNames.size(); index++) {
			itemNamesToPricesMap.put(inventoryNames.get(index)
					.getText(),
					inventoryPrices.get(index)
							.getText());
		}
		return itemNamesToPricesMap;
	}
}
