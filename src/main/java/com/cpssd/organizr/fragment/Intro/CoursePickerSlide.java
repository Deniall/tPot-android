package com.cpssd.organizr.fragment.Intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cpssd.organizr.R;


public class CoursePickerSlide extends Fragment {


    protected EditText courseCode;
    protected EditText college;
    protected com.shawnlin.numberpicker.NumberPicker year;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.intro_course_picker, container, false);
        courseCode = (EditText) rootView.findViewById(R.id.course_picker);
        year = (com.shawnlin.numberpicker.NumberPicker) rootView.findViewById(R.id.year_picker);
        college = (EditText) rootView.findViewById(R.id.college_picker);
        return rootView;

    }
    public String getCollege(){
        return String.valueOf(college.getText());
    }
    public String getCourseCode(){
        return String.valueOf(courseCode.getText());
    }
    public int getYear(){
        return year.getValue();
    }
}
