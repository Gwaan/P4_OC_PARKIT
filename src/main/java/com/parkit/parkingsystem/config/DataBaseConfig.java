package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * <p>Class allowing initialization and closure of a database connection
 * and closure of resources PreparedStatement and ResultSet</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class DataBaseConfig {

    private static final Logger logger = LogManager.getLogger("DataBaseConfig");
    private ReadProperties rP = new ReadProperties();

    /**
     * <p>Method returning a Mysql connection</p>
     *
     * @return connection to database
     * @throws ClassNotFoundException if no definition for the class could be found
     * @throws SQLException           if a database access error occurs
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        logger.info("Create DB connection");
        rP.getDbConnectionInfo();
        Class.forName(rP.getDriver());
        return DriverManager.getConnection(rP.getUrl(), rP.getUser(), rP.getPassword());
    }

    /**
     * <p>Method closing a connection to database</p>
     *
     * @param con Connection to close
     */
    public void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
                logger.info("Closing DB connection");
            } catch (SQLException e) {
                logger.error("Error while closing connection", e);
            }
        }
    }

    /**
     * <p>Method closing a PreparedStatement</p>
     *
     * @param ps PreparedStatement to close
     */
    public void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
                logger.info("Closing Prepared Statement");
            } catch (SQLException e) {
                logger.error("Error while closing prepared statement", e);
            }
        }
    }

    /**
     * <p>Method closing a ResultSet</p>
     *
     * @param rs ResultSet to close
     */
    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                logger.info("Closing Result Set");
            } catch (SQLException e) {
                logger.error("Error while closing result set", e);
            }
        }
    }
}
