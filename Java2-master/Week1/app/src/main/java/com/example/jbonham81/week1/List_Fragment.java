package com.example.jbonham81.week1;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class List_Fragment extends Fragment {

    public static final String TAG = "------------List Fragment------------";
    private Interface movieInterface;

    public static List_Fragment newInstance(){
        List_Fragment fragment = new List_Fragment();
        return fragment;
    }

    public List_Fragment(){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof Interface) {
            movieInterface = (Interface) activity;
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

        ListView list = (ListView)getActivity().findViewById(R.id.ListOptions);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.titles));
        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                movieInterface.getMovie(adapterView.getItemAtPosition(position).toString());
            }
        });
    }


}
