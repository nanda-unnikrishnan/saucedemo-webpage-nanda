package com.saucedemo.qa.base;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {

	private static final Properties PROPERTIES;

	static {
		try {
			PROPERTIES = new Properties();
			PROPERTIES.load(AppConfig.class.getClassLoader()
					.getResourceAsStream("config.properties"));
			PROPERTIES.putAll(System.getProperties());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getConfigValue(String configName) {
		return PROPERTIES.getProperty(configName);
	}
}
