package com.example.submission3.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.submission3.R;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetFavorit extends AppWidgetProvider {
    public static final String TOAST_ACTION = "com.example.submission3.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.submission3.EXTRA_ITEM";

   private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

       Intent intent = new Intent(context, StackWidgetService.class);

       intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
       intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

       RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_favorit);
       views.setRemoteAdapter(R.id.stack_view, intent);
       views.setEmptyView(R.id.stack_view, R.id.empty_view);

       Intent toastIntent = new Intent(context, WidgetFavorit.class);
       toastIntent.setAction(WidgetFavorit.TOAST_ACTION);
       toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
       intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
       PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
       views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

       appWidgetManager.updateAppWidget(appWidgetId, views);
   }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

