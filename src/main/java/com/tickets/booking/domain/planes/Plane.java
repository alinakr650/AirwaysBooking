package com.tickets.booking.domain.planes;

import java.util.ArrayList;

public abstract class Plane {
    public abstract int getSeatsCount();

    public abstract ArrayList<String> getAllSeatsNames();
}
