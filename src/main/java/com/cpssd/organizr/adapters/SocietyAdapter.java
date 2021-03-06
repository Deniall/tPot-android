package com.cpssd.organizr.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cpssd.organizr.R;
import com.cpssd.organizr.pojos.Settings.Calendar;

import java.util.List;

/**
 * Created by Niall on 4/20/2017.
 */

public class SocietyAdapter extends ArrayAdapter {

    List<String> prefItems = null;
    Context context;

    public SocietyAdapter(Context context, List<String> resource) {
        super(context, R.layout.society_item, resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.prefItems = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.society_item, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.society_name);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.society_check);
        name.setText(prefItems.get(position));
        cb.setChecked(true);
        return convertView;
    }

}
