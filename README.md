# Q: Implement a test to verify the following user journey

 1. Login to https://www.saucedemo.com/ using the "standard_user" account
 2. Sort the products by Price (high to low)
 3. Add the cheapest & the 2nd costliest products to your basket
 4. Open the basket
 5. Checkout
 6. Enter details and Finish the purchase

## Summary of solution
- Solution written using Selenium WebDriver with Java and executed using TestNG.
- PageObjectModel pattern used to create page classes

- Configurations (browser and webdriver to use, default username, password etc) are provided in config.properties file which can be overriden as a property from command line as well (using -DpropertyName=propertyValue syntax)

- A data file contains various sample data for Login page as well as User info page. This is tested in `LoginFunctionalityTest` and `YourInfoPageTest`. This data file location can also be overriden as a property from command line.

- Exact scenario from question is tested under `CheckoutOverviewTest#testCheckoutOverview_ItemsAddedArePresentInCheckout_Multiple`

## Test execution

1. Clone https://github.com/nanda-unnikrishnan/global-qa-interview-nanda.git
2. Go to directory global-qa-interview-nanda
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

#Q: API Automation 
##Summary
- Collection created in Postman
- Queries are added as separate within the collection taking variables from collection where required

##Test Execution
1. Clone https://github.com/nanda-unnikrishnan/global-qa-interview-nanda.git
2. Go to directory global-qa-interview-nanda/postman
3. Import json file `Spacex.postman_collection.json` into your postman instance
4. Run collection