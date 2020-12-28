Feature: Calculate Needs

  Background:
    Given I started the Application
    And I Edit Goals Screen

  Scenario Outline: Edit Goals
    
    When I type current body weight <body weight>
    And I type water  <water>
    And I type calories <calories>
    And I type carbohydrates <carbohydrates>
    And I type protein is <protein>
    And I type fat is <fat>
	And I Close the Edit Goals Screen 
	Then The Changes are saved in Storage
    Examples:
   | body weight | water | calories | carbohydrates | protein| fat
   | 70 			| 1.5	 | 1723 	| 231 		| 63 		| 55
   |50 			| 1.4   |  1102		| 147		| 40 		| 35

  Scenario Outline: Edit Goals with no Changes
    When I the current Settings are <body weight> and <water> and <calories> and <carbohydrates> and <protein> and <fat>
	And I Close the Screen
	Then the Seetings are <body weight> and <water> and <calories> and <carbohydrates> and <protein> and <fat>
     | body weight | water | calories | carbohydrates | protein| fat
	| 74			| 3.0 | 1934 	| 234		| 23 		| 45
   
	
	
#Add more later with Aim Body Weight and Weight Change  and Physical Activity
