package dataprovider;

import basic.IgnoreTestsExample;
import common.CustomDriver;
import common.CustomLogger;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import training.SetupOfTestNgHelper;
import training.basic.HomePage;
import training.basic.LoginPage;

public class SearchFunctionalityTests {

    private static String username;
    private static String password;

    private String searchQuery;

    private CustomDriver myDriver;

    private static Logger LOG;

    @BeforeClass
    public static void runBeforeClassInit() {
        LOG = CustomLogger.getInstance(IgnoreTestsExample.class).getLogger();
        LOG.info("Running setup before class test methods initialization");
    }

    @AfterClass
    public static void runAfterClassFinished() {
        LOG.info("Running teardown after class test methods run finished");
    }

    @BeforeMethod
    public void runBeforeEachTestMethod() {
        LOG.info("Running setup before each test method");

        username = SetupOfTestNgHelper.readValidUsername();
        password = SetupOfTestNgHelper.readValidPassword();

        myDriver = CustomDriver.getInstance();
    }

    @AfterMethod
    public void runAfterEachTestMethod() {
        LOG.info("Running teardown before each test method");
        myDriver.closeDriver();
    }

    @Test
    public void loginAndSearchMultipleResults() {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        HomePage homePage = loginPage.performLogin(username, password);
        Assert.assertTrue(homePage.isSearchAreaDisplayed(), "The search area is not displayed");

        searchQuery = "appium";

        homePage.performSearch(searchQuery);
        Assert.assertTrue(homePage.isRepositorySearchResultListDisplayed(), "The search action did not return multiple results");
    }


    @Test
    public void loginAndSearchNoResults() {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        HomePage homePage = loginPage.performLogin(username, password);
        Assert.assertTrue(homePage.isSearchAreaDisplayed(), "The search area is not displayed");

        searchQuery = "poooooooop";

        homePage.performSearch(searchQuery);
        Assert.assertFalse(homePage.isRepositorySearchResultListDisplayed(), "The search action returned results");
        Assert.assertTrue(homePage.isEmptyResultDisplayed(), "The empty results container is not displayed");
    }
}
