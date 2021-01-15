package com.example.thanoseventmanager.modeles;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class Groupe {

    private final static String TAG = "Group";
    String id;
    String nom;
    ArrayList<String> listeIdUsers;
    ArrayList<Event> listeEvents;

    public Groupe(){
        this.id = "";
        this.nom = "";
        this.listeIdUsers = new ArrayList<String>();
        this.listeEvents = new ArrayList<Event>();
    }

    public Groupe(String id, String nom, String idUserAdmin){
        this.id = id;
        this.nom = nom;
        this.listeIdUsers = new ArrayList<String>();
        this.listeIdUsers.add(idUserAdmin);
        this.listeEvents = new ArrayList<Event>();
    }

    public void setId(String id){this.id = id;}

    public String getId(){return this.id;}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String toString() { return this.getNom(); };

    // LISTE USERS ID

    public ArrayList<String> getListeIdUsers() {
        return this.listeIdUsers;
    }

    public void setListeIdUsers(ArrayList<String> listeIdUsers) {
        this.listeIdUsers = listeIdUsers;
    }

    public void addUser(String newUserId) { this.listeIdUsers.add(newUserId); };

    public void removeUser(String userIdToDelete) {
        for (int i = 0; i < this.listeIdUsers.size(); i++) {
            if ((this.listeIdUsers.get(i)).equalsIgnoreCase(userIdToDelete)) {
                this.listeIdUsers.remove(i);
            }
        }
    };

    // LISTE EVENTS

    public ArrayList<Event> getListeEvents() { return listeEvents; }

    public void addEvent(Event eventToAdd) { this.listeEvents.add(eventToAdd); }

    public void deleteEvent(Event eventToDeleted) {
        for (int i = 0; i < this.listeEvents.size(); i++) {
            if (this.listeEvents.get(i).getId().equals(eventToDeleted.getId())) {
                this.listeEvents.remove(i);
            }
        }
    }

    public void updateEvent(Event eventToUpdate) {
        for (int i = 0; i < this.listeEvents.size(); i++) {
            String eventId1 = this.listeEvents.get(i).getId();
            String eventId2 = eventToUpdate.getId();
            if (eventId1.equals(eventId2)) {
                Log.d(TAG,"eventId1 : " + eventId1);
                Log.d(TAG,"eventId2 : " + eventId2);
                this.listeEvents.set(i, eventToUpdate);
            }
        }
    }
}
