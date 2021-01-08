package com.example.thanoseventmanager.firebase;

import android.util.Log;

import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UserHelper {

    private static final String TAG = "CONNECTION";
    private static final String COLLECTION_NAME = "users";

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String id, String numero, String pseudo) {
        User newUser = new User(id, numero, pseudo);
        Log.d(TAG, "Create Membre");
        return UserHelper.getUsersCollection().document(id).set(newUser);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUserByID(String id) {
        return UserHelper.getUsersCollection().document(id).get();
    }

    public static Query getUserByPhone(String phone) {
        return UserHelper.getUsersCollection().whereEqualTo("numero", phone);
    }

    public static Query getAllUsersFromGroupe(Groupe grpSelected) {
        return UserHelper.getUsersCollection().whereIn("id", grpSelected.getListeIdUsers());
    }

    // --- UPDATE ---

    public static Task<Void> updateUserPseudo(String id, String pseudo) {
        return UserHelper.getUsersCollection().document(id).update("pseudo", pseudo);
    }

    public static Task<Void> updateUserLat(String id, double latitude) {
        return UserHelper.getUsersCollection().document(id).update("latitude", latitude);
    }

    public static Task<Void> updateUserLng(String id, double longitude) {
        return UserHelper.getUsersCollection().document(id).update("longitude", longitude);
    }

    // --- DELETE ---

    public static Task<Void> deleteMember(String id) {
        return UserHelper.getUsersCollection().document(id).delete();
    }
}
