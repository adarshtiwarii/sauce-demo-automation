package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import constants.AppConstants;

/**
 * Checkout Page Object
 * Contains elements and methods for Checkout process
 */
public class CheckoutPage extends BasePage {

    // Step One - Your Information
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    // Step Two - Overview
    private final By pageTitle = By.className("title");
    private final By paymentInfo = By.cssSelector("[data-test='payment-info-value']");
    private final By shippingInfo = By.cssSelector("[data-test='shipping-info-value']");
    private final By subtotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By finishButton = By.id("finish");
    private final By backButton = By.id("cancel");

    // Complete
    private final By completeHeader = By.className("complete-header");
    private final By completeText = By.className("complete-text");
    private final By backHomeButton = By.id("back-to-products");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enter checkout information
     */
    public CheckoutPage enterCheckoutInformation(String firstName, String lastName, String postalCode) {
        enterText(firstNameField, firstName);
        enterText(lastNameField, lastName);
        enterText(postalCodeField, postalCode);
        log.info("Entered checkout information: " + firstName + " " + lastName + ", " + postalCode);
        return this;
    }

    /**
     * Click Continue button (Step One)
     */
    public CheckoutPage clickContinue() {
        click(continueButton);
        log.info("Clicked Continue button");
        return this;
    }

    /**
     * Click Cancel button
     */
    public CartPage clickCancel() {
        click(cancelButton);
        log.info("Clicked Cancel button");
        return new CartPage(driver);
    }

    /**
     * Get error message (Step One)
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /**
     * Verify checkout step one page
     */
    public boolean isCheckoutStepOneDisplayed() {
        return getCurrentUrl().contains(AppConstants.CHECKOUT_STEP_ONE_URL);
    }

    /**
     * Verify checkout step two (overview) page
     */
    public boolean isCheckoutStepTwoDisplayed() {
        return getCurrentUrl().contains(AppConstants.CHECKOUT_STEP_TWO_URL);
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return getText(pageTitle);
    }

    /**
     * Get subtotal
     */
    public String getSubtotal() {
        return getText(subtotal);
    }

    /**
     * Get tax
     */
    public String getTax() {
        return getText(tax);
    }

    /**
     * Get total
     */
    public String getTotal() {
        return getText(total);
    }

    /**
     * Click Finish button
     */
    public CheckoutPage clickFinish() {
        click(finishButton);
        log.info("Clicked Finish button");
        return this;
    }

    /**
     * Verify checkout complete page
     */
    public boolean isCheckoutComplete() {
        return getCurrentUrl().contains(AppConstants.CHECKOUT_COMPLETE_URL);
    }

    /**
     * Get completion message
     */
    public String getCompleteMessage() {
        return getText(completeHeader);
    }

    /**
     * Click Back Home button
     */
    public ProductsPage clickBackHome() {
        click(backHomeButton);
        log.info("Clicked Back Home button");
        return new ProductsPage(driver);
    }

    /**
     * Complete full checkout process
     */
    public CheckoutPage completeCheckout(String firstName, String lastName, String postalCode) {
        enterCheckoutInformation(firstName, lastName, postalCode);
        clickContinue();
        clickFinish();
        log.info("Checkout completed successfully");
        return this;
    }
}