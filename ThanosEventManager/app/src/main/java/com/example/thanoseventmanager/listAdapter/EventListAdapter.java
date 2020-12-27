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
            holder.nomEventView = (TextView) convertView.findViewById(R.id.textView_nom);
            holder.nomGroupeView = (TextView) convertView.findViewById(R.id.textView_groupe);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Event event = this.listeEvent.get(position);
        holder.nomEventView.setText(event.getNom());
        holder.nomGroupeView.setText("Groupe: " + event.getGroupe().getNom());

        return convertView;
    }

    static class ViewHolder{
        TextView nomEventView;
        TextView nomGroupeView;
    }
}
