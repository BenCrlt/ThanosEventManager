package com.example.thanoseventmanager.activities.main_activity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.actionreceiver.ActionReceiver;
import com.example.thanoseventmanager.activities.createevent_activity.CreateEventActivity;
import com.example.thanoseventmanager.activities.groups_activity.GroupsActivity;
import com.example.thanoseventmanager.activities.profile_activity.ProfileActivity;
import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.activities.login_activity.LoginActivity;
import com.example.thanoseventmanager.activities.profile_activity.ProfileActivity;
import com.example.thanoseventmanager.firebase.GroupeHelper;
import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.modeles.User;
import com.example.thanoseventmanager.viewmodels.ViewModel_MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Hello";

    ViewModel_MainActivity viewModel;
    FragmentManager fm = getSupportFragmentManager();
    NavController navController;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "on create " + getLocalClassName()) ;

        //Listener sur la liste d'invitations à des groupes
        setInviteListListener();

        //Création du channel de notifications de l'app
        createNotificationChannel();

        navController = Navigation.findNavController(this, R.id.fragment_nav_host);

        this.viewModel = new ViewModelProvider(this).get(ViewModel_MainActivity.class);
        updateListeEvents();

        // Color of the button of the menu
        ((Button)findViewById(R.id.buttonMap)).setBackgroundColor(Color.GRAY);
        ((Button)findViewById(R.id.buttonList)).setBackgroundColor(Color.BLUE);
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

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "on start " + getLocalClassName()) ;
        //updateListeEvents();
    }

    public void updateListeEvents() {
        if (mAuth.getCurrentUser() != null) {
            GroupeHelper.getAllGroupesOfUser(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            Groupe groupeFound;
                            List<Event> listAllEvents = new ArrayList<Event>();
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                groupeFound = document.toObject(Groupe.class);
                                listAllEvents.addAll(groupeFound.getListeEvents());
                            }
                            viewModel.setListAllEvents(listAllEvents);
                        }
                    });
        }
    }

    private void createNotificationChannel() {
        // Créer le channel de notification (nécessaire depuis API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_Thanos";
            String description = "channel description of Thanos app";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("thanosNotificationsChannel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void setInviteListListener(){
        final DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {

                User usr = snapshot.toObject(User.class);

                for (int i=0; i<usr.getInvitList().size();i++) {
                    sendNotification(usr.getInvitList().get(i).getGroupToJoin());
                }


            }
        });
    }

    public void sendNotification(String idGroupSelected){

        GroupeHelper.getGroupeById(idGroupSelected).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Groupe groupSelected = documentSnapshot.toObject(Groupe.class);

                //Création d'une intent correspondant au choix d'utilisateur sur la notification
                Intent acceptIntent = new Intent(getApplicationContext(), ActionReceiver. class ) ;
                Intent declineIntent = new Intent(getApplicationContext(), ActionReceiver. class ) ;

                declineIntent.putExtra("ACTION","DECLINED");
                acceptIntent.putExtra( "ACTION" , "ACCEPTED" ) ;

                declineIntent.putExtra("GROUP_ID",groupSelected.getId());
                acceptIntent.putExtra( "GROUP_ID" , groupSelected.getId()) ;

                declineIntent.putExtra("GROUP_NAME",groupSelected.getNom());
                acceptIntent.putExtra( "GROUP_NAME" , groupSelected.getNom()) ;

                PendingIntent acceptPendingIntent = PendingIntent.getBroadcast(getApplicationContext(),1,acceptIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent declinePendingIntent = PendingIntent.getBroadcast(getApplicationContext(),2,declineIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                //Création d'une notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "thanosNotificationsChannel")
                        .setSmallIcon(R.mipmap.ic_launcher_foreground)
                        .setContentTitle("Invitation à un groupe")
                        .setContentText("Vous avez été invité(e) à rejoindre le groupe " + groupSelected.getNom())
                        .addAction(R.drawable. ic_launcher_foreground , "Accepter" , acceptPendingIntent)
                        .addAction(R.drawable. ic_launcher_foreground , "Refuser" , declinePendingIntent)
                        .setAutoCancel(true);

                //Faire apparaître la notif
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                notificationManager.notify(1, builder.build());
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "on stop " + getLocalClassName()) ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "on destroy " + getLocalClassName()) ;
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "on pause " + getLocalClassName()) ;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "on resume " + getLocalClassName()) ;
    }

    public void onClickFragmentMapView(View v) {
        navController.popBackStack(navController.getGraph().getStartDestination(), false);
        // Modification des couleurs des boutons des menus pour que celui qui est appuyé soit gris et l'autre non appuyé soit bleu
        ((Button)findViewById(R.id.buttonMap)).setBackgroundColor(Color.GRAY);
        ((Button)findViewById(R.id.buttonList)).setBackgroundColor(Color.BLUE);
    }

    @SuppressLint("ResourceType")
    public void onClickFragmentEventList(View v) {
        navController.popBackStack(navController.getGraph().getStartDestination(), false);
        navController.navigate(R.id.fragmentEventList);
        // Modification des couleurs des boutons des menus pour que celui qui est appuyé soit gris et l'autre non appuyé soit bleu
        ((Button)findViewById(R.id.buttonList)).setBackgroundColor(Color.GRAY);
        ((Button)findViewById(R.id.buttonMap)).setBackgroundColor(Color.BLUE);

    }

    public void onClickFragmentEventView() {
        navController.popBackStack(navController.getGraph().getStartDestination(), false);
        navController.navigate(R.id.fragmentEventView);
    }

    public void onClickCreateEvent(View v) {
        Intent intent = new Intent(this, CreateEventActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}