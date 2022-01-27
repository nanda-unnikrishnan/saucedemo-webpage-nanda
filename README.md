# Q: Implement a test to verify the following user journey

 1. Login to https://www.saucedemo.com/ using the "standard_user" account
 2. Sort the products by Price (high to low)
 3. Add the cheapest & the 2nd costliest products to your basket
 4. Open the basket
 5. Checkout
 6. Enter details and Finish the purchase

# Summary of solution
- Solution written using Selenium, TestNG and Java.
- PageObjectModel used to model various website pages
- Checkout scenarios tested in UserCheckoutJourneyTest
- Configurations (browser and webdriver to use, default username, password etc) provided in config.properties file, or overriden as -D property in CLI


# Test execution

1. Clone https://github.com/nanda-unnikrishnan/global-qa-interview-nanda.git
2. Go to directory global-qa-interview-nanda
3. Execute below command which uses maven (needs to be installed prior)
```
mvn clean test
```
4. Verify test outputs in test-output folder
