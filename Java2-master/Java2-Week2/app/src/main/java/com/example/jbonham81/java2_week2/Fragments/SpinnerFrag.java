package com.example.jbonham81.java2_week2.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.jbonham81.java2_week2.MainActivity.DataInterface;
import com.example.jbonham81.java2_week2.R;

public class SpinnerFrag extends Fragment {

    public static final String TAG = "------------ Spinner Frag ------------";


    private int spinnerPosition;
    private DataInterface delegate;
    public static SpinnerFrag newInstance(){
        SpinnerFrag fragment = new SpinnerFrag();
        return fragment;
    }

    public SpinnerFrag(){}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof DataInterface){

            delegate = (DataInterface) activity;

        }else {
            throw new IllegalArgumentException("Nope!");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.spinner_fragment, container, false);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.options));
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                delegate.getInfo(adapterView.getSelectedItem().toString());
                Log.d(TAG, adapterView.getSelectedItem().toString());
                SpinnerFrag.this.spinnerPosition = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }
}
