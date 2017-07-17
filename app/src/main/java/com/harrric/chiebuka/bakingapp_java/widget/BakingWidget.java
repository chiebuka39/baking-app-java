package com.harrric.chiebuka.bakingapp_java.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import com.harrric.chiebuka.bakingapp_java.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {

    public static final String WIDGET_IM_NEXT ="AppWidget_imNext";
    public static final String WIDGET_IM_PREV ="AppWidget_imPrev";



    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        //RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bakig_widget);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_widget);




        remoteViews.setTextViewText(R.id.recipe_title, "Recipe Ingredients");
        //views.setTextViewText(R.id.recipe_title, "harry");
        setRemoteAdapter(context,remoteViews);

        remoteViews.setOnClickPendingIntent(R.id.next, getPendingSelfIntent(context,WIDGET_IM_NEXT));

        // Instruct the widget manager to update the widget
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.layout.list_view_item);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.ingredients,
                new Intent(context, BakingService.class));
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
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        Log.v("Harry", "dataset changed3");
        if(action.equals(BakingWidget.WIDGET_IM_NEXT)){

            Log.v("Harry", "dataset changed4  ");
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, BakingWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.ingredients);

        }
        super.onReceive(context, intent);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}

