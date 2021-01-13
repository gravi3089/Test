Feature: Validate vehicle details

  Scenario: Verify car details
    Given I navigated to cartaxcheck website
    When I search extracted registration number on car tax check website home page
    Then The details on the website should match with the details in output text file