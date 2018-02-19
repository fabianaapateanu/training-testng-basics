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

public class DataProviderSearchObjectTest {
    private CustomDriver myDriver;
    private static String username;
    private static String password;
    private HomePage homePage;

    private static Logger LOG;

    @DataProvider(name = "search_data")
    public Object[] searchForData(){
        return new Object[]{new Search("appium"),
                            new Search("webdriver"),
                            new Search("testng")};
    }

    @BeforeClass()
    public static void runBeforeClassInit() {
        LOG = CustomLogger.getInstance(DataProviderPrimitiveTests.class).getLogger();
        LOG.info("Running setup before class test methods initialization");
    }

    @AfterClass()
    public static void runAfterClassFinished() {
        LOG.info("Running teardown after class test methods run finished");
    }

    @BeforeMethod()
    public void runBeforeEachTestMethod() {
        LOG.info("Running setup before each test method");
        username = LoginUserHelper.readValidUsername();
        password = LoginUserHelper.readValidPassword();
        myDriver = CustomDriver.getInstance();
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        homePage = loginPage.performLogin(username, password);
    }

    @AfterMethod()
    public void runAfterEachTestMethod() {
        LOG.info("Running teardown before each test method");
        myDriver.closeDriver();
    }

    @Test(dataProvider = "search_data")
    public void searchWithMultipleResults(Search searchQuery) {
        Assert.assertTrue(homePage.isSearchAreaDisplayed(), "The search area is not displayed");
        homePage.performSearch(searchQuery.getSearchWord());

        Assert.assertTrue(homePage.isRepositorySearchResultListDisplayed(), "The search action did not return multiple results");
    }



}
