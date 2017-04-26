package com.cpssd.organizr.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.cpssd.organizr.R;
import com.cpssd.organizr.pojos.Events.End;
import com.cpssd.organizr.pojos.Events.Event;
import com.cpssd.organizr.pojos.Events.Start;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;


/**
 * Created by Niall on 05/12/2016.
 * Handles creating a new event and sending it to the POST endpoint on the server with the
 * userId and am event object.
 */

public class AddEvent extends AppCompatActivity {

    private String TAG = "activity.AddEvent";
    public String SUB;
    public SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_event);
        contextOfApplication = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        SUB = preferences.getString("sub", "Error");
        final EditText titleText = (EditText) findViewById(R.id.new_event_title);
        final EditText descText = (EditText) findViewById(R.id.new_event_description);
        final DatePicker datePicker = (DatePicker) findViewById(R.id.new_event_date);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.new_event_time);
        Button submitButton = (Button) findViewById(R.id.submit_event);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(View v) {
                String new_title = titleText.getText().toString();
                String new_desc = descText.getText().toString();
                String new_date_year = Integer.toString(datePicker.getYear());
                String new_date_month = Integer.toString(datePicker.getMonth()+1);
                String new_date_day = Integer.toString(datePicker.getDayOfMonth());
                String new_time_hour = Integer.toString(timePicker.getHour());
                String new_time_minute = Integer.toString(timePicker.getMinute());
                String dateTimeStart = new_date_year+'-'+new_date_month+'-'+new_date_day+'T'+new_time_hour+':'+new_time_minute+":00Z";
                String dateTimeEnd = new_date_year+'-'+new_date_month+"-"+new_date_day+'T'+new_time_hour+':'+new_time_minute+":10Z";
                Log.d(TAG, dateTimeStart + dateTimeEnd);
                new sendEvent().execute(new_title, new_desc, dateTimeStart, dateTimeEnd, SUB);

            }
        });
    }

    class sendEvent extends AsyncTask<String, Void, Void> {
        protected void onPreExecute() {
        }

        protected Void doInBackground(String... params) {

            String new_title = params[0];
            String new_desc = params[1];
            String dateTimeStart = params[2];
            String dateTimeEnd = params[3];
            Start start = new Start(dateTimeStart);
            End end = new End(dateTimeEnd);
            String SUB = params[4];
            Event newEvent = new Event();
            newEvent.setSummary(new_title);
            newEvent.setDescription(new_desc);
            newEvent.setStart(start);
            newEvent.setEnd(end);
            new NetworkController().postEvent(newEvent, SUB);
            getFragmentManager().popBackStackImmediate();
            return  null;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getFragmentManager().popBackStackImmediate();
    }
}
