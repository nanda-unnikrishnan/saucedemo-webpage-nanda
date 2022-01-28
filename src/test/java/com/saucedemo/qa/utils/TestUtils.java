package com.saucedemo.qa.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.saucedemo.qa.base.AppConfig;

public class TestUtils {

	private static final Workbook WORKBOOK;

	private static final String SCREENSHOT_DIRECTORY = System.getProperty("user.dir") + IOUtils.DIR_SEPARATOR
			+ "screenshots" + IOUtils.DIR_SEPARATOR;

	static {
		try {
			WORKBOOK = WorkbookFactory.create(TestUtils.class.getClassLoader()
					.getResourceAsStream(AppConfig.getConfigValue("testdata.filename")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	//
	public static Object[][] getTestData(String sheetName) {
		Sheet sheet;

		sheet = WORKBOOK.getSheet(sheetName);

		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0)
				.getLastCellNum()];

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int j = 0; j < sheet.getRow(0)
					.getLastCellNum(); j++) {
				data[i][j] = sheet.getRow(i + 1) // Exclude first row as it is header
						.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK)
						.toString();
			}
		}

		return data;
	}

	public static void takeScreenshot(WebDriver driver, String screenshotFileName) {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(SCREENSHOT_DIRECTORY + screenshotFileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
