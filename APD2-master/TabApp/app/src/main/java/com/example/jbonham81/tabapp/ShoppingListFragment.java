// Jeremiah Bonham
// 3D Printing Companion

package com.example.jbonham81.tabapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    Button save;
    Button edit;
    EditText shoppingList;

    public static ShoppingListFragment newInstance(int sectionNumber) {
        ShoppingListFragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ShoppingListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shoppinglist, container, false);

        Button save = (Button) view.findViewById(R.id.saveShopList);
        Button edit = (Button) view.findViewById(R.id.editShopList);

        EditText shoppingList = (EditText) view.findViewById(R.id.shoppinglist);


        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}

//
////        save.setOnClickListener(new View.OnClickListener() {
////            @Override
//            public void onClick(View view) {
//                // TODO Auto-generated method stub
//                try {
//                    String sl = shoppingList.getText().toString();
//                    if (!sl.trim().equals("")) {
//                        File file = new File("shoppinglist");
//
//                        //if file doesnt exists, then create it
//                        if (!file.exists()) {
//                            file.createNewFile();
//                        }
//
//
//                        FileWriter fileWritter = new FileWriter(file.getName(), true);
//                        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
//                        bufferWritter.write(sl);
//                        bufferWritter.close();
//                    }
//                } catch (IOException e) {
//
//                    e.printStackTrace();
//                }
//
//
//            }
//
//        });
//    }
}

