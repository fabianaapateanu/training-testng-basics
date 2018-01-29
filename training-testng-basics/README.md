# training-testng-basic 
Basic TestNG examples with WebDriver and Chrome browser.

## Project resources
* src/main/resources/drivers/mac - chromedriver v. 2.35
* src/main/resources/drivers/mac - geckodriver v. 0.19.1
* src/main/resources/drivers/windows - chromedriver.exe v.2.35
* src/main/resources/drivers/windows - geckodriver.exe 0.19.1

## Project structure
* src/main/java/common - webdriver custom driver and helpers
* src/main/java/training/basic - page object classes and dataprovider object class for the demos
    * /dataprovider
    * /pageObject
    * LoginUserHelper.java - helper class for basic login information
* src/test/java
    * /basic - test classes for basic examples
    * /dataprovider - test classes for dataprovider examples
    
## Running the Demos
1. You will need to create yourself a valid GitHub account
2. For login tests and purposes you will need to add your login information:
    * `LoginUserHelper.java` in methods `readValidUsername()`, `readValidPassword()`

## Demo 1
Example of most used annotations in TestNG for creating the test setup.

1. In test class `SetupOfTestNgExample.java` we have an example for each annotation:
    1. @BeforeClass & @AfterClass
        * Only logs a message, this is run before the class gets initialized
    2. @BeforeGroups & @AfterGroups
        1. For this 2 groups are created: "positive_tests" & "negative_tests"
        2. For each group we have a set of setup methods
    3. @BeforeMethod & AfterMethod
        1. In before we always start & initialize the driver
        2. In after we always close the driver 
        
## Demo 2
Example of test methods which will be ignored and of test methods which are expecting exceptions.

1. In test class `IgnoreTestsExample.java`:
    1. Similar test setup is created as in `SetupOfTestNgExample.java`
    2. One of the two test methods is annotated with:
        *  `@Test(enabled = false)`
    3. When running this test class only the `loginWithValidUser()` test method will be run
2. In test class `ExceptionTestExample.java`:
    1. Similar test setup is created as in `SetupOfTestNgExample.java`
    2. The test method is expecting to raise a NoSuchElementException class type exception:
        * `@Test(expectedExceptions = {NoSuchElementException.class})`
    3. Running the test class should run the test method which will pass because the exception will be raised.

## Demo 3
Data provider with primitive types and with custom object type.

1. In test class `DataProviderPrimitiveTests.java`:
    1. Two test methods for search functionality with & without results
    2. Two data provider methods one for multiple results, one for no results
        * `@DataProvider(name = "valid_search_data")` / `@DataProvider(name = "invalid_search_data")`
    3. The two test methods use the data provider 
        * `@Test(groups = "positive_tests", dataProvider = "valid_search_data")`
        * `@Test(groups = "negative_tests", dataProvider = "invalid_search_data")`
    4. Running the test class will result in running each test method 3 times for each test data set provided.
2. In test class `DataProviderObjectTests.java`:
    1. A custom user object has been created `User.java`
    2. A data provider which returns two user objects (invalid login information)
        * `@DataProvider(name = "invalid_user_data")`
    3. A login test method which uses the data provider:
        * ` @Test(dataProvider = "invalid_user_data")`
    
## Demo 4
Running the tests with TestNG xml suite files

1. Test suite based on selected test classes: `runSuite.xml`
2. Test suite based on selected groups from within a package: `runGroups.xml`

## Practice, practice :exclamation: TestNG basics :sweat:
1. In `DataProviderObjectTests.java` add a new data provider method and perform a valid login test
    1. Create a new data provider method `loginValidProvider()` for creating valid login data
    2. Create a new test data method which is using the valid data provider from point 1.1

2. Create a data provider object example for a search functionality test
    1. Create a class which will hold the search test data (package main/java/training/basic/dataprovider)
    2. Create a test class which will have the search test (package test/java/dataprovider)
    3. In the test class create a test data provider method for creating the search test data object from point i
    4. Create the search test method by using the data provider from point iii