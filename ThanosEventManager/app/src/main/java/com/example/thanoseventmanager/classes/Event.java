package com.example.thanoseventmanager.classes;

import java.util.Date;

public class Event {

    String nom = new String();
    Date date = new Date();
    Groupe groupe = new Groupe();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    //public void setGroupe(Groupe groupe) {this.groupe = groupe;}

}
