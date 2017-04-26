package com.cpssd.organizr.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cpssd.organizr.R;
import com.cpssd.organizr.activity.NetworkController;
import com.cpssd.organizr.adapters.MapAdapter;
import com.cpssd.organizr.pojos.Maps.NearbyStop;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    protected Button getStops;
    protected RecyclerView stopsRecycler;

    private OnFragmentInteractionListener mListener;

    public MapsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    public static MapsFragment newInstance(String param1, String param2) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_maps, container, false);
        getStops = (Button) rootView.findViewById(R.id.fragment_maps_get_stops);
        getStops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getNearbyStops().execute();
            }
        });
        stopsRecycler = (RecyclerView) rootView.findViewById(R.id.fragment_maps_recycler);
        stopsRecycler.setLayoutManager(
                new LinearLayoutManager(getContext()));
        stopsRecycler.scrollToPosition(0);
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class getNearbyStops extends AsyncTask<String, Void, ArrayList<NearbyStop>> {

        protected void onPreExecute() {


        }

        protected ArrayList<NearbyStop> doInBackground(String... urls) {

            ArrayList<NearbyStop> nearbyStopArrayList = null;
            try {
                nearbyStopArrayList = new NetworkController().getNearbyStops("53.38574","-6.25723");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return nearbyStopArrayList;
        }

        protected void onPostExecute(final ArrayList<NearbyStop> nearbyStops) {
            if (!nearbyStops.isEmpty()){
                Log.d("GetNearbyStops", nearbyStops.get(0).getName());
            }
            MapAdapter adapter = new MapAdapter(nearbyStops);
            stopsRecycler.setAdapter(adapter);
        }

    }
}
