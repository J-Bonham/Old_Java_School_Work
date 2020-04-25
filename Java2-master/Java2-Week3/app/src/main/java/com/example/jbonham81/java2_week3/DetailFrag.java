// Java 2 Week 3 1412
// Jeremiah Bonham

package com.example.jbonham81.java2_week3;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DetailFrag extends Fragment {

    private TextView itemTitle;
    private TextView itemType;
    private TextView itemBorrower;
    private Button deleteButton;
    private Button remind;
    private ArrayList<Items> itemsArrayList = new ArrayList<Items>();
    public String borrowed;
    public DetailFrag () {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detailfrag, container, false);

        itemTitle = (TextView) view.findViewById(R.id.titleText);
        itemType = (TextView) view.findViewById(R.id.typeText);
        itemBorrower = (TextView) view.findViewById(R.id.borrowerText);
        deleteButton = (Button) view.findViewById(R.id.deletebutton);
        remind  = (Button) view.findViewById(R.id.remind);

        Intent intent = getActivity().getIntent();
        itemsArrayList = (ArrayList<Items>) intent.getSerializableExtra("itemArray");
        Bundle data = intent.getExtras();

        if (data != null) {
            String passTitle = data.getString("Title").toString();
            borrowed = data.get("Title").toString();
            String passType = data.getString("Type").toString();
            String passBorrower = data.getString("Borrower").toString();

            itemTitle.setText(passTitle);
            itemType.setText(passType);
            itemBorrower.setText(passBorrower);

        }

        deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int deleteIndex = MainActivity.deleteIndex;
                MainActivity.itemsArray.remove(deleteIndex);
                MainActivity.adapter.notifyDataSetChanged();
                updateStorage();
                getActivity().finish();
            }
        });

        remind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent implicit = new Intent(Intent.ACTION_SEND);
                implicit.putExtra(Intent.EXTRA_TEXT, "Reminding you that you borrowed " + borrowed + ".  Please return it at your earliest opportunity.");
                implicit.setType("text/plain");

                startActivity(implicit);
            }
        });

        return view;

    }

    private void updateStorage() {
        try {

            FileOutputStream outputStream = getActivity().openFileOutput("item.data", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            for (int i = 0; i < MainActivity.itemsArray.size(); i++) {
                MainActivity.itemData = MainActivity.itemsArray.get(i);

                objectOutputStream.writeObject(MainActivity.itemData);

            }
            objectOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}