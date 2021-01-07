package com.example.thanoseventmanager.modeles;

import android.util.Log;

import java.util.ArrayList;

public class Groupe {

    String id;
    String nom;
    ArrayList<String> listeIdUsers;
    ArrayList<Event> listeEvents;

    public Groupe(){
        this.id = new String();
        this.nom = new String();
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
            if (this.listeEvents.get(i).getId() == eventToDeleted.getId()) {
                this.listeEvents.remove(i);
            }
        }
    }

    public void updateEvent(Event eventToUpdate) {
        for (int i = 0; i < this.listeEvents.size(); i++) {
            if (this.listeEvents.get(i).getId() == eventToUpdate.getId()) {
                this.listeEvents.set(i, eventToUpdate);
            }
        }
    }
}
