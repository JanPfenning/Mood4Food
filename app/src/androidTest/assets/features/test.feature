Feature: Test
  Perform click on button

  @test
  Scenario Outline: Click the Button
    Given I am on test screen
    When I click the button
    Then I should see a text
    Examples:
      |  |