package com.example.thanoseventmanager.activities.createevent_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.activities.groups_activity.GroupsActivity;
import com.example.thanoseventmanager.activities.login_activity.LoginActivity;
import com.example.thanoseventmanager.activities.profile_activity.ProfileActivity;
import com.example.thanoseventmanager.api.GroupeHelper;
import com.example.thanoseventmanager.modeles.Groupe;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.thanoseventmanager.api.GroupeHelper.getAllGroupesOfUser;

public class CreateEventActivity extends AppCompatActivity {
    Spinner spinner_grp, spinner_evt ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        /********* Spinner Groupe **********/
        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        spinner_grp = (Spinner) findViewById(R.id.spinner_group);





        /********* Spinner Event **********/
        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        //spinner_evt = (Spinner) findViewById(R.id.spinner_event);
        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        /*List exempleList2 = new ArrayList();
        exempleList2.add("Assinie");
        exempleList2.add("Bassam");
        exempleList2.add("Abidjan");
*/
        /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        Avec la liste des elements (exemple) */
        /*ArrayAdapter adapter2 = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                exempleList2
        );
*/
        /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
        //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        //spinner_evt.setAdapter(adapter2);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Requête pour mettre à jour la liste
        GroupeHelper.getAllGroupesOfUser(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Groupe> liste_groupe = queryDocumentSnapshots.toObjects(Groupe.class) ;
                setSpinnerList(liste_groupe) ;
            }
        }) ;


    }

    private void setSpinnerList(List<Groupe> liste_grp)
    {
        /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        Avec la liste des elements (exemple) */
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                liste_grp
        );

        /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner_grp.setAdapter(adapter);


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


    // Bouton Créer
    public void onClick_create_event(View v)
    {
        // Récupérer Nom Event



        // Récupérer date event



        // Récupérer groupe event



        // Récupérer localisation event



        
    }
}
