package com.harrric.chiebuka.bakingapp_java.fragments;


import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.harrric.chiebuka.bakingapp_java.R;
import com.harrric.chiebuka.bakingapp_java.adapters.RecipeAdapter;
import com.harrric.chiebuka.bakingapp_java.data.RecipeApiService;
import com.harrric.chiebuka.bakingapp_java.data.RemoteRecipeRepository;
import com.harrric.chiebuka.bakingapp_java.models.Recipe;

import java.util.ArrayList;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends Fragment {

    RemoteRecipeRepository recipeRepository;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    RecyclerView recyclerView;
    ProgressBar progressBar;
    RecipeAdapter recipeAdapter;

    private String PREFS_FILENAME = "com.example.chiebuka.thebakingapp";
    private String FIRST_LOAD = "first_load";
    private SharedPreferences prefs = null;


    public RecipeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recipe_recycler);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        recipeRepository = new RemoteRecipeRepository(RecipeApiService.creator.create());
        final Realm realm = Realm.getDefaultInstance();

        prefs = getActivity().getSharedPreferences(PREFS_FILENAME, 0);
        boolean firstLoad = prefs.getBoolean(FIRST_LOAD, true);

        if(firstLoad){
            compositeDisposable.add( recipeRepository.getRecipes()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(new Consumer<ArrayList<Recipe>>() {
                        @Override
                        public void accept(ArrayList<Recipe> recipes) throws Exception {
                            displayProgress(true);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<ArrayList<Recipe>>() {
                        @Override
                        public void accept(ArrayList<Recipe> recipes) throws Exception {
                            realm.beginTransaction();
                            for (Recipe recipe: recipes){
                                Recipe recip = realm.copyToRealm(recipe);
                            }
                            realm.commitTransaction();
                            recipeAdapter = new RecipeAdapter(getActivity(), recipes);
                            recyclerView.setAdapter(recipeAdapter);
                            setLayout_Manager();
                            displayProgress(false);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean(FIRST_LOAD, false);
                            editor.apply();
                        }
                    })
            );
        }else {

            RealmQuery<Recipe> realmQuery = realm.where(Recipe.class);
            RealmResults<Recipe> recipes = realmQuery.findAll();
            ArrayList<Recipe> recipeArrayList = new ArrayList<>();
            recipeArrayList.addAll(recipes.subList(0, recipes.size()));

            //Toast.makeText(getActivity(), recipeArrayList.get(0).getName(), Toast.LENGTH_SHORT).show();
            recipeAdapter = new RecipeAdapter(getActivity(), recipeArrayList);

            recyclerView.setAdapter(recipeAdapter);
            setLayout_Manager();
            //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            displayProgress(false);

        }





        return view;
    }

    private void setLayout_Manager() {
        if (getActivity().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        } else {

            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
    }

    private void  displayProgress(Boolean display){
        if(display){
            recyclerView.setVisibility( View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
