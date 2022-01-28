package com.saucedemo.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saucedemo.qa.base.PageBase;

public class LoginPage extends PageBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

	// Object repository for the page
	@FindBy(id = "user-name")
	private WebElement username;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(id = "login-button")
	private WebElement loginButton;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public ProductsPage login(String usernameValue, String passwordValue) {
		username.clear();
		username.sendKeys(usernameValue);
		password.clear();
		password.sendKeys(passwordValue);

		loginButton.click();

		if (isErrorMessagePresent()) {
			LOGGER.warn("Login unsuccessful");
			return null;
		}

		LOGGER.info("Login button clicked.");
		return new ProductsPage(getDriver());
	}

}
