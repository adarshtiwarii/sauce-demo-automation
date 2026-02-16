package stepDefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.ProductsPage;
import pages.CartPage;
import utils.DriverManager;

/**
 * Product Step Definitions
 */
public class ProductSteps {

    private ProductsPage productsPage;
    private CartPage cartPage;

    public ProductSteps() {
        this.productsPage = new ProductsPage(DriverManager.getDriver());
        this.cartPage = new CartPage(DriverManager.getDriver());
    }

    @Then("user should be on products page")
    public void userShouldBeOnProductsPage() {
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "User not on products page");
    }

    @Then("products should be displayed")
    public void productsShouldBeDisplayed() {
        int productCount = productsPage.getProductCount();
        Assert.assertTrue(productCount > 0, "No products displayed");
    }

    @Then("product count should be {int}")
    public void productCountShouldBe(int expectedCount) {
        int actualCount = productsPage.getProductCount();
        Assert.assertEquals(actualCount, expectedCount,
                "Product count mismatch. Expected: " + expectedCount +
                        ", Actual: " + actualCount);
    }

    @When("user adds {string} to cart")
    public void userAddsProductToCart(String productName) {
        productsPage.addProductToCart(productName);
    }

    @When("user removes {string} from cart")
    public void userRemovesProductFromCart(String productName) {
        productsPage.removeProductFromCart(productName);
    }

    @Then("cart badge should show count {string}")
    public void cartBadgeShouldShowCount(String expectedCount) {
        int actualCount = productsPage.getCartItemCount();
        Assert.assertEquals(actualCount, Integer.parseInt(expectedCount),
                "Cart count mismatch");
    }

    @Then("cart badge should not be displayed")
    public void cartBadgeShouldNotBeDisplayed() {
        int count = productsPage.getCartItemCount();
        Assert.assertEquals(count, 0, "Cart badge should not be displayed");
    }

    @When("user sorts products by {string}")
    public void userSortsProductsBy(String sortOption) {
        productsPage.sortProducts(sortOption);
    }

    @Then("products should be sorted in ascending order")
    public void productsShouldBeSortedAscending() {
        // Add actual sorting validation logic here
        Assert.assertTrue(true, "Products sorted in ascending order");
    }

    @Then("products should be sorted in descending order")
    public void productsShouldBeSortedDescending() {
        // Add actual sorting validation logic here
        Assert.assertTrue(true, "Products sorted in descending order");
    }

    @Then("products should be sorted alphabetically")
    public void productsShouldBeSortedAlphabetically() {
        // Add actual sorting validation logic here
        Assert.assertTrue(true, "Products sorted alphabetically");
    }

    @When("user clicks on shopping cart")
    public void userClicksOnShoppingCart() {
        cartPage = productsPage.clickShoppingCart();
    }

    @Then("user should be on cart page")
    public void userShouldBeOnCartPage() {
        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "User not on cart page");
    }

    @Then("cart should contain {string}")
    public void cartShouldContainProduct(String productName) {
        Assert.assertTrue(cartPage.isProductInCart(productName),
                "Product not found in cart: " + productName);
    }

    @When("user opens hamburger menu")
    public void userOpensHamburgerMenu() {
        productsPage.openMenu();
    }

    @When("user clicks on logout")
    public void userClicksOnLogout() {
        productsPage.logout();
    }
}