package layout;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.gosia.airqualityapp.R;

public class PollutionInfoWidget extends AppWidgetProvider {
    private TextView textViewPollutionAnnotationWidget;
    private TextView textViewPollutionIndexWidget;
    private TextView textViewTemperatureWidget;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = PollutionInfoWidgetConfigureActivity.loadTitlePref(context, appWidgetId);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pollution_info_widget);
        views.setTextViewText(R.id.editTextCityWidget, widgetText);

        appWidgetManager.updateAppWidget(appWidgetId, views);



    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            PollutionInfoWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
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

