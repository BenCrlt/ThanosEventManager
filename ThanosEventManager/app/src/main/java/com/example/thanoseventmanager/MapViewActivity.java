package com.example.thanoseventmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.MapView;

public class MapViewActivity extends AppCompatActivity {

    private static final String TAG = "Map Filter";
    private MapView mapView;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    FragmentManager fm = getSupportFragmentManager();
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        Log.i(TAG, "on create " + getLocalClassName()) ;

        navController = Navigation.findNavController(this, R.id.fragment_nav_host);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "on start " + getLocalClassName()) ;
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "on stop " + getLocalClassName()) ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "on destroy " + getLocalClassName()) ;
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "on pause " + getLocalClassName()) ;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "on resume " + getLocalClassName()) ;
    }

    public void onClickMainActivity(View v){
        //Launch the main activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void onClickFragmentMapView(View v)
    {
        navController.popBackStack(navController.getGraph().getStartDestination(), false);
    }
    public void onClickFragmentUserList(View v)
    {
        navController.popBackStack(navController.getGraph().getStartDestination(), false);
        navController.navigate(R.id.fragmentUserList);
    }
}