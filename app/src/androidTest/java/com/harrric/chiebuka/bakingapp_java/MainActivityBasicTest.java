package com.harrric.chiebuka.bakingapp_java;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.harrric.chiebuka.bakingapp_java.fragments.RecipeDetailFragment;
import com.harrric.chiebuka.bakingapp_java.fragments.RecipeListFragment;
import com.harrric.chiebuka.bakingapp_java.models.Recipe;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

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

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Test
    public void check_text_in_recycler_list_item() {

        mActivityTextRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecipeListFragment recipeListFragment = startRecipeListFragment();
            }
        });

        ViewInteraction textView = onView(
                allOf(withId(R.id.recipeName), withText("Nutella Pie"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_recycler),
                                        0),
                                0),
                        isDisplayed()));
        //textView.check(matches(withText("Nutella Pie".toLowerCase())));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipe_recycler), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));



    }

    @Test
    public void mainActivityTest2() {

        mActivityTextRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecipeListFragment recipeListFragment = startRecipeListFragment();
            }
        });

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipe_recycler), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatTextView = onView(
                allOf(withText("Steps"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.right), isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.right), isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.exo_play), withContentDescription("Play"), isDisplayed()));
        appCompatImageButton.perform(click());

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
