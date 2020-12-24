package com.example.thanoseventmanager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.thanoseventmanager.geolocalisation.MyLocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMapView extends Fragment implements
        OnRequestPermissionsResultCallback,
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback {

    //Initialisation variables locales
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MyLocationListener  myListener = new MyLocationListener();
    private MapView             mapView;
    private GoogleMap           gm;
    private LocationManager     locationManager;
    private Location            myCurrentLocation;
    private String              provider;

    public FragmentMapView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Création d'un service pour accéder à la géolocalisation
        locationManager = (LocationManager)this.requireActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria critere = new Criteria();
        critere.setAccuracy(Criteria.ACCURACY_FINE);
        critere.setBearingRequired(true);
        critere.setCostAllowed(false);

        provider = locationManager.getBestProvider(critere, false);
        myCurrentLocation = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Récupération de la Map View dans le fragment
        View v = inflater.inflate(R.layout.fragment_map_view, container, false);
        mapView = v.findViewById(R.id.mapView);

        //Création de l'instance Map View
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
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

        //Récupération de la carte Google Maps
        gm = googleMap;

        //Paramétrages de la carte
        gm.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gm.getUiSettings().setZoomControlsEnabled(true);
        gm.getUiSettings().setMapToolbarEnabled(false);

        //Ajout de marqueurs sur la carte
        gm.addMarker(new MarkerOptions().position(new LatLng(47.528868, -0.568809)).title("Cantenay"));
        gm.addMarker(new MarkerOptions().position(new LatLng(47.483425, -0.570988)).title("Chez Toinou le rayon X"));
        gm.addMarker(new MarkerOptions().position(new LatLng(49.418080, -1.627571)).title("BestPlace"));
        gm.addMarker(new MarkerOptions().position(new LatLng(46.749495, -1.739786)).title("SchmoutLand"));
        gm.addMarker(new MarkerOptions().position(new LatLng(47.085868, 2.395971)).title("Vilkipu"));

        //Activation de la localisation avec permission requise
        this.enableMyLocation();

        try {
            this.getMyLocation();
        } catch (SecurityException e) {
            Log.e("SecurityException", e.getMessage());
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Retourne faux pour avoir le comportement par défaut : zoomer la caméra sur la position
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        //Création du message à afficher
        double Lat = location.getLatitude();
        double Lng = location.getLongitude();
        String message = "Current location :" + "\nLat = " + Lat + "\nLng = " + Lng;

        //Affichage du message si on clique sur notre position
        Toast.makeText(this.requireActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    //Méthode appelée automatiquement après "requestPermissions()" dans la fonction "enableMyLocation()"
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Check si la permission concerne la bonne requête
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            //PERMISSION_GRANTED : Autorisation accordée
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Active la localisation
                this.enableMyLocation();
                //Affiche un message de succès
                Toast.makeText(this.requireActivity(), "Localisation Permission Granted", Toast.LENGTH_SHORT).show();
                //Cherche la localisation actuelle
                //this.getMyLocation();
            }
            //PERMISSION_DENIED : Autorisation rejetée
            else {
                //Affiche un message d'échec
                Toast.makeText(this.requireActivity(), "Localisation Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void enableMyLocation() {

        //Check l'état de la permission d'accès à la localisation
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        int permissionState = ContextCompat.checkSelfPermission(this.requireActivity(), permission);

        //PERMISSION_GRANTED : Autorisation accordée
        if (permissionState == PackageManager.PERMISSION_GRANTED) {
            if (gm != null) {
                //Paramétrages supplémentaires de la carte Google Maps
                gm.setMyLocationEnabled(true);
                gm.setOnMyLocationButtonClickListener(this);
                gm.setOnMyLocationClickListener(this);
            }
        }
        //PERMISSION_DENIED : Autorisation rejetée
        else {
            //Envoi d'une requête à l'utilisateur pour avoir la permission ou non d'accès à la localisation
            requestPermissions(new String[]{permission}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void getMyLocation(){

        //Check l'état de la permission d'accès à la localisation
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        int permissionState = ContextCompat.checkSelfPermission(this.requireActivity(), permission);

        //PERMISSION_GRANTED : Autorisation accordée
        if (permissionState == PackageManager.PERMISSION_GRANTED) {

            if (locationManager != null && provider != null) {

                myCurrentLocation = locationManager.getLastKnownLocation(provider);

                if (myCurrentLocation != null) {
                    // add location to the location listener for location changes
                    myListener.onLocationChanged(myCurrentLocation);
                } else {
                    Toast.makeText(this.requireActivity(), "Localisation Needed", Toast.LENGTH_SHORT).show();
                    /*
                    //Intention d'afficher les paramètres du télophone pour activer les données de localisation
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                     */
                }
                // location updates: at least 1 meter and 500 milli seconds change
                locationManager.requestLocationUpdates(provider, 500, 1, myListener);
            }
        }
    }
}