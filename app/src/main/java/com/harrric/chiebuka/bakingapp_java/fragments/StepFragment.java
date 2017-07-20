package com.harrric.chiebuka.bakingapp_java.fragments;


import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.harrric.chiebuka.bakingapp_java.R;
import com.harrric.chiebuka.bakingapp_java.models.Recipe;
import com.harrric.chiebuka.bakingapp_java.models.StepsItem;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {

    private StepsItem mStepsItem;
    private SimpleExoPlayerView exoView;
    private SimpleExoPlayer simpleExoPlayer;
    private TextView simpleDescription;
    private ImageView thumbnail;

    public static StepFragment newInstance(int stepsId){
        StepFragment stepFragment = new StepFragment();

        Bundle args = new Bundle();
        args.putInt("STEPS", stepsId);
        stepFragment.setArguments(args);
        return stepFragment;
    }

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            int recipe_id =getArguments().getInt("STEPS");
            RealmQuery<StepsItem> realmQuery = Realm.getDefaultInstance().where(StepsItem.class);

            mStepsItem = realmQuery.equalTo("id", recipe_id).findFirst();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_, container, false);

        exoView = (SimpleExoPlayerView) view.findViewById(R.id.exo_player);
        simpleDescription = (TextView) view.findViewById(R.id.step_description);
        thumbnail = (ImageView) view.findViewById(R.id.thumbnail_image);


        if(mStepsItem != null){
            simpleDescription.setText( mStepsItem.getDescription());

            if(mStepsItem.getVideoURL().isEmpty()){
                exoView.setVisibility( View.GONE);
            }else{
                exoView.setVisibility( View.VISIBLE);
                intializePlayer(mStepsItem.getVideoURL());
            }

            if(mStepsItem.getThumbnailURL() != null){
                thumbnail.setVisibility(View.VISIBLE);
                Glide.with(this).load(mStepsItem.getThumbnailURL()).into(thumbnail);
            }

        }




        return view;
    }


    private void intializePlayer(String mediaUrl) {

        exoView.setBackgroundColor(Color.BLACK);

        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        DefaultLoadControl loadControl = new DefaultLoadControl();
        SimpleExoPlayer exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

        Uri videoUri = Uri.parse(mediaUrl);
        DefaultHttpDataSourceFactory dataSourceFactory =
                new DefaultHttpDataSourceFactory("ExoPlayerDemo");
        DefaultExtractorsFactory extractor = new DefaultExtractorsFactory();
        ExtractorMediaSource videoSource =
                new  ExtractorMediaSource(videoUri, dataSourceFactory, extractor, null, null);
        exoPlayer.prepare(videoSource);



        exoView.setPlayer(exoPlayer);
    }

    @Override
    public void onStop() {
        super.onStop();
        stopPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlayer();
    }

    public void stopPlayer() {
        try {
            if (!mStepsItem.getVideoURL().isEmpty()) {
                simpleExoPlayer.stop();
                simpleExoPlayer.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
