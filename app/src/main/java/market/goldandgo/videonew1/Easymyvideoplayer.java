package market.goldandgo.videonew1;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.easyvideoplayer.EasyVideoCallback;
import market.goldandgo.videonew1.easyvideoplayer.EasyVideoPlayer;

/**
 * Created by Go Goal on 12/25/2017.
 */

public class Easymyvideoplayer extends AppCompatActivity implements EasyVideoCallback {

    private EasyVideoPlayer player;
    String url;
    FrameLayout fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easyvideoplayer);

        player = (EasyVideoPlayer) findViewById(R.id.player);

        player.setCallback(this);
        url=getIntent().getExtras().getString("url");
        player.setSource(null);
        player.setSource(Uri.parse(url));



            AdView adView = new AdView(this);
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId(Constant.Banner);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.ad_view);
            layout.addView(adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();


             fm = (FrameLayout) findViewById(R.id.ad_vi1ew);


            adView.loadAd(adRequest);
            adView.setAdListener(new AdListener() {

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();

                }
            });
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {

        fm.setVisibility(View.GONE);

    }

    @Override
    public void onPaused(EasyVideoPlayer player) {


            fm.setVisibility(View.VISIBLE);



    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {
        Log.e("EVP-Sample", "onPreparing()");
    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {
        Log.e("EVP-Sample", "onPrepared()");
        player.setAutoPlay(true);
    }

    @Override
    public void onBuffering(int percent) {
        Log.e("EVP-Sample", "onBuffering(): " + percent + "%");
    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {
        Log.e("EVP-Sample", "onError(): " + e.getMessage());

    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {
        Log.e("EVP-Sample", "onCompletion()");
    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {
       // Toast.makeText(this, "Retry", Toast.LENGTH_SHORT).show();
        Log.e("EVP-Sample", "Retry()");
    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {
       // Toast.makeText(this, "Submit", Toast.LENGTH_SHORT).show();
        Log.e("EVP-Sample", "Submit()");
    }

    @Override
    public void onClickVideoFrame(EasyVideoPlayer player) {
    //    Toast.makeText(this, "Click video frame.", Toast.LENGTH_SHORT).show();
    }
}
