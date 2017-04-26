package com.cpssd.organizr.adapters;

import android.database.DataSetObserver;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpssd.organizr.R;
import com.cpssd.organizr.pojos.Finance.Transaction;
import com.cpssd.organizr.pojos.Recipes.Recipe;

import java.util.ArrayList;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;

/**
 * Created by Niall on 07/02/2017.
 */

public class FinanceDayAdapter extends RecyclerView.Adapter<FinanceDayAdapter.ViewHolder> implements ListAdapter {
    private static final String TAG = "FinanceDayAdapter";

    public ArrayList<Transaction> mDataSet = new ArrayList<>();

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public FinanceDayAdapter(ArrayList<Transaction> transactions){
        this.mDataSet = transactions;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.finance_day_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        String title = mDataSet.get(position).getSummary();
        int amount = mDataSet.get(position).getAmount();
        viewHolder.getTransaction_title().setText(String.valueOf(title));
        if(amount<0){
            viewHolder.getTransaction_amount().setText("- €"+String.valueOf(amount).substring(1)+".00");
        }
        else{
            viewHolder.getTransaction_amount().setText("+ €"+String.valueOf(amount)+".00");
        }

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView transaction_title;
        private final TextView transaction_amount;



        ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            transaction_title = (TextView) v.findViewById(R.id.finance_day_item_description);
            transaction_amount = (TextView) v.findViewById(R.id.finance_day_item_amount);

        }
        TextView getTransaction_title() {
            return transaction_title;
        }
        TextView getTransaction_amount() {
            return transaction_amount;
        }


    }
}
