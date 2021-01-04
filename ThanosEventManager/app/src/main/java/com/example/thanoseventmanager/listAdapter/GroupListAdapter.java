package com.example.thanoseventmanager.listAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.modeles.Groupe;

import java.util.List;

public class GroupListAdapter extends BaseAdapter {

    private List<Groupe> listeGroupe;
    private Context context;
    private LayoutInflater layoutInflater;

    public GroupListAdapter(Context aContext,  List<Groupe> listData) {
        this.context = aContext;
        this.listeGroupe = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listeGroupe.size();
    }

    @Override
    public Object getItem(int position) {
        return listeGroupe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupListAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listegroupe_groupe_layout, null);
            holder = new GroupListAdapter.ViewHolder();
            holder.imageGroupe = (ImageView) convertView.findViewById(R.id.imageView_Groupe);
            holder.nomGroupeView = (TextView) convertView.findViewById(R.id.textView_nomGroupe);
            holder.countMembersView = (TextView) convertView.findViewById(R.id.textView_members);
            convertView.setTag(holder);
        } else {
            holder = (GroupListAdapter.ViewHolder) convertView.getTag();
        }

        Groupe groupe = this.listeGroupe.get(position);

        holder.nomGroupeView.setText(groupe.getNom());

        //Pour l'image
        //int imageId = this.getMipmapResIdByName(groupe.getImage());

        //holder.imageGroupe.setImageResource(imageId);

        return convertView;
    }

    // Retrouver l'ID d'une image à l'aide du nom du fichie rimage dans /mipmap
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        return resID;
    }

    static class ViewHolder{
        ImageView imageGroupe;
        TextView nomGroupeView;
        TextView countMembersView;
    }
}