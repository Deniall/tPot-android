package com.cpssd.organizr.fragment.Intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cpssd.organizr.R;
import com.cpssd.organizr.adapters.CuisineAdapter;
import com.cpssd.organizr.pojos.Settings.CuisinePref;

/**
 * Created by Niall on 4/13/2017.
 */

public class CuisinePickerSlide extends android.support.v4.app.Fragment {

    CuisinePref[] dietPrefs = new CuisinePref[5];
    CuisinePref[] intolerancePrefs = new CuisinePref[11];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.intro_cuisine_picker, container, false);
        ListView dietListView = (ListView) rootView.findViewById(R.id.diet_listview);
        ListView intoleranceListView = (ListView) rootView.findViewById(R.id.intolerances_listview);
        dietPrefs = new CuisinePref[5];
        intolerancePrefs = new CuisinePref[11];

        dietPrefs[0] = new CuisinePref("Vegan", 0);
        dietPrefs[1] = new CuisinePref("Vegetarian", 0);
        dietPrefs[2] = new CuisinePref("Pescetarian", 0);
        dietPrefs[3] = new CuisinePref("Lacto-Vegetarian", 0);
        dietPrefs[4] = new CuisinePref("Ovo-Vegetarian", 0);

        intolerancePrefs[0] = new CuisinePref("Dairy", 0);
        intolerancePrefs[1] = new CuisinePref("Eggs", 0);
        intolerancePrefs[2] = new CuisinePref("Gluten", 0);
        intolerancePrefs[3] = new CuisinePref("Peanut", 0);
        intolerancePrefs[4] = new CuisinePref("Sesame", 0);
        intolerancePrefs[5] = new CuisinePref("Seafood", 0);
        intolerancePrefs[6] = new CuisinePref("Shellfish", 0);
        intolerancePrefs[7] = new CuisinePref("Soy", 0);
        intolerancePrefs[8] = new CuisinePref("Sulfites", 0);
        intolerancePrefs[9] = new CuisinePref("Tree Nuts", 0);
        intolerancePrefs[10] = new CuisinePref("Wheat", 0);
        CuisineAdapter dietAdapter = new CuisineAdapter(getActivity(), dietPrefs);
        CuisineAdapter intoleranceAdapter = new CuisineAdapter(getActivity(), intolerancePrefs);
        dietListView.setAdapter(dietAdapter);
        intoleranceListView.setAdapter(intoleranceAdapter);

        return rootView;

    }

    public CuisinePref[] getDietPrefs(){
        return dietPrefs;
    }

    public CuisinePref[] getIntolerancePrefs(){
        return intolerancePrefs;
    }

}
