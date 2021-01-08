package com.example.thanoseventmanager.activities.profile_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.activities.groups_activity.GroupsActivity;
import com.example.thanoseventmanager.activities.login_activity.LoginActivity;
import com.example.thanoseventmanager.firebase.GroupeHelper;
import com.example.thanoseventmanager.firebase.UserHelper;
import com.example.thanoseventmanager.modeles.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        //R.menu.menu est l'id de notre menu
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemGroup) {
            Intent intent = new Intent(this, GroupsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            return(true);
        } else if (item.getItemId() == R.id.itemDeconnexion){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.itemProfil){
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    public void onClickBoutonEnregistrer(View v) {
        EditText editTextNewPseudo =findViewById(R.id.editTextNewPseudo);
        String newName = editTextNewPseudo.getText().toString();

        UserHelper.getUserByID(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User usr = documentSnapshot.toObject(User.class);
                if (usr != null){
                    GroupeHelper.generateGroupeId().addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            if(!editTextNewPseudo.getText().toString().isEmpty()) {
                                UserHelper.updateUserPseudo(usr.getId(), newName);

                                AlertDialog.Builder ErrorMsg = new AlertDialog.Builder(v.getContext());
                                ErrorMsg.setMessage("Votre nouvelle identité : " + newName)
                                        .setTitle("Changement effectué");
                                ErrorMsg.create();
                                ErrorMsg.show();
                            }else {
                                AlertDialog.Builder ErrorMsg = new AlertDialog.Builder(v.getContext());
                                ErrorMsg.setMessage("Veuillez rentrer un pseudo")
                                        .setTitle("Erreur");
                                ErrorMsg.create();
                                ErrorMsg.show();
                            }
                        }
                    });
                }
            }
        });
    }

}
