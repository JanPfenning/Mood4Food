Feature: Adding Recipe

  Background:
    Given I started the Application
    And I am on add recipe screen

  Scenario Outline: Adding Correct Recipe
    When I type name <name>
    And I type ingredient <ingredient>
    And I type material <material>
    And I press choose photo
    And I press save button
    Then App should save Recipe
    And recipe screen is closed
    Examples:
      | name     | ingredient  | material |
      | Recipe 1 | Pizzacheese | Ofen     |

  Scenario: Canceling action
    When I type ingredient <ingredient>
    And I press cancel button
    Then recipe screen is closed

  Scenario Outline: Adding multiple ingredients
    When I type ingredient <ingredient>
    Then new ingredient-field should appear
    Examples:
      | ingredient  |
      | Pizzacheese |

  Scenario Outline: Adding multiple materials
    When I type material <material>
    Then new material-field should appear
    Examples:
      | material  |
      | Ofen |

  Scenario: Adding Photo
    When I press chose photo
    Then open galery

  Scenario Outline: Adding False Recipe
    When I type ingredient <ingredient>
    And I press the save button
    Then field name should be colored red
    Examples:
      | ingredient  |
      | Pizzacheese |


