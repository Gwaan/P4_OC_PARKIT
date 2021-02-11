package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * <p>Class who provides info to establish a connection to database.</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class ReadProperties {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger("ReadProperties");
    /**
     * DB url.
     */
    private String url;
    /**
     * DB user.
     */
    private String user;
    /**
     * DB password.
     */
    private String password;
    /**
     * DB driver.
     */
    private String driver;

    /**
     * <p>Method getting values from a properties file and setting them to
     * fields.</p>
     */
    public void getDbConnectionInfo() {
        FileInputStream fIS = null;
        Properties prop = null;
        try {
            fIS = new FileInputStream("src/main/resources/config.properties");
            prop = new Properties();
            prop.load(fIS);
            this.url = prop.getProperty("url");
            this.user = prop.getProperty("username");
            this.password = prop.getProperty("password");
            this.driver = prop.getProperty("driver");
        } catch (FileNotFoundException f) {
            LOGGER.error("Error while reading config.properties");
        } catch (IOException e) {
            LOGGER.error("I/O Exception found");
        } finally {
            try {
                if (fIS != null) {
                    fIS.close();
                }
            } catch (IOException e) {
                LOGGER.error("I/O Exception found");
            }
        }
    }

    /**
     * getter for DB url.
     *
     * @return DB's url
     */
    public String getUrl() {
        return url;
    }

    /**
     * getter for DB user.
     *
     * @return DB's user
     */
    public String getUser() {
        return user;
    }

    /**
     * getter for DB password.
     *
     * @return DB's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * getter for DB driver.
     *
     * @return DB's driver
     */
    public String getDriver() {
        return driver;
    }
}
