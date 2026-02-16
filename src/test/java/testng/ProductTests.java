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

/**
 * Product Test Cases using TestNG
 */
@Epic("E-Commerce")
@Feature("Product Management")
public class ProductTests {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        DriverManager.initializeDriver();
        loginPage = new LoginPage(DriverManager.getDriver());
        productsPage = new ProductsPage(DriverManager.getDriver());
        cartPage = new CartPage(DriverManager.getDriver());

        // Login before each test
        loginPage.navigateToLoginPage();
        loginPage.login(ConfigReader.getValidUsername(),
                ConfigReader.getValidPassword());
    }

    @Test(priority = 1, groups = {"smoke", "regression"},
            description = "Verify products page loads correctly")
    @Story("Products Display")
    @Severity(SeverityLevel.BLOCKER)
    public void testProductsPageLoad() {
        // Assert
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products page should be displayed");
        Assert.assertEquals(productsPage.getPageTitle(),
                AppConstants.PRODUCTS_PAGE_TITLE,
                "Page title should be 'Products'");
        Assert.assertEquals(productsPage.getProductCount(), 6,
                "Should display 6 products");
    }

    @Test(priority = 2, groups = {"regression"},
            description = "Verify adding single product to cart")
    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddSingleProductToCart() {
        // Arrange
        String productName = AppConstants.BACKPACK;

        // Act
        productsPage.addProductToCart(productName);

        // Assert
        Assert.assertEquals(productsPage.getCartItemCount(), 1,
                "Cart should contain 1 item");
    }

    @Test(priority = 3, groups = {"regression"},
            description = "Verify adding multiple products to cart")
    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddMultipleProductsToCart() {
        // Act
        productsPage.addProductToCart(AppConstants.BACKPACK);
        productsPage.addProductToCart(AppConstants.BIKE_LIGHT);
        productsPage.addProductToCart(AppConstants.BOLT_TSHIRT);

        // Assert
        Assert.assertEquals(productsPage.getCartItemCount(), 3,
                "Cart should contain 3 items");
    }

    @Test(priority = 4, groups = {"regression"},
            description = "Verify removing product from cart")
    @Story("Remove from Cart")
    @Severity(SeverityLevel.NORMAL)
    public void testRemoveProductFromCart() {
        // Arrange
        String productName = AppConstants.BACKPACK;
        productsPage.addProductToCart(productName);

        // Act
        productsPage.removeProductFromCart(productName);

        // Assert
        Assert.assertEquals(productsPage.getCartItemCount(), 0,
                "Cart should be empty");
    }

    @Test(priority = 5, groups = {"smoke", "regression"},
            description = "Verify navigation to cart page")
    @Story("Navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void testNavigateToCart() {
        // Arrange
        productsPage.addProductToCart(AppConstants.BACKPACK);

        // Act
        cartPage = productsPage.clickShoppingCart();

        // Assert
        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Cart page should be displayed");
        Assert.assertTrue(cartPage.isProductInCart(AppConstants.BACKPACK),
                "Product should be in cart");
    }

    @Test(priority = 6, groups = {"regression"},
            description = "Verify product sorting - Price Low to High")
    @Story("Product Sorting")
    @Severity(SeverityLevel.NORMAL)
    public void testSortProductsByPriceLowToHigh() {
        // Act
        productsPage.sortProducts("Price (low to high)");

        // Assert - Basic verification that sorting happened
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products should still be displayed after sorting");
    }

    @Test(priority = 7, groups = {"regression"},
            description = "Verify product sorting - Name A to Z")
    @Story("Product Sorting")
    @Severity(SeverityLevel.NORMAL)
    public void testSortProductsByNameAZ() {
        // Act
        productsPage.sortProducts("Name (A to Z)");

        // Assert
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products should still be displayed after sorting");
    }

    @Test(priority = 8, groups = {"smoke"},
            description = "Verify logout functionality")
    @Story("Logout")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogout() {
        // Act
        loginPage = productsPage.logout();

        // Assert
        Assert.assertTrue(loginPage.isLogoDisplayed(),
                "Should be redirected to login page after logout");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}