package utils;

import java.util.Properties;

public class DatabaseConfiguration {
	private static final Properties PROPERTIES = new Properties();

	static {
		try {
			PROPERTIES.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sqlconfig.properties"));
		} catch (Exception e) {
			System.err.println("Failed to Load SQL Properties.");
			System.out.println("Failed to Load SQL Properties.");
		}
	}

	public static String getValue(String key) {
		return PROPERTIES.getProperty(key);
	}
}
