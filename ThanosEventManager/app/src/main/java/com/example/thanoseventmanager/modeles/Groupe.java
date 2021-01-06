package com.example.thanoseventmanager.modeles;

import android.util.Log;

import java.util.ArrayList;

public class Groupe {

    String id;
    String nom;
    ArrayList<User> listeUsers;
    ArrayList<Event> listeEvents;

    public Groupe(){
        this.id = new String();
        this.nom = new String();
        this.listeUsers = new ArrayList<User>();
        this.listeEvents = new ArrayList<Event>();
    }

    public Groupe(String id, String nom, User userAdmin){
        this.id = id;
        this.nom = nom;
        this.listeUsers = new ArrayList<User>();
        this.listeUsers.add(userAdmin);
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

    // LISTE USERS

    public ArrayList<User> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(ArrayList<User> listeUsers) {
        this.listeUsers = listeUsers;
    }

    public void addUser(User newUser) { this.listeUsers.add(newUser); };

    public void removeUser(User userToDelete) {
        for (int i = 0; i < this.listeUsers.size(); i++) {
            if ((this.listeUsers.get(i)).getId().equalsIgnoreCase(userToDelete.getId())) {
                this.listeUsers.remove(i);
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
