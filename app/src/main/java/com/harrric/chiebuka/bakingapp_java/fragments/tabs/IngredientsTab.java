package com.harrric.chiebuka.bakingapp_java.fragments.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harrric.chiebuka.bakingapp_java.R;
import com.harrric.chiebuka.bakingapp_java.adapters.IngredientsAgapter;
import com.harrric.chiebuka.bakingapp_java.models.Recipe;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsTab extends Fragment {

    private static final String ARG_PARAM1 = "recipe";
    private Recipe recipe;



    public IngredientsTab() {
        // Required empty public constructor
    }


    public static IngredientsTab newInstance(String name) {
        IngredientsTab fragment = new IngredientsTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String recipe_name =getArguments().getString(ARG_PARAM1);
            RealmQuery<Recipe> realmQuery = Realm.getDefaultInstance().where(Recipe.class);

            recipe = realmQuery.equalTo("name", recipe_name).findFirst();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_tabs, container, false);
        Log.v("Ingredients", "${recipe.ingredients?.size}");

        RecyclerView recyclerIngredients =(RecyclerView) view.findViewById(R.id.recycler_ingredients);
        IngredientsAgapter ingredientsAdapter = new IngredientsAgapter(recipe.getIngredients(), getActivity());
        recyclerIngredients.setAdapter(ingredientsAdapter);
        recyclerIngredients.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

}
