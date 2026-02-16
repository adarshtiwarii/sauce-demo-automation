package testng;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ConfigReader;
import utils.DriverManager;
import constants.AppConstants;
import io.qameta.allure.*;

/**
 * Login Test Cases using TestNG
 */
@Epic("Authentication")
@Feature("User Login")
public class LoginTests {

    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeMethod
    public void setUp() {
        DriverManager.initializeDriver();
        loginPage = new LoginPage(DriverManager.getDriver());
        productsPage = new ProductsPage(DriverManager.getDriver());
        loginPage.navigateToLoginPage();
    }

    @Test(priority = 1, groups = {"smoke", "regression"},
            description = "Verify login with valid credentials")
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test to verify user can login with valid username and password")
    public void testValidLogin() {
        // Arrange
        String username = ConfigReader.getValidUsername();
        String password = ConfigReader.getValidPassword();

        // Act
        loginPage.login(username, password);

        // Assert
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "User should be redirected to products page");
        Assert.assertEquals(productsPage.getPageTitle(), AppConstants.PRODUCTS_PAGE_TITLE,
                "Products page title should match");
    }

    @Test(priority = 2, groups = {"regression"},
            description = "Verify login with invalid username")
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidUsername() {
        // Arrange
        String invalidUsername = "invalid_user";
        String password = ConfigReader.getValidPassword();

        // Act
        loginPage.login(invalidUsername, password);

        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface"),
                "Error message should contain 'Epic sadface'");
    }

    @Test(priority = 3, groups = {"regression"},
            description = "Verify login with invalid password")
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidPassword() {
        // Arrange
        String username = ConfigReader.getValidUsername();
        String invalidPassword = "invalid_password";

        // Act
        loginPage.login(username, invalidPassword);

        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("do not match"),
                "Error message should indicate credentials don't match");
    }

    @Test(priority = 4, groups = {"regression"},
            description = "Verify login with locked out user")
    @Story("Locked User")
    @Severity(SeverityLevel.NORMAL)
    public void testLockedOutUser() {
        // Arrange
        String lockedUsername = ConfigReader.getLockedUsername();
        String password = ConfigReader.getValidPassword();

        // Act
        loginPage.login(lockedUsername, password);

        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for locked user");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"),
                "Error message should indicate user is locked out");
    }

    @Test(priority = 5, groups = {"regression"},
            description = "Verify login with empty username")
    @Story("Empty Credentials")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyUsername() {
        // Arrange
        String username = "";
        String password = ConfigReader.getValidPassword();

        // Act
        loginPage.login(username, password);

        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                "Error message should indicate username is required");
    }

    @Test(priority = 6, groups = {"regression"},
            description = "Verify login with empty password")
    @Story("Empty Credentials")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyPassword() {
        // Arrange
        String username = ConfigReader.getValidUsername();
        String password = "";

        // Act
        loginPage.login(username, password);

        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"),
                "Error message should indicate password is required");
    }

    @Test(priority = 7, groups = {"smoke"},
            description = "Verify login page elements are displayed")
    @Story("Login Page UI")
    @Severity(SeverityLevel.MINOR)
    public void testLoginPageElements() {
        // Assert
        Assert.assertTrue(loginPage.isLogoDisplayed(),
                "Login logo should be displayed");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}