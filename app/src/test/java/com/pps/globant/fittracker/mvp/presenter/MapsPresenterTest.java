package com.pps.globant.fittracker.mvp.presenter;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pps.globant.fittracker.MapsActivity;
import com.pps.globant.fittracker.mvp.model.MapsModel;
import com.pps.globant.fittracker.mvp.view.MapsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class MapsPresenterTest {

    public static final double ANY_VALUE = 1.0;
    public static final int NUMBER_OF_LOCATIONS = 2;
    MapsPresenter presenter;

    @Mock
    MapsModel model;
    @Mock
    MapsView view;
    @Mock
    MapsActivity activity;
    @Mock
    Location location1;
    @Mock
    Location location2;
    
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(view.getActivity()).thenReturn(activity);
        presenter = new MapsPresenter(model, view);
    }

    @Test
    public void onMapReadyWithoutLocations() throws Exception {
        Mockito.when(model.getLocations()).thenReturn(new ArrayList<Location>());

        GoogleMap map = null;
        PolylineOptions polylineOptions = new PolylineOptions();
        presenter.onMapReady(map, polylineOptions);
        Mockito.verify(model).getLocations();
        Mockito.verify(view).initMap(map, polylineOptions);
        Mockito.verifyNoMoreInteractions(model);
        Mockito.verifyNoMoreInteractions(view);

    }

    @Test
    public void onMapReady() throws Exception {
        Mockito.when(location1.getLatitude()).thenReturn(ANY_VALUE);
        Mockito.when(location2.getLatitude()).thenReturn(ANY_VALUE);
        Mockito.when(location1.getLongitude()).thenReturn(ANY_VALUE);
        Mockito.when(location2.getLongitude()).thenReturn(ANY_VALUE);
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(location1);
        locations.add(location2);
        Mockito.when(model.getLocations()).thenReturn(locations);
        GoogleMap map = null;
        PolylineOptions polylineOptions = new PolylineOptions();
        presenter.onMapReady(map, polylineOptions);
        Mockito.verify(model).getLocations();
        Mockito.verify(view).initMap(map, polylineOptions);
        Mockito.verifyNoMoreInteractions(model);
        Mockito.verifyNoMoreInteractions(view);
        assertEquals(NUMBER_OF_LOCATIONS, polylineOptions.getPoints().size());
    }
}