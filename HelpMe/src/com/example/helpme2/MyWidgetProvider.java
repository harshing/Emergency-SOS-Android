package com.example.helpme2;

import com.example.helpme2.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by Jacek Milewski looksok.wordpress.com
 */

public class MyWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		for(int i=0;i<appWidgetIds.length;i++){
			int appWidgetId = appWidgetIds[i];
		
		
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_demo);
		/*
		 * remoteViews.setOnClickPendingIntent(R.id.widget_button,
		 * buildButtonPendingIntent(context));
		 */
		
		/*Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		ComponentName cn = new ComponentName("com.example.helpme2","com.example.helpme2.Options");
		intent.setComponent(cn);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/

		Intent configIntent = new Intent(context,Options.class);

		PendingIntent configPendingIntent = PendingIntent.getActivity(context,
				0, configIntent, 0);
		/*PendingIntent configPendingIntent = PendingIntent.getActivity(context,
				0, intent, 0);*/

		remoteViews.setOnClickPendingIntent(R.id.widget_button,
				configPendingIntent);

		pushWidgetUpdate(context, remoteViews);

		// /*RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
		// R.layout.widgetlayout);*/

		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
		}

	}

	public static PendingIntent buildButtonPendingIntent(Context context) {
		Intent intent = new Intent();
		intent.setAction("com.example.intent.action.CHANGE_PICTUREE");
		return PendingIntent.getBroadcast(context, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
	}

	public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
		ComponentName myWidget = new ComponentName(context,
				MyWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(myWidget, remoteViews);
	}
}
