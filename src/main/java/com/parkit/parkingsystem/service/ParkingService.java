package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * <p>Service class which proceed to entering and exiting vehicles.</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class ParkingService {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger("ParkingService");

    /**
     * @see FareCalculatorService
     */
    private static FareCalculatorService fareCalculatorService =
            new FareCalculatorService();

    /**
     * @see InputReaderUtil
     */
    private InputReaderUtil inputReaderUtil;
    /**
     * @see ParkingSpotDAO
     */
    private ParkingSpotDAO parkingSpotDAO;
    /**
     * @see TicketDAO
     */
    private TicketDAO ticketDAO;
    /**
     * Constant thousand.
     */
    private static final short THOUSAND = 1000;

    /**
     * @param pInputReaderUtil utility class to get input from the user
     * @param pParkingSpotDAO  dao class to execute request on parking table
     *                         on mysql database
     * @param pTicketDAO       adao class to execute request on ticket table
     *                         on mysql database
     */
    public ParkingService(final InputReaderUtil pInputReaderUtil,
                          final ParkingSpotDAO pParkingSpotDAO,
                          final TicketDAO pTicketDAO) {
        this.inputReaderUtil = pInputReaderUtil;
        this.parkingSpotDAO = pParkingSpotDAO;
        this.ticketDAO = pTicketDAO;
    }

    /**
     * <p>Method of entering vehicles.</p>
     *
     * @throws IllegalArgumentException if the vehicle registration number is
     *                                  still parked in the parking lot
     */
    public void processIncomingVehicle() {
        try {
            ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
            if (parkingSpot != null && parkingSpot.getId() > 0) {

                String vehicleRegNumber = getVehichleRegNumber();
                if (ticketDAO.getCarAlreadyInParking(vehicleRegNumber)) {
                    throw new IllegalArgumentException(
                            "This vehicle is " + "already in parking !");
                }
                parkingSpot.setAvailable(false);
                parkingSpotDAO.updateParking(parkingSpot); //allot this
                // parking space and mark it's availability as false

                Date inTime = new Date();
                Ticket ticket = new Ticket();
                if (ticketDAO.getVehicleRegNumber(vehicleRegNumber)) {
                    System.out.println("Welcome back! As a recurring user of "
                            + "our parking lot, you'll benefit from a 5% "
                            + "discount.");
                    ticket.setIsRecurrentUser(true);
                }
                ticket.setParkingSpot(parkingSpot);
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(0);
                ticket.setInTime(inTime);
                ticket.setOutTime(null);
                ticketDAO.saveTicket(ticket);
                System.out.println("Generated Ticket and saved in DB");
                System.out.println("Please park your vehicle in spot number:"
                        + parkingSpot.getId());
                System.out.println("Recorded in-time for vehicle number:"
                        + vehicleRegNumber + " is:" + inTime);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to process incoming vehicle", e);
        }
    }

    /**
     * <p>Method to get vehicle registration number input.</p>
     *
     * @return String input of the vehicle registration number
     */
    private String getVehichleRegNumber() throws Exception {
        System.out.println("Please type the vehicle registration number and "
                + "press enter key");
        return inputReaderUtil.readVehicleRegistrationNumber();
    }

    /**
     * <p>Method to get the next parking number.</p>
     *
     * @return a parking spot which is available
     */
    public ParkingSpot getNextParkingNumberIfAvailable() {
        int parkingNumber = 0;
        ParkingSpot parkingSpot = null;
        try {
            ParkingType parkingType = getVehichleType();
            parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
            if (parkingNumber > 0) {
                parkingSpot = new ParkingSpot(parkingNumber, parkingType, true);
            } else {
                throw new Exception("Error fetching parking number from DB. "
                        + "Parking slots might be full");
            }
        } catch (IllegalArgumentException ie) {
            LOGGER.error("Error parsing user input for type of vehicle", ie);
        } catch (Exception e) {
            LOGGER.error("Error fetching next available parking slot", e);
        }
        return parkingSpot;
    }

    /**
     * <p>Method to get the vehicle type.</p>
     *
     * @return the vehicle parking type
     * @throws IllegalArgumentException if the input is not a vehicle type
     */
    private ParkingType getVehichleType() {
        System.out.println("Please select vehicle type from menu");
        System.out.println("1 CAR");
        System.out.println("2 BIKE");
        int input = inputReaderUtil.readSelection();
        switch (input) {
            case 1:
                return ParkingType.CAR;

            case 2:
                return ParkingType.BIKE;

            default:
                System.out.println("Incorrect input provided");
                throw new IllegalArgumentException("Entered input is invalid");

        }
    }

    /**
     * <p>Method of exiting vehicles that update the ticket with fare and
     * update the parking spot with availability.</p>
     */
    public void processExitingVehicle() {
        try {
            String vehicleRegNumber = getVehichleRegNumber();
            Ticket ticket = ticketDAO.getTicket(vehicleRegNumber,
                    DBConstants.GET_TICKET_CAR_ALREADY_PARKED);
            Date outTime = new Date(System.currentTimeMillis() + THOUSAND);
            ticket.setOutTime(outTime);
            if (ticketDAO.getVehicleRegNumber(vehicleRegNumber)) {
                ticket.setIsRecurrentUser(true);
            }
            fareCalculatorService.calculateFare(ticket);
            if (ticketDAO.updateTicket(ticket)) {
                ParkingSpot parkingSpot = ticket.getParkingSpot();
                parkingSpot.setAvailable(true);
                parkingSpotDAO.updateParking(parkingSpot);
                System.out.println(
                        "Please pay the parking fare:" + ticket.getPrice());
                System.out.println("Recorded out-time for vehicle number:"
                        + ticket.getVehicleRegNumber() + " is:" + outTime);
            } else {
                System.out.println("Unable to update ticket information. "
                        + "Error occurred");
            }
        } catch (Exception e) {
            LOGGER.error("Unable to process exiting vehicle", e);
        }
    }
}
