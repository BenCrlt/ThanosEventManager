package com.example.thanoseventmanager.api;

import android.util.Log;

import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class GroupeHelper {

    private static final String TAG = "CONNECTION";
    private static final String COLLECTION_NAME = "groupes";

    public static CollectionReference getGroupesCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Task<Void> createGroupe(String id, String nom) {
        Groupe newGroupe = new Groupe(id, nom);
        Log.d(TAG, "Create Groupe");
        return GroupeHelper.getGroupesCollection().document(id).set(newGroupe);
    }

    public static Task<Void> addUser(Groupe groupeSelected, User userToAdd) {
        //groupeSelected.addUser(userToAdd);
        Log.d(TAG, "Size liste : " + groupeSelected.getNom());
        return GroupeHelper.getGroupesCollection().document(groupeSelected.getId()).set(groupeSelected);
    }

    /*public static Task<Void> deleteUser(Groupe groupeSelected, User userToDelete) {
        groupeSelected.getListeUsers().remove(userToDelete);
        return GroupeHelper.getGroupesCollection().document(groupeSelected.getId())
    }*/
}
