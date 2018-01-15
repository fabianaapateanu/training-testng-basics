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
 * Upper header menu which is available when user is logged in
 *
 * @author fapateanu
 */
public class UserHeaderMenu {
    @FindBy(className = "HeaderMenu")
    protected WebElement menuContainer;

    @FindBy(className = "header-search")
    protected WebElement searchContainer;

    @FindBy(className = "header-search-input")
    protected WebElement searchInput;

    @FindBy(id = "user-links")
    protected WebElement userButtonsGroupElem;

    protected WebDriver driver;
    protected ActionHelper driverActionHelper;
    protected WaitHelper driverWaitHelper;

    private Logger log;

    public UserHeaderMenu(WebDriver driver) {
        log = CustomLogger.getInstance(UserHeaderMenu.class).getLogger();
        log.info("Initializing UserHeaderMenu...");
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driverActionHelper = ActionHelper.getInstance();
        driverWaitHelper = WaitHelper.getInstance();

        driverWaitHelper.waitForElementVisibility(menuContainer);

        log.info("Finished UserHeaderMenu initialization");
    }

    public boolean isSearchAreaDisplayed() {
        log.info("Is search area displayed in the header menu");
        return (searchContainer.isDisplayed() && searchInput.isDisplayed());
    }

    public boolean isUserMenuButtonGroupVisible() {
        log.info("Is user links area  displayed in the header menu");
        return userButtonsGroupElem.isDisplayed();
    }

    protected void typeSearchQuery(String searchQuery) {
        log.info("Input the search string: " + searchQuery);
        driverActionHelper.sendKeys(searchInput, searchQuery);
    }
}
