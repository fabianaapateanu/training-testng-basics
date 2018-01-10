package basic;

import common.CustomDriver;
import common.CustomLogger;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import training.SetupOfTestNgHelper;
import training.basic.HomePage;
import training.basic.LoginPage;

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
