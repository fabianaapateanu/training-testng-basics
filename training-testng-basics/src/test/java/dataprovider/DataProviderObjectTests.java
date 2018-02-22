package dataprovider;

import common.CustomDriver;
import common.CustomLogger;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import training.basic.dataprovider.Search;
import training.basic.dataprovider.User;
import training.basic.pageObject.LoginPage;

/**
 * DataProvider can be used with your own defined objects.
 * {main/java/training/basic/dataprovider/User.java} is used in this example for login test data.
 *
 * @author fapateanu
 */
public class DataProviderObjectTests {
    private CustomDriver myDriver;
    private static Logger LOG;
    //For the search functionality
    WebElement user;
    WebElement pass;
    WebElement searchBar;
    WebElement signIn;


    @DataProvider(name = "invalid_user_data")
    public static Object[] loginInvalidProvider() {
        return new Object[]{new User("yeseniaworld@gmail.com", "test12345"),
                new User("invalidUser@yahoo", "invalidPass")};
    }

    @DataProvider(name = "valid_user_data")
    public static Object[] loginValidProvider() {
        //User user1 = new User("yes", "par");

        return new Object[]{ /*user1,*/ new User("yeseniaworld@gmail.com", "test12345"),
                new User("cosminTestcosmin", "Cosmin160692")};
    }


    @BeforeClass()
    public static void runBeforeClassInit() {
        LOG = CustomLogger.getInstance(DataProviderObjectTests.class).getLogger();
        LOG.info("Running setup before class test methods initialization");
    }

    @AfterClass()
    public static void runAfterClassFinished() {
        LOG.info("Running teardown after class test methods run finished");
    }

    @BeforeMethod()
    public void runBeforeEachTestMethod() {
        LOG.info("Running setup before each test method");
        myDriver = CustomDriver.getInstance();
    }

    @AfterMethod()
    public void runAfterEachTestMethod() {
        LOG.info("Running teardown before each test method");
        myDriver.closeDriver();
    }

    @Test(dataProvider = "invalid_user_data")
    public void loginWithInvalidUser(User testUser) {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        loginPage.fillUsername(testUser.getUsername());
        loginPage.fillPassword(testUser.getPassword());
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Some failure log");
    }

    @Test(dataProvider = "valid_user_data")
    public void loginWithValidUser(User testUser) {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        loginPage.fillUsername(testUser.getUsername());
        loginPage.fillPassword(testUser.getPassword());
        loginPage.clickLogin();
        //try with performLogin + method for clicking, on homepage, to see "signed in as..."
        Assert.assertTrue(loginPage.isLoginFormisplayed(), "Learn Git and GitHub without any code!");
    }

    @Test(dataProvider = "search_text")
    public void searchText(Search firstSearch) {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        //click on Username textbox and send user
        user = myDriver.getDriver().findElement(By.id("login_field"));
        user.sendKeys();
        //click on Password textbox and send pass
        pass.findElement(By.id("password")).sendKeys("Cosmin160692");
        //click on Sign in button
        signIn.findElement(By.className("btn btn-primary btn-block")).click();
        //Assert after login
        Assert.assertTrue(loginPage.isLoginFormisplayed(), "Learn Git and GitHub without any code!");

        //click on search button
        searchBar.findElement(By.className("form-control header-search-input js-site-search-focus "))
                .sendKeys(firstSearch.getSearchText());
        searchBar.findElement(By.className("form-control header-search-input js-site-search-focus "))
                .sendKeys(Keys.ENTER);
    }
}
