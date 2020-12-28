Feature: Set Favorite Ingredients

  Background:
    Given I started the Application
    And I am on the Set favorite Ingredients Screen

	Scenario: Press Add Button for Ingredients you like
    	When I press add Button for Ingredients you like
		Then a Textfield appears 
    
  Scenario Outline: Add Ingredient I like
    
    When I press add Button for Ingredients I like
	And I type <ingredient>
	And  I press the Save Button
	Then the <ingredient> appears on the Screen under Ingredient I like and is saved to Storage
     Examples: 
		| ingredient| 
		| Potato	|
		| Fish	|
	
  Scenario Outline: Add Ingredient I dont like
    
    When I press add Button for Ingredients I dont like
	And I type <ingredient>
	And  I press the Save Button
	Then the <ingredient> appears on the Screen under Ingredient I dont like and is saved to Storage
    Examples: 
		| ingredient| 
		| Potato	|
		| Fish	|
	
  Scenario Outline: Add Ingredient I dont like which is already added
    
    When I press add Button for Ingredients I dont like
	And I type <ingredientnew >
	And the Ingredients in Storage for Ingredient I dont like are <ingredientold>
	And  I press the Save Button
	Then a Warning appears that Ingredient is already added
    Examples: 
		| ingredientnew| ingredientold|
		| Potato	|	Potato|
		| Fish	|	Fish|
	
	
  Scenario Outline: Add Ingredient I dont like which is already added in Ingredient I like
    
    When I press add Button for Ingredients I dont like
	And I type <ingredientadddontLike >
	And the Ingredients in Storage for Ingredient I like are <ingredientstoragelike>
	And  I press the Save Button
	Then a Warning appears that Ingredient is Ingredient I Like 
     Examples: 
		| ingredientadddontLike| ingredientstoragelike|
		| Potato	|	Potato|
		| Fish	|	Fish| 

  Scenario Outline: Press cancel Button at add Ingredient
    
    When I press add Button for Ingredients I like
	And the Ingredient in Storage for Ingredient I like are <ingredientold>
	And  I press the Cancel Button
	Then I am on Set favorite Ingredients Screen
	And the Ingredient I like are <ingredientold>
     Examples: 
		| ingredientold|
		|	Potato|
		|	Fish| 


   
	
	
