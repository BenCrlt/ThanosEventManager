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
import android.widget.ListView;
import android.widget.TextView;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.firebase.GroupeHelper;
import com.example.thanoseventmanager.firebase.UserHelper;
import com.example.thanoseventmanager.listAdapter.UserListAdapter;
import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.User;
import com.example.thanoseventmanager.viewmodels.ViewModel_MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentEventView extends Fragment implements View.OnClickListener {

    private final static String TAG = "EventView";
    ViewModel_MainActivity viewModel;
    Button participateButton;
    ListView listView;
    String myUserId;
    Groupe myGroup;
    List<User> myGroupUsers;
    Event eventToView;
    boolean isParticipant = false;


    public FragmentEventView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Récupération de l'evet selectionné via le ViewModel
        viewModel = new ViewModelProvider(this.requireActivity()).get(ViewModel_MainActivity.class);
        eventToView = viewModel.getEventToView().getValue();

        //Récupération des éléments du fragment
        View view = inflater.inflate(R.layout.fragment_event_view, container, false);
        TextView nomEvent = view.findViewById(R.id.eventTitre);
        TextView dateEvent = view.findViewById(R.id.dateEvent);
        TextView lieuEvent = view.findViewById(R.id.lieuEvent);
        ImageView eventIcon1 = view.findViewById(R.id.eventIcon1);
        ImageView eventIcon2 = view.findViewById(R.id.eventIcon2);
        listView = view.findViewById(R.id.listParticipants);
        participateButton = view.findViewById(R.id.participateButton);

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

            participateButton.setOnClickListener(this);
        }

        this.updateParticipateButton();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.getFirebaseData();
    }

    @Override
    public void onResume() { super.onResume(); }

    @Override
    public void onPause() { super.onPause(); }

    @Override
    public void onStop() { super.onStop(); }

    @Override
    public void onDestroy() { super.onDestroy(); }

    @Override
    public void onAttach(@NonNull Context context) { super.onAttach(context); }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) { super.onActivityCreated(savedInstanceState); }

    @Override
    public void onDestroyView() { super.onDestroyView(); }

    @Override
    public void onDetach() { super.onDetach(); }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.participateButton) {
            if (!isParticipant) {
                isParticipant = true;
                this.updateParticipateButton();
                this.addParticipant();
            } else {
                isParticipant = false;
                this.updateParticipateButton();
            }
        }
    }

    //Changer l'apparence du bouton "Particier" de l'event view
    private void updateParticipateButton() {
        String text1 = "Participer";
        String text2 = "Ne pas participer";
        if (!isParticipant) {
            participateButton.setBackgroundColor(Color.GREEN);
            participateButton.setText(text1);
        } else {
            participateButton.setBackgroundColor(Color.RED);
            participateButton.setText(text2);
        }
    }

    //Récupérer tous les participants d'un évènement
    private List<User> getAllParticipants(){

        List<String> participants = eventToView.getParticipantList();
        List<User> usersToShow = new ArrayList<>();

        for(User user : myGroupUsers) {

            String userId = user.getId();
            Log.d(TAG, "Nom User : " + userId);

            for(String participantId : participants){
                if(participantId.equals(userId)){
                    usersToShow.add(user);
                }
            }
        }

        return usersToShow;
    }

    //M'ajouter dans la liste des participants de l'event
    private void addParticipant() {
        List<String> participantList = eventToView.getParticipantList();
        participantList.add(myUserId);
        eventToView.setParticipantList(participantList);

        Log.d(TAG,"Groupe : " + myGroup);
        Log.d(TAG,"Event : " + eventToView.getNom());

        //MAJ de l'event dans Firebase
        GroupeHelper.updateEvent(myGroup, eventToView);
    }

    //M'enlever de la liste des participants de l'event
    private void removeParticipant() {
        List<String> participantList = eventToView.getParticipantList();
    }

    //Retrouver l'ID d'une image à l'aide du nom du fichier image dans /mipmap
    private int getMipmapResIdByName(String resName)  {
        String pkgName = this.requireActivity().getPackageName();
        // Return 0 if not found.
        return this.requireActivity().getResources().getIdentifier(resName , "mipmap", pkgName);
    }

    //Récupération de diverses informations de Firebase
    private void getFirebaseData() {

        //Récupération de l'id de l'utilisateur courant
        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Récupération du Groupe concerné par l'event à visualiser
        GroupeHelper.getGroupeById(eventToView.getGrpId()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                myGroup = documentSnapshot.toObject(Groupe.class);

                //Récupération de tous les Users du Groupe obtenu
                UserHelper.getAllUsersFromGroupe(myGroup).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        myGroupUsers = queryDocumentSnapshots.toObjects(User.class);
                        listView.setAdapter( new UserListAdapter(getContext(),getAllParticipants()) );
                    }
                });
            }
        });
    }
}
