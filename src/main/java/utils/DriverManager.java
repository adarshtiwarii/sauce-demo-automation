package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Driver Manager - Singleton Pattern
 * Manages WebDriver instances for different browsers
 */
public class DriverManager {

    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Get WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Initialize WebDriver
     */
    public static void initializeDriver() {
        String browser = ConfigReader.getProperty("browser");
        String headless = ConfigReader.getProperty("headless");

        if (driver.get() == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = getChromeOptions();
                    if ("true".equalsIgnoreCase(headless)) {
                        chromeOptions.addArguments("--headless");
                    }
                    driver.set(new ChromeDriver(chromeOptions));
                    log.info("Chrome Driver initialized");
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = getFirefoxOptions();
                    if ("true".equalsIgnoreCase(headless)) {
                        firefoxOptions.addArguments("--headless");
                    }
                    driver.set(new FirefoxDriver(firefoxOptions));
                    log.info("Firefox Driver initialized");
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = getEdgeOptions();
                    driver.set(new EdgeDriver(edgeOptions));
                    log.info("Edge Driver initialized");
                    break;

                default:
                    log.warn("Browser '" + browser + "' not recognized. Using Chrome.");
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver(getChromeOptions()));
                    break;
            }

            driver.get().manage().window().maximize();
            driver.get().manage().deleteAllCookies();
            log.info("Browser window maximized and cookies cleared");
        }
    }

    /**
     * Chrome Options
     */
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        return options;
    }

    /**
     * Firefox Options
     */
    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        return options;
    }

    /**
     * Edge Options
     */
    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        return options;
    }

    /**
     * Quit WebDriver
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            log.info("Driver quit successfully");
        }
    }
}