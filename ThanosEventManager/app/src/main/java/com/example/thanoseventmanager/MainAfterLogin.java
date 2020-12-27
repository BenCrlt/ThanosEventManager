package com.example.thanoseventmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thanoseventmanager.listAdapter.EventListAdapter;
import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.Lieu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainAfterLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_after_login);

        //Création d'un objet ListView correspondant à "listEvents" du layout activity_main_after_login.xml
        ListView listView = (ListView)findViewById(R.id.listEvents);

        //Création liste d'évènements à partir de la méthode getListData
        List<Event> listeEvent = getListData();
        listView.setAdapter(new EventListAdapter(this,listeEvent));

    }

    private  List<Event> getListData() {
        List<Event> list = new ArrayList<Event>();

        Groupe team_andor = new Groupe();
        team_andor.setNom("Team Andor");

        Lieu chez_toinou = new Lieu();
        chez_toinou.setNom("Chez toinou");

        Event event1 = new Event("1","Pyjama Party", new Date(), team_andor, chez_toinou);
        Event event2 = new Event("2","Andor", new Date(), team_andor, chez_toinou);
        Event event3 = new Event("3","Super Smash Bros", new Date(), team_andor, chez_toinou);
        Event event4 = new Event("4","Tonton Foch", new Date(), team_andor, chez_toinou);
        Event event5 = new Event("5","Soirée chez Arnaud", new Date(), team_andor, chez_toinou);
        Event event6 = new Event("6","Développement Android", new Date(), team_andor, chez_toinou);
        Event event7 = new Event("7","Brainstorming intensif", new Date(), team_andor, chez_toinou);
        Event event8 = new Event("8","Entraînement salto arrière en slip", new Date(), team_andor, chez_toinou);

        list.add(event1);
        list.add(event2);
        list.add(event3);
        list.add(event4);
        list.add(event5);
        list.add(event6);
        list.add(event7);

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        //R.menu.menu est l'id de notre menu
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void onClickMap(View v){
        //Launch the map view activity
        Intent intent = new Intent(this, MapViewActivity.class);
        startActivity(intent);
    }
}