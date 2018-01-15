package com.example.midori.phonebookapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.midori.phonebookapp.R;
import com.example.midori.phonebookapp.model.Contact;

import java.util.List;

/**
 * Created by midori on 2018/01/15.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    private Context context;
    private int resource;
    private List<Contact> arrContact;

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contact> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrContact = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){

            convertView = LayoutInflater.from(context).inflate(R.layout.activity_number_phone_items,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imgAvatarContact = convertView.findViewById(R.id.img_avatar);
            viewHolder.tvNameContact = convertView.findViewById(R.id.tv_name);
            viewHolder.tvNumberContact = convertView.findViewById(R.id.tv_number);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Contact currentContact = arrContact.get(position);
        if (currentContact.getmAvatar() == 1 ){
            viewHolder.imgAvatarContact.setImageResource(R.mipmap.avatar_male);
        }else {
            viewHolder.imgAvatarContact.setImageResource(R.mipmap.avatar_famale);
        }
        viewHolder.tvNameContact.setText(currentContact.getmName());
        viewHolder.tvNumberContact.setText(currentContact.getmNumber());

        return convertView;
    }
    public class ViewHolder{
        ImageView imgAvatarContact;
        TextView tvNameContact;
        TextView tvNumberContact;
    }
}
