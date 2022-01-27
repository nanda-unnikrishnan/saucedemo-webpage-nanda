package com.saucedemo.qa.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saucedemo.qa.base.PageBase;

public class LoginPage extends PageBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

	// Object repository for the page
	@FindBy(id = "user-name")
	WebElement username;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(id = "login-button")
	WebElement loginButton;

	@FindBy(xpath = "//h3//button[@class='error-button']")
	WebElement errorButton;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public ProductsPage login(String usernameValue, String passwordValue) {
		username.clear();
		username.sendKeys(usernameValue);
		password.clear();
		password.sendKeys(passwordValue);

		loginButton.click();

		// Reduce timeout so as to fail fast when element is not found
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
		try {
			if (errorButton.isDisplayed()) {
				LOGGER.error("Unable to login");
				return null;
			}
		} catch (NoSuchElementException e) {
			// Ignore this error as the error button is not seen on successfull login
		}

		LOGGER.error("Login successfull");
		// Reset timeout
		getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		return new ProductsPage(getDriver());
	}

}
