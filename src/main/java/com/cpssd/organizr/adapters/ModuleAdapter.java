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
import com.cpssd.organizr.pojos.Settings.CuisinePref;
import com.cpssd.organizr.pojos.Settings.Module;

import java.util.List;

/**
 * Created by Niall on 4/20/2017.
 */

public class ModuleAdapter extends ArrayAdapter {

    List<Module> prefItems = null;
    Context context;

    public ModuleAdapter(Context context, List<Module> resource) {
        super(context, R.layout.calendar_items, resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.prefItems = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.modules_item, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.module_name);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.module_check);
        name.setText(prefItems.get(position).getModule());
        if(prefItems.get(position).getValue())
            cb.setChecked(true);
        else
            cb.setChecked(false);
        return convertView;
    }

}
