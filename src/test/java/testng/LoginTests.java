package testng;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ConfigReader;
import utils.DriverManager;
import constants.AppConstants;
import io.qameta.allure.*;

@Epic("Authentication")
@Feature("User Login")
public class LoginTests {

    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeClass
    public void setupClass() {
        // Load configuration
        ConfigReader.getProperty("browser"); // This initializes config
    }

    @BeforeMethod
    public void setUp() {
        // Initialize driver
        DriverManager.initializeDriver();

        // Initialize page objects AFTER driver is created
        loginPage = new LoginPage(DriverManager.getDriver());
        productsPage = new ProductsPage(DriverManager.getDriver());

        // Navigate to login page
        loginPage.navigateToLoginPage();
    }

    @Test(priority = 1, groups = {"smoke", "regression"})
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    public void testValidLogin() {
        String username = ConfigReader.getValidUsername();
        String password = ConfigReader.getValidPassword();

        productsPage = loginPage.login(username, password);

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "User should be redirected to products page");
        Assert.assertEquals(productsPage.getPageTitle(), AppConstants.PRODUCTS_PAGE_TITLE);
    }

    @Test(priority = 2, groups = {"regression"})
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidUsername() {
        String invalidUsername = "invalid_user";
        String password = ConfigReader.getValidPassword();

        loginPage.login(invalidUsername, password);

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface"));
    }

    @Test(priority = 3, groups = {"regression"})
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidPassword() {
        String username = ConfigReader.getValidUsername();
        String invalidPassword = "invalid_password";

        loginPage.login(username, invalidPassword);

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
    }

    @Test(priority = 4, groups = {"regression"})
    @Story("Locked User")
    @Severity(SeverityLevel.NORMAL)
    public void testLockedOutUser() {
        String lockedUsername = ConfigReader.getLockedUsername();
        String password = ConfigReader.getValidPassword();

        loginPage.login(lockedUsername, password);

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"));
    }

    @Test(priority = 5, groups = {"regression"})
    @Story("Empty Credentials")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyUsername() {
        loginPage.enterUsername("");
        loginPage.enterPassword(ConfigReader.getValidPassword());
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }

    @Test(priority = 6, groups = {"regression"})
    @Story("Empty Credentials")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyPassword() {
        loginPage.enterUsername(ConfigReader.getValidUsername());
        loginPage.enterPassword("");
        loginPage.clickLoginButton();

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"));
    }

    @Test(priority = 7, groups = {"smoke"})
    @Story("Login Page UI")
    @Severity(SeverityLevel.MINOR)
    public void testLoginPageElements() {
        Assert.assertTrue(loginPage.isLogoDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}