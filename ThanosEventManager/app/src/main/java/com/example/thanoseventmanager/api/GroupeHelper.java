package com.example.thanoseventmanager.api;

import android.util.Log;

import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.Lieu;
import com.example.thanoseventmanager.modeles.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.acl.Group;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GroupeHelper {

    private static final String TAG = "CONNECTION";
    private static final String COLLECTION_NAME = "groupes";

    public static CollectionReference getGroupesCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // GROUPE MANAGER

    public static void createGroupe(String nom, User userAdmin) {
        Groupe newGroupe = new Groupe(nom, userAdmin);
        GroupeHelper.getGroupesCollection().add(newGroupe).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                GroupeHelper.getGroupesCollection().document(documentReference.getId()).update("id", documentReference.getId());
            }
        });
    }

    public static Task<DocumentSnapshot> getGroupeById(String id) {
        return GroupeHelper.getGroupesCollection().document(id).get();
    }

    public static Task<Void> deleteGroupe(Groupe groupeToDelete) {
        return GroupeHelper.getGroupesCollection().document(groupeToDelete.getId()).delete();
    }

    public static Task<Void> updateNameGroupe(Groupe groupeToUpdate, String newName) {
        return GroupeHelper.getGroupesCollection().document(groupeToUpdate.getId()).update("nom", newName);
    }

    // USER MANAGER

    public static Task<Void> addUser(Groupe groupeSelected, User userToAdd) {
        groupeSelected.addUser(userToAdd);
        return GroupeHelper.getGroupesCollection().document(groupeSelected.getId()).update("listeUsers", groupeSelected.getListeUsers());
    }

    public static Task<Void> deleteUser(Groupe groupeSelected, User userToDelete) {
        groupeSelected.removeUser(userToDelete);
        return GroupeHelper.getGroupesCollection().document(groupeSelected.getId()).update("listeUsers", groupeSelected.getListeUsers());
    }

    // EVENT MANAGER

    public static Task<Void> addEvent(Groupe grp, String id, String nom, Date date, Lieu lieu) {
        grp.addEvent(new Event(id, nom, date, lieu));
        return GroupeHelper.getGroupesCollection().document(grp.getId()).update("listEvents", grp.getListeEvents());
    }

    public static Task<Void> deleteEvent(Groupe grp, Event eventToDelete) {
        grp.deleteEvent(eventToDelete);
        return GroupeHelper.getGroupesCollection().document(grp.getId()).update("listEvents", grp.getListeEvents());
    }

    public static Task<Void> updateEvent(Groupe grp, Event eventToUpdate) {
        grp.updateEvent(eventToUpdate);
        return GroupeHelper.getGroupesCollection().document(grp.getId()).update("listEvents", grp.getListeEvents());
    }
}
