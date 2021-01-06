package com.example.thanoseventmanager.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventHelper {

    private static final String COLLECTION_NAME = "events";

    public static CollectionReference getEventsCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- UPDATE ---

    public static Task<Void> updateFlagMarker(String id, double flagMarker) {
        return EventHelper.getEventsCollection().document(id).update("flagMarker", flagMarker);
    }
}
