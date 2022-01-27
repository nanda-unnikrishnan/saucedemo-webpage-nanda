package com.saucedemo.qa.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.saucedemo.qa.base.AppConfig;

public class TestBase {

	private WebDriver driver;

	protected void setUp() {
		initializeDriver();
	}

	private void initializeDriver() {
		String browserName = AppConfig.getConfigValue("browser");

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", AppConfig.getConfigValue("chrome.driver.location"));
			driver = new ChromeDriver();
		} else if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", AppConfig.getConfigValue("firefox.driver.location"));
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get(AppConfig.getConfigValue("url"));
	}

	protected WebDriver getDriver() {
		return driver;
	}

	public void tearDown() {
		driver.quit();
	}
}
