package com.saucedemo.qa.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class PageBase {

	@FindBy(xpath = "//span[@class='title']")
	private WebElement pageTitle;

	@FindBy(xpath = "//div[@class='error-message-container error']")
	WebElement errorMessageBlock;

	private WebDriver driver;

	public PageBase(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String getTitle() {
		return this.pageTitle.getText();
	}

	protected WebDriver getDriver() {
		return this.driver;
	}

	public String getErrorText() {
		return errorMessageBlock.getText();
	}

	public boolean isErrorMessagePresent() {
		getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
		boolean isErrorMessagePresent;
		try {
			isErrorMessagePresent = errorMessageBlock.isDisplayed();
		} catch (NoSuchElementException e) {
			isErrorMessagePresent = false;
		}
		getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return isErrorMessagePresent;
	}

}
