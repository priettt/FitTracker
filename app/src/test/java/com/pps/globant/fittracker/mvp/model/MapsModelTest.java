package com.pps.globant.fittracker.mvp.model;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;


public class MapsModelTest {
    @Mock
    private ArrayList<Location> locations;

    MapsModel model;

    @Before
    public void setUp() throws Exception {
        locations=new ArrayList<>();
        model = new MapsModel(locations);
    }

    @Test
    public void getLocations() {
        assertArrayEquals(locations.toArray() ,model.getLocations().toArray());
    }
}
