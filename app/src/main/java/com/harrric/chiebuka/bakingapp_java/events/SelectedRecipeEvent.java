package com.harrric.chiebuka.bakingapp_java.events;

import com.harrric.chiebuka.bakingapp_java.models.Recipe;

/**
 * Created by chiebuka on 6/26/17.
 */

public class SelectedRecipeEvent {

    public Recipe recipe;

    public SelectedRecipeEvent(Recipe recipe){
        this.recipe = recipe;
    }
}
