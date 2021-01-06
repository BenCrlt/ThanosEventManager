package com.example.thanoseventmanager;

import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.Lieu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestListeEvents {

    public List<Event> getListData() {

        List<Event> list = new ArrayList<>();

        Groupe team_andor = new Groupe();
        team_andor.setNom("Team Andor");

        Lieu chez_toinou = new Lieu();
        chez_toinou.setNom("Chez toinou");
        chez_toinou.setAdresse("6 Rue des Gouronnières");
        chez_toinou.setVille("Angers");
        chez_toinou.setCp("49100");

        Lieu chez_pierrot = new Lieu();
        chez_pierrot.setNom("Chez pierrot");
        chez_pierrot.setAdresse("Clos de l'Etriviere");
        chez_pierrot.setVille("Cantenay-Epinard");

        Lieu nulle_part = new Lieu();

        Lieu tonton_foch = new Lieu();
        tonton_foch.setAdresse("Tonton Foch");
        tonton_foch.setVille("Angers");

        Event event1 = new Event("1", "Pyjama Party", new Date(), team_andor, chez_toinou);
        Event event2 = new Event("2", "Andor", new Date(), team_andor, nulle_part);
        Event event3 = new Event("3", "Super Smash Bros", new Date(), team_andor, nulle_part);
        Event event4 = new Event("4", "Tonton Foch", new Date(), team_andor, tonton_foch);
        Event event5 = new Event("5", "Soirée chez Arnaud", new Date(), team_andor, nulle_part);
        Event event6 = new Event("6", "Développement Android", new Date(), team_andor, nulle_part);
        Event event7 = new Event("7", "Brainstorming intensif", new Date(), team_andor, chez_pierrot);
        Event event8 = new Event("8", "Entraînement salto arrière en slip", new Date(), team_andor, nulle_part);
        Event event9 = new Event("9", "Delirium", new Date(), team_andor, nulle_part);

        event1.setImage("fireworks");
        event3.setImage("gamepad");
        event4.setImage("beer");
        event5.setImage("fireworks");
        event6.setImage("coding");
        event9.setImage("beer");

        event1.setFlagMarker(true);
        event4.setFlagMarker(true);
        event7.setFlagMarker(true);

        list.add(event1);
        list.add(event2);
        list.add(event3);
        list.add(event4);
        list.add(event5);
        list.add(event6);
        list.add(event7);
        list.add(event8);
        list.add(event9);

        return list;
    }

}
