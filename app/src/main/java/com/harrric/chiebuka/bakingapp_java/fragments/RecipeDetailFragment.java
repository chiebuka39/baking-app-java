package com.harrric.chiebuka.bakingapp_java.fragments;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harrric.chiebuka.bakingapp_java.R;
import com.harrric.chiebuka.bakingapp_java.adapters.RecipePagerAdapter;
import com.harrric.chiebuka.bakingapp_java.models.Recipe;
import com.harrric.chiebuka.bakingapp_java.widget.BakingWidget;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment {

    Recipe mRecipe;
    ViewPager pager;
    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailFragment newInstance( String name){
        RecipeDetailFragment detailFragment = new RecipeDetailFragment();

        Bundle args = new Bundle();
        //args.putSerializable("RECIPE", recipe);
        args.putString("RECIPE_NAME", name);
        detailFragment.setArguments(args);

        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm = Realm.getDefaultInstance();
        if (getArguments() != null){
            //mRecipe =(Recipe) getArguments().getSerializable("RECIPE");
            String recipe_name =getArguments().getString("RECIPE_NAME");
            RealmQuery<Recipe> realmQuery = realm.where(Recipe.class);

            mRecipe = realmQuery.equalTo("name", recipe_name).findFirst();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        pager = (ViewPager) view.findViewById(R.id.pager);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout =(TabLayout)  view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Steps"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);




        RecipePagerAdapter recipePagerAdapter = new RecipePagerAdapter(
                tabLayout.getTabCount(), mRecipe, getActivity().getSupportFragmentManager());
        pager.setAdapter(recipePagerAdapter);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab != null) {
                    pager.setCurrentItem(tab.getPosition());
                    //EventBus.getDefault().post()
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}
