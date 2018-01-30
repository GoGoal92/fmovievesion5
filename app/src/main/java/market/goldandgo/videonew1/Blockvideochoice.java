package market.goldandgo.videonew1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import market.goldandgo.videonew1.Object.Constant;

/**
 * Created by Go Goal on 11/25/2017.
 */

public class Blockvideochoice extends AppCompatActivity {


    static String url, imgpath,sdurl;
    ImageView iv;
    static Button hdown,sddown;
    static AppCompatActivity ac;
    static TextView sizetv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fakelayoutwatch);

        ac = this;


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

        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        url = getIntent().getExtras().getString("url");
        sdurl = getIntent().getExtras().getString("sdurl");
        imgpath = getIntent().getExtras().getString("img");


        iv = (ImageView) findViewById(R.id.video_thumbnail);
        hdown = (Button) findViewById(R.id.hddown);
        sddown= (Button) findViewById(R.id.sddown);
        sizetv = (TextView) findViewById(R.id.filesizetv);

        Bitmap myBitmap = BitmapFactory.decodeFile(imgpath);
        Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
        iv.setBackground(myDrawable);

        hdown.setEnabled(false);
        hdown.setBackgroundColor(getResources().getColor(R.color.tethintcolor));
        sddown.setEnabled(false);
        sddown.setBackgroundColor(getResources().getColor(R.color.tethintcolor));


         new Downloadsize().execute();

        sddown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ac, Myexoplayer.class);
                it.putExtra("url", sdurl);

                ac.startActivity(it);
            }
        });

        hdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ac, Myexoplayer.class);
                it.putExtra("url", url);

                ac.startActivity(it);
            }
        });

    }

  private  class Downloadsize extends AsyncTask<Void,Void,Void>{

        int file_size,sdsize;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL codeurl = new URL(url);
                URLConnection urlConnection = codeurl.openConnection();
                urlConnection.connect();
                file_size = urlConnection.getContentLength();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                URL codeurl = new URL(sdurl);
                URLConnection urlConnection = codeurl.openConnection();
                urlConnection.connect();
                sdsize = urlConnection.getContentLength();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            sizetv.setText("SD Size : "+getFileSize(sdsize)+" / HD Size : "+getFileSize(file_size));
            hdown.setEnabled(true);
            hdown.setBackgroundColor(ac.getResources().getColor(R.color.colorPrimary));

            if (sdsize !=0){

                sddown.setEnabled(true);
                sddown.setBackgroundColor(ac.getResources().getColor(R.color.colorAccent));
            }


        }
    }


    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
