package com.example.thanoseventmanager;

import android.Manifest;
import android.content.RestrictionsManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMapView extends Fragment implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback {

    private static final String TAG = "Hello";

    private MapView mapView;
    GoogleMap gm;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public FragmentMapView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(TAG, "on create view " + getClass().getSimpleName());

        View v = inflater.inflate(R.layout.fragment_map_view, container, false);

        mapView = (MapView) v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        Log.i(TAG, "on start " + getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        Log.i(TAG, "on resume " + getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        Log.i(TAG, "on pause " + getClass().getSimpleName());
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        Log.i(TAG, "on stop " + getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        Log.i(TAG, "on destroy " + getClass().getSimpleName());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gm = googleMap;
        LatLng cantenay = new LatLng(47.528868, -0.568809);

        gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        gm.getUiSettings().setZoomControlsEnabled(true);

        gm.addMarker(new MarkerOptions().position(cantenay).title("Cantenay"));
        gm.addMarker(new MarkerOptions().position(new LatLng(47.483425, -0.570988)).title("Chez Toinou le rayon X"));
        gm.addMarker(new MarkerOptions().position(new LatLng(49.418080, -1.627571)).title("BestPlace"));
        gm.addMarker(new MarkerOptions().position(new LatLng( 46.749495, -1.739786)).title("SchmoutLand"));
        gm.addMarker(new MarkerOptions().position(new LatLng( 47.085868, 2.395971)).title("Vilkipu"));
        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(cantenay, 10));

        this.enableMyLocation();

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this.getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this.getActivity(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        // Checking whether user granted the permission or not.
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Enable the my location layer if the permission has been granted.
            this.enableMyLocation();
            Toast.makeText(this.getActivity(), "Camera Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            Toast.makeText(this.getActivity(), "Camera Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    public void enableMyLocation() {

        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        int permissionState = ContextCompat.checkSelfPermission(this.getActivity(), permission);

        //PERMISSION_GRANTED : Autorisation accordée
        if (permissionState == PackageManager.PERMISSION_GRANTED) {
            if (gm != null) {
                gm.setMyLocationEnabled(true);
                gm.setOnMyLocationButtonClickListener(this);
                gm.setOnMyLocationClickListener(this);
            }
            //PERMISSION_DENIED : Autorisation rejetée
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{permission}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
}