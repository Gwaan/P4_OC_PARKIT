package com.parkit.parkingsystem;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataBaseConfigTest {

    private DataBaseConfig dataBaseConfig;

    @BeforeEach
    public void setUpPerTest() {
        dataBaseConfig = new DataBaseConfig();
    }

    @DisplayName("Instantiating connection, expected is not null")
    @Test
    public void getConnectionTest() throws SQLException,
            ClassNotFoundException {
        // ARRANGE
        Connection connection;

        // ACT
        connection = dataBaseConfig.getConnection();

        // ASSERT
        assertNotNull(connection);
    }

    @DisplayName("Closing connection, expected is true")
    @Test
    public void closeConnectionTest() throws SQLException,
            ClassNotFoundException {
        // ARRANGE
        Connection connection;

        // ACT
        connection = dataBaseConfig.getConnection();
        dataBaseConfig.closeConnection(connection);

        // ASSERT
        assertTrue(connection.isClosed());
    }

    @DisplayName("Closing PreparedStatement, expected is true")
    @Test
    public void closePreparedStatementTest() throws SQLException,
            ClassNotFoundException {
        // ARRANGE
        Connection connection = dataBaseConfig.getConnection();
        PreparedStatement ps = null;

        // ACT
        ps = connection.prepareStatement(
                DBConstants.GET_TICKET_CAR_ALREADY_PARKED);
        dataBaseConfig.closePreparedStatement(ps);
        dataBaseConfig.closeConnection(connection);

        // ASSERT
        assertTrue(ps.isClosed());
    }

    @DisplayName("Closing ResultSet, expected is true")
    @Test
    public void closeResultSetTest() throws SQLException,
            ClassNotFoundException {
        // ARRANGE
        Connection connection = dataBaseConfig.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        // ACT
        ps = connection.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
        ps.setString(1, "CAR");
        rs = ps.executeQuery();
        dataBaseConfig.closeResultSet(rs);
        dataBaseConfig.closePreparedStatement(ps);
        dataBaseConfig.closeConnection(connection);

        // ASSERT
        assertTrue(rs.isClosed());
    }
}
