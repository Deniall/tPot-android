package com.cpssd.organizr.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.cpssd.organizr.R;
import com.cpssd.organizr.adapters.ModuleAdapter;
import com.cpssd.organizr.pojos.Settings.Module;

import java.util.ArrayList;

import static com.cpssd.organizr.activity.MainActivity.modules;

/**
 * Created by Niall on 4/20/2017.
 */

public class ModuleSelectActivity extends AppCompatActivity {

    protected ListView moduleList;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public static Context contextOfApplication;

    @Override
    public void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        setContentView(R.layout.activity_module_select);
        contextOfApplication = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        editor = preferences.edit();

        moduleList = (ListView) findViewById(R.id.module_listview);
        ArrayList<Module> mods = new ArrayList<>();
        for (int i = 0; i < modules.size(); i++){
            Module module = modules.get(i);
            module.setModule(module.getModule());
            Log.d("ModuleSelect", module.getModule());
            module.setValue(modules.get(i).getValue());
            mods.add(module);
        }

        ModuleAdapter moduleAdapter = new ModuleAdapter(ModuleSelectActivity.this, modules);
        moduleList.setAdapter(moduleAdapter);

    }
}
