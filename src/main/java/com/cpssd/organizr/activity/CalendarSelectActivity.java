package com.cpssd.organizr.activity;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.cpssd.organizr.R;
import com.cpssd.organizr.adapters.CalendarAdapter;
import com.cpssd.organizr.pojos.Settings.Calendar;

import java.util.ArrayList;

import static com.cpssd.organizr.activity.MainActivity.CURRENT_TAG;
import static com.cpssd.organizr.activity.MainActivity.calendars;



public class CalendarSelectActivity extends AppCompatActivity {

    protected ListView calendarList;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public static Context contextOfApplication;

    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_calendar_select);
        contextOfApplication = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        editor = preferences.edit();

        calendarList = (ListView) findViewById(R.id.calendar_listview);
        ArrayList<Calendar> cals = new ArrayList<Calendar>();
        for (int i = 0; i < calendars.size(); i++){
            Calendar calendar = calendars.get(i);
            calendar.setSummary(preferences.getString(calendar.getSummary(), null));
            Log.d(CURRENT_TAG, preferences.getString(calendar.getSummary(), null));
            calendar.setValue(calendars.get(i).getValue());
            calendar.setId(preferences.getString(calendar.getId(), null));
            cals.add(calendar);
        }

        CalendarAdapter calendarAdapter = new CalendarAdapter(CalendarSelectActivity.this, cals);
        calendarList.setAdapter(calendarAdapter);

    }

}
