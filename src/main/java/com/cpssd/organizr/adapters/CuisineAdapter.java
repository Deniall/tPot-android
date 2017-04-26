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
import com.cpssd.organizr.pojos.Settings.CuisinePref;

public class CuisineAdapter extends ArrayAdapter{

    CuisinePref[] prefItems = null;
    Context context;

    public CuisineAdapter(Context context, CuisinePref[] resource) {
            super(context, R.layout.cuisine_item, resource);
            // TODO Auto-generated constructor stub
            this.context = context;
            this.prefItems = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.cuisine_item, parent, false);
            TextView name = (TextView) convertView.findViewById(R.id.cuisine_text);
            CheckBox cb = (CheckBox) convertView.findViewById(R.id.cuisine_check);
            name.setText(prefItems[position].getName());
            if(prefItems[position].getValue() == 1)
            cb.setChecked(true);
            else
            cb.setChecked(false);
            return convertView;
        }

}
