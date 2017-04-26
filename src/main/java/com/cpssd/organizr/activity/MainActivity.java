package com.cpssd.organizr.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpssd.organizr.R;
import com.cpssd.organizr.fragment.EventsFragment;
import com.cpssd.organizr.fragment.FinanceFragment;
import com.cpssd.organizr.fragment.Intro.CoursePickerSlide;
import com.cpssd.organizr.fragment.MapsFragment;
import com.cpssd.organizr.fragment.OverviewFragment;
import com.cpssd.organizr.fragment.RecipesFragment;
import com.cpssd.organizr.fragment.SettingsFragment;
import com.cpssd.organizr.other.AboutUsActivity;
import com.cpssd.organizr.other.PrivacyPolicyActivity;
import com.cpssd.organizr.pojos.Settings.Calendar;
import com.cpssd.organizr.pojos.Settings.Module;
import com.cpssd.organizr.pojos.Settings.Settings;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.pusher.android.PusherAndroid;
import com.pusher.android.notifications.ManifestValidator;
import com.pusher.android.notifications.PushNotificationRegistration;

import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;


public class MainActivity extends AppCompatActivity {
    public static List<Calendar> calendars;
    public static List<Module> modules;
    public static List<String> societies;


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    protected View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    // To store user prefs
    public SharedPreferences preferences;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static final String TAG = ".activity.MainActivity";

    // index to identify current nav menu item
    public static int navItemIndex = 0;


    public String SUB;
    public String profile_name;
    public String profile_email;
    public String profile_image;

    // tags used to attach the fragments
    private static final String TAG_EVENTS = "events";
    private static final String TAG_MAPS = "maps";
    private static final String TAG_FINANCE = "finance";
    private static final String TAG_RECIPES = "recipes";
    private static final String TAG_OVERVIEW = "overview";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_EVENTS;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load events fragment when user presses back key
    protected boolean shouldLoadEventsFragOnBackPress = true;
    private Handler mHandler;


    SharedPreferences.Editor e;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contextOfApplication = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        e = preferences.edit();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences

                //  Create a new boolean and preference and set it to true

                    //  Launch app intro
                    Intent i = new Intent(MainActivity.this, MyIntro.class);
                    startActivity(i);

                    //  Make a new preferences editor

                }

        });

        // Start the thread
        t.start();


        profile_name = preferences.getString("profile_name", "Error");
        profile_email = preferences.getString("profile_email", "Error");
        profile_image = preferences.getString("profile_image", "Error");
        SUB = preferences.getString("sub", "Error");

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(SUB != null) {
            Log.d(TAG, SUB);
        }
        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addEvent = new Intent(MainActivity.this, AddEvent.class);
                startActivity(addEvent);
                drawer.closeDrawers();
            }
        });

        loadEventsFragment();

        new getSettings().execute();
        new getModules().execute();

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (playServicesAvailable()) {
            PusherAndroid pusher = new PusherAndroid("b47ba822f151444f7a12");
            PushNotificationRegistration nativePusher = pusher.nativePusher();

            // pulled from google-services.json
            String defaultSenderId = getString(R.string.gcm_defaultSenderId);
            try {
                nativePusher.registerGCM(this, defaultSenderId);
            } catch (ManifestValidator.InvalidManifestException i) {
                i.printStackTrace();
            }
            nativePusher.subscribe("societies");

        }

    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, overview action view (dot)
     */
    private boolean playServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    private void loadNavHeader() {
        // name, website
        txtName.setText(profile_name);
        txtWebsite.setText(profile_email);

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(profile_image)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new com.cpssd.organizr.other.CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to overview label
//        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadEventsFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();


        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = null;
                try {
                    fragment = getEventsFragment();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        mHandler.post(mPendingRunnable);

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getEventsFragment() throws ParseException {
        switch (navItemIndex) {
            case 0:
                // events

                return new EventsFragment();
            case 1:
                // finance
                Log.d(TAG, preferences.getString("courseCodeAndYear", "OOPS"));
                return new FinanceFragment();
            case 2:
                // recipes fragment
                return new RecipesFragment();
            case 3:
                // overview fragment
                return new OverviewFragment();
            case 4:
                // settings fragment
                return new MapsFragment();
            case 5:
                return new SettingsFragment();

            default:
                return new EventsFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_events:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_EVENTS;
                        break;

                    case R.id.nav_finance:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_FINANCE;
                        break;

                    case R.id.nav_recipes:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_RECIPES;
                        break;

                    case R.id.nav_overview:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_OVERVIEW;
                        break;

                    case R.id.nav_maps:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_MAPS;
                        break;

                    case R.id.nav_settings:
                        startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadEventsFragment();

                return true;
            }
        });



        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads events fragment when back key is pressed
        // when user is in other fragment than events
        if (shouldLoadEventsFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than events

            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_EVENTS;
                loadEventsFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when events fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Events/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Logged user out!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            intent.putExtra("status", "logout");
            startActivity(intent);
        }

        // user is in overview fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_done) {
            Toast.makeText(getApplicationContext(), "All tasks marked as done!", Toast.LENGTH_LONG).show();
        }

        // user is in overview fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_tasks) {
            Toast.makeText(getApplicationContext(), "Clear all tasks!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }





    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }


    public class getSettings extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            Settings settings = new NetworkController().getSettings(SUB);
            calendars = settings.getCalendars();
            for(Calendar calendar : calendars){
                e.putBoolean(calendar.getSummary(), calendar.getValue());
                e.putString(calendar.getSummary(), calendar.getSummary());
                e.putString(calendar.getId(), calendar.getId());
                e.apply();
                Log.d(TAG, String.valueOf(preferences.getString(calendar.getSummary(), calendar.getSummary())));
            }
            e.apply();
            return null;
        }
    }

    private class getModules extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            Settings settings = new NetworkController().getSettings(SUB);
            modules = settings.getCourse().getModules();
            for(Module module : modules){
                e.putString(module.getModule(), module.getValue().toString());
                Log.d(TAG, module.getModule());
            }
            return null;
        }
    }

    public class getSocieties extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... params) {
            Settings settings = new NetworkController().getSettings(SUB);
            societies = settings.getSocieties();
            for(String society : societies){
                e.putString(society, String.valueOf(true));
                Log.d(TAG, society);
            }
            return null;
        }
    }
}
