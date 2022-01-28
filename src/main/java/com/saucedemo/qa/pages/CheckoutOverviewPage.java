package com.saucedemo.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.saucedemo.qa.base.PageBase;

public class CheckoutOverviewPage extends PageBase {

	public CheckoutOverviewPage(WebDriver driver) {
		super(driver);
	}

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

	public CheckoutCompletePage proceedToCheckoutCompletePage() {
		finishButton.click();
		return new CheckoutCompletePage(getDriver());
	}

}
