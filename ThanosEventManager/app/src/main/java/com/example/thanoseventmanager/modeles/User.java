package com.example.thanoseventmanager.modeles;

import java.util.ArrayList;
import java.util.List;

public class User {

    String id;
    String numero;
    String pseudo;
    List<Invitation> invitList = new ArrayList<Invitation>();
    double latitude;
    double longitude;

    
    public User(){
        this.id = new String();
        this.numero = new String();
        this.pseudo = new String();
    }
    
    public User(String id, String numero, String pseudo){
        this.id = id;
        this.numero = numero;
        this.pseudo = pseudo;
    }

    public List<Invitation> getInvitList() {
        return invitList;
    }

    public void setInvitList(List<Invitation> invitList) {
        this.invitList = invitList;
    }

    public void setId(String id){this.id = id;}

    public String getId(){return this.id;}

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getLat() {
        return latitude;
    }

    public void setLat(double latitude) {
        this.latitude = latitude;
    }

    public double getLng() {
        return longitude;
    }

    public void setLng(double longitude) {
        this.longitude = longitude;
    }
}
