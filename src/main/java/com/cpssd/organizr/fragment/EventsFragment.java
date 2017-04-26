package com.cpssd.organizr.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cpssd.organizr.R;
import com.cpssd.organizr.activity.NetworkController;
import com.cpssd.organizr.adapters.EventAdapter;
import com.cpssd.organizr.adapters.LectureAdapter;
import com.cpssd.organizr.adapters.RecyclerItemClickListener;
import com.cpssd.organizr.other.EventTouchHelper;
import com.cpssd.organizr.other.LectureTouchHelper;
import com.cpssd.organizr.other.OnSwipeTouchListener;
import com.cpssd.organizr.pojos.Events.Event;
import com.cpssd.organizr.pojos.Events.Lecture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsFragment#} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String TAG = "EventsFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    public ArrayList<Lecture> lectures;
    public ArrayList<Event> events;


    String API_URL = "https://tpot.space/tasks/day/";

    public String SUB;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
    String currentDate = sdf.format(new Date());
    int currentIntDate = Integer.parseInt(currentDate);
    Date date = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(currentDate);
    String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);




    public EventsFragment() throws ParseException {
    }


    public interface OnListItemClickListener {
        void onListItemClick(int position);
    }

    private OnListItemClickListener mItemClickListener;
    private Integer mParam1;
    private Integer mParam2;
    private TextView textView;

    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;


    private OnFragmentInteractionListener mListener;

    public enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView calendarEventsRecycler;
    protected RecyclerView lecturesRecycler;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RelativeLayout eventsLoadingPanel;
    protected RelativeLayout lectureLoadingPanel;

    protected String college;
    protected String course;
    protected String year;

    protected String lectureApiUrl = "https://tpot.space/college/";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        java.util.Calendar c = java.util.Calendar.getInstance();
        preferences = PreferenceManager.getDefaultSharedPreferences(contextOfApplication);
        SUB = preferences.getString("sub", "Error");
        college = preferences.getString("pref_key_user_college", null);
        course = preferences.getString("pref_key_user_course", null);
        year = preferences.getString("pref_key_user_year", null);
        Log.d(TAG, SUB);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        rootView.setTag(TAG);
        eventsLoadingPanel = (RelativeLayout) rootView.findViewById(R.id.eventsLoadingPanel);
        lectureLoadingPanel = (RelativeLayout) rootView.findViewById(R.id.lecturesLoadingPanel);
        calendarEventsRecycler = (RecyclerView) rootView.findViewById(R.id.events_calendar_recycler);

        calendarEventsRecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(final View view, int position) {

                        EditText title;
                        EditText summary;
                        EditText time;
                        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext());
                        builder.title("Event Details");
                        builder.customView(R.layout.event_detail_popup, true);
                        builder.positiveText("Done");
                        builder.icon(getResources().getDrawable(R.drawable.ic_event_black_24dp));
                        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                TextView eventTitle = (TextView) view.findViewById(R.id.event_title);
                                TextView eventDescription = (TextView) view.findViewById(R.id.event_description);
                                TextView eventTime = (TextView) view.findViewById(R.id.event_time);

                                EditText title  = (EditText) dialog.getCustomView().findViewById(R.id.event_detail_content_title);
                                EditText desc = (EditText) dialog.getCustomView().findViewById(R.id.event_detail_content_summary);
                                EditText time = (EditText) dialog.getCustomView().findViewById(R.id.event_detail_content_time);

                                title.setText(eventTitle.getText());
                                desc.setText(eventDescription.getText());
                                time.setText(eventTime.getText());

                                String newTitle = String.valueOf(title.getText());
                                String newDesc = String.valueOf(desc.getText());
                                String newTime = String.valueOf(time.getText());

                                eventTitle.setText(newTitle);
                                eventDescription.setText(newDesc);
                                eventTime.setText(newTime);
                            }
                        });
                        MaterialDialog dialog = builder.build();
                        View view1 = dialog.getCustomView();
                        title = (EditText) view1.findViewById(R.id.event_detail_content_title);
                        summary = (EditText) view1.findViewById(R.id.event_detail_content_summary);
                        time = (EditText) view1.findViewById(R.id.event_detail_content_time);
                        dialog.show();
                        title.setText(String.valueOf(events.get(position).getSummary()));
                        if(events.get(position).getDescription()=="null"){
                            summary.setText("Description not set");
                        }
                        else {
                            summary.setText(String.valueOf(events.get(position).getDescription()));
                        }
                        time.setText(String.valueOf(events.get(position).getStart().getDateTime().substring(11, 16)));


                    }
                }));

        lecturesRecycler = (RecyclerView) rootView.findViewById(R.id.events_lectures_recycler);
        lecturesRecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(final View view, int position) {

                        EditText title;
                        EditText summary;
                        EditText time;
                        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext());
                        builder.title("Event Details");
                        builder.customView(R.layout.event_detail_popup, true);
                        builder.positiveText("Done");
                        builder.icon(getResources().getDrawable(R.drawable.ic_event_black_24dp));
                        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                TextView eventTitle = (TextView) view.findViewById(R.id.lecture_title);
                                EditText editText = (EditText) dialog.getCustomView().findViewById(R.id.event_detail_content_title);
                                String newTitle = String.valueOf(editText.getText());
                                eventTitle.setText(newTitle);
                            }
                        });
                        MaterialDialog dialog = builder.build();
                        View view1 = dialog.getCustomView();
                        title = (EditText) view1.findViewById(R.id.event_detail_content_title);
                        summary = (EditText) view1.findViewById(R.id.event_detail_content_summary);
                        time = (EditText) view1.findViewById(R.id.event_detail_content_time);
                        dialog.show();
                        title.setText(String.valueOf(lectures.get(position).getSummary()));
                        summary.setText(String.valueOf(lectures.get(position).getDescription()));
                        time.setText(String.valueOf(lectures.get(position).getStart()));


                    }
                }));


        rootView.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeTop() {

            }
            public void onSwipeRight() {
                currentIntDate-=1;
                new getCalendarEvents().execute();
                new getLectures().execute();
            }
            public void onSwipeLeft() {
                currentIntDate+=1;
                new getCalendarEvents().execute();
                new getLectures().execute();
            }
            public void onSwipeBottom() {

            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        // Set CustomAdapter as the adapter for RecyclerView
        setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
        // new getCalendarEvents().execute();
        new getLectures().execute();
        new getCalendarEvents().execute();
        return rootView;


    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;
        calendarEventsRecycler.setLayoutManager(mLayoutManager);
        lecturesRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        calendarEventsRecycler.scrollToPosition(scrollPosition);
        lecturesRecycler.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }




    class getCalendarEvents extends AsyncTask<Void, Void, ArrayList<Event>> {

        protected void onPreExecute() {

            calendarEventsRecycler.setVisibility(View.GONE);
            eventsLoadingPanel.setVisibility(View.VISIBLE);

        }

        protected ArrayList<Event> doInBackground(Void... urls) {
            String URL = API_URL+SUB+'/'+currentIntDate;
            Log.d(TAG,URL);
            events = new NetworkController().getCalendarEvents(URL);
            return events;
        }

        protected void onPostExecute(final ArrayList<Event> events) {
            if (events.isEmpty()){
                Toast.makeText(contextOfApplication, "No events today!", Toast.LENGTH_SHORT).show();
            }
            calendarEventsRecycler.setVisibility(View.VISIBLE);
            EventAdapter adapter = new EventAdapter(events);
            calendarEventsRecycler.setAdapter(adapter);
            eventsLoadingPanel.setVisibility(View.GONE);
            ItemTouchHelper.Callback callback = new EventTouchHelper(adapter);
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(calendarEventsRecycler);



        }

    }

    class getLectures extends AsyncTask<String, Void, ArrayList<Lecture>>{

        @Override
        protected void onPreExecute() {
            lecturesRecycler.setVisibility(View.GONE);
            lectureLoadingPanel.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Lecture> doInBackground(String... params) {

            String URL = lectureApiUrl+SUB+'/'+currentIntDate;
            Log.d(TAG,URL);
            lectures = new NetworkController().getLectureData(URL);
            return lectures;
        }

        @Override
        protected void onPostExecute(ArrayList<Lecture> lectures) {
            if (lectures.isEmpty()){
                Toast.makeText(contextOfApplication, "No lectures today!", Toast.LENGTH_SHORT).show();
            }
            lecturesRecycler.setVisibility(View.VISIBLE);
            LectureAdapter adapter = new LectureAdapter(lectures);
            lecturesRecycler.setAdapter(adapter);
            lectureLoadingPanel.setVisibility(View.GONE);
            ItemTouchHelper.Callback callback = new LectureTouchHelper(adapter);
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(lecturesRecycler);

        }
    }

}
