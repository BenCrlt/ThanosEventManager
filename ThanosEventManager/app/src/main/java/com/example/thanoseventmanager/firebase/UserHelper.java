package com.example.thanoseventmanager.firebase;

import android.util.Log;

import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.Invitation;
import com.example.thanoseventmanager.modeles.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

/**
 * FIREBASE
 *  Singleton pour pouvoir gérer la collection des utilisateurs sur la base de données Firestore
 */
public class UserHelper {

    private static final String TAG = "CONNECTION";
    private static final String COLLECTION_NAME = "users";

    /**
     * Récupère la collection des utilisateurs sur la base de données
     */
    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    /**
     * Créer un nouvel utilisateur
     * @param id id de l'utilsateur
     * @param numero numéro de l'utilisateur
     * @param pseudo pseudo de l'utilisateur
     */
    public static Task<Void> createUser(String id, String numero, String pseudo) {
        User newUser = new User(id, numero, pseudo);
        Log.d(TAG, "Create Membre");
        return UserHelper.getUsersCollection().document(id).set(newUser);
    }

    /**
     * Ajout d'une invitation pour rejoindre un groupe à un utiisateur
     * @param usr utilisateur destinataire de l'invitation
     * @param groupId l'identifiant du groupe
     */
    public static Task<Void> addInviteToUser(User usr, String groupId) {
        boolean alreadyExist = false;

        for (int i=0; i<usr.getInvitList().size();i++) {
            if (usr.getInvitList().get(i).getGroupToJoin().equals(groupId)){
                alreadyExist = true;
            }
        }
        //Si l'invitation existe déjà, ne pas l'ajouter
        if (!alreadyExist){
            usr.getInvitList().add(new Invitation(groupId));
        }

        return UserHelper.getUsersCollection().document(usr.getId()).update("invitList", usr.getInvitList());
    }


    /**
     * Récupère l'utilisateur à partir de son identifiant
     * @param id identifiant de l'utilisateur
     * @return l'objet User trouvé dans la base de données
     */
    public static Task<DocumentSnapshot> getUserByID(String id) {
        return UserHelper.getUsersCollection().document(id).get();
    }

    /**
     * Récupère la liste des invitations
     * @param userId identifiant de l'utilisateur
     * @return List<Invitation> de l'utilisateur
     */
    public static Query getListInvitations(String userId) {
        return UserHelper.getUsersCollection().whereArrayContains("listeInvitations", userId);
    }

    /**
     * Récupère l'utilisateur à partir de son téléphone
     * @param phone numéro de l'utilsateur destinataire
     * @return l'ojet User trouvé dans la base
     */
    public static Query getUserByPhone(String phone) {
        return UserHelper.getUsersCollection().whereEqualTo("numero", phone);
    }

    /**
     * Récupère tous les utilisateurs d'un groupe
     * @param grpSelected Groupe sélectionné
     * @return List<User> qui sont dans le groupe
     */
    public static Query getAllUsersFromGroupe(Groupe grpSelected) {
        return UserHelper.getUsersCollection().whereIn("id", grpSelected.getListeIdUsers());
    }


    /**
     * Met à jour le pseudo d'un utilisateur
     * @param id identifiant de l'utilisateur
     * @param pseudo pseudo de l'utilisateur
     */
    public static Task<Void> updateUserPseudo(String id, String pseudo) {
        return UserHelper.getUsersCollection().document(id).update("pseudo", pseudo);
    }

    /**
     * Supprime toutes les invitations d'un utilisateur
     * @param userID identifiant de l'utilisateur
     */
    public static Task<Void> clearListInvit(String userID) {
        return UserHelper.getUsersCollection().document(userID).update("invitList", new ArrayList<Invitation>());
    }

    /**
     * Supprimer un utilisateur
     * @param id identifiant de l'utilisateur
     */
    public static Task<Void> deleteUser(String id) {
        return UserHelper.getUsersCollection().document(id).delete();
    }
}
