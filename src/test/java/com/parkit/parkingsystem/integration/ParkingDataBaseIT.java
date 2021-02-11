package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig =
            new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.setDataBaseConfig(dataBaseTestConfig);
        ticketDAO = new TicketDAO();
        ticketDAO.setDataBaseConfig(dataBaseTestConfig);
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(
                "ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown() {

    }

    @Test
    @Tag("ParkingACar")
    @DisplayName("At vehicle entry, when processIncomingVehicle() is called, "
            + "db table ticket should be populated with a new entry and "
            + "parking table should be updated")
    public void testParkingACar() {
        // GIVEN
        ParkingService parkingService = new ParkingService(inputReaderUtil,
                parkingSpotDAO, ticketDAO);

        // WHEN
        parkingService.processIncomingVehicle();
        Ticket expectedTicket = ticketDAO.getTicket("ABCDEF",
                DBConstants.GET_TICKET);
        int expectedParkingSlot = parkingSpotDAO.getNextAvailableSlot(
                ParkingType.CAR);

        // THEN
        assertNotNull(expectedTicket);
        assertEquals(expectedParkingSlot, 2);
    }

    @Test
    @Tag("ExitParking")
    @DisplayName(
            "At vehicle exit, when processExitingVehicle() is called, then "
                    + "price and out_time column should be updated in db ")

    public void testParkingLotExit() {
        // GIVEN
        testParkingACar();
        ParkingService parkingService = new ParkingService(inputReaderUtil,
                parkingSpotDAO, ticketDAO);

        // WHEN
        parkingService.processExitingVehicle();
        Ticket ticket = ticketDAO.getTicket("ABCDEF", DBConstants.GET_TICKET);
        ticket.setOutTime(new

                Date(ticket.getInTime().

                getTime() + 2000));

        // THEN
        assertEquals(0, ticket.getPrice());

        assertNotNull(ticket.getOutTime());
    }

}
