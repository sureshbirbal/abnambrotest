Feature: Recipes api tests

  Background:
    Given http baseUri is http://localhost:8080
    And I set Accept-Language http header to en-US
    And I set http headers to:
      | Accept        | application/json  |
      | Content-Type  | application/json  |

  Scenario: User Should be authenticated
    When I HEAD authenticated
    Then http response code should be 401
    When I authenticate with login/password suresh/password12345
    And I HEAD /authenticated
    Then http response code should be 200
    And I store the value of http response header Authorization as authToken in scenario scope

  Scenario: Register the new user
    When I authenticate with login/password sp2020.rai@gmail.com/password12345
    And I set http body to {"username": "sp2020.rai@gmail.com","password" :"password12345"}
    And I set http body path $.username to "sp2020.rai@gmail.com"
    And I POST /register
    Then http response header Content-Type should not be application/xml
    And http response code should be 201
    And http response header Content-Type should be application/json
    And http response body path $.id must be 1
    And http response body path $.id should be 1
    And http response body path $.username should be sp2020.rai@gmail.com
    And http response body path $.role should be USER


  Scenario: Add Authorization token into header to create recipe
    And I set Authorization http header to `$authToken`
    And I set http body to {"id":3,"name":"Real Homemade Bagels","created":"","vegan":"true","servings":1,"instructions":"Combine 1 1/4 cup water, flour, sugar, 1 teaspoon salt, vegetable oil, and yeast in the mixing bowl of a stand mixer. Mix on low speed using the dough hook until well-developed, about 8 minutes","ingredients":[{"name":"1 ¼ cups water"},{"name":"4 ½ cups bread flour"},{"name":"3 tablespoons white sugar"},{"name":"2 tablespoons vegetable oil"},{"name":"1 tablespoon instant yeast"},{"name":"4 quarts water"},{"name":"1 cup honey (Optional)"}]}
    And I POST /recipes
    Then http response header Content-Type should exist
    Then http response header xsrf-token should not exist
    And http response body should be valid json
    Then http response code should not be 404
    And http response code should be 201



  Scenario: Get wrong recipe
    When I GET /recipes/124333
    Then http response code should be 404
    And http response body path $ should not have content

  Scenario: Delete wrong recipe
    When I DELETE /recipes/40
    Then http response code should be 404
    And http response body path $ should not exist
    And http response body path $ should not have content

  Scenario: Delete recipe
    When I DELETE /recipes/1
    Then http response code should be 200
    And I DELETE /recipes/2
    Then http response code should be 200
