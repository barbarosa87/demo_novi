Feature: Novi feature

  @validate
  Scenario: task1
    Given User opens live-program page
    And User filter with "Ποδόσφαιρο" filter
    And User closes the live-program browser

  @validate
  Scenario: task2345
    Given User opens live-program page
    And User opens stoixima-live page
    And User waits 30 minutes and checking if events are delayed or dropped
    Then User closes stoixima-live the browser
    Then User closes the live-program browser