package com.example.jbonham81.week1;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity implements Interface {

    public static final String TAG = "------------Main------------";
    private FragmentManager fragMan;

    public static final String KEY_DISPLAY = "DisplayText";
    ASYNC_Class async_class;
    public String movieTitle;
    public String movieRelease;
    public String movieRating;
    ConnectivityCheck connectivityCheck;
    Movie movieObject;
    ProgressDialog dialog;
    String totalAppend;
    String movieSelect;
    String itemSelectedPass;
    File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragMan = getFragmentManager();

        if (getResources().getConfiguration().orientation == 1) {
            System.out.println("-----------------------------portait next week-----------------------------");
            //fragMan.beginTransaction() .replace(R.id.listContainer, List_Fragment.newInstance(), List_Fragment.TAG) .commit();

        } else {
            fragMan.beginTransaction().replace(R.id.listContainer, List_Fragment.newInstance(), List_Fragment.TAG).commit();
        }
    }

    @Override
    public void getMovie(String selector) {

        fragMan.beginTransaction().replace(R.id.detailContainer, Detail_Fragment.newInstance(ReadingFromLocal(selector)), Detail_Fragment.TAG).commit();

        async_class = new ASYNC_Class();
        itemSelectedPass = selector;
        movieSelect = selector;
        String urlBegin = "http://www.omdbapi.com/?t=";
        String urlEnd = "&y=&plot=short&r=json";
        totalAppend = urlBegin + movieSelect + urlEnd;
        async_class.execute(totalAppend);

     }

    public class ASYNC_Class extends AsyncTask<String, Integer, Movie> {

        protected String movieJSON;
        private HttpURLConnection movieWebConnect;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Gathering Movie Info");
            dialog.show();
        }

        @Override
        protected Movie doInBackground(String... strings) {
            try{

                URL url = new URL(strings[0]);

                movieWebConnect = (HttpURLConnection) url.openConnection();
                movieWebConnect.connect();

                InputStream inputStream = movieWebConnect.getInputStream();
                movieJSON = IOUtils.toString(inputStream);
                inputStream.close();

                JSONObject obj = new JSONObject(movieJSON);

                movieTitle = obj.getString("Title");
                movieRelease = obj.getString("Released");
                movieRating = obj.getString("Rated");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                movieWebConnect.disconnect();
            }

            movieObject = new Movie(movieTitle, movieRelease, movieRating);
            return movieObject;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Movie objects) {
            super.onPostExecute(objects);
            objects = movieObject;
            dialog.dismiss();

            try {

                file = getExternalFilesDir(null);
                FileOutputStream fos = openFileOutput(objects.getMovieTitle() + ".dat", Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(objects);
                oos.close();

                Log.d("ABSOLUTE PATH", file.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
            }
            //Major problem here, cannot find problem
            System.out.println("____________________Getting to fragment manager for detail, not loading_____________________________");
            fragMan.beginTransaction().replace(R.id.detailContainer, Detail_Fragment.newInstance(movieObject), Detail_Fragment.TAG).commit();

        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }
    @Override

    public void onSaveInstanceState(Bundle outState) {

        TextView name = (TextView)findViewById(R.id.titletext);
        ListView lv = (ListView) findViewById(R.id.ListOptions);
        ArrayAdapter<String> arrayAD = (ArrayAdapter<String>)lv.getAdapter();
        int holder = arrayAD.getPosition(name.getText().toString());
        Log.d("LIST_KEY", "" + holder);
        outState.putInt(KEY_DISPLAY, holder);
        super.onSaveInstanceState(outState);
    }

    public Movie ReadingFromLocal (String selector) {

        String selectorData = selector + ".dat";
        try {

            FileInputStream fileInputStream = openFileInput(selectorData);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Movie movie = (Movie) objectInputStream.readObject();
            objectInputStream.close();

            return movie;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
