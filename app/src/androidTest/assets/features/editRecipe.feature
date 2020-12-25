Feature: Edit Recipe

  Background:
    Given I started the Application
    And I am on edit recipe screen

  Scenario Outline: Delete Ingredient
    When I press delte Button after the <Ingredient>
	Then the the new Ingredients are <newIngredient>
    Examples:
      | Ingredient     | newIngredient  | 
      | Pizzacheese  |  |
	  
  Scenario : Delete Material
    When I press delte Button after the <Material> 
	Then the the new Ingredients are <newMaterial>
    Examples:
      | material     | newMaterial  | 
      | Ofen |  |
	  
  Scenario: Adding Photo
    When I press chose photo
    Then open galery
	
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
	  
  Scenario Outline: Edit Correct Recipe
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
	  
  Scenario: Save action
    When I press save button
    Then recipe screen is closed
	And recipe is saved to Storage
	  

	


