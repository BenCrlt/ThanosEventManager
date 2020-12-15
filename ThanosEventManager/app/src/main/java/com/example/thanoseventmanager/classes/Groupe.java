package com.example.thanoseventmanager.classes;

import java.util.ArrayList;

public class Groupe {

    String nom = new String();
    ArrayList<Membre> listeMembres = new ArrayList<Membre>();

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
