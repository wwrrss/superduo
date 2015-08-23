package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link ScoreAppWidgetConfigureActivity ScoreAppWidgetConfigureActivity}
 */
public class ScoreAppWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            ScoreAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
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

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {



        //CharSequence widgetText = ScoreAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.score_app_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        //
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor =contentResolver.query(DatabaseContract.BASE_CONTENT_URI,null,null,null,"match_id DESC limit 1");
        // i will show in the widget the last match details
        String score;
        while(cursor.moveToNext()){
            cursor.getColumnCount();
            views.setTextViewText(R.id.home_name,cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_COL)));
            views.setTextViewText(R.id.away_name,cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_COL)));
            score = Utilies.getScores((Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_GOALS_COL)))),Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_GOALS_COL))));
            views.setTextViewText(R.id.score_textview,score);
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



}


