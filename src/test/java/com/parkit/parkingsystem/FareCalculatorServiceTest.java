package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;


import java.util.Date;

public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;


    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Nested
    @Tag("NormalRates")
    class NormalRates {

        @Test
        @DisplayName("For a car parked an hour, the fare should be equal to "
                + "the car rate per hour")
        public void calculateFareCar() {
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,
                    false);

            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);
            fareCalculatorService.calculateFare(ticket);
            assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR);
        }

        @Test
        @DisplayName("For a bike parked an hour, the fare should be equal to "
                + "the bike rate per hour")
        public void calculateFareBike() {
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,
                    false);

            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);
            fareCalculatorService.calculateFare(ticket);
            assertEquals(Fare.BIKE_RATE_PER_HOUR, ticket.getPrice());
        }

        @Test
        @DisplayName("For a bike parked less than hour hour, the fare should "
                + "be equal to duration multiplied by bike rate per hour")
        public void calculateFareBikeWithLessThanOneHourParkingTime() {
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() - (45 * 60
                    * 1000));//45 minutes parking time should give 3/4th
            // parking
            // fare
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,
                    false);

            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);
            fareCalculatorService.calculateFare(ticket);
            assertEquals((0.75 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
        }

        @Test
        @DisplayName("For a car parked less than hour, the fare should "
                + "be equal to duration multiplied by car rate per hour")
        public void calculateFareCarWithLessThanOneHourParkingTime() {
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() - (45 * 60
                    * 1000));//45 minutes parking time should give 3/4th
            // parking
            // fare
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,
                    false);

            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);
            fareCalculatorService.calculateFare(ticket);
            assertEquals(
                    (Math.round(0.75 * Fare.CAR_RATE_PER_HOUR * 100) / 100.00),
                    ticket.getPrice());
        }

        @Test
        @DisplayName("For a car parked 24 hours, the fare should be equal to "
                + "24 hours multiplied by car fare rate per hour")
        public void calculateFareCarWithMoreThanADayParkingTime() {
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,
                    false);

            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);
            fareCalculatorService.calculateFare(ticket);
            assertEquals((24 * Fare.CAR_RATE_PER_HOUR), ticket.getPrice());
        }

        @Test
        @DisplayName("For a bike parked 24 hours, the fare should be equal to "
                + "24 hours multiplied by bike fare rate per hour")
        public void calculateFareBikeWithMoreThanADayParkingTime() {
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,
                    false);

            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);
            fareCalculatorService.calculateFare(ticket);
            assertEquals((24 * Fare.BIKE_RATE_PER_HOUR), ticket.getPrice());
        }
    }

    @Nested
    @Tag("Exceptions")
    class Exceptions {

        @Test
        @DisplayName("For a unknown parking type, calculateFare() should "
                + "throws a NullPointException")
        public void calculateFareUnknownType() {
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, null, false);

            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);
            assertThrows(NullPointerException.class,
                    () -> fareCalculatorService.calculateFare(ticket));
        }

        @Test
        @DisplayName("If parking exit time is prior to entry time, "
                + "calculateFare() should throws an IllegalArgumentException")
        public void calculateFareBikeWithFutureInTime() {
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,
                    false);

            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);
            assertThrows(IllegalArgumentException.class,
                    () -> fareCalculatorService.calculateFare(ticket));
        }
    }


    @Nested
    @Tag("RecurrentUser")
    class RecurrentUser {

        @Test
        @DisplayName(
                "For a recurrent user's car parked for 24h, the fare should be"
                        + " equals to 24 hours multiplied by car fare rate "
                        + "per hour " + "minus 5%")
        public void calculateFareCarWith5PercentDiscountForARecurrentUser() {
            // ARRANGE
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));
            // fare
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,
                    false);
            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);
            ticket.setIsRecurrentUser(true);

            // ACT
            fareCalculatorService.calculateFare(ticket);

            // ASSERT
            assertEquals(34.2, ticket.getPrice());
        }

        @Test
        @DisplayName(
                "For a recurrent user's bike parked for 24h, the fare should be"
                        + " equals to 24 hours multiplied by bike fare rate "
                        + "per hour " + "minus 5%")
        public void calculateFareBikeWith5PercentDiscountForARecurrentUser() {
            // ARRANGE
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60
                    * 1000));//45 minutes parking time should give 3/4th parking
            // fare
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,
                    false);
            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);
            ticket.setIsRecurrentUser(true);

            // ACT
            fareCalculatorService.calculateFare(ticket);

            // ASSERT
            assertEquals(22.8, ticket.getPrice());
        }

    }

    @Nested
    @Tag("FreeParking")
    class FreeParking {

        @Test
        @DisplayName("For a recurrent user's car parked with less than 30 min, "
                + "the fare should be free")
        public void calculateFareCarWithLessThanThirtyMinutesParkingTime() {
            // ARRANGE
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() - (25 * 60 * 1000));
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,
                    false);
            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);

            // ACT
            fareCalculatorService.calculateFare(ticket);

            // ASSERT
            assertEquals(0, ticket.getPrice());

        }

        @Test
        @DisplayName(
                "For a recurrent user's bike parked with less than 30 min, "
                        + "the fare should be free")
        public void calculateFareBikeWithLessThanThirtyMinutesParkingTime() {
            // ARRANGE
            Date inTime = new Date();
            inTime.setTime(System.currentTimeMillis() - (25 * 60 * 1000));
            Date outTime = new Date();
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,
                    false);
            ticket.setInTime(inTime);
            ticket.setOutTime(outTime);
            ticket.setParkingSpot(parkingSpot);

            // ACT
            fareCalculatorService.calculateFare(ticket);

            // ASSERT
            assertEquals(0, ticket.getPrice());
        }
    }


}