package stepDefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ConfigReader;
import utils.DriverManager;

public class LoginSteps {

    private LoginPage loginPage;
    private ProductsPage productsPage;

    public LoginSteps() {
        this.loginPage = new LoginPage(DriverManager.getDriver());
        this.productsPage = new ProductsPage(DriverManager.getDriver());
    }

    @Given("user is on the SauceDemo login page")
    public void userIsOnLoginPage() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(loginPage.isLogoDisplayed(), "Login page not loaded");
    }

    @When("user enters username {string}")
    public void userEntersUsername(String username) {
        // Handle empty string case
        if (username != null && !username.isEmpty()) {
            loginPage.enterUsername(username);
        }
    }

    @When("user enters password {string}")
    public void userEntersPassword(String password) {
        // Handle empty string case
        if (password != null && !password.isEmpty()) {
            loginPage.enterPassword(password);
        }
    }

    @When("user clicks on login button")
    public void userClicksLoginButton() {
        loginPage.clickLoginButton();
    }

    @When("user logs in with valid credentials")
    public void userLogsInWithValidCredentials() {
        String username = ConfigReader.getValidUsername();
        String password = ConfigReader.getValidPassword();
        productsPage = loginPage.login(username, password);
    }

    @Then("user should be redirected to products page")
    public void userShouldBeRedirectedToProductsPage() {
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "User not redirected to products page");
    }

    @Then("products page should display {string} title")
    public void productsPageShouldDisplayTitle(String expectedTitle) {
        String actualTitle = productsPage.getPageTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Page title mismatch");
    }

    @Then("error message should be displayed")
    public void errorMessageShouldBeDisplayed() {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message not displayed");
    }

    @Then("error message should contain {string}")
    public void errorMessageShouldContain(String expectedText) {
        String actualMessage = loginPage.getErrorMessage();
        Assert.assertTrue(actualMessage.contains(expectedText),
                "Error message doesn't contain expected text");
    }

    @Then("login should be {string}")
    public void loginShouldBe(String result) {
        if ("success".equalsIgnoreCase(result)) {
            Assert.assertTrue(productsPage.isProductsPageDisplayed());
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        }
    }

    @Then("user should be on login page")
    public void userShouldBeOnLoginPage() {
        Assert.assertTrue(loginPage.isLogoDisplayed(), "User not on login page");
    }
}