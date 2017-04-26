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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpssd.organizr.R;
import com.cpssd.organizr.activity.AddTransaction;
import com.cpssd.organizr.activity.NetworkController;
import com.cpssd.organizr.adapters.FinanceDayAdapter;
import com.cpssd.organizr.pojos.Finance.Transaction;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONException;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Date;

import solar.blaz.date.week.WeekDatePicker;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;
import static com.cpssd.organizr.activity.LoginActivity.getContextOfApplication;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FinanceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FinanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinanceFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String TAG = "FinanceFragment";

    public String FinanceDayURL = "https://tpot.space/finance/day/";

    private String mParam1;
    private String mParam2;
    protected ArrayList<Transaction> mDataset;
    private OnFragmentInteractionListener mListener;
    protected WeekDatePicker weekDatePicker;
    protected TextView recyclerItemAmount;
    protected RecyclerView IncomeRecycler;
    protected RecyclerView ExpendRecycler;
    protected TextView netDayBalance;
    protected TextView dayBalanceTitle;
    protected TextView incomeTransactionAdd;
    protected TextView expenditureTransactionAdd;
    protected RelativeLayout expenditureLoadingBar;
    protected RelativeLayout incomeLoadingBar;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public String SUB;
    public Date dateSelected;
    public LocalDate currentDate;
    public String dateF;
    public Date startDateOfSelectedWeek;
    public FinanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinanceFragment.
     */

    public static FinanceFragment newInstance(String param1, String param2) {
        FinanceFragment fragment = new FinanceFragment();
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
        currentDate = LocalDate.now();
        dateF = currentDate.toString();
        Log.d("FinanceTragment", currentDate.toString());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_finance, container, false);
        LineChart chart = (LineChart) rootView.findViewById(R.id.chart);
        IncomeRecycler = (RecyclerView) rootView.findViewById(R.id.finance_day_income_recycler);
        ExpendRecycler = (RecyclerView) rootView.findViewById(R.id.finance_day_expend_recycler);
        IncomeRecycler.setVisibility(View.GONE);
        ExpendRecycler.setVisibility(View.GONE);

        expenditureLoadingBar = (RelativeLayout) rootView.findViewById(R.id.expendloading);
        incomeLoadingBar = (RelativeLayout) rootView.findViewById(R.id.incomeLoading);
        weekDatePicker = (WeekDatePicker) rootView.findViewById(R.id.date_picker);
        weekDatePicker.setDateIndicator(LocalDate.now(), true);
        weekDatePicker.setLimits(LocalDate.now(), null);
        weekDatePicker.setOnDateSelectedListener(new WeekDatePicker.OnDateSelected() {
                    @Override
                    public void onDateSelected(LocalDate date) {
                        dateF = date.toString();
                        new DayBalance().execute(dateF);

                    }
                });

        IncomeRecycler.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        ExpendRecycler.setLayoutManager(
                new LinearLayoutManager(getActivity()));
        IncomeRecycler.scrollToPosition(0);
        ExpendRecycler.scrollToPosition(0);
        dayBalanceTitle = (TextView) rootView.findViewById(R.id.finances_day_balance_title);
        netDayBalance = (TextView) rootView.findViewById(R.id.finances_day_balance);
        incomeTransactionAdd = (TextView) rootView.findViewById(R.id.finance_income_add);
        incomeTransactionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContextOfApplication(), AddTransaction.class);
                intent.putExtra("type", "Income");
                intent.putExtra("date", dateF.replace("-",""));
                intent.putExtra("weekStart", dateF.replace("-",""));
                startActivity(intent);
            }
        });

        expenditureTransactionAdd = (TextView) rootView.findViewById(R.id.finance_expenditure_add);
        expenditureTransactionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTransaction.class);
                intent.putExtra("type", "Expenditure");
                intent.putExtra("date", dateF);
                intent.putExtra("weekStart", dateF.replace("-",""));
                startActivity(intent);
            }
        });



        new DayBalance().execute("dateF");

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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class DayBalance extends AsyncTask<String, Void, ArrayList<Transaction>> {

        protected void onPreExecute() {
            IncomeRecycler.setVisibility(View.GONE);
            ExpendRecycler.setVisibility(View.GONE);
            incomeLoadingBar.setVisibility(View.VISIBLE);
            expenditureLoadingBar.setVisibility(View.VISIBLE);



        }

        protected ArrayList<Transaction> doInBackground(String... urls) {

            new NetworkController().callGetWeek(SUB,dateF.replace("-", ""));

            try {
                mDataset = new NetworkController().getDayTransactions(FinanceDayURL, dateF.replace("-",""), SUB);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mDataset;
        }

        protected void onPostExecute(final ArrayList<Transaction> transactions) {
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
            FinanceDayAdapter IncomeAdapter = new FinanceDayAdapter(incomes);
            FinanceDayAdapter ExpendAdapter = new FinanceDayAdapter(expenditures);
            int totalDayBalance = totalIncome-totalExpend;
            if(totalDayBalance<0){
                netDayBalance.setTextColor(Color.RED);
            }
            else{

                netDayBalance.setTextColor(getResources().getColor(R.color.goodgreen));
            }
            dayBalanceTitle.setText("Net Day Balance: ");
            netDayBalance.setText("€"+totalDayBalance+".00");
            IncomeRecycler.setAdapter(IncomeAdapter);
            ExpendRecycler.setAdapter(ExpendAdapter);

            expenditureLoadingBar.setVisibility(View.GONE);
            incomeLoadingBar.setVisibility(View.GONE);

            ExpendRecycler.setVisibility(View.VISIBLE);
            IncomeRecycler.setVisibility(View.VISIBLE);


        }

    }
}
