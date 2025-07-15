package com.favqs.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public final class PropertyFileUtil {

	private final static String filePath = "\\src\\test\\resources\\Util.Properties";
	private final static Properties properties = new Properties();

	private PropertyFileUtil() {
		throw new RuntimeException("Constructor cant be created for this Utility class ");
	}
	
	static {
		loadProperties();
	}

	// Load properties from the file
	private static void loadProperties() {
		try (InputStream input = new FileInputStream(System.getProperty("user.dir")+filePath)) {
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load properties file: " + filePath, e);
		}
	}

	// Get a property value
	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	// Set a property value and save it to the file
	public static void setProperty(String key, String value) {
		properties.setProperty(key, value);
		saveProperties();
	}

	// Save updated properties back to the file
	private static void saveProperties() {
		try (OutputStream output = new FileOutputStream(System.getProperty("user.dir")+filePath)) {
			properties.store(output, "Updated by PropertyFileUtil");
		} catch (IOException e) {
			throw new RuntimeException("Failed to save properties file: " + filePath, e);
		}
	}

}
