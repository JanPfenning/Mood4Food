Feature: Analyse Waterbalance Statistics

  Background:
    Given I started the Apllication
    And I am on the water balance screen
    And I switch to the Statistics screen

  Scenario Outline: Open Waterbalance Statistics
    Given the currentweek is $<week> and
    And  the day1 waterbalance is $<day1> and day2 waterbalance is $<day2>
    And the waterlevel is $<waterlevel>
    Then the Statistics are displayed
    Examples:
      | week | day1 | day2 | waterlevel |
      | 1.0  | 0.5  | 1.5  | 5.0        |
      | 5.0  | 1.0  | 2.5  | 4.0        |


  Scenario Outline: Open Waterbalance Statistics
    Given the currentweek is $<week>
    Then I press on the week forward buttom
    Then the newweek is $<newweek>
    Examples:
      | week | newweek |
      | 1    | 2       |
      | 5    | 6       |

  Scenario Outline: Open Waterbalance Statistics
    Given the currentweek is $<week>
    Then I press on the week backward buttom
    Then the newweek is $<newweek>
    Examples:
      | week | newweek |
      | 23   | 22      |
      | 12   | 11      |

	
	