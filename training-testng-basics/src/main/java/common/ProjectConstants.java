package common;

import java.io.File;

/**
 * Created by rpapara on 11/14/2017.
 */
public class ProjectConstants {
    //driver constants
    public static final String URL = "https://github.com/login";
    public static final String PLATFORM_WINDOWS_OS = "windows";
    public static final String PLATFORM_MAC_OS = "mac";
    public static final String DRIVER_CHROMEDRIVER_PROP_NAME = "webdriver.chrome.driver";

    //chromedriver paths
    public static final String DRIVER_PROJECT_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "drivers";
    public static final String CHROMEDRIVER_MAC_OS_PATH = DRIVER_PROJECT_PATH + File.separator + "mac" + File.separator + "chromedriver";
    public static final String CHROMEDRIVER_WIN_OS_PATH = DRIVER_PROJECT_PATH + File.separator + "win" + File.separator + "chromedriver.exe";

    public static final String separator = "_";

    //logs
    public static final String LOGFILE_PROP_NAME = "logfilename";
    public static final String LOGS_DIR_PATH = System.getProperty("user.dir") + File.separator + "logs";

    //maven params
    public static final String MAVEN_LOG_OFF = "noLogging";
}
