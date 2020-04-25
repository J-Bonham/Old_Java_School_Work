// Jeremiah Bonham
// MDF3 1501
// Week 2

package com.example.jbonham81.audioplayer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;
import java.util.Random;

public class AudioService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    public MediaPlayer mPlayer;
    boolean mPrepared;
    boolean mActivityResumed;
    int mAudioPosition;
    String[] tracks;
    String[] titles;

    //Week 2
    //Array for AlbumArt
    String[] albumArt;

    //Week 2
    //Bool for Shuffle and Repeat
    private boolean repeat = false;
    private boolean shuffle = false;

    //Week 2
    //Random Int for shuffle
    Random randomTrack;
    int min = 0;
    int max = 4;

    public static final int STANDARD_NOTIFICATION = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        //create random number for shuffle
        randomTrack = new Random();

        //Set Array for audio files, and titles of tracks
        tracks = new String[]{"/raw/a", "/raw/b", "/raw/c", "/raw/d", "/raw/g"};
        titles = new String[]{"Skip - Planet Cruizer", "Decktonic - Seppuku Bot", "Blasterhead - Killbots", "Ricky Brugal - Lip Sync", "Azureflux - Beast Mode"};

        //Week 2
        albumArt = new String[]{"/raw/a", "/raw/b", "/raw/c", "/raw/d", "/raw/e"};

        mPrepared = mActivityResumed = false;
        mAudioPosition = 0;

        if (mPlayer == null){
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(this);

            try {
                mPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/raw/a"));
            } catch (IOException e) {
                e.printStackTrace();

                mPlayer.release();
                mPlayer = null;
            }
        }
    }

    public class AudioBinder extends Binder {
        public AudioService getService() {
            return AudioService.this;
        }
    }

    //Binder
    @Override
    public IBinder onBind(Intent intent) {
        return new AudioBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mPlayer == null){
            mPlayer.start();
        }
        return START_STICKY;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();

        if (mPlayer != null){
            mPlayer.stop();
        }
        mPlayer.release();
        stopForeground(true);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (repeat) {
            mPlayer.setLooping(true);
        } else {
            mPlayer.setLooping(false);
        }

        if (mAudioPosition < tracks.length - 1){
            mAudioPosition++;
            mediaPlayer.reset();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(AudioService.this);
            mediaPlayer.setOnCompletionListener(AudioService.this);

            try{
                mediaPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + "/" + tracks[mAudioPosition]));
            } catch (IOException e){
                e.printStackTrace();
            }

            mediaPlayer.prepareAsync();
        }

        else {
            mAudioPosition = 0;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(AudioService.this);
            mediaPlayer.setOnCompletionListener(AudioService.this);

            try{
                mediaPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + "/" + tracks[mAudioPosition]));
            } catch (IOException e){
                e.printStackTrace();
            }
            mediaPlayer.prepareAsync();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mPrepared = true;
        if (repeat) {
            mPlayer.setLooping(true);
        } else {
            mPlayer.setLooping(false);
        }


        if (mediaPlayer != null && mActivityResumed) {
            mediaPlayer.seekTo(mAudioPosition);
            mediaPlayer.start();
        } else {
            mediaPlayer.start();
        }

        String songPlaying = titles[mAudioPosition];



        //Adding logic to pull albumArt based on track number
        Bitmap art = null;
        if (mAudioPosition == 0){
            art = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        } else if (mAudioPosition == 1){
            art = BitmapFactory.decodeResource(getResources(), R.drawable.b);
        } else if (mAudioPosition == 2){
            art = BitmapFactory.decodeResource(getResources(), R.drawable.c);
        } else if (mAudioPosition == 3){
            art = BitmapFactory.decodeResource(getResources(), R.drawable.d);
        } else if (mAudioPosition == 4){
            art = BitmapFactory.decodeResource(getResources(), R.drawable.e);
        }


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setContentIntent(pendingIntent);
        notification.setSmallIcon(R.drawable.notif);
        notification.setLargeIcon(art);
        notification.setContentTitle("Currently Playing: ");
        notification.setContentText(songPlaying);
        manager.notify(STANDARD_NOTIFICATION, notification.build());

        //Fixed from week 1 - Actually starting foreground notification
        startForeground(STANDARD_NOTIFICATION, notification.build());
    }

    //Shuffle Method
    public void onShuffle(Boolean bool) {

        if (bool){
            shuffle = true;
        } else {
            shuffle = false;
        }
    }

    //Repeat Method
    public void onRepeat(Boolean bool) {

        if (bool){
            repeat = true;
            mPlayer.setLooping(true);
        } else {
            repeat = false;
            mPlayer.setLooping(true);
        }
    }

    public void onPlay() {

        if (shuffle){
            mAudioPosition = randomTrack.nextInt(max - min + 1) + min;

            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(AudioService.this);
            mPlayer.setOnCompletionListener(AudioService.this);

            try{
                mPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + tracks[mAudioPosition]));
                mPlayer.prepareAsync();
            } catch (IOException e){
                e.printStackTrace();
            }

        } else {
            mAudioPosition = 0;
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(AudioService.this);
            mPlayer.setOnCompletionListener(AudioService.this);

            try {
                mPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + tracks[mAudioPosition]));
                mPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onResume() {
        mActivityResumed = true;
        if (mPlayer != null && !mPrepared){
            mPlayer.prepareAsync();
        } else if (mPlayer != null && mPrepared) {
            mPlayer.start();
        }
    }

    public void onStop() {
        mPlayer.stop();
        //mPlayer.release();
        mPrepared = false;
        stopForeground(true);


    }

    public void onPause() {
        if (mPlayer.isPlaying()){
            mPlayer.pause();
        } else {
            this.onResume();
        }

    }

    public void onForward(){
        if (shuffle){
            mAudioPosition = randomTrack.nextInt(max - min + 1) + min;
            mPlayer.reset();
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(AudioService.this);
            mPlayer.setOnCompletionListener(AudioService.this);

            try {
                mPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + tracks[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        } else if (repeat) {
            mPlayer.reset();
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(AudioService.this);
            mPlayer.setOnCompletionListener(AudioService.this);

            try {
                mPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + tracks[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();


        } else if (mAudioPosition < tracks.length - 1) {
            mAudioPosition++;
            mPlayer.reset();
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(AudioService.this);
            mPlayer.setOnCompletionListener(AudioService.this);

            try {
                mPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + tracks[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();

        } else {
            mAudioPosition = 0;
            mPlayer.reset();
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(AudioService.this);
            mPlayer.setOnCompletionListener(AudioService.this);

            try {
                mPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + tracks[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        }
    }


    public void onBack(){
        if (shuffle){
            mAudioPosition = randomTrack.nextInt(max - min + 1) + min;
            mPlayer.reset();
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(AudioService.this);
            mPlayer.setOnCompletionListener(AudioService.this);

            try {
                mPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + tracks[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();

        } else if (repeat) {
            mPlayer.reset();
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(AudioService.this);
            mPlayer.setOnCompletionListener(AudioService.this);

            try {
                mPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + tracks[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();

        }

        else if (mAudioPosition == 0) {
            mAudioPosition = tracks.length - 1;
            mPlayer.reset();
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(AudioService.this);
            mPlayer.setOnCompletionListener(AudioService.this);

            try {
                mPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + tracks[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();

        } else {
            mAudioPosition--;
            mPlayer.reset();
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(AudioService.this);
            mPlayer.setOnCompletionListener(AudioService.this);

            try {
                mPlayer.setDataSource(AudioService.this, Uri.parse("android.resource://" + getPackageName() + tracks[mAudioPosition]));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        }
    }
}