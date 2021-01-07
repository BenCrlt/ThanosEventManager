package com.example.thanoseventmanager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.thanoseventmanager.listAdapter.GroupListAdapter;
import com.example.thanoseventmanager.modeles.Groupe;

import java.util.ArrayList;
import java.util.List;

public class FragmentGroupList extends Fragment implements View.OnClickListener {
    private static final String TAG = "Hello";

    NavController navController;
    Button btn_createGroupe;

    public FragmentGroupList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Création d'un objet ListView correspondant à "listEvents" du layout activity_main_after_login.xml
        View v = inflater.inflate(R.layout.fragment_group_list, container, false);
        ListView listView = v.findViewById(R.id.listGroups);
        navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_host_GroupsActivity);

        //Création liste d'évènements à partir de la méthode getListData
        List<Groupe> listeGroupe = getListData();
        listView.setAdapter(new GroupListAdapter(getContext(),listeGroupe));

        btn_createGroupe = v.findViewById(R.id.btn_createGroupe);
        btn_createGroupe.setOnClickListener(this);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_createGroupe) {
            navController.popBackStack(navController.getGraph().getStartDestination(), false);
            navController.navigate(R.id.fragmentGroupCreate);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private  List<Groupe> getListData() {
        List<Groupe> list = new ArrayList<Groupe>();

        Groupe team_andor = new Groupe();
        team_andor.setNom("Team Andor");

        Groupe a3eseo = new Groupe();
        a3eseo.setNom("A3 ESEO");

        Groupe club_petanque = new Groupe();
        club_petanque.setNom("Team Petanque 49");

        Groupe hells = new Groupe();
        hells.setNom("Hell's Angels");

        list.add(team_andor);
        list.add(a3eseo);
        list.add(club_petanque);
        list.add(hells);

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
