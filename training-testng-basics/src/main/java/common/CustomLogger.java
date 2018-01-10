package common;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomLogger {

    private Logger logger;
    private static Class<?> loggerClazz;
    private static CustomLogger instance = null;

    private CustomLogger(Class<?> clazz) {
        loggerClazz = clazz;
        logger = createLogger(clazz);
    }

    /**
     * Get the logger instance for the given class name
     *
     * @param clazz
     * @return
     */
    public static CustomLogger getInstance(Class<?> clazz) {
        if ((instance == null && loggerClazz == null) || (loggerClazz.getName() != clazz.getName()))
            instance = new CustomLogger(clazz);
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    /**
     * Create the logger according to parameters
     *
     * @param clazz
     * @return
     */
    private Logger createLogger(Class<?> clazz) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy" + ProjectConstants.separator + "MM" + ProjectConstants.separator + "dd" + ProjectConstants.separator + "HH.mm.ss");
        Date date = new Date();
        System.setProperty(ProjectConstants.LOGFILE_PROP_NAME, ProjectConstants.LOGS_DIR_PATH + File.separator + String.valueOf(dateFormat.format(date)));
        logger = org.apache.log4j.Logger.getLogger(clazz);
        if (System.getProperty(ProjectConstants.MAVEN_LOG_OFF) != null) {
            logger.setLevel(Level.OFF);
        }
        return logger;
    }
}
