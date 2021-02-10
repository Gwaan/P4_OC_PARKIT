package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>A command line app for managing a parking system
 * and storing the data in a MYsql DB</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class App {

    private static final Logger logger = LogManager.getLogger("App");

    /**
     * Main
     *
     * @param args args
     */
    public static void main(String args[]) {
        logger.info("Initializing Parking System");
        InteractiveShell.loadInterface();
    }
}
