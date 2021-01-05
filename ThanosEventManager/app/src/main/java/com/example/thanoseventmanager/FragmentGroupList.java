package com.example.thanoseventmanager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thanoseventmanager.listAdapter.GroupListAdapter;
import com.example.thanoseventmanager.modeles.Groupe;

import java.util.ArrayList;
import java.util.List;

public class FragmentGroupList extends Fragment {
    private static final String TAG = "Hello";

    public FragmentGroupList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Création d'un objet ListView correspondant à "listEvents" du layout activity_main_after_login.xml
        View v = inflater.inflate(R.layout.fragment_group_list, container, false);
        ListView listView = v.findViewById(R.id.listGroups);

        //Création liste d'évènements à partir de la méthode getListData
        List<Groupe> listeGroupe = getListData();
        listView.setAdapter(new GroupListAdapter(getContext(),listeGroupe));

        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private  List<Groupe> getListData() {
        List<Groupe> list = new ArrayList<Groupe>();

        Groupe team_andor = new Groupe();
        team_andor.setNom("Team Andor");

        list.add(team_andor);

        return list;
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
}
