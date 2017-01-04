package com.example.gosia.airqualityapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import java.util.Random;

public class WidgetProvider extends AppWidgetProvider{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            String number = String.format("%03d", (new Random().nextInt(900) + 100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.pollution_app_widget);
            remoteViews.setTextViewText(R.id.textViewPollutionInfoWidget, number); //info here

            Intent intent = new Intent(context, WidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_layout, pendingIntent); //layout as button
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
