package com.harrric.chiebuka.bakingapp_java;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;

import com.harrric.chiebuka.bakingapp_java.fragments.RecipeDetailFragment;
import com.harrric.chiebuka.bakingapp_java.fragments.RecipeListFragment;
import com.harrric.chiebuka.bakingapp_java.models.Recipe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by chiebuka on 6/24/17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {
    @Rule public ActivityTestRule<MainActivity> mActivityTextRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void fragment_can_be_instantiated() {
        mActivityTextRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecipeListFragment recipeListFragment = startRecipeListFragment();
            }
        });
        // Then use Espresso to test the Fragment
        onView(withId(R.id.recipe_recycler)).check(matches(isDisplayed()));
    }




    private RecipeListFragment startRecipeListFragment() {
        MainActivity activity = (MainActivity) mActivityTextRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        transaction.add(recipeListFragment, "recipe");
        transaction.commit();
        return recipeListFragment;
    }




}
