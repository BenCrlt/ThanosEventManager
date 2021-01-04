package com.example.thanoseventmanager.api;

import android.util.Log;

import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class GroupeHelper {

    private static final String TAG = "CONNECTION";
    private static final String COLLECTION_NAME = "groupes";

    public static CollectionReference getGroupesCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Task<Void> createGroupe(String id, String nom, User userAdmin) {
        Groupe newGroupe = new Groupe(id, nom, userAdmin);
        Log.d(TAG, "Create Groupe");
        return GroupeHelper.getGroupesCollection().document(id).set(newGroupe);
    }

    public static Task<Void> addUser(Groupe groupeSelected, User userToAdd) {
        groupeSelected.addUser(userToAdd);
        Log.d(TAG, "Size liste : " + groupeSelected.getNom());
        return GroupeHelper.getGroupesCollection().document(groupeSelected.getId()).update("listeUsers", groupeSelected.getListeUsers());
    }

    public static Task<DocumentSnapshot> getGroupeById(String id) {
        return GroupeHelper.getGroupesCollection().document(id).get();
    }

    public static Task<Void> deleteUser(Groupe groupeSelected, User userToDelete) {
        groupeSelected.removeUser(userToDelete);
        Log.d(TAG, "Size groupe " + groupeSelected.getListeUsers().size());
        return GroupeHelper.getGroupesCollection().document(groupeSelected.getId()).update("listeUsers", groupeSelected.getListeUsers());
    }

    public static Task<Void> deleteGroupe(Groupe groupeToDelete) {
        return GroupeHelper.getGroupesCollection().document(groupeToDelete.getId()).delete();
    }

    public static Task<Void> updateNameGroupe(Groupe groupeToUpdate, String newName) {
        return GroupeHelper.getGroupesCollection().document(groupeToUpdate.getId()).update("nom", newName);
    }
}
