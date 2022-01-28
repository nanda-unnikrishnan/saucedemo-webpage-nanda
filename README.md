# Q: Implement a test to verify the following user journey

 1. Login to https://www.saucedemo.com/ using the "standard_user" account
 2. Sort the products by Price (high to low)
 3. Add the cheapest & the 2nd costliest products to your basket
 4. Open the basket
 5. Checkout
 6. Enter details and Finish the purchase

## Solution
- This solution is built based on PageObjectModel design pattern with a data driven Framework.
- Implementation is using Selenium WebDriver(with Java) for scripting, Maven for build and TestNG for execution.
- Separate page level tests are written independent of each other.
- The specific user journey from the question is tested under `CheckoutOverViewTest#testCheckoutOverview_ItemsAddedArePresentInCheckout_CheapestAndSecondCostliest`.

### Data and Configuration files

- Below configurations are provided in config.properties file.
  - browser
  - location of the associated WebDriver
  - default username and password (for happy path scenarios)
  
  These properties can be overriden while running from the command line (using -DpropertyName=propertyValue).
  `eg. -Dbrowser=chrome`
  
- An excel data file facilitates the input for data driven testing on the text input fields (of the Login and Your Information page) of the application.
  - `LoginFunctionalityTest` and `YourInfoPageTest` are the ones that are currently implemented as data driven tests.
  
  The data file name can also be overriden while running from the command line (using -DpropertyName=propertyValue syntax).
  `eg. -Dtestdata.filename=<New file name>`



## Test execution

1. Clone https://github.com/nanda-unnikrishnan/global-qa-interview-nanda.git
2. Go to directory *global-qa-interview-nanda*
3. Execute below command which uses maven (needs to be installed prior) after updating chrome driver location
```
mvn clean test -Dchrome.driver.location=<location_of_chromedriver>
```

You could override browsers to firefox/chrome by passing arguments for browser and driver location.
For Chrome:
```
mvn clean test -Dbrowser=chrome -Dchrome.driver.location=<location_of_chromedriver>
```

For Firefox:
```
mvn clean test -Dbrowser=firefox -Dfirefox.driver.location=<location_of_geckodriver>
```

Run a specific test case:
```
mvn clean test -Dbrowser=chrome -Dchrome.driver.location=<location_of_chromedriver> -Dtest=CheckoutOverviewTest#testCheckoutOverview_ItemsAddedArePresentInCheckout_Multiple
```

4. Verify test outputs in output folder to view html reports

# Q: API Automation 

## Summary
- Collection created in Postman
- Queries are added as separate within the collection taking variables from collection where required

## Test Execution
1. Clone https://github.com/nanda-unnikrishnan/global-qa-interview-nanda.git
2. Go to directory *global-qa-interview-nanda/postman*
3. Import json file `Spacex.postman_collection.json` into your postman instance
4. Run collection
