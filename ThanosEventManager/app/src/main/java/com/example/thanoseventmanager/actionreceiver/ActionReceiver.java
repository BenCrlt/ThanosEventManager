package com.example.thanoseventmanager.actionreceiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.thanoseventmanager.firebase.GroupeHelper;
import com.example.thanoseventmanager.firebase.UserHelper;
import com.example.thanoseventmanager.modeles.Groupe;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

public class ActionReceiver extends BroadcastReceiver {

    String action,groupId,groupName;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);

        action=intent.getStringExtra("ACTION");
        groupId = intent.getStringExtra("GROUP_ID");
        groupName = intent.getStringExtra("GROUP_NAME");

        if(action.equals("ACCEPTED")){

            GroupeHelper.getGroupeById(groupId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    GroupeHelper.addUser(documentSnapshot.toObject(Groupe.class), FirebaseAuth.getInstance().getCurrentUser().getUid());
                    //TODO : une fois accepté ou refusé, supprimer l'invitation
                }
            });

            Toast.makeText(context,"Vous avez rejoint " + groupName,Toast.LENGTH_SHORT).show();
        }
        else if(action.equals("DECLINED")){
            UserHelper.clearListInvit(FirebaseAuth.getInstance().getCurrentUser().getUid());

            Toast.makeText(context,"Vous avez refusé l'invitation pour : " + groupName,Toast.LENGTH_SHORT).show();
        }
        //This is used to close the notification tray
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

}