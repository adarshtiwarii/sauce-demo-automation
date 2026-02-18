package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import constants.AppConstants;

import java.time.Duration;
import java.util.List;

/**
 * Products Page Object
 * Contains elements and methods for Products/Inventory page
 */
public class ProductsPage extends BasePage {

    // Locators
    private final By pageTitle = By.className("title");
    private final By hamburgerMenu = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By shoppingCartBadge = By.className("shopping_cart_badge");
    private final By shoppingCartLink = By.className("shopping_cart_link");
    private final By productItems = By.className("inventory_item");
    private final By productSortDropdown = By.className("product_sort_container");
    private final By inventoryItemName = By.className("inventory_item_name");
    private final By inventoryItemPrice = By.className("inventory_item_price");
    private final By addToCartButtons = By.cssSelector("button[id^='add-to-cart']");
    private final By removeButtons = By.cssSelector("button[id^='remove']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Get page title text
     */
    public String getPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Verify products page is displayed
     */
    public boolean isProductsPageDisplayed() {
        return getCurrentUrl().contains(AppConstants.INVENTORY_URL)
                && getPageTitle().equals(AppConstants.PRODUCTS_PAGE_TITLE);
    }

    /**
     * Get number of products displayed
     */
    public int getProductCount() {
        List<WebElement> products = driver.findElements(productItems);
        int count = products.size();
        log.info("Number of products: " + count);
        return count;
    }

    /**
     * Add product to cart by name
     */
    public ProductsPage addProductToCart(String productName) {
        String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        By addButton = By.id(buttonId);
        click(addButton);
        log.info("Added product to cart: " + productName);
        return this;
    }

    /**
     * Remove product from cart by name
     */
    public ProductsPage removeProductFromCart(String productName) {
        String buttonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        By removeButton = By.id(buttonId);
        click(removeButton);
        log.info("Removed product from cart: " + productName);
        return this;
    }

    /**
     * Get cart item count
     */
    public int getCartItemCount() {
        if (isDisplayed(shoppingCartBadge)) {
            String count = getText(shoppingCartBadge);
            return Integer.parseInt(count);
        }
        return 0;
    }

    /**
     * Click on shopping cart
     */
    public CartPage clickShoppingCart() {
        click(shoppingCartLink);
        log.info("Clicked on shopping cart");
        return new CartPage(driver);
    }

    /**
     * Sort products
     */
    public ProductsPage sortProducts(String sortOption) {
        selectByVisibleText(productSortDropdown, sortOption);
        log.info("Products sorted by: " + sortOption);
        return this;
    }

    /**
     * Open hamburger menu (FIXED - with proper wait and fallback)
     */
    public ProductsPage openMenu() {
        try {
            // Wait a moment for any animations to complete
            Thread.sleep(500);

            // Try regular click first
            try {
                WebElement menuButton = waitForElementClickable(hamburgerMenu);
                menuButton.click();
                log.info("Hamburger menu clicked (regular)");
            } catch (Exception e) {
                // Fallback to JavaScript click
                log.info("Regular click failed, using JavaScript click");
                WebElement menuButton = driver.findElement(hamburgerMenu);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", menuButton);
                log.info("Hamburger menu clicked (JavaScript)");
            }

            // Wait for menu to be visible
            Thread.sleep(800);

        } catch (InterruptedException e) {
            log.error("Thread interrupted while opening menu: " + e.getMessage());
        }

        log.info("Hamburger menu opened");
        return this;
    }

    /**
     * Logout (FIXED - with proper menu handling)
     */
    public LoginPage logout() {
        // Open menu first
        openMenu();

        try {
            // Wait for logout link to be clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement logoutElement = wait.until(
                    ExpectedConditions.elementToBeClickable(logoutLink)
            );

            // Try regular click
            try {
                logoutElement.click();
                log.info("Logout clicked (regular)");
            } catch (Exception e) {
                // Fallback to JavaScript click
                log.info("Regular logout click failed, using JavaScript");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", logoutElement);
                log.info("Logout clicked (JavaScript)");
            }

        } catch (Exception e) {
            log.error("Failed to click logout: " + e.getMessage());
            // One more attempt with JavaScript
            WebElement logoutElement = driver.findElement(logoutLink);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", logoutElement);
            log.info("Logout clicked (JavaScript fallback)");
        }

        log.info("User logged out");
        return new LoginPage(driver);
    }

    /**
     * Get first product name
     */
    public String getFirstProductName() {
        List<WebElement> products = driver.findElements(inventoryItemName);
        if (!products.isEmpty()) {
            return products.get(0).getText();
        }
        return null;
    }

    /**
     * Get first product price
     */
    public String getFirstProductPrice() {
        List<WebElement> prices = driver.findElements(inventoryItemPrice);
        if (!prices.isEmpty()) {
            return prices.get(0).getText();
        }
        return null;
    }
}