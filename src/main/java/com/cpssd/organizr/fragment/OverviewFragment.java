package com.cpssd.organizr.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpssd.organizr.R;
import com.cpssd.organizr.activity.AddEvent;
import com.cpssd.organizr.activity.AddTransaction;
import com.cpssd.organizr.activity.NetworkController;
import com.cpssd.organizr.activity.RecipeDetailActivity;
import com.cpssd.organizr.adapters.FinanceDayAdapter;
import com.cpssd.organizr.adapters.LectureAdapter;
import com.cpssd.organizr.adapters.OverviewFoodAdapter;
import com.cpssd.organizr.adapters.RecyclerItemClickListener;
import com.cpssd.organizr.pojos.Events.Lecture;
import com.cpssd.organizr.pojos.Finance.Transaction;
import com.cpssd.organizr.pojos.Recipes.Recipe;

import org.json.JSONException;
import org.threeten.bp.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OverviewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "OverViewFragment";

    private String mParam1;
    private String mParam2;

    protected ArrayList<Recipe> mDataset;

    String EVENTS_URL = "https://tpot.space/tasks/day/";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
    String currentDate = sdf.format(new Date());
    public String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());

    int currentIntDate = Integer.parseInt(currentDate);
    Date date;
    public String SUB;

    protected RecyclerView FoodRecyclerView;
    protected RecyclerView LectureRecycler;
    protected RecyclerView FinancesRecyclerView;
    protected TextView DayTitleView;
    protected TextView NoEvents;
    protected TextView NoRecipes;
    protected TextView addEvent;
    protected TextView addRecipe;
    protected TextView addTransaction;
    protected TextView dayBalance;
    protected RelativeLayout foodProgressBar;
    protected RelativeLayout lectureProgressBar;
    protected RelativeLayout transactionProgressBar;

    protected LocalDate localDate;
    protected String dateF;
    protected ArrayList<Transaction> transactionDataSet;

    protected RecyclerView.LayoutManager mLayoutManager;
    private OnFragmentInteractionListener mListener;
    enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;

    protected OverviewFragment.LayoutManagerType mCurrentLayoutManagerType;

    public OverviewFragment() throws ParseException {
        // Required empty public constructor
        try {
            date = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OverviewFragment.
     */
    public static OverviewFragment newInstance(String param1, String param2) throws ParseException {

        OverviewFragment fragment = new OverviewFragment();
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
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        SUB = preferences.getString("sub", "Error");
        localDate = LocalDate.now();
        dateF = localDate.toString();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
        DayTitleView = (TextView) rootView.findViewById(R.id.overview_day_title);

        dayBalance = (TextView) rootView.findViewById(R.id.overview_finances_balance);

        lectureProgressBar = (RelativeLayout) rootView.findViewById(R.id.eventLoadingBar);
        foodProgressBar = (RelativeLayout) rootView.findViewById(R.id.foodLoadingBar);
        transactionProgressBar = (RelativeLayout) rootView.findViewById(R.id.transactionLoadingBar);

        FoodRecyclerView = (RecyclerView) rootView.findViewById(R.id.overview_food_recycler);
        FinancesRecyclerView = (RecyclerView) rootView.findViewById(R.id.overview_finances_recycler);

        NoEvents = (TextView) rootView.findViewById(R.id.overview_events_none);
        NoRecipes = (TextView) rootView.findViewById(R.id.overview_food_none);

        addEvent = (TextView) rootView.findViewById(R.id.overview_events_add);
        addRecipe = (TextView) rootView.findViewById(R.id.overview_food_add);
        addTransaction = (TextView) rootView.findViewById(R.id.overview_finances_add);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddEvent.class);
                startActivity(intent);
            }
        });
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipesFragment nextFrag= new RecipesFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, nextFrag, TAG);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });
        addTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTransaction.class);
                startActivity(intent);
            }
        });

        NoEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddEvent.class);
                startActivity(intent);
            }
        });
        NoRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipesFragment nextFrag= new RecipesFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, nextFrag, TAG);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });


        FoodRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        final Recipe newRecipe = mDataset.get(position);
                        Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
                        intent.putExtra("recipeImage", String.valueOf(newRecipe.getImage()));
                        intent.putExtra("recipeId", String.valueOf(newRecipe.getId()));
                        startActivity(intent);
                    }
                }));

        LectureRecycler = (RecyclerView) rootView.findViewById(R.id.overview_events_recycler);
        Log.d(TAG, dayOfWeek);
        DayTitleView.setText(dayOfWeek);
        DayTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getContext(), view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        DayTitleView.setText(item.getTitle());
                        dayOfWeek = item.getTitle().toString();
                        new RetrieveDay().execute();
                        return true;
                    }
                });


                popup.inflate(R.menu.week);
                popup.show();
            }
        });

        mCurrentLayoutManagerType = OverviewFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        FoodRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        LectureRecycler.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        FinancesRecyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity()));

        FoodRecyclerView.scrollToPosition(0);
        LectureRecycler.scrollToPosition(0);
        FinancesRecyclerView.scrollToPosition(0);

        new getLectures().execute();
        new RetrieveDay().execute();
        new RetrieveTransactions().execute();

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

    class RetrieveDay extends AsyncTask<String, Void, ArrayList<Recipe>> {

        protected void onPreExecute() {

            FoodRecyclerView.setVisibility(View.GONE);
            foodProgressBar.setVisibility(View.VISIBLE);

        }

        protected ArrayList<Recipe> doInBackground(String... urls) {
            Log.d(TAG, dayOfWeek.toLowerCase());
            try {
                mDataset = new NetworkController().getDay(SUB, dayOfWeek.toLowerCase());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return mDataset;
        }

        protected void onPostExecute(final ArrayList<Recipe> recipes) {
            if (!(recipes == null || recipes.isEmpty() )){
                Log.d(TAG, recipes.get(0).getTitle());
            }
            else{
                NoRecipes.setVisibility(View.VISIBLE);
                Log.d(TAG, "No recipes");
            }
            OverviewFoodAdapter adapter = new OverviewFoodAdapter(recipes);
            FoodRecyclerView.setAdapter(adapter);
            foodProgressBar.setVisibility(View.GONE);
            FoodRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    class getLectures extends AsyncTask<String, Void, ArrayList<Lecture>>{

        @Override
        protected void onPreExecute() {
            LectureRecycler.setVisibility(View.GONE);
            LectureRecycler.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Lecture> doInBackground(String... params) {
            String URL = "https://tpot.space/college/"+SUB+'/'+currentIntDate;
            Log.d(TAG,URL);
            return new NetworkController().getLectureData(URL);
        }

        @Override
        protected void onPostExecute(ArrayList<Lecture> lectures) {
            if (lectures.isEmpty()){
                Toast.makeText(contextOfApplication, "No lectures today!", Toast.LENGTH_SHORT).show();
            }
            LectureRecycler.setVisibility(View.VISIBLE);
            LectureAdapter adapter = new LectureAdapter(lectures);
            LectureRecycler.setAdapter(adapter);
            lectureProgressBar.setVisibility(View.GONE);


    }


    }
    
    public class RetrieveTransactions extends AsyncTask<String, Void, ArrayList<Transaction>>{
        
        protected void onPreExecute(){
            transactionProgressBar.setVisibility(View.VISIBLE);
            FinancesRecyclerView.setVisibility(View.GONE);
            
        }

        @Override
        protected ArrayList<Transaction> doInBackground(String... urls) {
            String FinanceDayURL = "https://tpot.space/finance/day/";
            try{
                transactionDataSet = new NetworkController().getDayTransactions(FinanceDayURL, dateF.replace("-", ""), SUB);
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return transactionDataSet;
        }
        
        protected void onPostExecute(final ArrayList<Transaction> transactions){
            ArrayList<Transaction> incomes = new ArrayList<>();
            int totalExpend = 0;
            int totalIncome = 0;
            ArrayList<Transaction> expenditures = new ArrayList<>();
            for(Transaction transaction : transactions){
                if(transaction.getAmount() < 0){
                    expenditures.add(transaction);
                    totalExpend += Integer.parseInt(String.valueOf(transaction.getAmount()).substring(1));
                }
                else{
                    incomes.add(transaction);
                    totalIncome+=transaction.getAmount();
                }
            }
            FinanceDayAdapter TransactionAdapter = new FinanceDayAdapter(transactions);
            int totalDayBalance = totalIncome-totalExpend;
            if(totalDayBalance<0){
                dayBalance.setTextColor(Color.RED);
            }
            else{

                dayBalance.setTextColor(getResources().getColor(R.color.goodgreen));
            }
            dayBalance.setText("Net Day Balance: €"+ totalDayBalance+".00");
            transactionProgressBar.setVisibility(View.GONE);
            FinancesRecyclerView.setAdapter(TransactionAdapter);
            FinancesRecyclerView.setVisibility(View.VISIBLE);

        }
        
        
    }


}
