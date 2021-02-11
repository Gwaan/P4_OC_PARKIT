package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


/**
 * <p>Class allowing initialization and closure of a database connection
 * and closure of resources PreparedStatement and ResultSet.</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class DataBaseConfig {

    /**
     * @see Logger
     */
    private static final Logger LOGGER = LogManager.getLogger("DataBaseConfig");
    /**
     * @see ReadProperties
     */
    private ReadProperties rP = new ReadProperties();

    /**
     * <p>Method returning a Mysql connection.</p>
     *
     * @return connection to database
     * @throws ClassNotFoundException f no definition for the class could be found
     * @throws SQLException           if a database access error occurs
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        LOGGER.info("Create DB connection");
        rP.getDbConnectionInfo();
        Class.forName(rP.getDriver());
        return DriverManager.getConnection(rP.getUrl(), rP.getUser(), rP.getPassword());
    }

    /**
     * <p>Method closing a connection to database.</p>
     *
     * @param con Connection to close
     */
    public void closeConnection(final Connection con) {
        if (con != null) {
            try {
                con.close();
                LOGGER.info("Closing DB connection");
            } catch (SQLException e) {
                LOGGER.error("Error while closing connection", e);
            }
        }
    }

    /**
     * <p>Method closing a PreparedStatement.</p>
     *
     * @param ps PreparedStatement to close
     */
    public void closePreparedStatement(final PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
                LOGGER.info("Closing Prepared Statement");
            } catch (SQLException e) {
                LOGGER.error("Error while closing prepared statement", e);
            }
        }
    }

    /**
     * <p>Method closing a ResultSet.</p>
     *
     * @param rs ResultSet to close
     */
    public void closeResultSet(final ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                LOGGER.info("Closing Result Set");
            } catch (SQLException e) {
                LOGGER.error("Error while closing result set", e);
            }
        }
    }
}
