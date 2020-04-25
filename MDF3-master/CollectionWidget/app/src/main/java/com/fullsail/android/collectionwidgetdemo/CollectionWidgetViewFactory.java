// Jeremiah Bonham
// MDF 3 1501
// Week 3 - Widget

package com.fullsail.android.collectionwidgetdemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;

public class CollectionWidgetViewFactory implements RemoteViewsService.RemoteViewsFactory {
	
	private static final int ID_CONSTANT = 0x0101010;
    private static final String TAG ="-----COLLECTIONVIEWFACTORY-----";

    private ArrayList<Items> mArticles;
	private Context mContext;
	
	public CollectionWidgetViewFactory(Context context) {
		mContext = context;
		mArticles = new ArrayList<Items>();
	}

	@Override
	public void onCreate() {
        loadData();
        Log.i(TAG, mArticles.toString());
	}

    public void loadData(){

        try {
            FileInputStream fin = mContext.openFileInput("items.txt");
            ObjectInputStream oin = new ObjectInputStream(fin);
            int count = oin.readInt();
            mArticles = new ArrayList<Items>();
            for (int i = 0; i < count; i++)
                mArticles.add((Items) oin.readObject());
            oin.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


	@Override
	public int getCount() {
		return mArticles.size();
	}

	@Override
	public long getItemId(int position) {
		return ID_CONSTANT + position;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public RemoteViews getViewAt(int position) {
		
		Items article = mArticles.get(position);
		
		RemoteViews itemView = new RemoteViews(mContext.getPackageName(), R.layout.article_item);

		itemView.setTextViewText(R.id.titleText, article.getTitle());
		itemView.setTextViewText(R.id.typeText, article.getType());
		itemView.setTextViewText(R.id.borrowerText, article.getBorrower());
		
		Intent intent = new Intent();
		intent.putExtra(CollectionWidgetProvider.EXTRA_ITEM, article);
		itemView.setOnClickFillInIntent(R.id.article_item, intent);
		
		return itemView;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public void onDataSetChanged() {
		// Heavy lifting code can go here without blocking the UI.
		// You would update the data in your collection here as well.
        loadData();
	}

	@Override
	public void onDestroy() {
		mArticles.clear();
	}
	
}