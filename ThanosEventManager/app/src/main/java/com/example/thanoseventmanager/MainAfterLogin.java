package com.example.thanoseventmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        ListView listEvents = (ListView)findViewById(R.id.listEvents);

        //Pour tester ListView : je créer quelques events
        Event event1 = new Event("1","event1", new Date(), new Groupe(), new Lieu());
        Event event2 = new Event("2","event2", new Date(), new Groupe(), new Lieu());
        Event event3 = new Event("3","event3", new Date(), new Groupe(), new Lieu());

        //On les ajoute à une liste d'évènements
        List<Event> events = new ArrayList<Event>();

        events.add(event1);
        events.add(event2);
        events.add(event3);

        // android.R.layout.simple_list_item_1 is a constant predefined layout of Android.
        // used to create a ListView with simple ListItem (Only one TextView).

        ArrayAdapter<Event> arrayAdapter
                = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1 , events);

        listEvents.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        //R.menu.menu est l'id de notre menu
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

}