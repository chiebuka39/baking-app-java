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
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.harrric.chiebuka.bakingapp_java.R;
import com.harrric.chiebuka.bakingapp_java.models.StepsItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {

    private StepsItem mStepsItem;
    private SimpleExoPlayerView exoView;
    private SimpleExoPlayer simpleExoPlayer;
    private TextView simpleDescription;

    public static StepFragment newInstance(StepsItem stepsItem){
        StepFragment stepFragment = new StepFragment();

        Bundle args = new Bundle();
        args.putSerializable("STEPS", stepsItem);
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
            mStepsItem = (StepsItem) getArguments().getSerializable("STEPS");
            Log.v("TAG_STEP", mStepsItem.getDescription());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_, container, false);

        exoView = (SimpleExoPlayerView) view.findViewById(R.id.exo_player);
        simpleDescription = (TextView) view.findViewById(R.id.step_description);
        if(mStepsItem != null){
            simpleDescription.setText( mStepsItem.getDescription());

            if(mStepsItem.getVideoURL().isEmpty()){
                exoView.setVisibility( View.GONE);
            }else{
                exoView.setVisibility( View.VISIBLE);
                intializePlayer(mStepsItem.getVideoURL());
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
