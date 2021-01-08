package com.example.thanoseventmanager.activities.groups_activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.firebase.GroupeHelper;
import com.example.thanoseventmanager.listAdapter.GroupListAdapter;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.viewmodels.ViewModel_GroupsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

public class FragmentGroupList extends Fragment implements View.OnClickListener {
    private static final String TAG = "Hello";

    NavController navController;
    Button btn_createGroupe;
    ListView listView;
    ViewModel_GroupsActivity viewModel;

    public FragmentGroupList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Création d'un objet ListView correspondant à "listEvents" du layout activity_main_after_login.xml
        View v = inflater.inflate(R.layout.fragment_group_list, container, false);
        listView = v.findViewById(R.id.listGroups);
        listView.addHeaderView(setTitle("Mes groupes"));

        navController = Navigation.findNavController(getActivity(), R.id.fragment_nav_host_GroupsActivity);

        btn_createGroupe = v.findViewById(R.id.btn_createGroupe);
        btn_createGroupe.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Groupe groupSelected = (Groupe)listView.getItemAtPosition(position);
                viewModel = new ViewModelProvider(getActivity()).get(ViewModel_GroupsActivity.class);
                viewModel.setGroupSelected(groupSelected);
                navController.popBackStack(navController.getGraph().getStartDestination(), false);
                navController.navigate(R.id.fragmentGroupProfile);
            }
        });

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

    @Override
    public void onStart() {
        super.onStart();

        Log.i(TAG, "on start " + getClass().getSimpleName());

        GroupeHelper.getAllGroupesOfUser(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                listView.setAdapter(new GroupListAdapter(getContext(),queryDocumentSnapshots.toObjects(Groupe.class)));
            }
        });
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

    public TextView setTitle(String name){
        TextView textView = new TextView(this.getContext());
        textView.setText(name);
        textView.setTextSize(30);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setHeight(150);

        return textView;
    }
}
