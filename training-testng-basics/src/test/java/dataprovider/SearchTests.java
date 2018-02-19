package dataprovider;

import common.CustomDriver;
import common.CustomLogger;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import training.LoginUserHelper;
import training.basic.pageObject.HomePage;
import training.basic.pageObject.LoginPage;

public class SearchTests {
    String username;
    String password;

    private CustomDriver myDriver;
    private static Logger LOG;
    private HomePage homePage;


    @DataProvider(name = "search_data")
    public static Object[] searchData(){
        Object[] myData = new Object[] {"webdriver", "selenium", "automation"};
        return myData;
    }

    @BeforeClass()
    public static void runBeforeClass() {
        LOG = CustomLogger.getInstance(DataProviderPrimitiveTests.class).getLogger();
        LOG.info("Running setup before class test methods initialization");
    }

    @AfterClass()
    public static void runAfterClass() {
    LOG.info("Running teardown after class test methods run finished");
    }

    @BeforeMethod()
    public void runBeforeTestMethod() {
        LOG.info("Running setup before test method");
        username = LoginUserHelper.readValidUsername();
        password = LoginUserHelper.readValidPassword();
        myDriver = CustomDriver.getInstance();
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        homePage = loginPage.performLogin(username, password);
    }

    @AfterMethod()
    public void runAfterTestMethod() {
        LOG.info("Running teardown before each test method");
        myDriver.closeDriver();
    }

    @Test(dataProvider = "search_data")
    public void isSortButtonDisplayed(String searchQuery) {
        Assert.assertTrue(homePage.isSearchAreaDisplayed(), "The search area is not displayed");
        homePage.performSearch(searchQuery);

        Assert.assertTrue(homePage.isSortButtonDisplayed(), "The Sort Button is not displayed");
    }

}
