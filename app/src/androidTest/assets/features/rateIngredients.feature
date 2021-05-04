Feature: Rate Ingredients

  Background:
    Given I started the Application
    And I RateIngredients Screen

  Scenario Outline: Adding Good Ingredient
    When I type ingredient you like <ingredient>
    And RateIngredients screen is closed
    Then App should save Ingredients
    Examples:
      | ingredient  |
      | Pizzacheese |
      | ketchup     |
      | Oil         |

  Scenario Outline: Adding Bad Ingredient
    When I type ingredient you dont like <ingredient>
    And RateIngredients screen is closed
    Then App should save Ingredients
    Examples:
      | ingredient  |
      | Pizzacheese |
      | ketchup     |
      | Oil         |

  Scenario Outline: Canceling action
    When I type ingredient <ingredient>
    And I press delete button from <ingredient>
    Then recipe screen is closed
    And Nothing should be changed
    Examples:
      | ingredient  |
      | Pizzacheese |
      | ketchup     |
      | Oil         |

  Scenario Outline: Delete Bad Ingredient
    When I press delete button from  <ingredient>
    Then App should save Ingredients
    And RateIngredients screen is closed
    Examples:
      | ingredient  |
      | Pizzacheese |
      | ketchup     |
      | Oil         |

