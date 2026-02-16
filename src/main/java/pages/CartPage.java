package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import constants.AppConstants;

import java.util.List;

/**
 * Cart Page Object
 * Contains elements and methods for Shopping Cart page
 */
public class CartPage extends BasePage {

    // Locators
    private final By pageTitle = By.className("title");
    private final By cartItems = By.className("cart_item");
    private final By cartItemNames = By.className("inventory_item_name");
    private final By cartItemPrices = By.className("inventory_item_price");
    private final By removeButtons = By.cssSelector("button[id^='remove']");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");
    private final By cartQuantity = By.className("cart_quantity");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Verify cart page is displayed
     */
    public boolean isCartPageDisplayed() {
        return getCurrentUrl().contains(AppConstants.CART_URL)
                && getPageTitle().equals(AppConstants.CART_PAGE_TITLE);
    }

    /**
     * Get number of items in cart
     */
    public int getCartItemCount() {
        List<WebElement> items = driver.findElements(cartItems);
        int count = items.size();
        log.info("Number of items in cart: " + count);
        return count;
    }

    /**
     * Get cart item names
     */
    public List<String> getCartItemNames() {
        List<WebElement> items = driver.findElements(cartItemNames);
        return items.stream()
                .map(WebElement::getText)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Remove item from cart by product name
     */
    public CartPage removeItem(String productName) {
        String buttonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        By removeButton = By.id(buttonId);
        click(removeButton);
        log.info("Removed item from cart: " + productName);
        return this;
    }

    /**
     * Click Continue Shopping
     */
    public ProductsPage continueShopping() {
        click(continueShoppingButton);
        log.info("Clicked Continue Shopping");
        return new ProductsPage(driver);
    }

    /**
     * Click Checkout
     */
    public CheckoutPage checkout() {
        click(checkoutButton);
        log.info("Clicked Checkout");
        return new CheckoutPage(driver);
    }

    /**
     * Verify product is in cart
     */
    public boolean isProductInCart(String productName) {
        List<String> itemNames = getCartItemNames();
        boolean found = itemNames.contains(productName);
        log.info("Product '" + productName + "' in cart: " + found);
        return found;
    }

    /**
     * Get total price of all items
     */
    public double getTotalPrice() {
        List<WebElement> prices = driver.findElements(cartItemPrices);
        double total = 0.0;
        for (WebElement priceElement : prices) {
            String priceText = priceElement.getText().replace("$", "");
            total += Double.parseDouble(priceText);
        }
        log.info("Total cart price: $" + total);
        return total;
    }
}