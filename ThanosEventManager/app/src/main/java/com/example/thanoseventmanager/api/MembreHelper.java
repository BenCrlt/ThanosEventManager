package com.example.thanoseventmanager.api;

import android.util.Log;

import com.example.thanoseventmanager.modeles.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MembreHelper {

    private static final String TAG = "CONNECTION";
    private static final String COLLECTION_NAME = "users";

    public static CollectionReference getMembersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Task<Void> createUser(String id, String numero, String pseudo) {
        User newUser = new User(id, numero, pseudo);
        Log.d(TAG, "Create Membre");
        return MembreHelper.getMembersCollection().document(id).set(newUser);
    }

    public static Task<DocumentSnapshot> getMembreByID(String id) {
        return MembreHelper.getMembersCollection().document(id).get();
    }

    public static Task<DocumentSnapshot> getMembreByPhone(String phone) {
        return MembreHelper.getMembersCollection().document(phone).get();
    }

    public static Task<Void> deleteMember(String id) {
        return MembreHelper.getMembersCollection().document(id).delete();
    }
}
