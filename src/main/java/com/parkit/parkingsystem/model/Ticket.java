package com.parkit.parkingsystem.model;

import java.util.Date;

/**
 * <p>Class representing a parking spot.</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class Ticket {

    /**
     * id of the ticket.
     */
    private int id;
    /**
     * @see ParkingSpot
     */
    private ParkingSpot parkingSpot;
    /**
     * vehicle registration number associated with this ticket.
     */
    private String vehicleRegNumber;
    /**
     * price associated with this ticket.
     */
    private double price;
    /**
     * recorded in time for this ticket.
     */
    private Date inTime;
    /**
     * recorded out time for this ticket.
     */
    private Date outTime;
    /**
     * define if an user is recurrent or not.
     */
    private boolean isRecurrentUser = false;

    /**
     * getter for ticket id.
     *
     * @return ticket id
     */
    public int getId() {
        return id;
    }

    /**
     * setter for ticket id.
     *
     * @param pId id to set
     */
    public void setId(final int pId) {
        this.id = pId;
    }

    /**
     * getter for parking spot of this ticket.
     *
     * @return parking spot of the ticket
     */
    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    /**
     * setter for parking slot number.
     *
     * @param pParkingSpot parking spot to set
     */
    public void setParkingSpot(final ParkingSpot pParkingSpot) {
        this.parkingSpot = pParkingSpot;
    }

    /**
     * getter for vehicle registration number of this ticket.
     *
     * @return vehicle registration number of the ticket
     */
    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    /**
     * setter for parking slot number.
     *
     * @param pVehicleRegNumber vehicle registration number to set
     */
    public void setVehicleRegNumber(final String pVehicleRegNumber) {
        this.vehicleRegNumber = pVehicleRegNumber;
    }

    /**
     * getter for price of this ticket.
     *
     * @return price of the ticket
     */
    public double getPrice() {
        return price;
    }

    /**
     * setter for parking slot number.
     *
     * @param pPrice price to set
     */
    public void setPrice(final double pPrice) {
        this.price = pPrice;
    }

    /**
     * getter for recorded in time of this ticket.
     *
     * @return recorded in time for the ticket
     */
    public Date getInTime() {
        return inTime != null ? (Date) inTime.clone() : null;
    }

    /**
     * setter for parking slot number.
     *
     * @param pInTime recorded in time to set
     */
    public void setInTime(final Date pInTime) {
        this.inTime = pInTime != null ? new Date(pInTime.getTime()) : null;
    }

    /**
     * getter for recorded out time of this ticket.
     *
     * @return out time for the ticket
     */
    public Date getOutTime() {
        return outTime != null ? (Date) outTime.clone() : null;
    }

    /**
     * setter for parking slot number.
     *
     * @param pOutTime recorded out time to set
     */
    public void setOutTime(final Date pOutTime) {
        this.outTime = pOutTime != null ? new Date(pOutTime.getTime()) : null;
    }

    /**
     * getter for recurrent user.
     *
     * @return either true if the user is reccurent or false if he is not
     */
    public boolean getIsRecurrentUser() {
        return isRecurrentUser;
    }

    /**
     * setter for parking slot number.
     *
     * @param pRecurrentUser set recurrent user
     */
    public void setIsRecurrentUser(final boolean pRecurrentUser) {
        isRecurrentUser = pRecurrentUser;
    }
}
