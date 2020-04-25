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
import android.widget.ListView;

import com.example.jbonham81.java2_week2.MainActivity.DataInterface;
import com.example.jbonham81.java2_week2.R;

public class ListFrag extends Fragment{

    private DataInterface dataInterface;
    public static final String TAG = "------------ List Frag ------------";

    public static ListFrag newInstance(){
        ListFrag fragment = new ListFrag();
        return fragment;
    }

    public ListFrag(){ }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof DataInterface) {
            dataInterface = (DataInterface) activity;
        }else{
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
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView list = (ListView)getActivity().findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.options));
        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                dataInterface.getInfo(adapterView.getItemAtPosition(position).toString());
                Log.d(TAG, adapterView.getItemAtPosition(position).toString());
            }
        });
    }
}
