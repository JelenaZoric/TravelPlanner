package com.example.travelplanner;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        Route route = (Route) this.getIntent().getSerializableExtra("route");

        LatLng destination = new LatLng(route.getLat(), route.getLon());
        CameraPosition position = new CameraPosition(destination, 15, 0, 0);

        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Context context = getApplicationContext();
                CharSequence msg = "Open new activity form!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, msg, duration);
                toast.show();
            }
        });
    }
}
