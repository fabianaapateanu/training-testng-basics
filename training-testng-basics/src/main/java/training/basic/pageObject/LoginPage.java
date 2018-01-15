package training.basic.pageObject;

import common.CustomLogger;
import common.helper.ActionHelper;
import common.helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Login page which is the first page opened by the driver
 *
 * @author fapateanu
 */
public class LoginPage {

    @FindBy(id = "login_field")
    private WebElement usernameElem;

    @FindBy(id = "password")
    private WebElement passwordElem;

    @FindBy(className = "flash-error")
    private WebElement errorMsgContainerElem;

    @FindBy(name = "commit")
    private WebElement loginBtnElem;

    @FindBy(className = "auth-form-body")
    private WebElement loginFormDivElem;

    protected WebDriver driver;
    protected ActionHelper driverActionHelper;
    protected WaitHelper driverWaitHelper;

    private Logger log;

    public LoginPage(WebDriver driver) {
        log = CustomLogger.getInstance(LoginPage.class).getLogger();
        log.info("Initializing LoginPage...");
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driverActionHelper = ActionHelper.getInstance();
        driverWaitHelper = WaitHelper.getInstance();

        driverWaitHelper.waitForElementVisibility(loginFormDivElem);

        log.info("Finished LoginPage initialization");
    }

    public void fillUsername(String username) {
        driverActionHelper.sendKeys(this.usernameElem, username);
    }

    public void fillPassword(String password) {
        driverActionHelper.sendKeys(this.passwordElem, password);
    }

    public void clickLogin() {
        driverActionHelper.clickOn(loginBtnElem);
    }

    public HomePage performLogin(String username, String password) {
        fillUsername(username);
        fillPassword(password);
        clickLogin();
        return new HomePage(this.driver);
    }

    public boolean isErrorMessageDisplayed() {
        log.info("Is error message displayed");
        driverWaitHelper.simpleWait(1000);
        return errorMsgContainerElem.isDisplayed();
    }

    public boolean isLoginFormisplayed() {
        log.info("Is login form displayed");
        return loginFormDivElem.isDisplayed();
    }
}
