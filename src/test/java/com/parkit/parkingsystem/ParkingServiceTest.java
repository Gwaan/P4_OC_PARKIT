package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    private static ParkingService parkingService;
    private static Ticket ticket;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;


    @BeforeEach
    private void setUpPerTest() {
        try {
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,
                    false);
            ticket = new Ticket();
            ticket.setInTime(
                    new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");
            //when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO,
                    ticketDAO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set up test mock objects");
        }
    }


    @Test
    @DisplayName("Given a recurrent user want to enter the parking, when "
            + "processIncomingVehicle is called, then the user is set to "
            + "reccurent and the ticket is saved in db")
    public void processIncomingVehicleTest() throws Exception {
        // ARRANGE
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(
                "ABCDEF");
        when(parkingSpotDAO.getNextAvailableSlot(
                any(ParkingType.class))).thenReturn(1);
        when(ticketDAO.getVehicleRegNumber("ABCDEF")).thenReturn(true);

        // ACT
        parkingService.processIncomingVehicle();

        //ASSERT
        verify(ticketDAO, times(1)).saveTicket(any(Ticket.class));
        verify(ticketDAO, times(1)).getVehicleRegNumber("ABCDEF");
        verify(parkingSpotDAO, times(1)).updateParking(any(ParkingSpot.class));
    }

    @Test
    @DisplayName("Given a recurrent user want to exit the parking, when "
            + "processExitingVehicle() is called, then the user is set to "
            + "recurrent, the fare is set and the parking spot is updated")
    public void processExitingVehicleTest() throws Exception {
        // ARRANGE
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(
                "ABCDEF");
        when(ticketDAO.getTicket(anyString(), anyString())).thenReturn(ticket);
        when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);
        when(ticketDAO.getVehicleRegNumber("ABCDEF")).thenReturn(true);

        // ACT
        parkingService.processExitingVehicle();

        // ASSERT
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(
                any(ParkingSpot.class));
        verify(ticketDAO, times(1)).updateTicket(any(Ticket.class));
        verify(ticketDAO, times(1)).getTicket(anyString(), anyString());

    }

}
