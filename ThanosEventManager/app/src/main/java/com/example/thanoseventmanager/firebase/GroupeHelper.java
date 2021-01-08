package com.example.thanoseventmanager.firebase;

import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.Lieu;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class GroupeHelper {

    private static final String TAG = "CONNECTION";
    private static final String COLLECTION_NAME = "groupes";

    public static CollectionReference getGroupesCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // GROUPE MANAGER

    public static Task<Void> createGroupe(String id, String nom, String idUserAdmin) {
        Groupe newGroupe = new Groupe(id, nom, idUserAdmin);
        return GroupeHelper.getGroupesCollection().document(id).set(newGroupe);
    }

    public static Task<DocumentReference> generateGroupeId() {
        Groupe emptyGroupe = new Groupe();
        return GroupeHelper.getGroupesCollection().add(emptyGroupe);
    }

    public static Task<DocumentSnapshot> getGroupeById(String id) {
        return GroupeHelper.getGroupesCollection().document(id).get();
    }

    public static Query getAllGroupesOfUser(String userId) {
        return GroupeHelper.getGroupesCollection().whereArrayContains("listeIdUsers", userId);
    }

    public static Task<Void> deleteGroupe(Groupe groupeToDelete) {
        return GroupeHelper.getGroupesCollection().document(groupeToDelete.getId()).delete();
    }

    public static Task<Void> updateNameGroupe(Groupe groupeToUpdate, String newName) {
        return GroupeHelper.getGroupesCollection().document(groupeToUpdate.getId()).update("nom", newName);
    }

    // USER MANAGER

    public static Task<Void> addUser(Groupe groupeSelected, String userIdToAdd) {
        groupeSelected.addUser(userIdToAdd);
        return GroupeHelper.getGroupesCollection().document(groupeSelected.getId()).update("listeIdUsers", groupeSelected.getListeIdUsers());
    }

    public static Task<Void> deleteUser(Groupe groupeSelected, String userIdToDelete) {
        groupeSelected.removeUser(userIdToDelete);
        return GroupeHelper.getGroupesCollection().document(groupeSelected.getId()).update("listeIdUsers", groupeSelected.getListeIdUsers());
    }

    // EVENT MANAGER

    public static Task<Void> addEvent(Groupe grp, String id, String nom, Date date, Lieu lieu) {
        grp.addEvent(new Event(id, nom, date, grp, lieu));
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
