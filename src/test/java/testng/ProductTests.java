package testng;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProductsPage;
import pages.CartPage;
import utils.ConfigReader;
import utils.DriverManager;
import constants.AppConstants;
import io.qameta.allure.*;

@Epic("E-Commerce")
@Feature("Product Management")
public class ProductTests {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;

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

        // Login
        loginPage.navigateToLoginPage();
        productsPage = loginPage.login(
                ConfigReader.getValidUsername(),
                ConfigReader.getValidPassword()
        );
    }

    @Test(priority = 1, groups = {"smoke", "regression"})
    @Story("Products Display")
    @Severity(SeverityLevel.BLOCKER)
    public void testProductsPageLoad() {
        Assert.assertTrue(productsPage.isProductsPageDisplayed());
        Assert.assertEquals(productsPage.getPageTitle(), AppConstants.PRODUCTS_PAGE_TITLE);
        Assert.assertEquals(productsPage.getProductCount(), 6);
    }

    @Test(priority = 2, groups = {"regression"})
    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddSingleProductToCart() {
        String productName = AppConstants.BACKPACK;
        productsPage.addProductToCart(productName);
        Assert.assertEquals(productsPage.getCartItemCount(), 1);
    }

    @Test(priority = 3, groups = {"regression"})
    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddMultipleProductsToCart() {
        productsPage.addProductToCart(AppConstants.BACKPACK);
        productsPage.addProductToCart(AppConstants.BIKE_LIGHT);
        productsPage.addProductToCart(AppConstants.BOLT_TSHIRT);
        Assert.assertEquals(productsPage.getCartItemCount(), 3);
    }

    @Test(priority = 4, groups = {"regression"})
    @Story("Remove from Cart")
    @Severity(SeverityLevel.NORMAL)
    public void testRemoveProductFromCart() {
        String productName = AppConstants.BACKPACK;
        productsPage.addProductToCart(productName);
        productsPage.removeProductFromCart(productName);
        Assert.assertEquals(productsPage.getCartItemCount(), 0);
    }

    @Test(priority = 5, groups = {"smoke", "regression"})
    @Story("Navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void testNavigateToCart() {
        productsPage.addProductToCart(AppConstants.BACKPACK);
        cartPage = productsPage.clickShoppingCart();
        Assert.assertTrue(cartPage.isCartPageDisplayed());
        Assert.assertTrue(cartPage.isProductInCart(AppConstants.BACKPACK));
    }

    @Test(priority = 6, groups = {"regression"})
    @Story("Product Sorting")
    @Severity(SeverityLevel.NORMAL)
    public void testSortProductsByPriceLowToHigh() {
        productsPage.sortProducts("Price (low to high)");
        Assert.assertTrue(productsPage.isProductsPageDisplayed());
    }

    @Test(priority = 7, groups = {"regression"})
    @Story("Product Sorting")
    @Severity(SeverityLevel.NORMAL)
    public void testSortProductsByNameAZ() {
        productsPage.sortProducts("Name (A to Z)");
        Assert.assertTrue(productsPage.isProductsPageDisplayed());
    }

    @Test(priority = 8, groups = {"smoke"})
    @Story("Logout")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogout() {
        // Wait a moment for menu to be ready
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        loginPage = productsPage.logout();
        Assert.assertTrue(loginPage.isLogoDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}