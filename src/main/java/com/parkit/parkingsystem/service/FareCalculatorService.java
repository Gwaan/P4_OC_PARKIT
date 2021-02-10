package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class FareCalculatorService {

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

    private double roundDouble(double d, int places) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}