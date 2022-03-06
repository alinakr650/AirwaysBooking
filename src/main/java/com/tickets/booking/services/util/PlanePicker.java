package com.tickets.booking.services.util;

import com.tickets.booking.domain.planes.AIRBUS_A320;
import com.tickets.booking.domain.planes.BOEING_717;
import com.tickets.booking.domain.planes.BOEING_737_800;
import com.tickets.booking.domain.planes.Plane;

public class PlanePicker {

    public static Plane getPlaneByName(String planeName) {

        switch (planeName) {
            case "BOEING_737_800":
                return BOEING_737_800.getInstance();
            case "BOEING_717":
                return BOEING_717.getInstance();
            case "AIRBUS_A320":
                return AIRBUS_A320.getInstance();

            default:
                throw new IllegalArgumentException("Plane doesn't exist.");
        }
    }
}
