package com.saucedemo.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.saucedemo.qa.base.PageBase;

public class YourInfoPage extends PageBase {

	public YourInfoPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "first-name")
	WebElement firstName;

	@FindBy(id = "last-name")
	WebElement lastName;

	@FindBy(id = "postal-code")
	WebElement postalCode;

	@FindBy(id = "continue")
	WebElement continueButton;

	public void enterYourInfo() {
		firstName.sendKeys("Test");
		lastName.sendKeys("User");
		postalCode.sendKeys("TU1 1TU");
	}

	public CheckoutOverviewPage continueCheckout() {
		continueButton.click();
		return new CheckoutOverviewPage(getDriver());
	}

	// Getter methods

	public WebElement getFirstName() {
		return firstName;
	}

	public WebElement getLastName() {
		return lastName;
	}

	public WebElement getPostalCode() {
		return postalCode;
	}

}
