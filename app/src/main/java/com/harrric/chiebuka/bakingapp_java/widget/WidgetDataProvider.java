package com.harrric.chiebuka.bakingapp_java.widget;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.harrric.chiebuka.bakingapp_java.R;
import com.harrric.chiebuka.bakingapp_java.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by chiebuka on 6/27/17.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    Recipe recipe  = null;
    List<Recipe> recipeList = null;
    int position = -1;
    int limit = 3;

    public WidgetDataProvider(Context context){
        mContext = context;
    }

    @Override
    public void onCreate() {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Recipe> realmQuery = realm.where(Recipe.class);
        RealmResults<Recipe> recipes = realmQuery.findAll();
        recipeList= new ArrayList<>();
        recipeList.addAll(recipes.subList(0, recipes.size()));
        Log.v("Harry", recipeList.get(2).getName());

    }

    @Override
    public void onDataSetChanged() {
        position++;
        Log.v("Harry", ""+position);
        recipe = recipeList.get(position);

        if(position == limit) {
            position = -1;
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.v("Harry_", ""+recipe.getIngredients().size());
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.list_view_item );
        Log.v("Harry__", recipe.getName());
        if(position == 0){
            remoteViews.setTextViewText(R.id.list_title,
                    "Recipe name:  ");
            remoteViews.setTextViewText(R.id.list_measure,
                    recipe.getName());
        }else{
            remoteViews.setTextViewText(R.id.list_title,
                    recipe.getIngredients().get(position).getIngredient());
            remoteViews.setTextViewText(R.id.list_measure,
                    recipe.getIngredients().get(position).getMeasure());
            remoteViews.setTextViewText(R.id.list_weight,
                    ""+recipe.getIngredients().get(position).getQuantity());
        }


        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
