// Java 2 Week 4
// Jeremiah Bonham

package com.example.jbonham81.navigationdrawerapp;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by jbonham81 on 12/18/14.
 */
public class Recent extends ListFragment {

    ArrayList recentNews;
    ArrayAdapter adapter;
    ListView list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recentNews = new ArrayList<String>();

        recentNews.add("Bitter Cold to Continue");
        recentNews.add("Internet is now 96% cat photos");
        recentNews.add("UFO Sightings Reported");
        recentNews.add("Ubisoft releases patch to fix broken games");
        recentNews.add("Warcraft back to 10 million subscriptions");
        recentNews.add("Internet is now 97% cat photos");

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, recentNews);
    }





    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Recent newInstance(int sectionNumber) {
        Recent fragment = new Recent();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Recent() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent, container, false);

        list = (ListView) view.findViewById(R.id.list);
        list.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

}
