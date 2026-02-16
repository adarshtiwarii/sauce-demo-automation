@products
Feature: Products Page Functionality
  As a logged in user
  I want to browse and manage products
  So that I can add them to my cart

  Background:
    Given user is on the SauceDemo login page
    When user logs in with valid credentials
    Then user should be on products page

  @smoke
  Scenario: Verify products page displays correctly
    Then products page should display "Products" title
    And products should be displayed
    And product count should be 6

  @regression
  Scenario: Add single product to cart
    When user adds "Sauce Labs Backpack" to cart
    Then cart badge should show count "1"

  @regression
  Scenario: Add multiple products to cart
    When user adds "Sauce Labs Backpack" to cart
    And user adds "Sauce Labs Bike Light" to cart
    And user adds "Sauce Labs Bolt T-Shirt" to cart
    Then cart badge should show count "3"

  @regression
  Scenario: Remove product from cart
    When user adds "Sauce Labs Backpack" to cart
    And user removes "Sauce Labs Backpack" from cart
    Then cart badge should not be displayed

  @smoke
  Scenario: Sort products by price low to high
    When user sorts products by "Price (low to high)"
    Then products should be sorted in ascending order

  @regression
  Scenario: Sort products by price high to low
    When user sorts products by "Price (high to low)"
    Then products should be sorted in descending order

  @regression
  Scenario: Sort products by name A to Z
    When user sorts products by "Name (A to Z)"
    Then products should be sorted alphabetically

  @smoke
  Scenario: Navigate to shopping cart
    When user adds "Sauce Labs Backpack" to cart
    And user clicks on shopping cart
    Then user should be on cart page
    And cart should contain "Sauce Labs Backpack"

  @smoke
  Scenario: Logout functionality
    When user opens hamburger menu
    And user clicks on logout
    Then user should be on login page