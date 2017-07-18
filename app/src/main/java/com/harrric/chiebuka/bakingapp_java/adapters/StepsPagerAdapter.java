package com.harrric.chiebuka.bakingapp_java.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.harrric.chiebuka.bakingapp_java.fragments.StepFragment;

import com.harrric.chiebuka.bakingapp_java.models.StepsItem;

import io.realm.RealmList;

/**
 * Created by chiebuka on 6/26/17.
 */

public class StepsPagerAdapter extends FragmentStatePagerAdapter {

    private RealmList<StepsItem> steps;
    public StepsPagerAdapter(RealmList<StepsItem> steps, FragmentManager fm) {
        super(fm);
        this.steps = steps;
    }

    @Override
    public Fragment getItem(int i) {
        Log.v("TAG_", steps.get(i).getDescription());
        //return  new BlankFragment();//StepFragment.newInstance(steps.get(i));
        return  StepFragment.newInstance(steps.get(i).getId());

    }

    @Override
    public int getCount() {
        return steps.size();
    }
}
