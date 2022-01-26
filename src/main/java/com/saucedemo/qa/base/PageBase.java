package com.saucedemo.qa.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class PageBase {

	@FindBy(xpath = "//span[@class='title']")
	private WebElement pageTitle;

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

}
