package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;


/**
 * <p>Service class calculating fare for different type of vehicles.</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class FareCalculatorService {

    /**
     * Constant thousand.
     */
    private static final short THOUSAND = 1000;
    /**
     * Constant three thousand six hundred.
     */
    private static final short THREE_THOUSAND_SIX_HUNDRED = 3600;
    /**
     * Constant zero point five.
     */
    private static final double ZERO_POINT_FIVE = 0.5;
    /**
     * Constant zero point zero five.
     */
    private static final double ZERO_POINT_ZERO_FIVE = 0.05;

    /**
     * <p>Method calculating fare for different type of vehicles.</p>
     *
     * @param ticket ticket for which the fare must be calculated
     * @throws IllegalArgumentException if the out time is null or in advance compared to the in time
     */
    public void calculateFare(final Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        Date inMilliseconds = ticket.getInTime();
        Date outMilliseconds = ticket.getOutTime();
        long diff = outMilliseconds.getTime() - inMilliseconds.getTime();
        double duration = (double) (diff / (double) THOUSAND) / THREE_THOUSAND_SIX_HUNDRED;

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR:
                if (ticket.getIsRecurrentUser() && duration > ZERO_POINT_FIVE) {
                    double price = duration * Fare.CAR_RATE_PER_HOUR;
                    double discountPrice = price - (price * ZERO_POINT_ZERO_FIVE);
                    ticket.setPrice(roundDouble(discountPrice, 2));
                    break;
                } else if (duration <= ZERO_POINT_FIVE) {
                    ticket.setPrice(0.00);
                    break;
                } else {
                    ticket.setPrice(roundDouble(duration * Fare.CAR_RATE_PER_HOUR, 2));
                    break;
                }

            case BIKE:
                if (ticket.getIsRecurrentUser() && duration > ZERO_POINT_FIVE) {
                    double price = duration * Fare.BIKE_RATE_PER_HOUR;
                    double discountPrice = price - (price * ZERO_POINT_ZERO_FIVE);
                    ticket.setPrice(roundDouble(discountPrice, 2));
                    break;
                } else if (duration <= ZERO_POINT_FIVE) {
                    ticket.setPrice(0.00);
                    break;
                } else {
                    ticket.setPrice(roundDouble(duration * Fare.BIKE_RATE_PER_HOUR, 2));
                    break;
                }

            default:
                throw new IllegalArgumentException("Unknown Parking Type");
        }
    }

    /**
     * <p>Method rounding to two places the fare.</p>
     *
     * @param d      double to be rounded
     * @param places scale
     * @return a double rounded to two places
     */
    private double roundDouble(final double d, final int places) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
