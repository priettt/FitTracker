package com.pps.globant.fittracker.mvp.model;

import android.location.Location;

import java.util.ArrayList;

public class MapsModel {


    private final ArrayList<Location> locations;

    public MapsModel(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }
}
