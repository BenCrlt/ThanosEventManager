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

/**
 * FIREBASE
 *  Singleton pour pouvoir gérer la collection des groupes sur la base de données Firestore
 */
public class GroupeHelper {

    private static final String TAG = "CONNECTION";
    private static final String COLLECTION_NAME = "groupes";

    /**
     * Retourne la collection des groupes qui est stockée sur la base de données
     */
    public static CollectionReference getGroupesCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // GROUPE MANAGER

    /**
     * Crée un nouveau groupe sur Firestore
     * @param id id pour le nouveau groupe
     * @param nom nom du groupe
     * @param idUserAdmin id de l'utilisateur qui est le créateur du groupe
     */
    public static Task<Void> createGroupe(String id, String nom, String idUserAdmin) {
        Groupe newGroupe = new Groupe(id, nom, idUserAdmin);
        return GroupeHelper.getGroupesCollection().document(id).set(newGroupe);
    }

    /**
     * Ajoute un groupe vide pour pouvoir récupérer l'id du futur
     * groupe généré automatiquement par Firebase
     * @return l'identifiant créé
     */
    public static Task<DocumentReference> generateGroupeId() {
        Groupe emptyGroupe = new Groupe();
        return GroupeHelper.getGroupesCollection().add(emptyGroupe);
    }

    /**
     * Récupère le groupe à partir de son identifiant
     * @param id id du groupe recherché
     * @return Retourne le groupe trouvé si il est présent dans la database
     */
    public static Task<DocumentSnapshot> getGroupeById(String id) {
        return GroupeHelper.getGroupesCollection().document(id).get();
    }

    /**
     * Récupère tous les groupes d'un utilisateur
     * @param userId id de l'utilisateur
     * @return Une liste de groupes
     */
    public static Query getAllGroupesOfUser(String userId) {
        return GroupeHelper.getGroupesCollection().whereArrayContains("listeIdUsers", userId);
    }

    /**
     * Supprimer un groupe
     * @param groupeToDelete l'objet Groupe à supprimer
     */
    public static Task<Void> deleteGroupe(Groupe groupeToDelete) {
        return GroupeHelper.getGroupesCollection().document(groupeToDelete.getId()).delete();
    }

    /**
     * Met à jour le nom d'un groupe
     * @param groupeToUpdate Le groupe à mettre à jour
     * @param newName le nouveau nom du groupe
     */
    public static Task<Void> updateNameGroupe(Groupe groupeToUpdate, String newName) {
        return GroupeHelper.getGroupesCollection().document(groupeToUpdate.getId()).update("nom", newName);
    }

    // USER MANAGER

    /**
     * Ajoute un nouvel utilisateur dans un groupe
     * @param groupeSelected le groupe où l'utilisateur va être ajouté
     * @param userIdToAdd l'identifiant de l'utilsateur à ajouter
     */
    public static Task<Void> addUser(Groupe groupeSelected, String userIdToAdd) {
        groupeSelected.addUser(userIdToAdd);
        return GroupeHelper.getGroupesCollection().document(groupeSelected.getId()).update("listeIdUsers", groupeSelected.getListeIdUsers());
    }

    /**
     * Supprime un utilisateur dans un groupe
     * @param groupeSelected le groupe où l'utilisateur va être supprimé
     * @param userIdToDelete l'identifiant de l'utilsateur à supprimer
     */
    public static Task<Void> deleteUser(Groupe groupeSelected, String userIdToDelete) {
        groupeSelected.removeUser(userIdToDelete);
        return GroupeHelper.getGroupesCollection().document(groupeSelected.getId()).update("listeIdUsers", groupeSelected.getListeIdUsers());
    }

    // EVENT MANAGER

    /**
     * Ajoute un nouvel évenement dans un groupe
     * @param grp le groupe sélectionné
     * @param nom le nom de l'évenement
     * @param date la date de l'évenement
     * @param lieu le lieu de l'événement
     */
    public static Task<Void> addEvent(Groupe grp, String nom, Date date, Lieu lieu) {
        String id = nom + date.toString();
        grp.addEvent(new Event(id, nom, date, grp, lieu));
        return GroupeHelper.getGroupesCollection().document(grp.getId()).update("listeEvents", grp.getListeEvents());
    }

    /**
     * Supprime un évenement dans un groupe
     * @param grp le groupe sélectionné
     * @param eventToDelete l'événement à supprimer
     */
    public static Task<Void> deleteEvent(Groupe grp, Event eventToDelete) {
        grp.deleteEvent(eventToDelete);
        return GroupeHelper.getGroupesCollection().document(grp.getId()).update("listeEvents", grp.getListeEvents());
    }

    /**
     * Met à jour l'évenement
     * @param grp le groupe sélectionné
     * @param eventToUpdate l'évenement sélectionné
     */
    public static Task<Void> updateEvent(Groupe grp, Event eventToUpdate) {
        grp.updateEvent(eventToUpdate);
        return GroupeHelper.getGroupesCollection().document(grp.getId()).update("listeEvents", grp.getListeEvents());
    }
}
