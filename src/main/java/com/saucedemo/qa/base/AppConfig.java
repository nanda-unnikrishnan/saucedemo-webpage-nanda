package com.saucedemo.qa.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {

	private static Properties props;

	static {
		try {
			props = new Properties();
			String configFilePath = "D:\\workspaces\\eclipse\\SauceDemoTest\\src\\main\\java\\com\\saucedemo\\qa\\config\\config.properties";
			FileInputStream ip = new FileInputStream(configFilePath);
			props.load(ip);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getConfigValue(String configName) {
		return props.getProperty(configName);
	}
}
