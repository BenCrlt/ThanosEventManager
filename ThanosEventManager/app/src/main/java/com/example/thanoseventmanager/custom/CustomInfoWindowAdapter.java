package com.example.thanoseventmanager.custom;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.modeles.Event;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final Context context;

    public CustomInfoWindowAdapter(Context context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.custom_info_window, null);
        Event event = (Event) marker.getTag();

        ImageView icon1 = view.findViewById(R.id.icon1);
        ImageView icon2 = view.findViewById(R.id.icon2);
        TextView title = view.findViewById(R.id.title);
        TextView snippet = view.findViewById(R.id.snippet);

        if (event != null) {

            //Récupération de l'id de l'image de l'event
            int imageEvent = this.getMipmapResIdByName(event.getImage());

            if (imageEvent != 0) {
                icon1.setImageResource(imageEvent);
                icon2.setImageResource(imageEvent);
            }
        }

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());

        return view;
    }

    // Retrouver l'ID d'une image à l'aide du nom du fichier image dans /mipmap
    private int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        return context.getResources().getIdentifier(resName , "mipmap", pkgName);
    }
}
