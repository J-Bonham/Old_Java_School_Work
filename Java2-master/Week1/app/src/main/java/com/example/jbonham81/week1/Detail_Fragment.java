package com.example.jbonham81.week1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Detail_Fragment extends Fragment {

    public static final String TAG = "------------Detail------------";
    String movieInfo;

    public static Detail_Fragment newInstance(Movie object){
        Detail_Fragment fragment = new Detail_Fragment();
        Bundle args = new Bundle();
        args.putSerializable("object", object);
        fragment.setArguments(args);
        return fragment;
    }

    public Detail_Fragment(){ }

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

                ((TextView) getActivity().findViewById(R.id.titletext)).setText(object.getMovieTitle());
                ((TextView) getActivity().findViewById(R.id.releasetext)).setText("Release Date: " + object.getMovieRelease());
                ((TextView) getActivity().findViewById(R.id.ratingtext)).setText("Rating: " + object.getMovieRating());
                movieInfo = object.getMovie();

            }

        }

    }

 }
