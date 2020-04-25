// Jeremiah Bonham
// MDF3 1501
// Week 1

package com.example.jbonham81.audioplayer;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MainFragment extends Fragment implements View.OnClickListener, ServiceConnection {

    public static final String TAG = "---- MainFragment ----";

    AudioService playerService;
    AudioService.AudioBinder serviceBinder;

    boolean mBound;

    //UI Elements
    TextView title;
    Button playButton;
    Button pauseButton;
    Button stopButton;
    Button forwardButton;
    Button backButton;

    //Empty Constructor
    public MainFragment() {

    }

    //Constructor
    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mainfrag, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();

        playButton = (Button) view.findViewById(R.id.playButton);
        playButton.setOnClickListener(this);

        pauseButton = (Button) view.findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(this);
        pauseButton.setEnabled(false);

        stopButton = (Button) view.findViewById(R.id.stopButton);
        stopButton.setOnClickListener(this);
        stopButton.setEnabled(false);

        backButton = (Button) view.findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
        backButton.setEnabled(false);

        forwardButton = (Button) view.findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(this);
        forwardButton.setEnabled(false);

        title = (TextView) view.findViewById(R.id.title);
        title.setText("");
    }

    @Override
    public void onStart(){
        super.onStart();
        Intent intent = new Intent(getActivity(), AudioService.class);
        getActivity().bindService(intent, this, Context.BIND_AUTO_CREATE);
        getActivity().startService(intent);
        Log.i(TAG, "---- Bound Service Started ----.");
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        serviceBinder = (AudioService.AudioBinder)iBinder;
        playerService = serviceBinder.getService();
        mBound = true;


    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        getActivity().unbindService(MainFragment.this);
        mBound = false;
        playerService = null;
        Intent intent = new Intent(getActivity(), AudioService.class);
        getActivity().bindService(intent, this, Context.BIND_AUTO_CREATE);
        getActivity().stopService(intent);
    }


    //Handle onClick Events
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getActivity(), AudioService.class);
        getActivity().startService(intent);

        //Play
        if (v.getId() == R.id.playButton) {
            playerService.onPlay();
            playButton.setEnabled(false);
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
            forwardButton.setEnabled(true);
            backButton.setEnabled(true);
            title.setText(playerService.titles[playerService.mAudioPosition]);

        //Stop
        } else if (v.getId() == R.id.stopButton) {
            playerService.onStop();
            playButton.setEnabled(true);
            stopButton.setEnabled(false);
            pauseButton.setEnabled(false);
            forwardButton.setEnabled(false);
            backButton.setEnabled(false);
            title.setText("Nothing");



        //Pause
        } else if (v.getId() == R.id.pauseButton) {
            playerService.onPause();
            playButton.setEnabled(false);
            stopButton.setEnabled(true);
            forwardButton.setEnabled(true);
            backButton.setEnabled(true);
            title.setText(playerService.titles[playerService.mAudioPosition]);

        //Back
        } else if (v.getId() == R.id.backButton) {
            playerService.onBack();
            playButton.setEnabled(false);
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
            forwardButton.setEnabled(true);
            backButton.setEnabled(true);
            title.setText(playerService.titles[playerService.mAudioPosition]);

        //Forward
        } else if (v.getId() == R.id.forwardButton) {
            playerService.onForward();
            playButton.setEnabled(false);
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
            forwardButton.setEnabled(true);
            backButton.setEnabled(true);
            title.setText(playerService.titles[playerService.mAudioPosition]);

        }
    }
}
