# training-testng-basic
Basic TestNG examples
# Main Project Resources
src/main/resources/drivers/mac - chromedriver v.
src/main/resources/drivers/windows - chromedriver.exe v.


# Practice, practice! - TestNG basics
1. In DataProviderObjectTests.java add a new data provider method and perform a valid login test
    1.1 Create a new data provider method "loginValidProvider" for creating valid login data
    1.2 Create a new test data method which is using the valid data provider from point 1.1

2. Create a data provider object example for a search functionality test
    2.1 Create a class which will hold the search test data (package main/java/training/basic/dataprovider)
    2.2 Create a test class which will have the search test (package test/java/dataprovider)
    2.3 In the test class create a test data provider method for creating the search test data object from point 1.1
    2.4 Create the search test method by using the data provider from point 1.3