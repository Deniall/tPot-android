package com.cpssd.organizr.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cpssd.organizr.R;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;

/**
 * Created by Niall on 4/18/2017.
 */

public class SocietyMain extends AppCompatActivity {

    protected TextView welcomeText;
    protected RecyclerView eventsRecycler;
    protected Button addEventButton;
    private SharedPreferences preferences;
    SharedPreferences.Editor e;
    String society_email;
    String society_college;
    String soc_name;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        contextOfApplication = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        setContentView(R.layout.activity_society_main);
        society_email = preferences.getString("society_login_email", null);
        soc_name = preferences.getString("pref_key_soc_name", null);
        welcomeText = (TextView) findViewById(R.id.society_main_welcome);
        welcomeText.setText("Welcome, "+soc_name);
        addEventButton = (Button) findViewById(R.id.society_add_event);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(SocietyMain.this);
                builder.title("Add A Society Event");
                builder.customView(R.layout.society_add_event, true);
                builder.positiveText("Create");
                builder.negativeText("Cancel");
                builder.icon(getResources().getDrawable(R.drawable.ic_event_black_24dp));
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            new AddSocEvent().execute();
                    }
                });
                MaterialDialog dialog = builder.build();
                View view1 = dialog.getCustomView();
                dialog.show();
            }
        });




    }

    public class AddSocEvent extends AsyncTask<String , Void, String>{

        @Override
        protected String doInBackground(String... params) {
            new NetworkController().addSocEvent();
            return null;
        }
    }




}
