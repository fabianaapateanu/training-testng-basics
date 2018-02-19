package dataprovider;

import common.CustomDriver;
import common.CustomLogger;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import training.LoginUserHelper;
import training.basic.dataprovider.SearchData;
import training.basic.pageObject.HomePage;
import training.basic.pageObject.LoginPage;

public class DataProviderSearchTests {
    private static String username;
    private static String password;
    private HomePage homePage;
    private CustomDriver myDriver;
    private static Logger LOG;

    @DataProvider(name = "valid_search_data")
    public Object[] validSearch() {
        return new Object[]{new SearchData("automation"),
                new SearchData("testng"),
                new SearchData("webtesting")};
    }

    @DataProvider(name = "invalid_search_data")
    public Object[] invalidSearch() {
        return new Object[]{new SearchData("testtttttttttttttt123"),
                new SearchData("helloooooooooooooooooooooo2313"),
                new SearchData("mynewtestssssssssssssssssssssssssss23432")};
    }


    @BeforeClass(groups = {"positive_tests", "negative_tests"})
    public static void runBeforeClssInit() {
        LOG = CustomLogger.getInstance(DataProviderSearchTests.class).getLogger();
        LOG.info("Running setup efore class test methods initialization");
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
        LOG.info("Running teardown after each test method");
        myDriver.closeDriver();
    }

    @Test(groups = "positive_tests", dataProvider = "valid_search_data")
    public void searchWithMultipleResults(SearchData data) {
        Assert.assertTrue(homePage.isSearchAreaDisplayed(), "The search area is not displayed");
        homePage.performSearch(data.getSearchData());
        Assert.assertTrue(homePage.isRepositorySearchResultListDisplayed(), "The search action did not return multiple results");
    }

    @Test(groups = "negative_tests", dataProvider = "invalid_search_data")
    public void searchWithNoResults(SearchData data) {
        Assert.assertTrue(homePage.isSearchAreaDisplayed(), "The search area is not displayed");
        homePage.performSearch(data.getSearchData());
        Assert.assertTrue(homePage.isEmptyResultDisplayed(), "The empty results container is not displayed");
    }
}
