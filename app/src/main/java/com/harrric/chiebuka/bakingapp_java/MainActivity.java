package com.harrric.chiebuka.bakingapp_java;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.harrric.chiebuka.bakingapp_java.events.SelectedRecipeEvent;
import com.harrric.chiebuka.bakingapp_java.fragments.RecipeDetailFragment;
import com.harrric.chiebuka.bakingapp_java.fragments.RecipeListFragment;
import com.harrric.chiebuka.bakingapp_java.models.Recipe;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            if(findViewById(R.id.container) != null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new RecipeListFragment())
                        .commit();
            }
        }




    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void onSelectedRecipeEvent(SelectedRecipeEvent recipeEvent){
        RecipeDetailFragment detailFragment = RecipeDetailFragment.newInstance( recipeEvent.recipe.getName());

        if(findViewById(R.id.detail_container) != null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, detailFragment)
                        .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
