package com.example.thanoseventmanager.listAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.modeles.Event;

import java.util.List;

public class EventListAdapter extends BaseAdapter {

    private List<Event> listeEvent;
    private Context context;
    private LayoutInflater layoutInflater;

    public EventListAdapter(Context aContext,  List<Event> listData) {
        this.context = aContext;
        this.listeEvent = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listeEvent.size();
    }

    @Override
    public Object getItem(int position) {
        return listeEvent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listeevent_event_layout, null);
            holder = new ViewHolder();
            holder.imageEvent = (ImageView) convertView.findViewById(R.id.imageView);
            holder.nomEventView = (TextView) convertView.findViewById(R.id.textView_nom);
            holder.dateEventView = (TextView)convertView.findViewById(R.id.textView_date);
            holder.nomGroupeView = (TextView) convertView.findViewById(R.id.textView_groupe);
            holder.imageMarker = (ImageView) convertView.findViewById(R.id.markerView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Event event = this.listeEvent.get(position);

        holder.nomEventView.setText(event.getNom());
        holder.dateEventView.setText(event.getDate().toString());
        holder.nomGroupeView.setText("Groupe: " + event.getGroupe().getNom());

        //Pour le logo de l'event
        int imageId = this.getMipmapResIdByName(event.getImage());
        holder.imageEvent.setImageResource(imageId);

        //Pour indiquer si l'event est affiché sur la map ou non
        if (event.getFlagMarker()) {
            holder.imageMarker.setVisibility(View.VISIBLE);
        } else {
            holder.imageMarker.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    // Retrouver l'ID d'une image à l'aide du nom du fichier image dans /mipmap
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        return resID;
    }

    static class ViewHolder{
        ImageView imageEvent;
        TextView nomEventView;
        TextView dateEventView;
        TextView nomGroupeView;
        ImageView imageMarker;
    }
}
