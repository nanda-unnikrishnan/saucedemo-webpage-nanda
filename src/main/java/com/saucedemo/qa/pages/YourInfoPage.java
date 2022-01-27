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

	@FindBy(xpath = "//h3//button[@class='error-button']")
	WebElement errorButton;

	public void enterYourInfo(String firstNameValue, String lastNameValue, String postalCodeValue) {
		firstName.sendKeys(firstNameValue);
		lastName.sendKeys(lastNameValue);
		postalCode.sendKeys(postalCodeValue);
	}

	public CheckoutOverviewPage continueCheckout() {
		continueButton.click();
		return new CheckoutOverviewPage(getDriver());
	}

	// Getter methods

	public String getFirstNameValue() {
		return firstName.getAttribute("value");
	}

	public String getLastNameValue() {
		return lastName.getAttribute("value");
	}

	public String getPostalCodeValue() {
		return postalCode.getAttribute("value");
	}

	public boolean isValidationErrorPresent() {
		return errorButton.isDisplayed();
	}

}
