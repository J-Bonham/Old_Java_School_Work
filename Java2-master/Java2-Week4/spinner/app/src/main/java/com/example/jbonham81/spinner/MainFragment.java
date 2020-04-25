package com.example.jbonham81.spinner;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    public int section;
    Bundle args;
    public ArrayList<String> player;
    public ArrayList<String> eachPlayer;
    public String allTeamNames;
    public ArrayAdapter adapter;
    ListView listView;

    public MainFragment() {
    }

    public static MainFragment newInstance(int section) {

        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt("section", section);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = getArguments();
        section = args.getInt("section", 0);

        player = new ArrayList<String>();

        player.add("Team 1 Player");
        player.add("Team 2 Player");
        player.add("Team 3 Player");
        player.add("Team 4 Player");
        player.add("Team 5 Player");
        player.add("Team 6 Player");

        eachPlayer = new ArrayList<String>();

        for (int i = 0; i < 9; i++) {
            eachPlayer.add(player.get(section));

        }
        allTeamNames = player.get(section);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, eachPlayer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        return view;
    }







}



