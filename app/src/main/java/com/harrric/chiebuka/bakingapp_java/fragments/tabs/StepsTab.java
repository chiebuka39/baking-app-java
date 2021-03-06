package com.harrric.chiebuka.bakingapp_java.fragments.tabs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.harrric.chiebuka.bakingapp_java.R;
import com.harrric.chiebuka.bakingapp_java.adapters.StepsPagerAdapter;
import com.harrric.chiebuka.bakingapp_java.events.ClickedNextOrPrev;
import com.harrric.chiebuka.bakingapp_java.models.Recipe;
import com.harrric.chiebuka.bakingapp_java.models.StepsItem;

import org.greenrobot.eventbus.EventBus;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsTab extends Fragment {

    private ViewPager view_pager;
    private ImageView prev, next;
    private Recipe recipe;

    private int position = 0;

    public StepsTab() {
        // Required empty public constructor
    }

    public static StepsTab newInstance(String name){
        StepsTab stepsTab = new StepsTab();
        Bundle args = new Bundle();
        args.putString("STEPS", name);
        stepsTab.setArguments(args);

        return stepsTab;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_tabs, container, false);

        view_pager = (ViewPager)  view.findViewById(R.id.steps_pager);
        prev =(ImageView) view.findViewById(R.id.left);
        next = (ImageView) view.findViewById(R.id.right);
        String recipe_name =getArguments().getString("STEPS");
        RealmQuery<Recipe> realmQuery = Realm.getDefaultInstance().where(Recipe.class);

        recipe = realmQuery.equalTo("name", recipe_name).findFirst();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StepsPagerAdapter stepsAdapter = new StepsPagerAdapter(recipe.getSteps(),
                getActivity().getSupportFragmentManager());

        view_pager.setAdapter( stepsAdapter);


        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    prev.setEnabled(false);
                    next.setEnabled(true);
                } else {
                    prev.setEnabled(true);

                    if (position == recipe.getSteps().size() - 1) {
                        next.setEnabled(false);

                    } else {
                        next.setEnabled(true);
                    }
                }
            }


            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Prev", Toast.LENGTH_SHORT).show();
                position--;
                view_pager.setCurrentItem(position);
                EventBus.getDefault().post(new ClickedNextOrPrev());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Next", Toast.LENGTH_SHORT).show();
                ++position;
                view_pager.setCurrentItem(position);
                EventBus.getDefault().post(new ClickedNextOrPrev());
            }
        });
        if (position != -1) view_pager.setCurrentItem(position);
    }
}
