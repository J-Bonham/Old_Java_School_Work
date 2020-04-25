// Jeremiah Bonham
// MDF 3 1501
// Week 3 - Widget

package com.fullsail.android.collectionwidgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class CollectionWidgetProvider extends AppWidgetProvider {
	
	public static final String ACTION_VIEW_DETAILS = "com.fullsail.android.ACTION_VIEW_DETAILS";
	public static final String EXTRA_ITEM = "com.fullsail.android.CollectionWidgetProvider.EXTRA_ITEM";
    public static final String ACTION_VIEW_ADD = "com.fullsail.android.CollectionWidgetProvider.ACTION_VIEW_ADD";
    private static final String TAG ="-----COLLECTIONPROVIDER-----";

    @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

        Log.i(TAG, "WIDGET UPDATING");

		for(int i = 0; i < appWidgetIds.length; i++) {
			
			int widgetId = appWidgetIds[i];




			Intent intent = new Intent(context, CollectionWidgetService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

			RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			widgetView.setRemoteAdapter(R.id.article_list, intent);
			widgetView.setEmptyView(R.id.article_list, R.id.empty);

            Intent detailIntent = new Intent(context, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widgetView.setPendingIntentTemplate(R.id.article_list, pIntent);

            Intent addIntent = new Intent(context, AddActivity.class);
            PendingIntent pIntent2 = PendingIntent.getActivity(context, 0, addIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widgetView.setOnClickPendingIntent(R.id.addButton, pIntent2);

			appWidgetManager.updateAppWidget(widgetId, widgetView);
            appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.article_list);
		}
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		if(intent.getAction().equals(ACTION_VIEW_DETAILS)) {
			Items article = (Items)intent.getSerializableExtra(EXTRA_ITEM);
			if(article != null) {
				Intent details = new Intent(context, DetailActivity.class);
				details.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				details.putExtra(DetailActivity.EXTRA_ITEM, article);
				context.startActivity(details);
			}
	 } else if (intent.getAction().equals(ACTION_VIEW_ADD)){
        Intent add = new Intent(context, MainActivity.class);
        add.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        add.putExtra(MainActivity.ACTION_VIEW_ADD, "addItems");
        context.startActivity(add);
    }

		super.onReceive(context, intent);
	}


}