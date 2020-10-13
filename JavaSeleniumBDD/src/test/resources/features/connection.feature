Feature: Connect to web Feature
  Verify if user is able to open chrome and navigate to a web page

Scenario: open http://13.92.244.119:3000/ to check h1
    Given user loads browser
    When user navigates to http://13.92.244.119:3000/
    Then user sees element h1
    Then user takes a screenshot

Scenario: open http://13.92.244.119:3000/ to check 'The Homelander' in hero list
    Given user loads browser
    When user navigates to http://13.92.244.119:3000/ 
    Then user sees 'The Homelander' in hero list
    Then user takes a screenshot of 'The Homelander' in hero list
    
Scenario: open http://13.92.244.119:3000/ to get 'The Homelander' value in Textbox
    Given user loads browser
    When user navigates to http://13.92.244.119:3000/ 
    Then user clicks 'The Homelander' in hero list and sees the value in Textbox
    Then user takes a screenshot of Textbox
