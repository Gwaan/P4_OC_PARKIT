package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;


/**
 * <p>DAO class for ticket table in Mysql database.</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class TicketDAO {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger("TicketDAO");

    /**
     * @see DataBaseConfig
     */
    private DataBaseConfig dataBaseConfig = new DataBaseConfig();
    /**
     * Constant one.
     */
    private static final byte ONE = 1;
    /**
     * Constant two.
     */
    private static final byte TWO = 2;
    /**
     * Constant three.
     */
    private static final byte THREE = 3;
    /**
     * Constant four.
     */
    private static final byte FOUR = 4;
    /**
     * Constant five.
     */
    private static final byte FIVE = 5;
    /**
     * Constant six.
     */
    private static final byte SIX = 6;

    /**
     * <p>Method returning the next available slot in the parking table.</p>
     *
     * @param ticket ticket to be saved
     * @return either true if the ticket was correctly saved in the database or false if save failed
     */
    public boolean saveTicket(final Ticket ticket) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.SAVE_TICKET);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            //ps.setInt(1,ticket.getId());
            ps.setInt(ONE, ticket.getParkingSpot().getId());
            ps.setString(TWO, ticket.getVehicleRegNumber());
            ps.setDouble(THREE, ticket.getPrice());
            ps.setTimestamp(FOUR, new Timestamp(ticket.getInTime().getTime()));
            ps.setTimestamp(FIVE, (ticket.getOutTime() == null) ? null : (new Timestamp(ticket.getOutTime().getTime())));
            return ps.execute();
        } catch (Exception ex) {
            LOGGER.error("Error fetching next available slot", ex);
            return false;
        } finally {
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
    }

    /**
     * <p>Method returning the next available slot in the parking table.</p>
     *
     * @param vehicleRegNumber vehicle registration number associated with ticket
     * @param sqlRequest       sql request to execute
     * @return ticket retrieved from database
     */
    public Ticket getTicket(final String vehicleRegNumber, final String sqlRequest) {
        Connection con = null;
        Ticket ticket = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(sqlRequest);
            //ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
            ps.setString(ONE, vehicleRegNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                ticket = new Ticket();
                ParkingSpot parkingSpot = new ParkingSpot(rs.getInt(ONE), ParkingType.valueOf(rs.getString(SIX)), false);
                ticket.setParkingSpot(parkingSpot);
                ticket.setId(rs.getInt(TWO));
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(rs.getDouble(THREE));
                ticket.setInTime(rs.getTimestamp(FOUR));
                ticket.setOutTime(rs.getTimestamp(FIVE));
            }
            dataBaseConfig.closeResultSet(rs);
        } catch (Exception ex) {
            LOGGER.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
        return ticket;
    }

    /**
     * <p>Method updating a ticket in database.</p>
     *
     * @param ticket ticket to be updated
     * @return either true if the ticket was correctly updated or false if the update failed
     */
    public boolean updateTicket(final Ticket ticket) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.UPDATE_TICKET);
            ps.setDouble(ONE, ticket.getPrice());
            ps.setTimestamp(TWO, new Timestamp(ticket.getOutTime().getTime()));
            ps.setInt(THREE, ticket.getId());
            ps.execute();
            return true;
        } catch (Exception ex) {
            LOGGER.error("Error saving ticket info", ex);
        } finally {
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
        return false;
    }

    /**
     * <p>Method returning if a vehicle registration number is existing in table ticket.</p>
     *
     * @param vehicleRegNumber vehicle registration number to be check
     * @return either true if the vehicle registration number exists in database or true if does not exist
     */
    public boolean getVehicleRegNumber(final String vehicleRegNumber) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_VEHICLE_REG_NUMBER);
            ps.setString(ONE, vehicleRegNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            LOGGER.error("Error while accessing database", ex);
        } finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
        return result;
    }

    /**
     * <p>Method returning if a vehicle is already parked in the parking.</p>
     *
     * @param vehicleRegNumber vehicle registration number to check
     * @return either true if the vehicle is already parked in the parking lot either false
     */
    public boolean getCarAlreadyInParking(final String vehicleRegNumber) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_VEHICLE_REG_NUMBER_CAR_ALREADY_PARKED);
            ps.setString(ONE, vehicleRegNumber);
            rs = ps.executeQuery();
            if (rs.next()) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            LOGGER.error("Error while accessing database", ex);
        } finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
        return result;
    }

    /**
     * setter for DB config.
     *
     * @param pDataBaseConfig data base configuration to be set
     */
    public void setDataBaseConfig(final DataBaseConfig pDataBaseConfig) {
        this.dataBaseConfig = pDataBaseConfig;
    }
}
