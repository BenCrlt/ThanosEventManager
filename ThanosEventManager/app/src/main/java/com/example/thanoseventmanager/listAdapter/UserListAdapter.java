package com.example.thanoseventmanager.listAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanoseventmanager.R;
import com.example.thanoseventmanager.modeles.User;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private List<User> listeUser;
    private Context context;
    private LayoutInflater layoutInflater;

    public UserListAdapter(Context aContext,  List<User> listData) {
        this.context = aContext;
        this.listeUser = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listeUser.size();
    }

    @Override
    public Object getItem(int position) {
        return listeUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserListAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listeuser_user_layout, null);
            holder = new UserListAdapter.ViewHolder();
            holder.imageUser = (ImageView) convertView.findViewById(R.id.userListView_userProfilePicture);
            holder.pseudoUserView = (TextView) convertView.findViewById(R.id.userListView_userName);
            holder.numberUserView = (TextView) convertView.findViewById(R.id.userListView_userPhoneNumber);
            convertView.setTag(holder);
        } else {
            holder = (UserListAdapter.ViewHolder) convertView.getTag();
        }

        User user = this.listeUser.get(position);

        holder.pseudoUserView.setText(user.getPseudo());
        holder.numberUserView.setText(user.getNumero());

        //Pour le logo de l'event
        int imageId = this.getMipmapResIdByName("hipster");
        holder.imageUser.setImageResource(imageId);

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
        ImageView imageUser;
        TextView pseudoUserView;
        TextView numberUserView;
    }
}
