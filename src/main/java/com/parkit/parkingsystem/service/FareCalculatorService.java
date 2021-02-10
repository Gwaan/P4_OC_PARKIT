package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * <p>Service class calculating fare for different type of vehicles</p>
 *
 * @author Gwen
 * @version 1.0
 */
public class FareCalculatorService {

    /**
     * <p>Method calculating fare for different type of vehicles</p>
     *
     * @param ticket ticket for which the fare must be calculated
     * @throws IllegalArgumentException if the out time is null or in advance compared to the in time
     */
    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        Date inMilliseconds = ticket.getInTime();
        Date outMilliseconds = ticket.getOutTime();
        long diff = outMilliseconds.getTime() - inMilliseconds.getTime();
        double duration = (double) (diff / (double) 1000) / 3600;

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                if (ticket.getIsRecurrentUser() && duration > 0.5) {
                    double price = duration * Fare.CAR_RATE_PER_HOUR;
                    double discountPrice = price - (price * 0.05);
                    ticket.setPrice(roundDouble(discountPrice, 2));
                    break;
                } else if (duration <= 0.5) {
                    ticket.setPrice(0.00);
                    break;
                } else {
                    ticket.setPrice(roundDouble(duration * Fare.CAR_RATE_PER_HOUR, 2));
                    break;
                }
            }
            case BIKE: {
                if (ticket.getIsRecurrentUser() && duration > 0.5) {
                    double price = duration * Fare.BIKE_RATE_PER_HOUR;
                    double discountPrice = price - (price * 0.05);
                    ticket.setPrice(roundDouble(discountPrice, 2));
                    break;
                } else if (duration <= 0.5) {
                    ticket.setPrice(0.00);
                    break;
                } else {
                    ticket.setPrice(roundDouble(duration * Fare.BIKE_RATE_PER_HOUR, 2));
                    break;
                }
            }
            default:
                throw new IllegalArgumentException("Unknown Parking Type");
        }
    }

    /**
     * <p>Method rounding to two places the fare</p>
     *
     * @param d      double to be rounded
     * @param places scale
     * @return a double rounded to two places
     */
    private double roundDouble(double d, int places) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}