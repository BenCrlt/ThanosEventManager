package com.example.thanoseventmanager.modeles;

import java.util.ArrayList;

public class Groupe {

    String id;
    String nom;
    ArrayList<User> listeUsers;

    public Groupe(){
        this.id = new String();
        this.nom = new String();
        this.listeUsers = new ArrayList<User>();
    }

    public Groupe(String nom){
        this.nom = nom;
        this.id = new String();
        this.listeUsers = new ArrayList<User>();
    }

    public void setId(String id){this.id = id;}

    public String getId(){return this.id;}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<User> getListeUsers() {
        return listeUsers;
    }

    public void setListeUsers(ArrayList<User> listeUsers) {
        this.listeUsers = listeUsers;
    }
}
