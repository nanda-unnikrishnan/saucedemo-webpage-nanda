package com.saucedemo.qa.base;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {

	private static Properties props;

	static {
		try {
			props = new Properties();
			props.load(AppConfig.class.getClassLoader().getResourceAsStream("config.properties"));
			props.putAll(System.getProperties());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getConfigValue(String configName) {
		return props.getProperty(configName);
	}
}
