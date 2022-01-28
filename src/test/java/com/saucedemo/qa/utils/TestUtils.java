package com.saucedemo.qa.utils;

import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.saucedemo.qa.base.AppConfig;

public class TestUtils {

	private static final Workbook WORKBOOK;

	static {
		try {
			WORKBOOK = WorkbookFactory.create(TestUtils.class.getClassLoader()
					.getResourceAsStream(AppConfig.getConfigValue("testdata.filename")));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

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

}
