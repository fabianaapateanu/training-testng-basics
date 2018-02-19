package dataprovider;

import common.CustomDriver;
import common.CustomLogger;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import training.basic.dataprovider.User;
import training.basic.pageObject.HomePage;
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

    @DataProvider(name = "invalid_user_data")
    public Object[] loginInvalidProvider() {
        return new Object[]{new User("yeseniaworld@gmail.com", "test12345"),
                new User("invalidUser@yahoo", "invalidPass")};
    }

    @DataProvider(name = "valid_user_data")
    public Object[] loginValidProvider() {
        return new Object[]{new User("nemesdaniela@gmail.com", "Magdalena89"),
                new User("nemesdaniela@gmail.com", "Magdalena89")};
    }

    @BeforeClass(groups = {"positive_tests", "negative_tests"})
    public static void runBeforeClassInit() {
        LOG = CustomLogger.getInstance(DataProviderObjectTests.class).getLogger();
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
    }

    @AfterMethod(groups = {"positive_tests", "negative_tests"})
    public void runAfterEachTestMethod() {
        LOG.info("Running teardown before each test method");
        myDriver.closeDriver();
    }

    @Test(groups = "positive_tests", dataProvider = "invalid_user_data")
    public void loginWithInvalidUser(User testUser) {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        loginPage.fillUsername(testUser.getUsername());
        loginPage.fillPassword(testUser.getPassword());
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Some failure log");
    }

    @Test(groups = "positive_tests", dataProvider ="valid_user_data")
    public void loginWithValidUser(User testUser) {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        HomePage homePage = loginPage.performLogin(testUser.getUsername(), testUser.getPassword());
        Assert.assertTrue(homePage.isUserMenuButtonGroupVisible(), "Home page was not loaded successfully");
    }
}
