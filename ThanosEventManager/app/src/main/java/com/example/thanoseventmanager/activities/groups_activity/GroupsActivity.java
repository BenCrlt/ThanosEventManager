package com.example.thanoseventmanager.activities.groups_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.thanoseventmanager.activities.profile_activity.ProfileActivity;
import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.activities.login_activity.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class GroupsActivity extends AppCompatActivity {
    /*
     *******************************************************
     * Cette activité contient l'hote de navigation de fragments
     * permettant de consulter ses groupes, créer un groupe
     * mais également d'inviter un autre utilisateur à un
     * groupe
     *******************************************************
     */
    NavController navController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        navController = Navigation.findNavController(this, R.id.fragment_nav_host_GroupsActivity);
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
}
