package com.pps.globant.fittracker;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pps.globant.fittracker.mvp.model.MapsModel;
import com.pps.globant.fittracker.mvp.presenter.MapsPresenter;
import com.pps.globant.fittracker.mvp.view.MapsView;
import com.pps.globant.fittracker.utils.Constants;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    MapsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        ArrayList<Location> locations=intent.getParcelableArrayListExtra(Constants.PATH_VECTOR);
        presenter=new MapsPresenter (new MapsModel(locations),new MapsView(this));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        presenter.onMapReady(googleMap,new PolylineOptions().width(Constants.POLYLINE_WIDTH).color(Color.RED));
    }
}
