package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConfigReader;

import java.time.Duration;

/**
 * Base Page - Parent class for all page objects
 * Contains common methods used across all pages
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger log = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getTimeout()));
    }

    /**
     * Wait for element to be visible
     */
    protected WebElement waitForElementVisible(By locator) {
        log.info("Waiting for element to be visible: " + locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     */
    protected WebElement waitForElementClickable(By locator) {
        log.info("Waiting for element to be clickable: " + locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Click element
     */
    protected void click(By locator) {
        waitForElementClickable(locator).click();
        log.info("Clicked on element: " + locator);
    }

    /**
     * Enter text
     */
    protected void enterText(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
        log.info("Entered text '" + text + "' in element: " + locator);
    }

    /**
     * Get text
     */
    protected String getText(By locator) {
        String text = waitForElementVisible(locator).getText();
        log.info("Got text '" + text + "' from element: " + locator);
        return text;
    }

    /**
     * Check if element is displayed
     */
    protected boolean isDisplayed(By locator) {
        try {
            boolean displayed = driver.findElement(locator).isDisplayed();
            log.info("Element " + locator + " displayed: " + displayed);
            return displayed;
        } catch (NoSuchElementException e) {
            log.info("Element " + locator + " not found");
            return false;
        }
    }

    /**
     * Select from dropdown by visible text
     */
    protected void selectByVisibleText(By locator, String text) {
        Select select = new Select(waitForElementVisible(locator));
        select.selectByVisibleText(text);
        log.info("Selected '" + text + "' from dropdown: " + locator);
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        log.info("Page title: " + title);
        return title;
    }

    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        log.info("Current URL: " + url);
        return url;
    }

    /**
     * Navigate to URL
     */
    protected void navigateTo(String url) {
        driver.get(url);
        log.info("Navigated to: " + url);
    }

    /**
     * Wait for page load
     */
    protected void waitForPageLoad() {
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
        log.info("Page loaded completely");
    }

    /**
     * Scroll to element
     */
    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        log.info("Scrolled to element: " + locator);
    }
}