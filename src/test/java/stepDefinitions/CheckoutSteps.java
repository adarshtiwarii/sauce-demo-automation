package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.CartPage;
import pages.CheckoutPage;
import utils.DriverManager;

import java.util.List;
import java.util.Map;

/**
 * Checkout Step Definitions
 */
public class CheckoutSteps {

    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    public CheckoutSteps() {
        this.cartPage = new CartPage(DriverManager.getDriver());
        this.checkoutPage = new CheckoutPage(DriverManager.getDriver());
    }

    @Given("user is on cart page")
    public void userIsOnCartPage() {
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "User not on cart page");
    }

    @When("user clicks on checkout button")
    public void userClicksCheckoutButton() {
        checkoutPage = cartPage.checkout();
    }

    @When("user enters checkout information")
    public void userEntersCheckoutInformation(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> checkoutInfo = data.get(0);

        String firstName = checkoutInfo.get("firstName");
        String lastName = checkoutInfo.get("lastName");
        String postalCode = checkoutInfo.get("postalCode");

        checkoutPage.enterCheckoutInformation(firstName, lastName, postalCode);
    }

    @When("user clicks continue button")
    public void userClicksContinueButton() {
        checkoutPage.clickContinue();
    }

    @Then("user should be on checkout overview page")
    public void userShouldBeOnCheckoutOverviewPage() {
        Assert.assertTrue(checkoutPage.isCheckoutStepTwoDisplayed(),
                "User not on checkout overview page");
    }

    @When("user clicks finish button")
    public void userClicksFinishButton() {
        checkoutPage.clickFinish();
    }

    @Then("checkout should be complete")
    public void checkoutShouldBeComplete() {
        Assert.assertTrue(checkoutPage.isCheckoutComplete(),
                "Checkout not complete");
    }

    @Then("completion message should be displayed")
    public void completionMessageShouldBeDisplayed() {
        String message = checkoutPage.getCompleteMessage();
        Assert.assertNotNull(message, "Completion message not displayed");
        Assert.assertFalse(message.isEmpty(), "Completion message is empty");
    }

    @Then("message should contain {string}")
    public void messageShouldContain(String expectedText) {
        String actualMessage = checkoutPage.getCompleteMessage();
        Assert.assertTrue(actualMessage.contains(expectedText),
                "Message doesn't contain expected text. Expected: " + expectedText +
                        ", Actual: " + actualMessage);
    }

    @When("user clicks cancel button")
    public void userClicksCancelButton() {
        cartPage = checkoutPage.clickCancel();
    }

    @Then("payment information should be displayed")
    public void paymentInformationShouldBeDisplayed() {
        String pageTitle = checkoutPage.getPageTitle();
        Assert.assertTrue(pageTitle.contains("Overview"),
                "Payment information section not displayed");
    }

    @Then("shipping information should be displayed")
    public void shippingInformationShouldBeDisplayed() {
        Assert.assertTrue(checkoutPage.isCheckoutStepTwoDisplayed(),
                "Shipping information not displayed");
    }

    @Then("price total should be displayed")
    public void priceTotalShouldBeDisplayed() {
        String total = checkoutPage.getTotal();
        Assert.assertNotNull(total, "Price total not displayed");
        Assert.assertTrue(total.contains("Total"), "Total price not found");
    }
}