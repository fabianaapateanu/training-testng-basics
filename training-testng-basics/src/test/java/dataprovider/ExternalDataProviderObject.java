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

public class ExternalDataProviderObject {

    private static String username;
    private static String password;
    private HomePage homePage;

    private CustomDriver myDriver;
    private static Logger LOG;


    @DataProvider(name = "valid_search_data")
    public static Object[] validSearchData() {
        Search data = new Search();
        return data.getSearchString();
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
        username = LoginUserHelper.readValidUsername();
        password = LoginUserHelper.readValidPassword();
        myDriver = CustomDriver.getInstance();
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        homePage = loginPage.performLogin(username, password);
    }

    @AfterMethod(groups = {"positive_tests", "negative_tests"})
    public void runAfterEachTestMethod() {
        LOG.info("Running teardown before each test method");
        myDriver.closeDriver();
    }

    @Test(groups = "positive_tests", dataProvider = "valid_search_data")
    public void searchWithMultipleResults(String searchQuery) {
        Assert.assertTrue(homePage.isSearchAreaDisplayed(), "The search area is not displayed");
        homePage.performSearch(searchQuery);

        Assert.assertTrue(homePage.isRepositorySearchResultListDisplayed(), "The search action did not return multiple results");
    }
}
