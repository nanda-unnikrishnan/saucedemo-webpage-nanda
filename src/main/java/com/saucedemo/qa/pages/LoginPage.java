package com.saucedemo.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.saucedemo.qa.base.PageBase;

public class LoginPage extends PageBase {

	// Object repository for the page
	@FindBy(id = "user-name")
	WebElement username;

	@FindBy(id = "password")
	WebElement password;

	@FindBy(id = "login-button")
	WebElement loginButton;

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public ProductsPage login(String user_name, String pwd) {

		username.sendKeys(user_name);
		password.sendKeys(pwd);
		loginButton.click();

		return new ProductsPage(getDriver());
	}

}
