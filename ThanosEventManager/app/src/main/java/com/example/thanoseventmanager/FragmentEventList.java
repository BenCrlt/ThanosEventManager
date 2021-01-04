package com.example.thanoseventmanager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thanoseventmanager.listAdapter.EventListAdapter;
import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.Lieu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentEventList extends Fragment {
    private static final String TAG = "Hello";

    public FragmentEventList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Création d'un objet ListView correspondant à "listEvents" du layout activity_main_after_login.xml
        View v = inflater.inflate(R.layout.fragment_event_list, container, false);
        ListView listView = v.findViewById(R.id.listEvents);

        //Création liste d'évènements à partir de la méthode getListData
        List<Event> listeEvent = getListData();
        listView.setAdapter(new EventListAdapter(getContext(),listeEvent));

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        event1.setImage("fireworks");
        event3.setImage("gamepad");
        event4.setImage("beer");
        event5.setImage("fireworks");
        event6.setImage("coding");

        list.add(event1);
        list.add(event2);
        list.add(event3);
        list.add(event4);
        list.add(event5);
        list.add(event6);
        list.add(event7);
        list.add(event8);

        return list;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "on start " + getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "on resume " + getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "on pause " + getClass().getSimpleName());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "on stop " + getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "on destroy " + getClass().getSimpleName());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "on attach " + getClass().getSimpleName());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "on activity created " + getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "on destroy view " + getClass().getSimpleName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "on detach " + getClass().getSimpleName());
    }
}
