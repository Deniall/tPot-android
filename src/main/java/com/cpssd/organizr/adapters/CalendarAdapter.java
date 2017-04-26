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

import java.util.List;

/**
 * Created by Niall on 4/20/2017.
 */

public class CalendarAdapter extends ArrayAdapter {

    List<Calendar> prefItems = null;
    Context context;

    public CalendarAdapter(Context context, List<Calendar> resource) {
        super(context, R.layout.calendar_items, resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.prefItems = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.calendar_items, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.calendar_summary);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.calendar_check);
        name.setText(prefItems.get(position).getSummary());
        if(prefItems.get(position).getValue())
            cb.setChecked(true);
        else
            cb.setChecked(false);
        return convertView;
    }

}
