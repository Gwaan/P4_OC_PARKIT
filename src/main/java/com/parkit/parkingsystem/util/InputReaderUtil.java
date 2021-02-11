package com.parkit.parkingsystem.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * <p>Utility class to get input from user.</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class InputReaderUtil {

    /**
     * @see Scanner
     */
    private Scanner scan;
    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger("InputReaderUtil");

    /**
     * <p>Get input from user.</p>
     *
     * @return input
     */
    public int readSelection() {
        try {
            scan = new Scanner(System.in, "UTF-8");
            int input = Integer.parseInt(scan.nextLine());
            return input;
        } catch (Exception e) {
            LOGGER.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter valid number for proceeding further");
            return -1;
        }
    }

    /**
     * <p>Get vehicle registration number from user.</p>
     *
     * @return String input of the vehicle registration number
     * @throws IllegalArgumentException if the input is null
     * @throws Exception                if the input is not a String
     */
    public String readVehicleRegistrationNumber() throws Exception {
        try {
            scan = new Scanner(System.in, "UTF-8");
            String vehicleRegNumber = scan.nextLine();
            if (vehicleRegNumber == null || vehicleRegNumber.trim().length() == 0) {
                throw new IllegalArgumentException("Invalid input provided");
            }
            return vehicleRegNumber;
        } catch (Exception e) {
            LOGGER.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter a valid string for vehicle registration number");
            throw e;
        }
    }


}
