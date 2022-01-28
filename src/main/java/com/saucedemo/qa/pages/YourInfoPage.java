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
	private WebElement firstName;

	@FindBy(id = "last-name")
	private WebElement lastName;

	@FindBy(id = "postal-code")
	private WebElement postalCode;

	@FindBy(id = "continue")
	private WebElement continueButton;

	@FindBy(xpath = "//h3//button[@class='error-button']")
	private WebElement errorButton;

	public YourInfoPage enterYourInfo(String firstNameValue, String lastNameValue, String postalCodeValue) {
		firstName.sendKeys(firstNameValue);
		lastName.sendKeys(lastNameValue);
		postalCode.sendKeys(postalCodeValue);
		return this;
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
