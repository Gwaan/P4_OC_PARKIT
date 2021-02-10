package com.parkit.parkingsystem.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * <p>Class who provides info to establish a connection to database</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class ReadProperties {

    private String url;
    private String user;
    private String password;
    private String driver;

    /**
     * <p>Method getting values from a properties file and setting them to fields</p>
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
            System.out.println("config.properties not found");
            f.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fIS != null) {
                    fIS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }
}
