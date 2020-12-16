package com.example.thanoseventmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Se connecter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void onClickMap(View v){
        //Launch the map view activity
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }

    // Bouton Se connecter
    public void onClick_Login(View v)
    {
        Log.i(TAG, "click on Se Connecter" + getLocalClassName()) ;

        /* Gestion appui sur le bouton*/
        Intent intent_Login = new Intent(this, MainAfterLogin.class) ;
        startActivity(intent_Login) ;
    }

    // Phone Text




    // Bouton Sign In


}