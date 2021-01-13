package com.example.thanoseventmanager.actionreceiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);

        String action=intent.getStringExtra("ACTION");
        if(action.equals("ACCEPTED")){
            performAccepted();
            Toast.makeText(context,"Vous avez rejoint",Toast.LENGTH_SHORT).show();
        }
        else if(action.equals("DECLINED")){
            performDeclined();
            Toast.makeText(context,"Vous avez refusé",Toast.LENGTH_SHORT).show();
        }
        //This is used to close the notification tray
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    public void performAccepted(){

    }

    public void performDeclined(){

    }

}