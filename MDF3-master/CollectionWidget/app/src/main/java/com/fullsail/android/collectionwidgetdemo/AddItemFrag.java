// Jeremiah Bonham
// MDF 3 1501
// Week 3 - Widget

package com.fullsail.android.collectionwidgetdemo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddItemFrag extends Fragment {

    public ArrayList<Items> items = new ArrayList<Items>();

    private EditText itemTitle;
    private EditText itemType;
    private EditText itemBorrower;
    private Button saveButton;
    private Items item;

    public AddItemFrag() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.addfrag, container, false);

        itemTitle = (EditText) view.findViewById(R.id.title);
        itemType = (EditText) view.findViewById(R.id.type);
        itemBorrower = (EditText) view.findViewById(R.id.borrower);
        saveButton = (Button) view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titleText = itemTitle.getText().toString();
                String typeText = itemType.getText().toString();
                String borrowerText = itemBorrower.getText().toString();

                item = new Items();
                item.setTitle(titleText);
                item.setType(typeText);
                item.setBorrower(borrowerText);
                items.add(item);

                Intent intent = new Intent();
                intent.putExtra("itemData", items);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }



}
