package market.goldandgo.videonew1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import br.com.bemobi.medescope.Medescope;
import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Downloadlist;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Utils.NotificationData;
import market.goldandgo.videonew1.gandg.videoplayer.Videoplayer;


/**
 * Created by Go Goal on 5/26/2017.
 */

public class Choicetoview extends AppCompatActivity {

    String url, vid;
    ImageView iv;
    String thumbnail = Constant.host1 + "thumbnail.php?id=", type,name,imgpath;
    static Button sddown, hdown;
    static AppCompatActivity ac;
    static TextView sizetv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fakelayoutwatch);

        ac = this;
        imgpath = getIntent().getExtras().getString("img");
            AdView adView = new AdView(this);
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId(Constant.Banner);
            RelativeLayout layout = (RelativeLayout) findViewById(R.id.ad_view);
            layout.addView(adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();


            final FrameLayout fm = (FrameLayout) findViewById(R.id.ad_vi1ew);


            adView.loadAd(adRequest);
            adView.setAdListener(new AdListener() {

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    fm.setVisibility(View.VISIBLE);
                }
            });


        if (Constant.intershow()) {
            final InterstitialAd mInterstitialAd = new InterstitialAd(ac);
            mInterstitialAd.setAdUnitId(Constant.Inter);
            AdRequest adRequestinter = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequestinter);

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mInterstitialAd.show();
                }
            });


        }


        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        url = getIntent().getExtras().getString("url");
        type = getIntent().getExtras().getString("type");
        vid = getIntent().getExtras().getString("vid");
        name= getIntent().getExtras().getString("name");

        iv = (ImageView) findViewById(R.id.video_thumbnail);
        sddown = (Button) findViewById(R.id.sddown);
        hdown = (Button) findViewById(R.id.hddown);
        sizetv = (TextView) findViewById(R.id.filesizetv);

        hdown.setText("Loading ...");
        hdown.setEnabled(false);
        hdown.setBackgroundColor(getResources().getColor(R.color.tethintcolor));

        sddown.setEnabled(false);
        sddown.setBackgroundColor(getResources().getColor(R.color.tethintcolor));

        MyRequest.gethdlinkfromwatch(vid);


        Bitmap myBitmap = BitmapFactory.decodeFile(imgpath);
        Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
        iv.setBackground(myDrawable);


        sddown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ac, Myexoplayer.class);
                it.putExtra("url", url);
                it.putExtra("mname", name);
                ac.startActivity(it);
            }
        });

        hdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ac, Myexoplayer.class);
                it.putExtra("url", HDlink);
                it.putExtra("mname", name);
                ac.startActivity(it);
            }
        });


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public static void Feedback_fbError() {
        ac.finish();
    }

    static String HDlink, hsize, sdsize;

    public static void Facebookfeedback(String s) {
        HDlink = Jsonparser.getonestring(s, "hd_download_url");
        hsize = Jsonparser.getonestring(s, "hdsize");
        sdsize = Jsonparser.getonestring(s, "sdsize");
        sizetv.setText("SD SIZE : " + sdsize + " / HD SIZE : " + hsize);

        if (HDlink.equals("null")) {
            hdown.setText("NO HD VIDEO");
            hdown.setEnabled(false);
            hdown.setBackgroundColor(ac.getResources().getColor(R.color.tethintcolor));
        } else {
            hdown.setText("HD WATCH");
            hdown.setEnabled(true);
            hdown.setBackgroundColor(ac.getResources().getColor(R.color.colorPrimary));
        }

        sddown.setEnabled(true);
        sddown.setBackgroundColor(ac.getResources().getColor(R.color.colorPrimary));
    }




}
