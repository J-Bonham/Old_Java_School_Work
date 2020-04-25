// Java 2 Week 3 1412
// Jeremiah Bonham

package com.example.jbonham81.java2_week3;

import android.app.ListFragment;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class MainFrag extends ListFragment {

    private int selectedIndex = -1;
    private ActionMode actionMode;

    public MainFrag (){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mainfrag, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity)getActivity()).loadData();

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    if(actionMode != null){
                        return false;
                    }

                    selectedIndex = position;
                    actionMode = getActivity().startActionMode(actionCallback);

                    return true;
                }
            });

    }

    @Override
    public void onListItemClick(ListView listview, View view, int position, long id) {
        super.onListItemClick(listview, view, position, id);

        MainActivity.deleteIndex = position;
        Items itemData = (Items) listview.getItemAtPosition(position);

        Bundle bundle = new Bundle();
        bundle.putString("Title", itemData.getTitle());
        bundle.putString("Type", itemData.getType());
        bundle.putString("Borrower", itemData.getBorrower());

        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        intent.putExtra("itemsArray", ((MainActivity)getActivity()).itemsArray);
        intent.putExtras (bundle);
        startActivity(intent);
    }

    private ActionMode.Callback actionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.delete, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {

                case R.id.delete:
                    MainActivity.itemsArray.remove(selectedIndex);
                    MainActivity.adapter.notifyDataSetChanged();
                    updateStorage();
                    mode.finish();

                    default:
                        return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };


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
