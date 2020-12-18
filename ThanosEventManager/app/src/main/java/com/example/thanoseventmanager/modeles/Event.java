package com.example.thanoseventmanager.modeles;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

    String id;
    String nom;
    Date date;
    Groupe groupe;

    public Event(){
        this.id = new String();
        this.nom = new String();
        this.date = new Date();
        this.groupe = new Groupe();
    }

    public Event(String id, String nom, Date date, Groupe grp){
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.groupe = grp;
    }

    public void setId(String id){this.id = id;}

    public String getId(){return this.id;}

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
