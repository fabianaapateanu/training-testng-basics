package training.basic;

import common.CustomLogger;
import common.helper.ActionHelper;
import common.helper.WaitHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage extends UserHeaderMenu {
    @FindBy(className = "logged-in")
    private WebElement loggedInBodyElem;

    //@FindBy(className = "codesearch-results")
    @FindBy(className = "repo-list")
    private WebElement respositorySearchResultsList;

    @FindBy(className = "blankslate")
    private WebElement emptySearchResults;

    protected ActionHelper driverActionHelper;
    protected WaitHelper driverWaitHelper;

    private Logger log;

    public HomePage(WebDriver driver) {
        super(driver);
        log = CustomLogger.getInstance(LoginPage.class).getLogger();
        log.info("Initializing HomePage...");
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driverActionHelper = ActionHelper.getInstance();
        driverWaitHelper = WaitHelper.getInstance();
        driverWaitHelper.waitForElementVisibility(loggedInBodyElem);
        log.info("Finished HomePage initialization");
    }

    public HomePage performSearch(String searchQuery) {
        typeSearchQuery(searchQuery);
        searchInput.sendKeys(Keys.ENTER);
        driverWaitHelper.simpleWait(1000);
        return this;
    }

    public boolean isRepositorySearchResultListDisplayed() {
        log.info("Is repository search result list displayed");
        return respositorySearchResultsList.isDisplayed();
    }

    public boolean isEmptyResultDisplayed() {
        log.info("Is empty search result displayed");
        return emptySearchResults.isDisplayed();
    }
}
