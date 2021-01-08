package com.example.thanoseventmanager.activities.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.thanoseventmanager.activities.createevent_activity.CreateEventActivity;
import com.example.thanoseventmanager.activities.groups_activity.GroupsActivity;
import com.example.thanoseventmanager.activities.profile_activity.ProfileActivity;
import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.activities.login_activity.LoginActivity;
import com.example.thanoseventmanager.api.GroupeHelper;
import com.example.thanoseventmanager.modeles.Event;
import com.example.thanoseventmanager.modeles.Groupe;
import com.example.thanoseventmanager.viewmodels.ViewModel_MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

        navController = Navigation.findNavController(this, R.id.fragment_nav_host);

        this.viewModel = new ViewModelProvider(this).get(ViewModel_MainActivity.class);
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

    public void onClickFragmentMapView() {
        navController.popBackStack(navController.getGraph().getStartDestination(), false);
    }

    public void onClickFragmentEventList() {
        navController.popBackStack(navController.getGraph().getStartDestination(), false);
        navController.navigate(R.id.fragmentEventList);
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