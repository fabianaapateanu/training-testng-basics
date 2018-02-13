package dataprovider;

import common.CustomDriver;
import common.CustomLogger;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import training.LoginUserHelper;
import training.basic.dataprovider.Search;
import training.basic.pageObject.HomePage;
import training.basic.pageObject.LoginPage;

/**
 * Created by rtomos on 2/13/2018.
 */
public class DataProviderSearchTests {
    private static String username;
    private static String password;
    private HomePage homePage;

    private CustomDriver myDriver;
    private static Logger LOG;

    @DataProvider(name = "valid_search_data")
    public static Object[] validSearchData() {
        Object[] data = new Object[]{new Search("project"), new Search("demo"), new Search("resources")};
        return data;
    }

    @DataProvider(name = "invalid_search_data")
    public static Object[] invalidSearchData() {
        Object[] data = new Object[]{new Search("t0m0$"), new Search("rareeees"), new Search("cypry@n")};
        return data;
    }

    @BeforeClass(groups = {"positive_tests", "negative_tests"})
    public static void runBeforeClassInit() {
        LOG = CustomLogger.getInstance(DataProviderPrimitiveTests.class).getLogger();
        LOG.info("Running setup before class test methods initialization");
    }

    @AfterClass(groups = {"positive_tests", "negative_tests"})
    public static void runAfterClassFinished() {
        LOG.info("Running teardown after class test methods run finished");
    }

    @BeforeMethod(groups = {"positive_tests", "negative_tests"})
    public void runBeforeEachTestMethod() {
        LOG.info("Running setup before each test method");
        myDriver = CustomDriver.getInstance();
        username = LoginUserHelper.readValidUsername();
        password = LoginUserHelper.readValidPassword();
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        homePage = loginPage.performLogin(username, password);
    }

    @AfterMethod(groups = {"positive_tests", "negative_tests"})
    public void runAfterEachTestMethod() {
        LOG.info("Running teardown before each test method");
        myDriver.closeDriver();
    }

    @Test(groups = "positive_tests", dataProvider = "valid_search_data")
    public void searchWithMultipleResults(Search search) {
        Assert.assertTrue(homePage.isSearchAreaDisplayed(), "The search area is not displayed");
        homePage.performSearch(search.getSearchInput());
        Assert.assertTrue(homePage.isRepositorySearchResultListDisplayed(), "The search action did not return multiple results");
    }

    @Test(groups = "negative_tests", dataProvider = "invalid_search_data")
    public void searchWithNoResults(Search search) {
        Assert.assertTrue(homePage.isSearchAreaDisplayed(), "The search area is not displayed");
        homePage.performSearch(search.getSearchInput());
        Assert.assertTrue(homePage.isEmptyResultDisplayed(), "The empty results container is not displayed");
    }
}
