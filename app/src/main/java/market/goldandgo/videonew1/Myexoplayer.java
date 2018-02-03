package market.goldandgo.videonew1;

/**
 * Created by Go Goal on 1/24/2018.
 */
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.wang.avi.AVLoadingIndicatorView;

public class Myexoplayer extends AppCompatActivity  {

    String url;
    SimpleExoPlayer player;

    private BandwidthMeter bandwidthMeter;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private DataSource.Factory mediaDataSourceFactory;
    AVLoadingIndicatorView pg;
    AppCompatActivity ac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.exo_main);
        pg= (AVLoadingIndicatorView) findViewById(R.id.imgpg);
        pg.show();
        ac=this;

//Remove notification bar
        url=getIntent().getExtras().getString("url");


        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);


        SimpleExoPlayerView simpleExoPlayerView= (SimpleExoPlayerView) findViewById(R.id.video_view);
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        simpleExoPlayerView.setPlayer(player);

        player.setPlayWhenReady(shouldAutoPlay);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
                mediaDataSourceFactory, extractorsFactory, null, null);
        player.prepare(mediaSource);




        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                    if (isLoading){
                        pg.setVisibility(View.VISIBLE);
                        pg.show();
                    }
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch(playbackState) {
                    case ExoPlayer.STATE_BUFFERING:

                        break;
                    case ExoPlayer.STATE_ENDED:
                        //do what you want
                        break;
                    case ExoPlayer.STATE_IDLE:
                        break;

                    case ExoPlayer.STATE_READY:
                        pg.setVisibility(View.GONE);

                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

                ac.finish();
                Toast.makeText(ac,"Network Fail",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPositionDiscontinuity() {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });

        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        player.seekTo(0);

    }


    private void pausePlayer(){
        player.setPlayWhenReady(false);
        player.getPlaybackState();
    }
    private void startPlayer(){
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();

    }

    @Override
    protected void onResume() {
        super.onResume();
        startPlayer();
    }



}