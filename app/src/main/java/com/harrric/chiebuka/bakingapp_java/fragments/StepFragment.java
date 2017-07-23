package com.harrric.chiebuka.bakingapp_java.fragments;


import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.harrric.chiebuka.bakingapp_java.R;
import com.harrric.chiebuka.bakingapp_java.events.ClickedNextOrPrev;
import com.harrric.chiebuka.bakingapp_java.models.Recipe;
import com.harrric.chiebuka.bakingapp_java.models.StepsItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment implements ExoPlayer.EventListener {

    private StepsItem mStepsItem;
    private SimpleExoPlayerView exoView;
    private SimpleExoPlayer simpleExoPlayer;
    private TextView simpleDescription;
    private ImageView thumbnail;

    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    private final String TAG = StepFragment.class.getSimpleName();

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
        EventBus.getDefault().register(this);
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
                initializeMediaSession();
                intializePlayer(mStepsItem.getVideoURL());
            }

            if(!mStepsItem.getThumbnailURL().isEmpty()){
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
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

        Uri videoUri = Uri.parse(mediaUrl);

        DefaultHttpDataSourceFactory dataSourceFactory =
                new DefaultHttpDataSourceFactory("ExoPlayerDemo");
        DefaultExtractorsFactory extractor = new DefaultExtractorsFactory();
        ExtractorMediaSource videoSource =
                new  ExtractorMediaSource(videoUri, dataSourceFactory, extractor, null, null);
        simpleExoPlayer.prepare(videoSource);



        exoView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.addListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        stopPlayer();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMediaSession!=null) {
            mMediaSession.setActive(false);
        }
        stopPlayer();
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    int w = 0;
    @Subscribe
    public void onClickedNextOrPrevEvent(ClickedNextOrPrev clickedNextOrPrev){
        Log.v("HARRY", "clicked "+ ++w);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible()) {
            if(!isVisibleToUser) {
                if (simpleExoPlayer != null) {
                    //Pause video when user swipes away in view pager
                    simpleExoPlayer.setPlayWhenReady(false);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopPlayer();
    }



    public void stopPlayer() {
        //TODO: add method of knowing when first called
        if(simpleExoPlayer!=null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }

    }

    private void initializeMediaSession() {
        mMediaSession = new MediaSessionCompat(getContext(), TAG);
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new MySessionCallback());
        mMediaSession.setActive(true);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    simpleExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            simpleExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            simpleExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            simpleExoPlayer.seekTo(0);
        }
    }
}
