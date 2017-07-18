package com.harrric.chiebuka.bakingapp_java.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.harrric.chiebuka.bakingapp_java.fragments.tabs.IngredientsTab;
import com.harrric.chiebuka.bakingapp_java.fragments.tabs.StepsTab;
import com.harrric.chiebuka.bakingapp_java.models.Recipe;

/**
 * Created by chiebuka on 6/26/17.
 */

public class RecipePagerAdapter extends FragmentStatePagerAdapter {

    private int mTabNum;
    private Recipe mRecipe;

    public RecipePagerAdapter(int tabNum, Recipe recipe, FragmentManager fm) {
        super(fm);
        mTabNum = tabNum;
        mRecipe = recipe;
    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0){
            return IngredientsTab.newInstance(mRecipe.getName());
        }else {
            return StepsTab.newInstance(mRecipe.getName());
        }
    }

    @Override
    public int getCount() {
        return mTabNum;
    }
}
