package com.cpssd.organizr.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cpssd.organizr.R;

import butterknife.ButterKnife;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;

public class SocietyLogin extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    EditText _collegeText;
    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    private SharedPreferences preferences;
    SharedPreferences.Editor e;
    public int STATUS_CODE;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society_login);
        ButterKnife.bind(this);
        contextOfApplication = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        _collegeText = (EditText) findViewById(R.id.input_college);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);


        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SocietyLogin.this,
                R.style.Theme_AppCompat_DayNight_NoActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        String college = _collegeText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        e = preferences.edit();
        e.putString("society_login_college", college);
        e.putString("society_login_email", email);
        e.putString("society_login_password", password);
        e.apply();

        new societyLogin().execute(email, college, password);
        Log.d("SocLogin", String.valueOf(STATUS_CODE));


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        finish();
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(false);
        Intent intent = new Intent(this, SocietyMain.class);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String college = _collegeText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }
        if (college.isEmpty() || college.length() != 3) {
            _collegeText.setError("Enter a valid college, eg. 'DCU'");
            valid = false;
        } else {
            _collegeText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public class societyLogin extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
        }

        protected String doInBackground(String... params) {
            String email = params[0];
            String college = params[1];
            String password = params[2];
            String response = new NetworkController().postSocietyLoginDetails(email, college, password);
            Log.d("SocLogin", String.valueOf(response));
            SharedPreferences.Editor e = preferences.edit();
            e.putString("pref_key_soc_name", response);
            e.apply();
            Log.d(TAG, response);

            return response;
        }

        @Override
        protected void onPostExecute(String string) {

            super.onPostExecute(string);
            if(string  != "none") {
                onLoginSuccess();

            }
        }
    }
}
