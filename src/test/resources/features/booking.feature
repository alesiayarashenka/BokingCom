Feature: Search on Booking.com

  @Smoke
  Scenario: Search by city
    Given User is looking for hotels in 'United States' city
    When User does search
    Then Hotel 'Grand Centennial Gulfport' should be on the search results page
    Then Hotel 'Grand Centennial Gulfport' rating is '8,8'

  @Smoke
  Scenario: Search by city with options
    Given User is looking for hotels in 'United Kingdom' city
    When User chooses city and dates
    And User chooses room for three adults
    And User chooses room for one child '3' years
    And User chooses room with two bedrooms
    And User chooses opportunity to stay with pets
    And User submits your options
    And User chooses filter villas
    Then Hotel 'Inishclare Cottages' should be on the search results page
    Then Hotel 'Inishclare Cottages' rating is '7,7'