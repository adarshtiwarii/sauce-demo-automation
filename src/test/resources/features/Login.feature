@login
Feature: User Login Functionality
  As a user
  I want to be able to login to the application
  So that I can access the products page

  Background:
    Given user is on the SauceDemo login page

  @smoke @positive
  Scenario: Successful login with valid credentials
    When user enters username "standard_user"
    And user enters password "secret_sauce"
    And user clicks on login button
    Then user should be redirected to products page
    And products page should display "Products" title

  @negative
  Scenario: Login with invalid username
    When user enters username "invalid_user"
    And user enters password "secret_sauce"
    And user clicks on login button
    Then error message should be displayed
    And error message should contain "Epic sadface"

  @negative
  Scenario: Login with invalid password
    When user enters username "standard_user"
    And user enters password "invalid_password"
    And user clicks on login button
    Then error message should be displayed
    And error message should contain "do not match"

  @negative
  Scenario: Login with locked out user
    When user enters username "locked_out_user"
    And user enters password "secret_sauce"
    And user clicks on login button
    Then error message should be displayed
    And error message should contain "locked out"

  @negative
  Scenario: Login with empty username
    When user enters username ""
    And user enters password "secret_sauce"
    And user clicks on login button
    Then error message should be displayed
    And error message should contain "Username is required"

  @negative
  Scenario: Login with empty password
    When user enters username "standard_user"
    And user enters password ""
    And user clicks on login button
    Then error message should be displayed
    And error message should contain "Password is required"

  @smoke
  Scenario Outline: Login with multiple users
    When user enters username "<username>"
    And user enters password "<password>"
    And user clicks on login button
    Then login should be "<result>"

    Examples:
      | username                | password     | result  |
      | standard_user           | secret_sauce | success |
      | locked_out_user         | secret_sauce | failed  |
      | problem_user            | secret_sauce | success |
      | performance_glitch_user | secret_sauce | success |