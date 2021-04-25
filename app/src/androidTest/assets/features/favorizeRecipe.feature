Feature: Favorize Recipe and find it

Background:
	Given I started the Apllication
    And I am on the Recipe screen
  
Scenario Outline: Favorize Recipe
  When I select a Recipe
  And I press the no-favorite button
   Then the new favorite status is "true" 

Scenario Outline: Unfavorize Recipe
	When I select a Recipe
  And I press the favorite button
   Then the new favorite status is "false" 

Scenario Outline: Find Favorite Recipes
	Given at least one Recipe is Favorized
  When I select a Favorite Recipe
   Then Recipe appears
	
	