package com.example.thanoseventmanager.listAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.modeles.Event;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventListAdapter extends BaseAdapter {

    /*
     *******************************************************
     * Cette classe un adapter permettant de peupler une
     * listView avec les éléments d'une java bean
     *******************************************************
     */

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

    public void setListeEvent(List<Event> newListeEvents) {
        listeEvent = newListeEvents;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listeevent_event_layout, null);
            holder = new ViewHolder();
            holder.imageEvent = convertView.findViewById(R.id.imageView);
            holder.nomEventView = convertView.findViewById(R.id.textView_nom);
            holder.dateEventView = convertView.findViewById(R.id.textView_date);
            holder.nomGroupeView = convertView.findViewById(R.id.textView_groupe);
            holder.imageMarker = convertView.findViewById(R.id.markerView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Event event = this.listeEvent.get(position);

        holder.nomEventView.setText(event.getNom());
        holder.nomGroupeView.setText("Groupe: " + event.getGrpName());

        //Affichage de la date
        java.text.DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        java.text.DateFormat formatHeure = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        String date = formatDate.format(event.getDate());
        String heure = formatHeure.format(event.getDate());
        String stringDate = "Date : " + date + "   Heure : " + heure;
        holder.dateEventView.setText(stringDate);

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
        return context.getResources().getIdentifier(resName , "mipmap", pkgName);
    }

    static class ViewHolder{
        ImageView imageEvent;
        TextView nomEventView;
        TextView dateEventView;
        TextView nomGroupeView;
        ImageView imageMarker;
    }
}
