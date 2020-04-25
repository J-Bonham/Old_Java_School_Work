package com.example.jbonham81.java2_week2.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jbonham81.java2_week2.Movie.Movie;
import com.example.jbonham81.java2_week2.R;

public class DetailFrag extends Fragment{

    public static final String TAG = "------------ Detail Frag ------------";
    String movie;

    public static DetailFrag newInstance(Movie object){

        DetailFrag fragment = new DetailFrag();
        Bundle args = new Bundle();
        args.putSerializable("object", object);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFrag(){ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();

        if(args != null){

            Movie object = (Movie) args.getSerializable("object");

            if(object!=null) {
                ((TextView) getActivity().findViewById(R.id.titleText)).setText(object.getMovieTitle());
                ((TextView) getActivity().findViewById(R.id.yearText)).setText("Movie Released in: " + object.getMovieYear());
                ((TextView) getActivity().findViewById(R.id.directorText)).setText("Director: " +object.getMovieDirector());
                ((TextView) getActivity().findViewById(R.id.writerText)).setText("Writer: " +object.getMovieWriter());
                ((TextView) getActivity().findViewById(R.id.genreText)).setText("Genre: " +object.getMovieGenre());
                movie = object.getMovieTitle();
            }


        }

    }


}
