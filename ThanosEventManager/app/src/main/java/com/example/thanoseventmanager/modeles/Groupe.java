package com.example.thanoseventmanager.modeles;

import java.util.ArrayList;

public class Groupe {

    String id;
    String nom;
    ArrayList<Membre> listeMembres;

    public Groupe(){
        this.id = new String();
        this.nom = new String();
        this.listeMembres = new ArrayList<Membre>();
    }

    public Groupe(String nom){
        this.nom = nom;
        this.id = new String();
        this.listeMembres = new ArrayList<Membre>();
    }

    public void setId(String id){this.id = id;}

    public String getId(){return this.id;}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Membre> getListeMembres() {
        return listeMembres;
    }

    public void setListeMembres(ArrayList<Membre> listeMembres) {
        this.listeMembres = listeMembres;
    }
}
