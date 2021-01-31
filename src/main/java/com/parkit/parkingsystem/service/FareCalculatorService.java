package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.util.*;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        Date inMilliseconds = ticket.getInTime();
        Date outMilliseconds = ticket.getOutTime();
        long diff = outMilliseconds.getTime() - inMilliseconds.getTime();
        double duration = (double) (diff / 1000) / 3600;

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                if (ticket.getIsRecurrentUser()) {
                    double price = duration * Fare.CAR_RATE_PER_HOUR;
                    double discountPrice = price - (price * 0.05);
                    ticket.setPrice(discountPrice);
                    break;
                } else {
                    ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                    break;
                }
            }
            case BIKE: {
                if (ticket.getIsRecurrentUser()) {
                    double price = duration * Fare.BIKE_RATE_PER_HOUR;
                    double discountPrice = price - (price * 0.05);
                    ticket.setPrice(discountPrice);
                    break;
                } else {
                    ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                    break;
                }
            }
            default:
                throw new IllegalArgumentException("Unknown Parking Type");
        }
    }
}