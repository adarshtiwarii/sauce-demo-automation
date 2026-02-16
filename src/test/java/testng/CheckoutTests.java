package testng;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.ConfigReader;
import utils.DriverManager;
import constants.AppConstants;
import io.qameta.allure.*;

/**
 * Checkout Test Cases using TestNG
 */
@Epic("E-Commerce")
@Feature("Checkout Process")
public class CheckoutTests {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void setUp() {
        DriverManager.initializeDriver();
        loginPage = new LoginPage(DriverManager.getDriver());
        productsPage = new ProductsPage(DriverManager.getDriver());
        cartPage = new CartPage(DriverManager.getDriver());
        checkoutPage = new CheckoutPage(DriverManager.getDriver());

        // Login and add product to cart
        loginPage.navigateToLoginPage();
        loginPage.login(ConfigReader.getValidUsername(),
                ConfigReader.getValidPassword());
        productsPage.addProductToCart(AppConstants.BACKPACK);
        cartPage = productsPage.clickShoppingCart();
    }

    @Test(priority = 1, groups = {"smoke", "e2e"},
            description = "Verify complete checkout process")
    @Story("Complete Checkout")
    @Severity(SeverityLevel.BLOCKER)
    public void testCompleteCheckout() {
        // Act
        checkoutPage = cartPage.checkout();
        checkoutPage.completeCheckout("John", "Doe", "12345");

        // Assert
        Assert.assertTrue(checkoutPage.isCheckoutComplete(),
                "Checkout should be complete");
        Assert.assertTrue(checkoutPage.getCompleteMessage()
                        .contains("Thank you for your order"),
                "Completion message should be displayed");
    }

    @Test(priority = 2, groups = {"regression"},
            description = "Verify checkout with missing first name")
    @Story("Checkout Validation")
    @Severity(SeverityLevel.NORMAL)
    public void testCheckoutMissingFirstName() {
        // Act
        checkoutPage = cartPage.checkout();
        checkoutPage.enterCheckoutInformation("", "Doe", "12345");
        checkoutPage.clickContinue();

        // Assert
        Assert.assertTrue(checkoutPage.getErrorMessage()
                        .contains("First Name is required"),
                "Error message for missing first name should be displayed");
    }

    @Test(priority = 3, groups = {"regression"},
            description = "Verify checkout with missing last name")
    @Story("Checkout Validation")
    @Severity(SeverityLevel.NORMAL)
    public void testCheckoutMissingLastName() {
        // Act
        checkoutPage = cartPage.checkout();
        checkoutPage.enterCheckoutInformation("John", "", "12345");
        checkoutPage.clickContinue();

        // Assert
        Assert.assertTrue(checkoutPage.getErrorMessage()
                        .contains("Last Name is required"),
                "Error message for missing last name should be displayed");
    }

    @Test(priority = 4, groups = {"regression"},
            description = "Verify checkout with missing postal code")
    @Story("Checkout Validation")
    @Severity(SeverityLevel.NORMAL)
    public void testCheckoutMissingPostalCode() {
        // Act
        checkoutPage = cartPage.checkout();
        checkoutPage.enterCheckoutInformation("John", "Doe", "");
        checkoutPage.clickContinue();

        // Assert
        Assert.assertTrue(checkoutPage.getErrorMessage()
                        .contains("Postal Code is required"),
                "Error message for missing postal code should be displayed");
    }

    @Test(priority = 5, groups = {"regression"},
            description = "Verify cancel checkout returns to cart")
    @Story("Cancel Checkout")
    @Severity(SeverityLevel.NORMAL)
    public void testCancelCheckout() {
        // Act
        checkoutPage = cartPage.checkout();
        cartPage = checkoutPage.clickCancel();

        // Assert
        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Should return to cart page after cancelling checkout");
    }

    @Test(priority = 6, groups = {"smoke"},
            description = "Verify checkout overview displays information")
    @Story("Checkout Overview")
    @Severity(SeverityLevel.CRITICAL)
    public void testCheckoutOverview() {
        // Act
        checkoutPage = cartPage.checkout();
        checkoutPage.enterCheckoutInformation("John", "Doe", "12345");
        checkoutPage.clickContinue();

        // Assert
        Assert.assertTrue(checkoutPage.isCheckoutStepTwoDisplayed(),
                "Checkout overview page should be displayed");
        Assert.assertNotNull(checkoutPage.getTotal(),
                "Total price should be displayed");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}