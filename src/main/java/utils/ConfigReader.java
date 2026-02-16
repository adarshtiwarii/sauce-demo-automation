package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Reader
 * Reads properties from config.properties file
 */
public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";

    /**
     * Load configuration
     */
    static {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config file: " + CONFIG_FILE_PATH);
        }
    }

    /**
     * Get property value
     */
    public static String getProperty(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = properties.getProperty(key);
        }
        return value;
    }

    /**
     * Get base URL
     */
    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    /**
     * Get browser
     */
    public static String getBrowser() {
        return getProperty("browser");
    }

    /**
     * Get valid username
     */
    public static String getValidUsername() {
        return getProperty("valid.username");
    }

    /**
     * Get valid password
     */
    public static String getValidPassword() {
        return getProperty("valid.password");
    }

    /**
     * Get locked username
     */
    public static String getLockedUsername() {
        return getProperty("locked.username");
    }

    /**
     * Get timeout
     */
    public static int getTimeout() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }
}