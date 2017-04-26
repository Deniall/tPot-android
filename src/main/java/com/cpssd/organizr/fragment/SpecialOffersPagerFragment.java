package com.cpssd.organizr.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpssd.organizr.R;
import com.cpssd.organizr.activity.NetworkController;
import com.cpssd.organizr.adapters.OfferAdapter;
import com.cpssd.organizr.pojos.Offers.Offer;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class SpecialOffersPagerFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    protected RecyclerView offerRecycler;


    private int position;

    public static SpecialOffersPagerFragment newInstance(int position) {
        SpecialOffersPagerFragment f = new SpecialOffersPagerFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
        if(position == 0){
            String shop = "supervalu";
            new getSpecialOffers().execute(shop);
        }
        else{
            String shop = "tesco";
            new getSpecialOffers().execute(shop);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_specialoffers_pager ,container,false);
        ViewCompat.setElevation(rootView, 50);
        offerRecycler = (RecyclerView) rootView.findViewById(R.id.fragment_offers_recycler);
        offerRecycler.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        offerRecycler.scrollToPosition(0);
        return rootView;
    }

    public class getSpecialOffers extends AsyncTask<String, Void, ArrayList<Offer>>{

        @Override
        protected ArrayList<Offer> doInBackground(String... params) {
            String shop = params[0];
            ArrayList<Offer> offers = new NetworkController().getOffers(shop);
            return offers;
        }

        protected void onPostExecute(ArrayList<Offer> offers) {
            if (!offers.isEmpty()){
                Log.d(TAG, offers.get(0).getCategory());
            }
            OfferAdapter adapter = new OfferAdapter(offers);
            offerRecycler.setAdapter(adapter);
        }
    }
}
