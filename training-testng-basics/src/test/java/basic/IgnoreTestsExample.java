package basic;

import common.CustomDriver;
import common.CustomLogger;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import training.LoginUserHelper;
import training.basic.pageObject.HomePage;
import training.basic.pageObject.LoginPage;

/**
 * TestNG can ignore tests by using the "enabled" attribute in the test annotation.
 * By default this is set to true, meaning the test will be run and not ignored.
 *
 * @author fapateanu
 */
public class IgnoreTestsExample {

    private static String username;
    private static String password;

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

        username = LoginUserHelper.readValidUsername();
        password = LoginUserHelper.readValidPassword();

        myDriver = CustomDriver.getInstance();
    }

    @AfterMethod
    public void runAfterEachTestMethod() {
        LOG.info("Running teardown before each test method");
        myDriver.closeDriver();
    }

    @Test
    public void loginWithValidUser() {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        HomePage homePage = loginPage.performLogin(username, password);
        Assert.assertTrue(homePage.isUserMenuButtonGroupVisible(), "Home page was not loaded successfully");
    }

    @Test(enabled = false)
    public void loginWithInvalidUser() {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        loginPage.fillUsername(username);
        loginPage.fillPassword(password);
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message was not displayed");
    }
}
