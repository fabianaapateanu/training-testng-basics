package common.helper;

import common.CustomDriver;
import common.CustomLogger;
import org.apache.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class WaitHelper {
    private WebDriver driver;
    private final Logger LOG = CustomLogger.getInstance(WaitHelper.class).getLogger();
    private final int DEFAULT_TIME = 5; //seconds

    private static WaitHelper instance = null;

    private WaitHelper() {
        driver = CustomDriver.getInstance().getDriver();
    }

    /**
     * Get the WaitHelper unique instance
     *
     * @return
     */
    static public WaitHelper getInstance() {
        if (instance == null)
            instance = new WaitHelper();
        return instance;
    }


    //simple wait for x milliseconds
    public void simpleWait(int milisec) {
        LOG.info(String.format("Waiting for %s seconds", milisec / 1000));
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Explicit wait using fluent wait.
     *
     * @param timeout in seconds
     * @return
     */
    private Wait<WebDriver> wait(int timeout) {
        return new FluentWait<WebDriver>(driver)
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(500, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);
    }

    /**
     * Wait method that will perform a poll at every 500 milliseconds
     * When used for waiting a certain condition to become true, it will evaluate
     * the condition every 500ms until timeout is reached or the condition becomes true
     * When the timeout period is reached a TimeoutException is thrown because the
     * condition did not evalute to true in this given timeout.
     *
     * @param condition the condition to be evaluated
     * @param timeout   the amount of time to wait given in seconds
     * @return
     */
    private void waitForElement(ExpectedCondition<?> condition, int timeout) {
        try {
            wait(timeout).until(condition);
        } catch (TimeoutException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Wait for an element to be visible
     */
    public void waitForElementVisibility(WebElement elem, int timeout) {
        LOG.info(String.format("Wait for an element to be visible with timeout %s", timeout));
        waitForElement(ExpectedConditions.visibilityOf(elem), timeout);
    }

    /**
     * Wait for an element visibility a default time
     */
    public void waitForElementVisibility(WebElement elem) {
        LOG.info("Wait for an element visibility with default timeout");
        waitForElement(ExpectedConditions.visibilityOf(elem), DEFAULT_TIME);
    }

    /**
     * Expected condition that evaluates to true if the given element is not displayed
     * Also if the element is not found at all it returns true
     *
     * @param elem the element that should not be displayed
     * @return
     */
    private ExpectedCondition<Boolean> elementIsNotDisplayed(final WebElement elem) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(final WebDriver driver) {
                try {
                    return (!elem.isDisplayed());
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    //element is not present at all, therefore it is not displayed
                    return true;
                }
            }
        };
    }

    /**
     * Wait DEFAULT_TIME for an element to not be displayed anymore
     */
    public void waitForElementToDisappear(WebElement elem) {
        LOG.info(String.format("Waiting for element %s to disappear", elem));
        waitForElement(elementIsNotDisplayed(elem), DEFAULT_TIME);
    }

    /**
     * Wait for an element to not be displayed anymore
     *
     * @param elem    the element that should disappear
     * @param timeout timeout period in seconds
     */
    public void waitForElementToDisappear(WebElement elem, int timeout) {
        LOG.info(String.format("Waiting for element %s to disappear", elem));
        waitForElement(elementIsNotDisplayed(elem), timeout);
    }
}
