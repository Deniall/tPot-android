package com.cpssd.organizr.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.cpssd.organizr.fragment.Intro.CoursePickerSlide;
import com.cpssd.organizr.fragment.Intro.CuisinePickerSlide;
import com.cpssd.organizr.pojos.Settings.CuisinePref;
import com.github.paolorotolo.appintro.AppIntro;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;


public class MyIntro extends AppIntro {

    private SharedPreferences preferences;
    SharedPreferences.Editor e;


    protected CoursePickerSlide coursePickerSlide = new CoursePickerSlide();
    protected CuisinePickerSlide cuisinePickerSlide = new CuisinePickerSlide();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contextOfApplication = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);

        addSlide(coursePickerSlide);
        addSlide(cuisinePickerSlide);
        setFlowAnimation();


        showStatusBar(false);


    }



    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {

        e = preferences.edit();
        String courseCode = coursePickerSlide.getCourseCode();
        String year = String.valueOf(coursePickerSlide.getYear());
        String college = coursePickerSlide.getCollege();

        CuisinePref[] dietPrefs = cuisinePickerSlide.getDietPrefs();
        CuisinePref[] intolerancePrefs = cuisinePickerSlide.getIntolerancePrefs();

        for(CuisinePref dietPref : dietPrefs){
            e.putInt("pref_key_user_diet_"+dietPref.getName().toLowerCase(), dietPref.getValue());
        }
        for(CuisinePref intolerancePref : intolerancePrefs){
            e.putInt("pref_key_user_intolerance_"+intolerancePref.getName().toLowerCase(), intolerancePref.getValue());
        }



        String userId = preferences.getString("pref_key_user_sub", null);
        e.putString("pref_key_user_course", courseCode);
        e.putString("pref_key_user_year", year);
        e.putString("pref_key_user_college", college);
        e.apply();
        new setCoursePrefs().execute(college, courseCode, String.valueOf(year), userId);
        super.onDonePressed(currentFragment);
        finish();
    }

    private class setCoursePrefs extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            String college = params[0];
            String course = params[1];
            String year = params[2];
            String userId = params[3];
            new NetworkController().postCoursePrefs(college, course, Integer.parseInt(year), userId);
            return true;
        }

    }
}