package com.parkit.parkingsystem.constants;

/**
 * <p>Class grouping the constants needed to execute sql queries</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class DBConstants {

    /**
     * Get next parking spot available
     */
    public static final String GET_NEXT_PARKING_SPOT = "select min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?";
    /**
     * Update parking spot  with availability
     */
    public static final String UPDATE_PARKING_SPOT = "update parking set available = ? where PARKING_NUMBER = ?";
    /**
     * Save ticket in database
     */

    public static final String SAVE_TICKET = "insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME) values(?,?,?,?,?)";
    /**
     * Update ticket with price and out time
     */
    public static final String UPDATE_TICKET = "update ticket set PRICE=?, OUT_TIME=? where ID=?";
    /**
     * Get ticket with his parking number and vehicle registration number
     */
    public static final String GET_TICKET = "select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from ticket t,parking p where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME  limit 1";
    /**
     * Get vehicle registration number
     */
    public static final String GET_VEHICLE_REG_NUMBER = "SELECT VEHICLE_REG_NUMBER FROM ticket WHERE VEHICLE_REG_NUMBER=?";
    /**
     * Get vehicle registration number where out time is null
     */
    public static final String GET_VEHICLE_REG_NUMBER_CAR_ALREADY_PARKED = "select VEHICLE_REG_NUMBER from ticket where VEHICLE_REG_NUMBER=? and OUT_TIME is null";
    /**
     * Get ticket with his parking number and vehicle registration number where out time is null
     */
    public static final String GET_TICKET_CAR_ALREADY_PARKED = "select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from ticket t,parking p where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER=? and t.OUT_TIME is null order by t.IN_TIME  limit 1";

}
