package com.example.thanoseventmanager.modeles;

public class Membre {

    String id;
    String numero;
    String pseudo;
    
    public Membre(){
        this.id = new String();
        this.numero = new String();
        this.pseudo = new String();
    }
    
    public Membre(String id,String numero, String pseudo){
        this.id = id;
        this.numero = numero;
        this.pseudo = pseudo;
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
}
