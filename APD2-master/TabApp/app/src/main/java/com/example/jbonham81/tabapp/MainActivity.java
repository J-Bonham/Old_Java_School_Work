// Jeremiah Bonham
// 3D Printing Companion

package com.example.jbonham81.tabapp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;


public class MainActivity extends Activity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;

    public static ArrayList<Data> itemsArray = new ArrayList<Data>();

    ViewPager mViewPager;

    private static final int REQUEST_CODE = 2;

    //public static ArrayList<Data> itemsArray = new ArrayList<Data>();
    public static ArrayAdapter adapter;
    public static Data itemData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return HomeFragment.newInstance(position);
                case 1:
                    return AddItemFragment.newInstance(position);
                case 2:
                    return GalleryFragment.newInstance(position);
                case 3:
                    return ShoppingListFragment.newInstance(position);

            }
            return HomeFragment.newInstance(position);

        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);

            }
            return null;
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


    public void saveData() {

        try {

            FileOutputStream outputStream = openFileOutput("data.txt", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            for (int i = 0; i < itemsArray.size(); i++) {

                itemData = itemsArray.get(i);
                objectOutputStream.writeObject(itemData);
            }
            objectOutputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    //@Override
    public void loadData() {

        GalleryFragment mainFrag = (GalleryFragment) getFragmentManager().findFragmentById(R.id.pager);

        try {
            FileInputStream inputStream = openFileInput("data.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            while (inputStream.available() != 0) {
                itemData = (Data) objectInputStream.readObject();
                itemsArray.add(itemData);
            }
            objectInputStream.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
