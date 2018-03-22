package market.goldandgo.videonew1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Utils.Downloadhelper;
import market.goldandgo.videonew1.Utils.Mydatabase;


/**
 * Created by Go Goal on 5/26/2017.
 */

public class FakeDialog extends AppCompatActivity {

    String url, vid;
    ImageView iv;
    String thumbnail = Constant.host1 + "thumbnail.php?id=", type,imgpath;
    static Button sddown, hdown;
    static AppCompatActivity ac;
    static TextView sizetv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fakelayout);

        ac = this;

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


        iv = (ImageView) findViewById(R.id.video_thumbnail);
        sddown = (Button) findViewById(R.id.sddown);
        hdown = (Button) findViewById(R.id.hddown);
        sizetv = (TextView) findViewById(R.id.filesizetv);

        hdown.setText("Loading ...");
        hdown.setEnabled(false);
        hdown.setBackgroundColor(getResources().getColor(R.color.tethintcolor));

        sddown.setEnabled(false);
        sddown.setBackgroundColor(getResources().getColor(R.color.tethintcolor));

        MyRequest.gethdlink(vid);

        imgpath = getIntent().getExtras().getString("img");
        Bitmap myBitmap = BitmapFactory.decodeFile(imgpath);
        Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
        iv.setBackground(myDrawable);
        /*Picasso.with(this) //
                .load(thumbnail + vid)
                .into(new Target() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        iv.setBackground(drawable);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });*/




        sddown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("md")) {

                    Detail.Facebookfeedback(url, sdsize);
                    webviewdownloader.finishactivity();
                    finish();

                } else if (type.equals("sd")) {

                //    Seriesdetail.Facebookfeedback(url, sdsize);
                    webviewdownloader.finishactivity();
                    finish();

                } else {

                    dialog(url, sdsize);


                }
            }
        });

        hdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("md")) {

                    Detail.Facebookfeedback(HDlink, hsize);
                    webviewdownloader.finishactivity();
                    finish();

                } else if (type.equals("sd")) {

                  //  Seriesdetail.Facebookfeedback(HDlink, hsize);
                    webviewdownloader.finishactivity();
                    finish();

                } else {

                    dialog(url, hsize);


                }
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
            hdown.setText("HD DOWNLOAD");
            hdown.setEnabled(true);
            hdown.setBackgroundColor(ac.getResources().getColor(R.color.colorPrimary));
        }

        sddown.setEnabled(true);
        sddown.setBackgroundColor(ac.getResources().getColor(R.color.colorPrimary));
    }


    public void dialog(final String downurl, final String mbsize) {

        final String mname = System.currentTimeMillis() + "";

        AlertDialog.Builder ab = new AlertDialog.Builder(ac);
        ab.setTitle("Confirmation");
        View vv = ac.getLayoutInflater().inflate(R.layout.ontextzawwgyirow, null);
        Zawgyitextview tv = (Zawgyitextview) vv.findViewById(R.id.ttv);
        tv.setText("Are you sure to download this video?\n" +
                "ဤ video ကို သင္ Download ျပဳလုပ္မည္မွာေသခ်ာပါသလား  ?");
        ab.setView(vv);
        ab.setPositiveButton("Add To Download List", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                String filecover = Constant.datalocation_scover + "12s23123.fmovie";
                Mydatabase mdb=new Mydatabase(ac);
                mdb.insertdata(downurl+"",mname,"0",filecover,System.currentTimeMillis()+"");
                Intent it=new Intent(ac,Mydownloadmanager.class);
                ac.startActivity(it);


                webviewdownloader.finishactivity();
                ac.finish();

            }
        }).setNegativeButton("  Copy  ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setClipboard(ac,downurl);
                Toast.makeText(ac,"Copied Link",Toast.LENGTH_SHORT).show();

            }
        }).setNeutralButton("Cancel", null).show();
    }

    private static void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }
}
