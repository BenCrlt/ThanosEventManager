package com.example.thanoseventmanager.custom;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.thanoseventmanager.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater = null;

    public CustomInfoWindowAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View view = inflater.inflate(R.layout.custom_info_window,null);

        TextView title = view.findViewById(R.id.title);
        TextView snippet = view.findViewById(R.id.snippet);

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());

        return view;
    }
}
