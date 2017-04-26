package com.cpssd.organizr.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.cpssd.organizr.R;
import com.cpssd.organizr.fragment.FinanceFragment;
import com.cpssd.organizr.pojos.Finance.BalanceUpdate;
import com.cpssd.organizr.pojos.Finance.Details;
import com.cpssd.organizr.pojos.Finance.Input;

import java.util.Calendar;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;

/**
 * Created by Niall on 22/03/2017.
 */

public class AddTransaction extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String TAG = "activity.AddTransaction";
    public String SUB;
    public SharedPreferences preferences;
    public String type;

    protected TextView transactionPicker;
    protected Button datePickerButton;
    protected EditText transactionAmount;
    protected EditText transactionSummary;
    protected Button doneButton;
    private String weekstart;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        String date = intent.getStringExtra("date");
        if(intent.getStringExtra("weekStart") != null){
            weekstart = date;
        }



        // All transaction picker stuff, including the dropdown picker menu
        transactionPicker = (TextView) findViewById(R.id.add_transaction_picker_textview);
        transactionPicker.setText(type);
        transactionPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getApplicationContext(), v);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        transactionPicker.setText(item.getTitle());
                        type = String.valueOf(item.getTitle());
                        return true;
                    }
                });
                popup.inflate(R.menu.transaction_picker_menu);
                popup.show();
            }

        });

        datePickerButton = (Button) findViewById(R.id.add_expend_date_picker_button);
        datePickerButton.setText(date);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        AddTransaction.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });


        transactionAmount = (EditText) findViewById(R.id.transaction_amount_edittext);

        transactionSummary = (EditText) findViewById(R.id.transaction_summary_edittext);

        contextOfApplication = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        SUB = preferences.getString("sub", "Error");
        doneButton = (Button) findViewById(R.id.transaction_done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = String.valueOf(transactionPicker.getText());
                int amount = Integer.parseInt(String.valueOf(transactionAmount.getText()));
                if (type.equals("Expenditure")){
                    amount = amount-(2*(amount));
                }
                String summary = String.valueOf(transactionSummary.getText());
                int date = Integer.parseInt(TextUtils.join("", datePickerButton.getText().toString().split("-")));
                Log.d(TAG, String.valueOf(date));
                weekstart = String.valueOf(date);
                BalanceUpdate balanceUpdate = new BalanceUpdate();
                balanceUpdate.setUserId(SUB);
                Details details = new Details();
                details.setAmount(amount);
                details.setSummary(summary);
                Input input = new Input();
                input.setAmount(amount);
                input.setDate(date);
                input.setWeekStart(date);
                input.setDetails(details);
                balanceUpdate.setInput(input);
                FinanceFragment nextFrag= new FinanceFragment();
                new updateBalance().execute(balanceUpdate);
                finish();
            }
        });




    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String date = year+"-0"+(++monthOfYear)+"-"+dayOfMonth;
        datePickerButton.setText(date);
    }

    class updateBalance extends AsyncTask<BalanceUpdate, Void, Void> {
        @Override
        protected Void doInBackground(BalanceUpdate... params) {
            BalanceUpdate balanceUpdate = params[0];
            new NetworkController().UpdateBalance(balanceUpdate);

            return null;
        }
    }

}
