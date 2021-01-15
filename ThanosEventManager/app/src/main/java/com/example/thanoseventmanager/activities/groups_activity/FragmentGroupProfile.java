package com.example.thanoseventmanager.activities.groups_activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.firebase.UserHelper;
import com.example.thanoseventmanager.listAdapter.UserListAdapter;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.User;
import com.example.thanoseventmanager.viewmodels.ViewModel_GroupsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FragmentGroupProfile extends Fragment implements View.OnClickListener{
    /*
     *******************************************************
     * Ce fragment est appelé lorsque l'utilisateur clique
     * sur un groupe de la vue liste. Au sein de ce fragment
     * l'utilisateur peut inviter un membre via son numéro
     * de téléphone
     *******************************************************
     */

    ViewModel_GroupsActivity viewModel;
    ListView listView;
    Groupe groupSelected;
    Button inviteButton;
    EditText phoneNumber;

    private static final String TAG = "Hello";

    public void FragmentGroupProfile(){
        //empty constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_profile, container, false);

        //Récupération des informations du groupe
        TextView nomGroupe = (TextView)v.findViewById(R.id.fragmentGroupProfile_groupName);
        TextView usersCount = (TextView)v.findViewById(R.id.fragmentGroupProfile_groupMembersCount);
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel_GroupsActivity.class);
        listView = (ListView)v.findViewById(R.id.listView_GroupProfile_userList);
        phoneNumber = (EditText)v.findViewById(R.id.groupProfile_editTextPhone);
        inviteButton = (Button)v.findViewById(R.id.groupProfile_inviteButton);
        inviteButton.setOnClickListener(this);

        //récupération de l'objet (Java) groupe selectionné
        groupSelected = viewModel.getGroupSelected().getValue();

        nomGroupe.setText(groupSelected.getNom());
        usersCount.setText(String.valueOf(groupSelected.getListeIdUsers().size()) + " membre(s) dans ce groupe");

        populateUserList();

        // Inflate the layout for this fragment
        return v;
    }

    public void populateUserList(){
        UserHelper.getAllUsersFromGroupe(groupSelected).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                listView.setAdapter(new UserListAdapter(getContext(),queryDocumentSnapshots.toObjects(User.class)));
            }
        }) ;
    }



    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.groupProfile_inviteButton:
                String phoneStr = "+33" + phoneNumber.getText().toString();

                //Récupération de l'USER correspondant à ce numéro de téléphone
                UserHelper.getUserByPhone(phoneStr).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<User> listTest = queryDocumentSnapshots.toObjects(User.class);
                        if (listTest.size() > 0){
                            User user = listTest.get(0);

                            //Ajouter une invitation à la liste d'invitations de l'USER
                            UserHelper.addInviteToUser(user,groupSelected.getId());
                            Toast.makeText(getContext(),"Invitation envoyée à " + user.getPseudo(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }) ;


            default:
                break;
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
