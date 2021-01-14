package com.example.thanoseventmanager.modeles;

public class Invitation {
    String idGroupToJoin;

    public Invitation(){
        this.idGroupToJoin = null;
    }

    public Invitation(String idGroupeToJoin){
        this.idGroupToJoin = idGroupeToJoin;
    }

    public String getGroupToJoin() {
        return idGroupToJoin;
    }

    public void setGroupToJoin(String groupToJoin) {
        this.idGroupToJoin = groupToJoin;
    }

}


