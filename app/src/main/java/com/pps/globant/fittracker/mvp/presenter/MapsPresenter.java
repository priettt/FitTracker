package com.pps.globant.fittracker.mvp.presenter;

import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pps.globant.fittracker.mvp.model.MapsModel;
import com.pps.globant.fittracker.mvp.view.MapsView;

public class MapsPresenter {
    private final MapsModel model;
    private final MapsView view;

    public MapsPresenter(MapsModel model, MapsView view) {
        this.model=model;
        this.view=view;
    }

    public void onMapReady(GoogleMap googleMap, PolylineOptions line) {
        for (Location location: model.getLocations()){
            line.add(new LatLng(location.getLatitude(),location.getLongitude()));
        }
        view.initMap(googleMap,line);
    }
}
