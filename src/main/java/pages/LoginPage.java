package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import constants.AppConstants;

/**
 * Login Page Object
 * Contains elements and methods for Login page
 */
public class LoginPage extends BasePage {

    // Locators
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By errorCloseButton = By.className("error-button");
    private final By logoImage = By.className("login_logo");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to login page
     */
    public LoginPage navigateToLoginPage() {
        navigateTo(AppConstants.LOGIN_PAGE_URL);
        waitForPageLoad();
        return this;
    }

    /**
     * Enter username
     */
    public LoginPage enterUsername(String username) {
        enterText(usernameField, username);
        return this;
    }

    /**
     * Enter password
     */
    public LoginPage enterPassword(String password) {
        enterText(passwordField, password);
        return this;
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        click(loginButton);
    }

    /**
     * Perform complete login
     */
    public ProductsPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        log.info("Login performed with username: " + username);
        return new ProductsPage(driver);
    }

    /**
     * Get error message
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /**
     * Check if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    /**
     * Check if logo is displayed
     */
    public boolean isLogoDisplayed() {
        return isDisplayed(logoImage);
    }

    /**
     * Close error message
     */
    public LoginPage closeErrorMessage() {
        if (isErrorMessageDisplayed()) {
            click(errorCloseButton);
            log.info("Error message closed");
        }
        return this;
    }
}