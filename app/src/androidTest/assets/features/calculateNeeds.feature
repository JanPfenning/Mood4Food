Feature: Calculate Needs

  Background:
    Given I started the Application
    And I am on Calculate your Needs screen

  Scenario Outline: Calculate Needs 
    When I type age <age>
    And I choose gender <gender>
    And I type body size <body size>
    And I type current body weight <body weight>
    And I press get result button 
    Then App should show Calculation Result Screen
    And water is <water>
    And calories is <calories>
    And carbohydrates is <carbohydrates>
    And protein is <protein>
    And fat is <fat>
    Examples:
      | age    | gender  | body size | body weight | water | calories | carbohydrates | protein| fat
      | 20 		|	 Male| 180   	 | 70 			| 1.5	 | 1723 	| 231 		| 63 		| 55
      | 26		| Female |160 		|50 			| 1.4   |  1102		| 147		| 40 		| 35

  Scenario: Calucation Needs Save Result
    When Calulate Needs is done 
    And I press Save Result As Goal
    Then Needs are saved in Storage
	
	
#Add more later with Aim Body Weight And Physical Activity