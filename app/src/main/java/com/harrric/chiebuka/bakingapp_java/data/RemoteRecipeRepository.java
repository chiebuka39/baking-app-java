package com.harrric.chiebuka.bakingapp_java.data;

import com.harrric.chiebuka.bakingapp_java.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by chiebuka on 6/24/17.
 */

public class RemoteRecipeRepository {


    private RecipeApiService recipeApiService;

    public RemoteRecipeRepository(RecipeApiService recipeApiService){
        this.recipeApiService = recipeApiService;
    }

    public Observable<ArrayList<Recipe>> getRecipes(){
        return recipeApiService.recipes();
    }
}
