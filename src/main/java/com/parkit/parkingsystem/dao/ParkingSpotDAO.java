package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <p>DAO class for parking table in Mysql database.</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class ParkingSpotDAO {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger("ParkingSpotDAO");

    /**
     * @see DataBaseConfig
     */
    private DataBaseConfig dataBaseConfig = new DataBaseConfig();

    /**
     * <p>Method returning the next available slot in the parking table.</p>
     *
     * @param parkingType Parking Type of the vehicle
     * @return next available parking slot
     */
    public int getNextAvailableSlot(final ParkingType parkingType) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = -1;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
            ps.setString(1, parkingType.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception ex) {
            LOGGER.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
        return result;
    }

    /**
     * <p>Method updating the availability for that parking slot.</p>
     *
     * @param parkingSpot Parking Spot to get updated
     * @return either true if an update has been realized or false if nothing
     * has been returned
     */
    public boolean updateParking(final ParkingSpot parkingSpot) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
            ps.setBoolean(1, parkingSpot.isAvailable());
            ps.setInt(2, parkingSpot.getId());
            int updateRowCount = ps.executeUpdate();
            return (updateRowCount == 1);
        } catch (Exception ex) {
            LOGGER.error("Error updating parking info", ex);
            return false;
        } finally {
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
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
