Feature: Get and View Api Recipe

  Background:
    Given I started the Application
    And I am on select recipe screen

  Scenario Outline: Search Recipe
    When I press openSearch
    And I type search <query>
    And I press confirm
    Then App should show pommesRecipe
    And searchInputField disaapears
    Examples:
      | query  |
      | Pommes |

  Scenario: Canceling Search
    When I press openSearch
    And I type search <query>
    And I press cancel button
    Then App should not show pommesRecipe
    And searchInputField disappears

    Scenario: View Recipe
      When I press recipe
      Then App should navigate to Recipe Details
