package com.example.thanoseventmanager.activities.createevent_activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.activities.groups_activity.GroupsActivity;
import com.example.thanoseventmanager.activities.login_activity.LoginActivity;
import com.example.thanoseventmanager.activities.main_activity.MainActivity;
import com.example.thanoseventmanager.activities.profile_activity.ProfileActivity;
import com.example.thanoseventmanager.firebase.GroupeHelper;
import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Groupe;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Spinner spinner_grp, spinner_icon;
    TextView infoDateText,nameEvent_TextView, adresse_TextView, cp_TextView, ville_TextView;
    Date dateEvent;
    int year, month, day, hour, minute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        ///********* Spinner Groupe **********/
        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        spinner_grp = findViewById(R.id.spinner_group);
        spinner_icon = findViewById(R.id.spinner_icon);
        infoDateText = findViewById(R.id.text_infodate_createEvent_activity);
        nameEvent_TextView = findViewById(R.id.editText_Name_event);
        adresse_TextView = findViewById(R.id.editText_address_event);
        cp_TextView = findViewById(R.id.editText_cp_event);
        ville_TextView = findViewById(R.id.editText_ville_event);

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
        dateEvent = selectedDate.getTime();

        //Affichage de la date
        java.text.DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        java.text.DateFormat formatHeure = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        String date = formatDate.format(dateEvent);
        String heure = formatHeure.format(dateEvent);
        String stringDate = "Date : " + date + "   Heure : " + heure;
        infoDateText.setText(stringDate);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Requête pour mettre à jour la liste
        GroupeHelper.getAllGroupesOfUser(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Groupe> liste_groupe = queryDocumentSnapshots.toObjects(Groupe.class) ;
            setSpinnerGroupList(liste_groupe) ;
        }) ;

        //Création liste spinner pour choisir l'icone de l'evenement
        List<String> liste_icone = new ArrayList<>();
        liste_icone.add("event");
        liste_icone.add("beer");
        liste_icone.add("coding");
        liste_icone.add("fireworks");
        liste_icone.add("gamepad");
        this.setSpinnerIconList(liste_icone);

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

    private void setSpinnerGroupList(List<Groupe> liste_grp)
    {
        //Création d'un adapter pour la présentation du spinner
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, liste_grp);

        // On definit une présentation du spinner quand il est déroulé
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_grp.setAdapter(adapter);
    }

    private void setSpinnerIconList(List<String> liste_icone)
    {
        //Création d'un adapter pour la présentation du spinner
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, liste_icone);

        // On definit une présentation du spinner quand il est déroulé
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_icon.setAdapter(adapter);
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
        Groupe groupeSelected;
        String iconSelected;
        String nom_event, address_event, cp_event, ville_event ;

        // Récupération du Nom Event
        nom_event = ((EditText)findViewById(R.id.editText_Name_event)).getText().toString() ;

        //Nom event requis
        if (!nom_event.isEmpty()) {
            new_event.setNom(nom_event);

            //Date requis
            if (dateEvent != null) {
                new_event.setDate(dateEvent);

                //Récupération du groupe sélectionné
                groupeSelected = (Groupe) spinner_grp.getSelectedItem();

                //Récupération de l'icone sélectionné
                iconSelected = (String) spinner_icon.getSelectedItem();
                new_event.setImage(iconSelected);

                ///******* Récupérer localisation event *******/
                // Récupérer Addresse
                address_event = ((EditText)findViewById(R.id.editText_address_event)).getText().toString() ;
                new_event.getLieu().setAdresse(address_event);

                // Récupérer Code Postal
                cp_event = ((EditText)findViewById(R.id.editText_cp_event)).getText().toString() ;
                new_event.getLieu().setCp(cp_event);

                // Récupérer Ville
                ville_event = ((EditText)findViewById(R.id.editText_ville_event)).getText().toString() ;
                new_event.getLieu().setVille(ville_event) ;

                GroupeHelper.addEvent(groupeSelected, new_event.getNom(), new_event.getDate(), new_event.getLieu(),new_event.getImage());

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
