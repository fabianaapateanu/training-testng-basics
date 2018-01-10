package common;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class CustomDriver {

    private WebDriver driver;
    private String runningOsPlatform = null;
    private static CustomDriver instance = null;

    private final Logger LOG = CustomLogger.getInstance(CustomDriver.class).getLogger();

    /**
     * Private constructor which will start the webdriver
     */
    private CustomDriver() {
        runningOsPlatform = computeRunningPlatform();
        LOG.info("Running platform is: " + runningOsPlatform);
        openDriver();
    }

    /**
     * Get the CustomDriver unique instance
     *
     * @return
     */
    static public CustomDriver getInstance() {
        if (instance == null)
            instance = new CustomDriver();
        return instance;
    }

    /**
     * Compute the running OS name
     *
     * @return
     */
    private String computeRunningPlatform() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "windows";
        } else if (os.contains("mac")) {
            return "mac";
        } else if (os.contains("linux")) {
            return "linux";
        } else {
            return "other";
        }
    }

    /**
     * Open the webdriver
     *
     * @return
     */
    private WebDriver openDriver() {
        LOG.info("Starting the driver...");
        String runingPlatfom = computeRunningPlatform();
        if (runingPlatfom.equalsIgnoreCase(ProjectConstants.PLATFORM_WINDOWS_OS)) {
            System.setProperty(ProjectConstants.DRIVER_CHROMEDRIVER_PROP_NAME, ProjectConstants.CHROMEDRIVER_WIN_OS_PATH);
            LOG.info("Chromedriver path is: " + ProjectConstants.CHROMEDRIVER_WIN_OS_PATH);
            driver = new ChromeDriver();
        } else if (runingPlatfom.equalsIgnoreCase(ProjectConstants.PLATFORM_MAC_OS)) {
            System.setProperty(ProjectConstants.DRIVER_CHROMEDRIVER_PROP_NAME, ProjectConstants.CHROMEDRIVER_MAC_OS_PATH);
            LOG.info("Chromedriver path is: " + ProjectConstants.CHROMEDRIVER_MAC_OS_PATH);
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        LOG.info("Driver started and it should navigate to: " + ProjectConstants.URL);
        driver.navigate().to(ProjectConstants.URL);
        return driver;
    }

    /**
     * @return
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Close the driver and its instance
     */
    public void closeDriver() {
        LOG.info("Closing the driver...");
        if (instance != null) {
            driver.quit();
            instance = null;
        } else {
            LOG.warn("The CustomDriver instance is null, driver is not probably started.");
        }
    }
}
