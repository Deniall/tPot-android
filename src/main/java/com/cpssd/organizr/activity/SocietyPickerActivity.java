package com.cpssd.organizr.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cpssd.organizr.R;
import com.cpssd.organizr.adapters.SocietyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niall on 4/20/2017.
 */

public class SocietyPickerActivity extends AppCompatActivity {
    protected ListView societyList;
    protected List<String> societies = MainActivity.societies;
    public List allSocs;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public static Context contextOfApplication;
    protected Button addSocs;
    ListView addSocsListView;
    public String SUB;
    MaterialDialog dialog;
    SocietyAdapter societyAdapter;

    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_societies_select);
        final ArrayList<String> socs = new ArrayList<>();
        contextOfApplication = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        SUB = preferences.getString("pref_key_user_sub", null);
        new getAllSocs().execute();
        addSocs = (Button) findViewById(R.id.add_socs);
        addSocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(SocietyPickerActivity.this);
                builder.title("DCU Societies");
                builder.customView(R.layout.add_societies_popup, true);
                builder.positiveText("Apply");
                builder.icon(getResources().getDrawable(R.drawable.ic_event_black_24dp));
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            for(Object soc : allSocs){
                                socs.add(soc.toString());
                                societyAdapter.notifyDataSetChanged();
                            }
                    }
                });
                dialog = builder.build();
                View view1 = dialog.getCustomView();
                addSocsListView = (ListView) view1.findViewById(R.id.add_society_listview);
                new getAllSocs().execute();
                societyAdapter = new SocietyAdapter(SocietyPickerActivity.this, allSocs);
                addSocsListView.setAdapter(societyAdapter);
                dialog.show();

            }
        });
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        editor = preferences.edit();

        societyList = (ListView) findViewById(R.id.society_listview);
        if(allSocs != null){
            for (int i = 0; i < allSocs.size(); i++){
                String society = (String) allSocs.get(i);
                socs.add(society);
            }
        }

        societyAdapter = new SocietyAdapter(SocietyPickerActivity.this, socs);
        societyList.setAdapter(societyAdapter);


    }

    public class getAllSocs extends AsyncTask<Object, Object, List<String>> {

        @Override
        protected List<String> doInBackground(Object... params) {
            allSocs = new NetworkController().getSocs(SUB);
            return allSocs;
        }

        @Override
        protected void onPostExecute(List<String> socs) {
            Log.d("TEST", socs.toString());
            societyAdapter = new SocietyAdapter(SocietyPickerActivity.this, socs);
            societyList.setAdapter(societyAdapter);

        }
    }
}
