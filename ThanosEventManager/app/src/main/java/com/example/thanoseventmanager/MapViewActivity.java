package com.example.thanoseventmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "Map Filter";
    private MapView mapView;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        Log.i(TAG, "on create " + getLocalClassName()) ;

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "on save instance state " + getLocalClassName()) ;

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();

        Log.i(TAG, "on start " + getLocalClassName()) ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();

        Log.i(TAG, "on stop " + getLocalClassName()) ;
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();

        Log.i(TAG, "on destroy " + getLocalClassName()) ;
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();

        Log.i(TAG, "on pause " + getLocalClassName()) ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

        Log.i(TAG, "on resume " + getLocalClassName()) ;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();

        Log.i(TAG, "on low memory " + getLocalClassName()) ;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.i(TAG, "on map ready " + getLocalClassName()) ;

        map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));
    }

    public void onClickMainActivity(View v){
        //Launch the main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}