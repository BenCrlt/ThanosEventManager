package com.example.thanoseventmanager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentMapView extends Fragment implements
        OnRequestPermissionsResultCallback,
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback {

    //Initialisation variables locales
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mapView;
    private GoogleMap gm;
    private Location myCurrentLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    //Récupération de l'utilisateur avec Firebase
    @Nullable
    private FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    private Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }


    public FragmentMapView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Utilisation de l'API Google Play Services pour la gestion de la localisation
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity());
        //Création d'une requête pour les demandes de localisation
        locationRequest = this.setLocationRequest();
        //Utilisation de la classe Location Callback pour récupérer les localisations
        locationCallback = this.setLocationCallback();
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

        this.startLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

        this.stopLocationUpdates();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        this.setMarkers();

        //Activation de la localisation avec permission requise
        this.enableMyLocation();
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
                Toast.makeText(this.requireActivity(), "Location Permission Granted", Toast.LENGTH_SHORT).show();
            }
            //PERMISSION_DENIED : Autorisation rejetée
            else {
                //Affiche un message d'échec
                Toast.makeText(this.requireActivity(), "Location Permission Denied", Toast.LENGTH_SHORT).show();
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

                //Recherche de la localisation de l'utilisateur
                this.getMyLocation();
            }
        }
        //PERMISSION_DENIED : Autorisation rejetée
        else {
            //Envoi d'une requête à l'utilisateur pour avoir la permission ou non d'accès à la localisation
            requestPermissions(new String[]{permission}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void getMyLocation() {

        //Check l'état de la permission d'accès à la localisation
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        int permissionState = ContextCompat.checkSelfPermission(this.requireActivity(), permission);

        //PERMISSION_GRANTED : Autorisation accordée
        if (permissionState == PackageManager.PERMISSION_GRANTED) {

            //Récupération de la dernière localisation de l'utilisateur
            Task<Location> getLocation = fusedLocationClient.getLastLocation();

            getLocation.addOnSuccessListener(this.requireActivity(),
                    location -> {
                        if (location != null) {
                            //Récupération de la localisation actuelle
                            myCurrentLocation = location;
                            //Zoom de la camera sur la position actuelle
                            this.setMyCamera(myCurrentLocation);
                        }
                    });

            getLocation.addOnFailureListener(this.requireActivity(),
                    error -> Toast.makeText(this.requireActivity(), "No Location Finded", Toast.LENGTH_SHORT).show());
        }
    }

    public LatLng getMyCoords(Location location) {
        //Récupération des coordonnées de la localisation
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        return new LatLng(lat, lng);
    }

    public void setMyCamera(@NonNull Location location) {
        //Récupère les cooordonnées de la position donnée
        LatLng myCurrentCoords = getMyCoords(location);
        //Zoom la caméra sur la position
        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(myCurrentCoords, 15));
    }

    private void setMarkers() {
        //Ajoute les marqueurs sur la carte
        gm.addMarker(new MarkerOptions()
                .position(new LatLng(47.528868, -0.568809))
                .title("Cantenay")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );
        gm.addMarker(new MarkerOptions().position(new LatLng(47.483425, -0.570988)).title("Chez Toinou le rayon X"));
        gm.addMarker(new MarkerOptions().position(new LatLng(49.418080, -1.627571)).title("BestPlace"));
        gm.addMarker(new MarkerOptions().position(new LatLng(46.749495, -1.739786)).title("SchmoutLand"));
        gm.addMarker(new MarkerOptions().position(new LatLng(47.085868, 2.395971)).title("Vilkipu"));
        gm.addMarker(new MarkerOptions().position(new LatLng(48.322190, 0.150082)).title("Troudfiak"));
    }

    public LocationRequest setLocationRequest() {
        //Création d'une nouvelle requête
        LocationRequest locationRequest = LocationRequest.create();
        //Détermine l'intervalle (en ms) entre chaque MAJ de la localisation
        locationRequest.setInterval(10000);
        //Détermine l'intervalle le plus rapide
        locationRequest.setFastestInterval(5000);
        //Détermine la priorité de la requête ("high accuracy" pour une localisation précise)
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        return locationRequest;
    }

    private LocationCallback setLocationCallback() {
        return new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
            }
        };
    }

    private void startLocationUpdates() {
        //Check l'état de la permission d'accès à la localisation
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        int permissionState = ContextCompat.checkSelfPermission(this.requireActivity(), permission);

        //PERMISSION_GRANTED : Autorisation accordée
        if (permissionState == PackageManager.PERMISSION_GRANTED) {
            //Démarre les MAJ de la localisation
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    private void stopLocationUpdates() {
        //Arrete les MAJ de la localisation
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}