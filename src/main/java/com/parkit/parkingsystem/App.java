package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>A command line app for managing a parking system
 * and storing the data in a Mysql DB.</p>
 *
 * @author Gwen
 * @version 1.0
 */
public final class App {

    private App() {
    }

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger("App");

    /**
     * Main.
     *
     * @param args args
     */
    public static void main(final String[] args) {
        LOGGER.info("Initializing Parking System");
        InteractiveShell.loadInterface();
    }
}
