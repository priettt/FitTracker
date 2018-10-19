package com.pps.globant.fittracker.mvp.view;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pps.globant.fittracker.MapsActivity;


public class MapsView extends ActivityView<MapsActivity> {

    public static final int ZERO = 0;
    public static final int ZOOM_LEVEL = 15;
    private GoogleMap map;

    public MapsView(MapsActivity activity) {
        super(activity);
    }

    public void initMap(GoogleMap map, PolylineOptions line) {
        final MapsActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        this.map = map;
        this.map.addPolyline(line);
        this.map.moveCamera(CameraUpdateFactory.zoomTo(ZOOM_LEVEL));
        this.map.moveCamera(CameraUpdateFactory.newLatLng(line.getPoints().get(ZERO)));
    }
}
