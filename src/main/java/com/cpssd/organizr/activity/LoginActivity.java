package com.cpssd.organizr.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.cpssd.organizr.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

import java.io.UnsupportedEncodingException;

import butterknife.ButterKnife;


/**
 *
 * Main worker class for the Login Activity.
 * Handles: Login, Authentication, getting consent to make GCalendar changes, getting
 * basic user profile info and saving them locally to update the app UI, and launching the
 * main activity + passing it the unique userID that the server must use to make requests.
 *
 */

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final int RC_GET_TOKEN = 9002;
    private static final Scope SCOPES = new Scope("https://www.googleapis.com/auth/calendar");



    public static Context contextOfApplication;
    private GoogleApiClient googleApiClient;
    protected TextView GoogleSignInLink;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    protected Button societySignIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        contextOfApplication = getApplicationContext();
        societySignIn = (Button) findViewById(R.id.society_signin);
        societySignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SocietyLogin.class);
                startActivity(intent);
            }
        });

        // Set up Views
        GoogleSignInLink = (TextView) findViewById(R.id.googlesignin_link);
        GoogleSignInLink.setText(Html.fromHtml("<a href=https://developers.google.com/identity/> Google Identity Platform"));
        GoogleSignInLink.setMovementMethod(LinkMovementMethod.getInstance());

        // Button click listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);


        // Requests the user's ID token, which can be used to identify the
        // user securely to the server. This will contain the user's basic
        // profile (name, profile picture URL, etc) so we don't need to
        // make an additional call to personalize the app.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(SCOPES)
                .requestServerAuthCode(getString(R.string.client_id))
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();


        // Build GoogleAPIClient with the Google Sign-In API and the above options.
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // SharedPreferences is how the users name, email and profile photo URL are saved to
        // personalize the app.
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        editor = preferences.edit();


    }

    public void getIdToken() {
        // Show an account picker to let the user choose a Google account from the device.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    public void signOut() {
        // uses a google api call to sign the user out.
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Log.d(TAG, "signOut:onResult:" + status);
                        editor.putString(getString(R.string.TEST_SUB), "");
                        editor.commit();
                        updateUI(false);
                    }
                });
    }

    public void revokeAccess() {
        //uses a google api call to revoke all consent given to the app.
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Log.d(TAG, "revokeAccess:onResult:" + status);
                        updateUI(false);
                    }
                });
    }

    @Override
    /*
    * The main function of the application.
    * Step by step:
    * 1: Check if the result of the activity has a Request Code of RC_GET_TOKEN (which is 9003 iirc)
    * 2: If success, get all the basic user info and store it in shared preferences.
    * 3: Request the idToken and auth code from the google API, which are needed to exchnage for
    *    access tokens and refresh tokens respectively.
    * 4: Create a new sendToken ASyncTask to do the network activity necessary (cant be done in UI thread)
    * 5: Take the sub (the email-specific ID used to make authenticated server requests) and save in sharedprefs
    * 6: Start the MainActivity intent and pass basic profile data to it (In case the google account ones werent set)
    *
    * */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GET_TOKEN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "onActivityResult:GET_TOKEN:success:" + result.getStatus().isSuccess()+"hhhh");

            try {
                if (result.isSuccess()) {
                    GoogleSignInAccount acct = result.getSignInAccount();

                    assert acct != null;

                    String personName = acct.getDisplayName();
                    Log.d(TAG, personName);

                    String personGivenName = acct.getGivenName();
                    editor.putString("pref_key_user_firstname", personGivenName);
                    Log.d(TAG, personGivenName);
                    String personFamilyName = acct.getFamilyName();
                    editor.putString("pref_key_user_secondname", personFamilyName);
                    Log.d(TAG, personFamilyName);

                    String personEmail = acct.getEmail();
                    Log.d(TAG, personEmail);
                    editor.putString("pref_key_user_email", personEmail);


                    String personId = acct.getId();
                    editor.putString("pref_key_user_sub", personId);
                    Log.d(TAG, personId);

                    Uri personPhoto = acct.getPhotoUrl();
                    assert personPhoto != null;
                    Log.d(TAG, personPhoto.toString());
                    editor.putString("pref_key_user_image", personPhoto.toString());
                    editor.apply();

                    String aud = "264007239616-gv1ib14d9qdju864gbp529249mm62ucq.apps.googleusercontent.com";
                    String idToken = acct.getIdToken();
                    String authCode = acct.getServerAuthCode();
                    Log.d(TAG, "idToken: "+idToken+"Aud: "+aud+"authCode "+authCode);
                    // Show signed-in UI.
                    sendToken tokenTask = new sendToken();
                    tokenTask.execute(idToken, aud, authCode);

                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    updateUI(true);
                    startActivity(intent);

                } else {
                    // Show signed-out UI.
                    updateUI(false);
                }
            }
            catch(Exception e){
                Log.e(TAG, e.toString()+"hhhhhhhh");
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public void updateUI(boolean signedIn) {

        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                getIdToken();
                break;
        }
    }

    /*
    *
    * In order to actually send the token to the server to be authenticated by Google,
    * it has to use the NetworkController functions to create network activity. Network activity can only
    * be performed in an ASyncTask so the UI thread doesn't get blocked and frozen.
    * @param1: idToken
    * @param2: audience (client ID)
    * @param3: authCode (received from server to make authenticated requests)
    *
    * */

    public class sendToken extends AsyncTask<String, Void, Void> {
        protected void onPreExecute() {
        }

        protected Void doInBackground(String... params) {
            String idToken = params[0];
            String aud = params[1];
            String authCode = params[2];
            try {
                new NetworkController().sendToken(idToken, aud, authCode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }
}