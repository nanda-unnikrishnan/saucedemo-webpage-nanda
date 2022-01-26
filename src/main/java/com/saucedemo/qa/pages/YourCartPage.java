package com.saucedemo.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.saucedemo.qa.base.PageBase;

public class YourCartPage extends PageBase {

	public YourCartPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "checkout")
	WebElement checkoutButton;

	public YourInfoPage checkout() {
		checkoutButton.click();
		return new YourInfoPage(getDriver());
	}
}
