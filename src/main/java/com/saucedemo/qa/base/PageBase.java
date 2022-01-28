package com.saucedemo.qa.base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class PageBase {

	protected static final int DOLLAR_SIGN_INDEX = 1;

	@FindBy(xpath = "//span[@class='title']")
	private WebElement pageTitle;

	@FindBy(xpath = "//div[@class='error-message-container error']")
	WebElement errorMessageBlock;

	private WebDriver driver;

	@FindBy(className = "inventory_item_name")
	protected List<WebElement> inventoryNames;

	@FindBy(xpath = "//div[@class='inventory_item_price']")
	protected List<WebElement> inventoryPrices;

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
		getDriver().manage()
				.timeouts()
				.implicitlyWait(1, TimeUnit.MILLISECONDS);
		boolean isErrorMessagePresent;
		try {
			isErrorMessagePresent = errorMessageBlock.isDisplayed();
		} catch (NoSuchElementException e) {
			isErrorMessagePresent = false;
		}
		getDriver().manage()
				.timeouts()
				.implicitlyWait(20, TimeUnit.SECONDS);
		return isErrorMessagePresent;
	}

	public List<String> getInventoryNames() {
		List<String> inventoryNamesValues = new ArrayList<>();
		for (WebElement element : inventoryNames) {
			inventoryNamesValues.add(element.getText());
		}
		return inventoryNamesValues;
	}

	public List<Double> getInventoryPrices() {
		List<Double> priceValues = new ArrayList<>();
		for (WebElement inventoryPrice : inventoryPrices) {
			priceValues.add(Double.valueOf(inventoryPrice.getText()
					.substring(DOLLAR_SIGN_INDEX)));
		}
		return priceValues;
	}

}
