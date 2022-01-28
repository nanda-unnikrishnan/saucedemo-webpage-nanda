package com.saucedemo.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.saucedemo.qa.base.PageBase;

public class YourCartPage extends PageBase {

	// Object repository for Products page

	@FindBy(id = "checkout")
	private WebElement checkoutButton;

	public YourCartPage(WebDriver driver) {
		super(driver);
	}

	public YourInfoPage proceedToYourInfoPage() {
		checkoutButton.click();
		return new YourInfoPage(getDriver());
	}

}
