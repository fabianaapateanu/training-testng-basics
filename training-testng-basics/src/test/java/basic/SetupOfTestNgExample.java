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
 * TestNG setup can be made with annotations:
 * BeforeClass, AfterClass, BeforeMethod, AfterMethod. BeforeGroups, AfterGroups
 *
 * @author fapateanu
 */
public class SetupOfTestNgExample {
    private static String username;
    private static String password;

    private CustomDriver myDriver;

    private static Logger LOG;

    @BeforeClass
    public static void runBeforeClassInit() {
        LOG = CustomLogger.getInstance(SetupOfTestNgExample.class).getLogger();
        LOG.info("Running setup before class test methods initialization");
    }

    @AfterClass
    public static void runAfterClassFinished() {
        LOG.info("Running teardown after class test methods run finished");
    }

    @BeforeGroups("positive_tests")
    public void runBeforeEachGroupPositive() {
        LOG.info("Running setup before group positive_tests initialization");
        //read valid login data
        username = LoginUserHelper.readValidUsername();
        password = LoginUserHelper.readValidPassword();
    }

    @AfterGroups("positive_tests")
    public void runAfterEachGroupPositive() {
        LOG.info("Running teardown after positive_tests group run finished");
    }

    @BeforeGroups("negative_tests")
    public void runBeforeEachGroupNegative() {
        LOG.info("Running setup before group negative_test initialization");
        //read invalid login data
        username = LoginUserHelper.readInvalidUsername();
        password = LoginUserHelper.readInvalidPassword();
    }

    @AfterGroups("negative_tests")
    public void runAfterEachGroupNegative() {
        LOG.info("Running teardown after negative_test group run finished");
    }

    @BeforeMethod
    public void runBeforeEachTestMethod() {
        LOG.info("Running setup before each test method");
        myDriver = CustomDriver.getInstance();
    }

    @AfterMethod
    public void runAfterEachTestMethod() {
        LOG.info("Running teardown before each test method");
        myDriver.closeDriver();
    }

    @Test(groups = "positive_tests")
    public void loginWithValidUser() {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        HomePage homePage = loginPage.performLogin(username, password);
        Assert.assertTrue(homePage.isUserMenuButtonGroupVisible(), "Some failure log");
    }

    @Test(groups = "negative_tests")
    public void loginWithInvalidUser() {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        loginPage.fillUsername(username);
        loginPage.fillPassword(password);
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Some failure log");
    }
}
