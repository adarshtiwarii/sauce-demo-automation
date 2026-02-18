package testng;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.ConfigReader;
import utils.DriverManager;
import constants.AppConstants;
import io.qameta.allure.*;

@Epic("E-Commerce")
@Feature("Checkout Process")
public class CheckoutTests {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeClass
    public void setupClass() {
        ConfigReader.getProperty("browser");
    }

    @BeforeMethod
    public void setUp() {
        // Initialize driver
        DriverManager.initializeDriver();

        // Initialize page objects
        loginPage = new LoginPage(DriverManager.getDriver());
        productsPage = new ProductsPage(DriverManager.getDriver());
        cartPage = new CartPage(DriverManager.getDriver());
        checkoutPage = new CheckoutPage(DriverManager.getDriver());

        // Login and add product
        loginPage.navigateToLoginPage();
        productsPage = loginPage.login(
                ConfigReader.getValidUsername(),
                ConfigReader.getValidPassword()
        );
        productsPage.addProductToCart(AppConstants.BACKPACK);
        cartPage = productsPage.clickShoppingCart();
    }

    @Test(priority = 1, groups = {"smoke", "e2e"})
    @Story("Complete Checkout")
    @Severity(SeverityLevel.BLOCKER)
    public void testCompleteCheckout() {
        checkoutPage = cartPage.checkout();
        checkoutPage.completeCheckout("John", "Doe", "12345");

        Assert.assertTrue(checkoutPage.isCheckoutComplete());
        Assert.assertTrue(checkoutPage.getCompleteMessage().contains("Thank you"));
    }

    @Test(priority = 2, groups = {"regression"})
    @Story("Checkout Validation")
    @Severity(SeverityLevel.NORMAL)
    public void testCheckoutMissingFirstName() {
        checkoutPage = cartPage.checkout();
        checkoutPage.enterCheckoutInformation("", "Doe", "12345");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"));
    }

    @Test(priority = 3, groups = {"regression"})
    @Story("Checkout Validation")
    @Severity(SeverityLevel.NORMAL)
    public void testCheckoutMissingLastName() {
        checkoutPage = cartPage.checkout();
        checkoutPage.enterCheckoutInformation("John", "", "12345");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Last Name is required"));
    }

    @Test(priority = 4, groups = {"regression"})
    @Story("Checkout Validation")
    @Severity(SeverityLevel.NORMAL)
    public void testCheckoutMissingPostalCode() {
        checkoutPage = cartPage.checkout();
        checkoutPage.enterCheckoutInformation("John", "Doe", "");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Postal Code is required"));
    }

    @Test(priority = 5, groups = {"regression"})
    @Story("Cancel Checkout")
    @Severity(SeverityLevel.NORMAL)
    public void testCancelCheckout() {
        checkoutPage = cartPage.checkout();
        cartPage = checkoutPage.clickCancel();

        Assert.assertTrue(cartPage.isCartPageDisplayed());
    }

    @Test(priority = 6, groups = {"smoke"})
    @Story("Checkout Overview")
    @Severity(SeverityLevel.CRITICAL)
    public void testCheckoutOverview() {
        checkoutPage = cartPage.checkout();
        checkoutPage.enterCheckoutInformation("John", "Doe", "12345");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isCheckoutStepTwoDisplayed());
        Assert.assertNotNull(checkoutPage.getTotal());
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}