package com.example.thanoseventmanager.activities.main_activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.listAdapter.EventListAdapter;
import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.viewmodels.ViewModel_MainActivity;

public class FragmentEventView extends Fragment {

    ViewModel_MainActivity viewModel;
    private static final String TAG = "EventView";

    public FragmentEventView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(TAG, "on create view " + getClass().getSimpleName());

        //Récupération de l'evet selectionné via le ViewModel
        viewModel = new ViewModelProvider(this.requireActivity()).get(ViewModel_MainActivity.class);
        Event dataEvent = viewModel.getEventToView().getValue();

        //Récupération du fragment
        View view = inflater.inflate(R.layout.fragment_event_view, container, false);
        TextView nomEvent = view.findViewById(R.id.eventTitre);
        ImageView eventIcon1 = view.findViewById(R.id.eventIcon1);
        ImageView eventIcon2 = view.findViewById(R.id.eventIcon2);

        //Traitement des données à afficher
        if (dataEvent != null) {

            //Affichage du titre
            nomEvent.setText(dataEvent.getNom());

            //Récupération de l'id de l'image de l'event
            int eventIcon = this.getMipmapResIdByName(dataEvent.getImage());
            //Affichage des images
            if (eventIcon != 0) {
                eventIcon1.setImageResource(eventIcon);
                eventIcon2.setImageResource(eventIcon);
            }
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "on start " + getClass().getSimpleName());
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

    // Retrouver l'ID d'une image à l'aide du nom du fichier image dans /mipmap
    private int getMipmapResIdByName(String resName)  {
        String pkgName = this.requireActivity().getPackageName();
        // Return 0 if not found.
        return this.requireActivity().getResources().getIdentifier(resName , "mipmap", pkgName);
    }
}