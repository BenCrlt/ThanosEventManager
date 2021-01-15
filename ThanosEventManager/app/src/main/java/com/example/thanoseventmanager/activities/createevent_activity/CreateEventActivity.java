package com.example.thanoseventmanager.activities.createevent_activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.activities.groups_activity.GroupsActivity;
import com.example.thanoseventmanager.activities.login_activity.LoginActivity;
import com.example.thanoseventmanager.activities.main_activity.MainActivity;
import com.example.thanoseventmanager.activities.profile_activity.ProfileActivity;
import com.example.thanoseventmanager.firebase.GroupeHelper;
import com.example.thanoseventmanager.firebase.GroupeHelper;
import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Groupe;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static com.example.thanoseventmanager.firebase.GroupeHelper.updateEvent;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private final static String TAG = "hello";
    Spinner spinner_grp, spinner_evt ;
    TextView infoDateText, nameEvent_TextView, adresse_TextView, cp_TextView, ville_TextView;
    Date dateEvent;
    Groupe groupeEvent;
    int year, month, day, hour, minute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        /********* Spinner Groupe **********/
        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        spinner_grp = (Spinner) findViewById(R.id.spinner_group);
        infoDateText = (TextView) findViewById(R.id.text_infodate_createEvent_activity);
        nameEvent_TextView = (TextView) findViewById(R.id.editText_Name_event);
        adresse_TextView = (TextView) findViewById(R.id.editText_address_event);
        cp_TextView = (TextView) findViewById(R.id.editText_cp_event);
        ville_TextView =  (TextView) findViewById(R.id.editText_ville_event);

    }

    @Override
    public void onDateSet(DatePicker view, int yearSet, int monthSet, int dayOfMonth) {
        year = yearSet;
        day = dayOfMonth;
        month = monthSet;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEventActivity.this, CreateEventActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minuteSet) {
        hour = hourOfDay;
        minute = minuteSet;
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, day, hour, minute);
        infoDateText.setText(selectedDate.getTime().toString());
        dateEvent = selectedDate.getTime();
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

    // Calendrier et horloge afin de sélectionner la date et l'heure
    public void onClickSelectDate(View v) {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, CreateEventActivity.this,year, month,day);
        datePickerDialog.show();
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
    public void onClickCreateEvent(View v)
    {
        Event new_event = new Event() ;
        Groupe groupeSelected = new Groupe();
        String nom_event, address_event, cp_event, ville_event ;
        Date date_event ;
        // Récupérer Nom Event
        nom_event = ((EditText)findViewById(R.id.editText_Name_event)).getText().toString() ;
        if (!nom_event.isEmpty()) {
            new_event.setNom(nom_event);
            if (dateEvent != null) {
                new_event.setDate(dateEvent);

                groupeSelected = (Groupe) spinner_grp.getSelectedItem();


                /******* Récupérer localisation event *******/
                // Récupérer Addresse
                address_event = ((EditText)findViewById(R.id.editText_address_event)).getText().toString() ;
                new_event.getLieu().setAdresse(address_event);

                // Récupérer Code Postal
                cp_event = ((EditText)findViewById(R.id.editText_cp_event)).getText().toString() ;
                new_event.getLieu().setCp(cp_event);

                // Récupérer Ville
                ville_event = ((EditText)findViewById(R.id.editText_ville_event)).getText().toString() ;
                new_event.getLieu().setVille(ville_event) ;

                GroupeHelper.addEvent(groupeSelected, new_event.getNom(), new_event.getDate(), new_event.getLieu());
                goToMainActivity();
            } else {
                showErrorMessage("Veuillez sélectionner une date pour votre événement !");
            }
        } else {
            showErrorMessage("Veuillez rentrer un nom pour l'événement");
        }
        
    }

    private void showErrorMessage(String errorMessage) {
        AlertDialog.Builder ErrorMsg = new AlertDialog.Builder(this);
        ErrorMsg.setMessage(errorMessage)
                .setTitle("Erreur");
        ErrorMsg.create();
        ErrorMsg.show();
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}
