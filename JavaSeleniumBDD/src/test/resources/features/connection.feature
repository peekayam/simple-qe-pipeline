Feature: Connect to web Feature
  Verify if user is able to open chrome and navigate to a web page

Scenario: open http://52.150.14.46:3000/ to check h1
    Given user loads browser
    When user navigates to http://52.150.14.46:3000/
    Then user sees element h1
    Then user takes a screenshot

Scenario: open http://52.150.14.46:3000/ to check 'The Homelander' in hero list
    Given user loads browser
    When user navigates to http://52.150.14.46:3000/ 
    Then user sees 'The Homelander' in hero list
    Then user takes a screenshot of 'The Homelander' in hero list
