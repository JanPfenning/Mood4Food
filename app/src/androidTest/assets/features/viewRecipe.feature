Feature: View Recipe

  Background:
    Given I started the Application
    And I am on add recipes overview screen

  Scenario Outline: View recommended recipes
    When I type press on recipe <recipe>
    Then recipes overview screen is closed
	And recipe screen for <recipe> is open
	Examples:
      | recipe |
      | Test |

	Scenario Outline: View favorite recipes
    When I type press on recipe <recipe>
    Then recipes overview screen is closed
	And recipe screen for <recipe> is open
	Examples:
      | recipe |
      | Test |
	  
Scenario Outline: View long ago recipes
    When I type press on recipe <recipe>
    Then recipes overview screen is closed
	And recipe screen for <recipe> is open
	Examples:
      | recipe |
      | Test |
	  
Scenario Outline: View own recipes
    When I type press on recipe <recipe>
    Then recipes overview screen is closed
	And recipe screen for <recipe> is open
	Examples:
      | recipe |
      | Test |
  
