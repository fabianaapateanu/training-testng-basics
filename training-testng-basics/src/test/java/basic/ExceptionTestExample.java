package basic;

import common.CustomDriver;
import common.CustomLogger;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.*;
import training.LoginUserHelper;
import training.basic.pageObject.HomePage;
import training.basic.pageObject.LoginPage;

/**
 * TestNG can verify that an exception was raised by using the
 * "expectedException = {ExceptionClass.class}" in the test annotation.
 *
 * @author fapateanu
 */
public class ExceptionTestExample {

    private static String username;
    private static String password;

    private CustomDriver myDriver;

    private static Logger LOG;

    @BeforeClass
    public static void runBeforeClassInit() {
        LOG = CustomLogger.getInstance(ExceptionTestExample.class).getLogger();
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

    @Test(expectedExceptions = {NoSuchElementException.class})
    public void loginWithValidUserAndNoLoginForm() {
        LoginPage loginPage = new LoginPage(myDriver.getDriver());
        HomePage homePage = loginPage.performLogin(username, password);
        Assert.assertFalse(loginPage.isLoginFormisplayed(), "Login form is still displayed");
    }

}
