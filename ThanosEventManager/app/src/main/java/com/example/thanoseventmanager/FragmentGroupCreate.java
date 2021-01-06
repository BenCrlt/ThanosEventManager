package com.example.thanoseventmanager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thanoseventmanager.api.GroupeHelper;
import com.example.thanoseventmanager.api.UserHelper;
import com.example.thanoseventmanager.modeles.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

public class FragmentGroupCreate extends Fragment implements View.OnClickListener {
    private static final String TAG = "Hello";

    Button boutonCreerGroupe;

    public FragmentGroupCreate() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "on create view " + getClass().getSimpleName());

        //Création d'un objet Button correspondant à "buttonCreateNewGroup" du fragment fragment_group_create
        View v = inflater.inflate(R.layout.fragment_group_create, container, false);
        boutonCreerGroupe = v.findViewById(R.id.buttonCreateNewGroup);
        boutonCreerGroupe.setOnClickListener(this);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonCreateNewGroup){
            UserHelper.getUserByID(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User usr = documentSnapshot.toObject(User.class);
                    if (usr != null){
                        GroupeHelper.createGroupe("666","Team Andor Test", usr) ;
                        GroupeHelper.createGroupe("222","Club de pétanque", usr) ;
                        GroupeHelper.createGroupe("333","A3 ESEO", usr) ;
                        GroupeHelper.createGroupe("444","Poney club", usr) ;
                    }
                }
            });
        }

    }
}
