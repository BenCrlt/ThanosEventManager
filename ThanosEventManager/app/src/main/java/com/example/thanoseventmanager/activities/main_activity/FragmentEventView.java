package com.example.thanoseventmanager.activities.main_activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.firebase.GroupeHelper;
import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.viewmodels.ViewModel_MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class FragmentEventView extends Fragment implements View.OnClickListener {

    private static final String TAG = "EventView";
    ViewModel_MainActivity viewModel;
    Button participateButton;
    boolean isParticipant = false;
    String myUserId;
    Groupe myGroupe;
    Event eventToView;

    public FragmentEventView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(TAG, "on create view " + getClass().getSimpleName());

        //Récupération de l'evet selectionné via le ViewModel
        viewModel = new ViewModelProvider(this.requireActivity()).get(ViewModel_MainActivity.class);
        eventToView = viewModel.getEventToView().getValue();

        //Récupération du fragment
        View view = inflater.inflate(R.layout.fragment_event_view, container, false);
        TextView nomEvent = view.findViewById(R.id.eventTitre);
        TextView dateEvent = view.findViewById(R.id.dateEvent);
        TextView lieuEvent = view.findViewById(R.id.lieuEvent);
        ImageView eventIcon1 = view.findViewById(R.id.eventIcon1);
        ImageView eventIcon2 = view.findViewById(R.id.eventIcon2);

        participateButton = view.findViewById(R.id.participateButton);
        participateButton.setOnClickListener(this);

        //Traitement des données à afficher
        if (eventToView != null) {

            //Affichage du titre
            nomEvent.setText(eventToView.getNom());

            //Affichage de la date
            DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            DateFormat formatHeure = new SimpleDateFormat("HH:mm", Locale.FRANCE);
            String date = formatDate.format(eventToView.getDate());
            String heure = formatHeure.format(eventToView.getDate());
            String stringDate = "Date : " + date + "   Heure : " + heure;
            dateEvent.setText(stringDate);

            //Affichage du lieu
            String adresse = eventToView.getLieu().getAdresse();
            String cp = eventToView.getLieu().getCp();
            String ville = eventToView.getLieu().getVille();
            String stringLieu = "Lieu : " + adresse + ", " + cp + " " + ville;
            lieuEvent.setText(stringLieu);

            //Récupération de l'id de l'image de l'event
            int eventIcon = this.getMipmapResIdByName(eventToView.getImage());
            //Affichage des images
            if (eventIcon != 0) {
                eventIcon1.setImageResource(eventIcon);
                eventIcon2.setImageResource(eventIcon);
            }
        }

        this.updateButton(participateButton);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.getFirebaseData();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "on resume " + getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "on pause " + getClass().getSimpleName());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "on stop " + getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "on destroy " + getClass().getSimpleName());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "on attach " + getClass().getSimpleName());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "on activity created " + getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "on destroy view " + getClass().getSimpleName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "on detach " + getClass().getSimpleName());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.participateButton) {
            isParticipant = !isParticipant;
            this.updateButton(participateButton);
        }
    }

    //Changer l'apparence du bouton "Particier" de l'event view
    private void updateButton(Button button) {
        if (!isParticipant) {
            button.setBackgroundColor(Color.GREEN);
            button.setText("Participer");
        } else {
            button.setBackgroundColor(Color.RED);
            button.setText("Ne pas participer");
        }
    }

    //M'ajouter dans la liste des participants de l'event
    private void addParticipant() {
        List<String> participantList = eventToView.getParticipantList();
        participantList.add(myUserId);

        //MAJ de l'event dans Firebase
        GroupeHelper.updateEvent(myGroupe, eventToView);
    }

    //Retrouver l'ID d'une image à l'aide du nom du fichier image dans /mipmap
    private int getMipmapResIdByName(String resName)  {
        String pkgName = this.requireActivity().getPackageName();
        // Return 0 if not found.
        return this.requireActivity().getResources().getIdentifier(resName , "mipmap", pkgName);
    }

    //Récupération de mon id User
    private void getFirebaseData() {
        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        GroupeHelper.getGroupeById(eventToView.getGrpId()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                myGroupe = documentSnapshot.toObject(Groupe.class);
            }
        });
    }
}
