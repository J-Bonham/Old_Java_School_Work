package com.example.jbonham81.tabapp;

import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class DetailFragment extends Fragment {

    private static final String TAG = "----- Detail Frag -----";
    public static ArrayList<Data> itemsArray = new ArrayList<Data>();
    public static ArrayAdapter adapter;
    public static Data itemData;

    private TextView itemTitle;
    private TextView itemTime;
    private TextView itemMaterial;
    private TextView itemColor;
    private TextView itemAmount;
    private TextView itemNotes;

    private Button deleteButton;
    private Button editButton;
    private ArrayList<Data> itemsArrayList = new ArrayList<Data>();
    public static int deleteIndex;

    public DetailFragment () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detailfragment, container, false);

        itemTitle = (TextView) view.findViewById(R.id.titleText);
        itemTime = (TextView) view.findViewById(R.id.timeText);
        itemMaterial = (TextView) view.findViewById(R.id.materialText);
        itemColor = (TextView) view.findViewById(R.id.colorText);
        itemAmount = (TextView) view.findViewById(R.id.amountText);
        itemNotes = (TextView) view.findViewById(R.id.notesText);

        deleteButton = (Button) view.findViewById(R.id.deleteButton);



        Intent intent = getActivity().getIntent();
        itemsArrayList = (ArrayList<Data>) intent.getSerializableExtra("data");
        Bundle data = intent.getExtras();


        System.out.println(data);

        if (data != null) {

            loadData();
            String passTitle = itemsArray.toString();
            itemTitle.setText(data.toString());

            Log.i(TAG, itemTitle.toString());

            try {
                FileInputStream inputStream = getActivity().openFileInput("data.txt");
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                while (inputStream.available() != 0) {

                    itemData = (Data) objectInputStream.readObject();
                    itemsArray.add(itemData);
                    Log.i(TAG, itemsArray.toString());
                }
                objectInputStream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



        }

        return view;

   }





    //@Override
    public void loadData() {

        try {
            FileInputStream inputStream = getActivity().openFileInput("data.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            while (inputStream.available() != 0) {
                itemData = (Data) objectInputStream.readObject();
                itemsArray.add(itemData);
               Log.i(TAG, itemData.toString());
            }
            objectInputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}