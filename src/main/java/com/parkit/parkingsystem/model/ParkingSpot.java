package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

/**
 * <p>Class representing a parking spot.</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class ParkingSpot {

    /**
     * Parking slot.
     */
    private int number;
    /**
     * Type of the parking spot.
     */
    private ParkingType parkingType;
    /**
     * Availability of the parking spot.
     */
    private boolean isAvailable;

    /**
     * @param pNumber      number of the parking slot
     * @param pParkingType type of vehicle
     * @param pIsAvailable availability of the spot
     */
    public ParkingSpot(final int pNumber, final ParkingType pParkingType, final boolean pIsAvailable) {
        this.number = pNumber;
        this.parkingType = pParkingType;
        this.isAvailable = pIsAvailable;
    }

    /**
     * getter for parking slot number.
     *
     * @return parking slot number
     */
    public int getId() {
        return number;
    }

    /**
     * setter for parking slot number.
     *
     * @param pNumber parking number to set
     */
    public void setId(final int pNumber) {
        this.number = pNumber;
    }

    /**
     * getter for DB driver.
     *
     * @return parking type
     */
    public ParkingType getParkingType() {
        return parkingType;
    }

    /**
     * setter for parking type.
     *
     * @param pParkingType vehicle type to be set
     */
    public void setParkingType(final ParkingType pParkingType) {
        this.parkingType = pParkingType;
    }

    /**
     * getter for parking spot availability.
     *
     * @return either true if the parking sport is available or false if it's not
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * setter for parking spot availability.
     *
     * @param pAvailable availability of the parking sport
     */
    public void setAvailable(final boolean pAvailable) {
        isAvailable = pAvailable;
    }

    /**
     * Check equality of Parking Spot by ID.
     *
     * @param o Parking spot
     * @return ID
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParkingSpot that = (ParkingSpot) o;
        return number == that.number;
    }

    /**
     * Define Hash with ID.
     *
     * @return ID
     */
    @Override
    public int hashCode() {
        return number;
    }
}
