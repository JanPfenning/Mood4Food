Feature: Checking and managing waterbalance
Background:
	Given I started the Apllication
    And I am on the add recipe screen
  
Scenario Outline: Adding water to waterbalance 
  Given the waterbalance is $<balance>
   When I press on the plus button
   And I want to add $<wateradd>
   And I press the add button
   Then the new waterbalance is $<newbalance>
  Examples: 
    | balance | wateradd | newbalance | 
    | 1.0     | 0.5      | 1.5       |  
    | 1.5     | 1.0	     | 2.5       |
Scenario Outline: Canceling action
	Given the waterbalance is $<balance>
   When I press on the plus button
   And I want to add $<wateradd>
   And I press the cancel button
   Then the new waterbalance is$<balance>
  Examples: 
    | balance | wateradd |  
    | 1.0     | 0.5      | 
    | 2.0     | 1.0	 |
Scenario Outline: Notification when enough water has been drunk 
	Given the waterbalance is $<balance> and waterlimit is $<limit>
   When I press on the plus button
   And I want to add $<wateradd>
   And I press the add button
   Then I get a Notification for reaching water limit
  Examples: 
    | balance | wateradd | limit| 
    | 2.5     | 0.5      |3.0 |
    | 2.0     | 1.5	 |3.0|
Scenario: Press plus button and show add screen
	Given: I am on the waterbalance screen
	When I press plus button
	Then I shoulf see the add screen

	
	