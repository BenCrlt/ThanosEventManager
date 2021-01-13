package com.example.thanoseventmanager.activities.groups_activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.actionreceiver.ActionReceiver;
import com.example.thanoseventmanager.firebase.UserHelper;
import com.example.thanoseventmanager.listAdapter.UserListAdapter;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.User;
import com.example.thanoseventmanager.viewmodels.ViewModel_GroupsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FragmentGroupProfile extends Fragment implements View.OnClickListener{

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

    public void sendNotification(){
        //Création d'une intent correspondant au choix d'utilisateur sur la notification
        Intent acceptIntent = new Intent(this.getContext(), ActionReceiver. class ) ;
        Intent declineIntent = new Intent(this.getContext(), ActionReceiver. class ) ;

        declineIntent.putExtra("ACTION","DECLINED");
        acceptIntent.putExtra( "ACTION" , "ACCEPTED" ) ;

        PendingIntent acceptPendingIntent = PendingIntent.getBroadcast(this.getContext(),1,acceptIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent declinePendingIntent = PendingIntent.getBroadcast(this.getContext(),2,declineIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        //Création d'une notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getContext(), "thanosNotificationsChannel")
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle("Invitation à un groupe")
                .setContentText("Vous avez été invité(e) à rejoindre le groupe " + groupSelected.getNom())
                .addAction(R.drawable. ic_launcher_foreground , "Accepter" , acceptPendingIntent)
                .addAction(R.drawable. ic_launcher_foreground , "Refuser" , declinePendingIntent)
                .setAutoCancel(true);

        //Faire apparaître la notif
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getContext());
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.groupProfile_inviteButton:
                String phoneStr = "+33" + phoneNumber.getText().toString();

                UserHelper.getUserByPhone(phoneStr).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<User> listTest = queryDocumentSnapshots.toObjects(User.class);
                        if (listTest.size() > 0){
                            Toast.makeText(getContext(),listTest.get(0).getPseudo(),Toast.LENGTH_SHORT).show();
                            //User usr = listTest.get(0);
                            //TODO ajouter un invit a firebase

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
