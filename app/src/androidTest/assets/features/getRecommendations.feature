Feature: Get Recommendations

  Background:
    Given I started the Application
    And I am on Homescreen

  Scenario Outline: View Recommendation
    When I type press on recipe <recipe>
    Then Homescreen is closed
	And recipe screen for <recipe> is open
	Examples:
      | recipe |
      | Test   |

  Scenario Outline: Refresh Recommendation
    When I type press on refresh
    Then new Recipes change