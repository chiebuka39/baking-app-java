package com.harrric.chiebuka.bakingapp_java.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.harrric.chiebuka.bakingapp_java.fragments.StepFragment;
import com.harrric.chiebuka.bakingapp_java.fragments.tabs.StepsTab;
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
        return  new StepFragment();//StepsTab.newInstance(steps.get(i));
    }

    @Override
    public int getCount() {
        return steps.size();
    }
}
