package com.example.thanoseventmanager.modeles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Event implements Serializable {

    String id;
    String nom;
    String image = "event";
    Date date;
    Lieu lieu;
    boolean isShowedOnMap;
    boolean isInProgress;
    String grpId;
    String grpName;
    List<String> participantList;

    public Event(){
        this.id = new String();
        this.nom = new String();
        this.date = new Date();
        this.lieu = new Lieu();
        this.isInProgress = true;
        this.participantList = new ArrayList<>();
    }

    public Event(String id, String nom, Date date, Lieu lieu){
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.isInProgress = true;
        this.participantList = new ArrayList<>();
    }

    public Event(String id, String nom, Date date, Groupe grp, Lieu lieu){
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.isInProgress = true;
        this.grpId = grp.getId();
        this.grpName = grp.getNom();
        this.participantList = new ArrayList<>();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public void setId(String id) { this.id = id; }

    public String getId() { return this.id; }

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

    public boolean getFlagMarker() { return this.isShowedOnMap; }

    public void setFlagMarker(boolean flag) { this.isShowedOnMap = flag; }

    public String getGrpName() { return this.grpName; }

    public String getGrpId() { return this.grpId; }

    public List<String> getParticipantList(){ return this.participantList; }

    public void setParticipantList(List<String> participantList) { this.participantList = participantList; }
}
