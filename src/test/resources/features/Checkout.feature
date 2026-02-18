@checkout
Feature: Checkout Process

  Background:
    Given user is on the SauceDemo login page
    When user logs in with valid credentials
    And user adds "Sauce Labs Backpack" to cart
    And user clicks on shopping cart
    And user is on cart page

  @smoke @e2e
  Scenario: Complete checkout with valid information
    When user clicks on checkout button
    And user enters checkout information
      | firstName | lastName | postalCode |
      | John      | Doe      | 12345      |
    And user clicks continue button
    Then user should be on checkout overview page
    When user clicks finish button
    Then checkout should be complete
    And completion message should be displayed

  @regression
  Scenario: Checkout with missing first name
    When user clicks on checkout button
    And user enters checkout information
      | firstName | lastName | postalCode |
      |           | Doe      | 12345      |
    And user clicks continue button
    Then error message should be displayed
    And error message should contain "First Name is required"

  @regression
  Scenario: Checkout with missing last name
    When user clicks on checkout button
    And user enters checkout information
      | firstName | lastName | postalCode |
      | John      |          | 12345      |
    And user clicks continue button
    Then error message should be displayed
    And error message should contain "Last Name is required"

  @regression
  Scenario: Checkout with missing postal code
    When user clicks on checkout button
    And user enters checkout information
      | firstName | lastName | postalCode |
      | John      | Doe      |            |
    And user clicks continue button
    Then error message should be displayed
    And error message should contain "Postal Code is required"

  @regression
  Scenario: Cancel checkout and return to cart
    When user clicks on checkout button
    And user clicks cancel button
    Then user should be on cart page

  @smoke
  Scenario: Verify checkout overview displays correct information
    When user clicks on checkout button
    And user enters checkout information
      | firstName | lastName | postalCode |
      | John      | Doe      | 12345      |
    And user clicks continue button
    Then user should be on checkout overview page
    And payment information should be displayed
    And shipping information should be displayed
    And price total should be displayed