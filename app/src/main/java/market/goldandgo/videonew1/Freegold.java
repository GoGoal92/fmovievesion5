package market.goldandgo.videonew1;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.wang.avi.AVLoadingIndicatorView;

import market.goldandgo.videonew1.Fragment.Fragment_menu;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Downloadlist;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Utils.calculate_st;


/**
 * Created by Go Goal on 6/3/2017.
 */

public class Freegold extends AppCompatActivity {
    //static int[] degree = new int[]{0, 45, 90, 135, 180, 225, 270, 315};
    //static String[] price = new String[]{"3 day", "20 day", "6 hr", "5 day", "30 day", "1 day", "12 hr", "10 day"};


    static int[] degree = new int[]{315, 135, 225, 90, 270, 45, 180, 0};
    static String[] price = new String[]{"300", "500", "700", "800", "X 2", "X 4", "1000", "2000"};


    static RelativeLayout wheel, fullloading;
    static ImageView spinbtn;
    static MediaPlayer mp, winner;
    static AppCompatActivity ac;
    static RotateAnimation ra;


    static RelativeLayout light;
    static TextView price_tv;
    static LinearLayout tans_layout;
    static ProgressBar mypg;
    static Animation zoomout;

    static TextView ticktv, remaintv, mytimer;

    static String userid, ticket, remain;

    static CountDownTimer cdt;

    static long adstime;
    private long remaintime;

    static AVLoadingIndicatorView pg;
    static RelativeLayout network, mainrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ac = this;
        ticktv = (TextView) findViewById(R.id.myticket);
        remaintv = (TextView) findViewById(R.id.remaintimetv);
        mytimer = (TextView) findViewById(R.id.time);

       /* userid = getIntent().getExtras().getString("a");
        ticket = getIntent().getExtras().getString("b");
        remain = getIntent().getExtras().getString("c");*/

        MyRequest.getuserinfo();

        fullloading = (RelativeLayout) findViewById(R.id.fullloading);


      /*  remaintv.setText(calculate_st.format(Long.parseLong(remain)));
        ticktv.setText("Multiple : x" + ticket);*/


        wheel = (RelativeLayout) findViewById(R.id.wheel);
        spinbtn = (ImageView) findViewById(R.id.spiniv);
        mp = MediaPlayer.create(ac, R.raw.leverclick);
        winner = MediaPlayer.create(ac, R.raw.winner);

        light = (RelativeLayout) findViewById(R.id.light);
        price_tv = (TextView) findViewById(R.id.price);
        tans_layout = (LinearLayout) findViewById(R.id.back_tran);
        mypg = (ProgressBar) findViewById(R.id.mypg);

        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        zoomout = AnimationUtils.loadAnimation(ac, R.anim.zzz);

        spinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mInterstitialAd = new InterstitialAd(ac);
                mInterstitialAd.setAdUnitId(Constant.Inter);
                if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mInterstitialAd.loadAd(adRequest);
                }


                try {
                    if (mp.isPlaying()) {
                        mp.stop();
                        mp.release();
                        mp = MediaPlayer.create(getApplicationContext(), R.raw.leverclick);
                    }
                    mp.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                spinbtn.setEnabled(false);
                mypg.setVisibility(View.VISIBLE);
                MyRequest.faosdonoaphin(userid);


            }
        });

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

        pg = (AVLoadingIndicatorView) findViewById(R.id.avi);
        network = (RelativeLayout) findViewById(R.id.networkerro);
        mainrl= (RelativeLayout) findViewById(R.id.mainscreen);
        pg.show();

        Button reload = (Button) findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyRequest.getuserinfo();
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
            }
        });

    }


    static InterstitialAd mInterstitialAd;


    private static void Spin(final int rand) {
        mypg.setVisibility(View.GONE);
        MediaPlayer wheelsong = MediaPlayer.create(ac, R.raw.wheel);
        wheelsong.start();


        //  final int radomnumber = Integer.parseInt();
        ra = new RotateAnimation(0, 3600 + degree[rand], Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(9000);
        ra.setFillAfter(true);
        wheel.startAnimation(ra);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                winner.start();
                tans_layout.setVisibility(View.VISIBLE);
                price_tv.setVisibility(View.VISIBLE);
                light.setVisibility(View.VISIBLE);

                if (rand == 5 || rand == 4) {
                    price_tv.setText(price[rand]);
                } else {
                    price_tv.setText(price[rand] + " X " + ticket);
                }

                ticket = newticket;


                RotateAnimation ratio = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                ratio.setDuration(1000 * 5);
                ratio.setFillAfter(true);
                light.startAnimation(ratio);
                price_tv.startAnimation(zoomout);
            }
        }, 9000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                price_tv.setVisibility(View.GONE);
                light.clearAnimation();
                light.setVisibility(View.GONE);
                wheel.clearAnimation();
                mytimer.setVisibility(View.VISIBLE);

                spinbtn.setEnabled(false);
                remaintv.setText(calculate_st.format(Long.parseLong(remain)));
                ticktv.setText("My Ticket : x" + ticket);
                Downloadlist.committime();
                adstime = Downloadlist.getremaintime();
                start(adstime);

                if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }


            }
        }, 9000 + 4000);

    }


//Myclient.sent(userid);


    @Override
    public void onResume() {
        super.onResume();
        adstime = Downloadlist.getremaintime();

        if (adstime != 0) {

            start(adstime);
        }
        //   MyRequest.getuseridanduserticket_main();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            cdt.cancel();
        } catch (Exception e) {

        }

    }

    public static void Feedback(String s) {


        ticket = Jsonparser.getonestring(s, "ticket");

        mainrl.setVisibility(View.VISIBLE);
        ticktv.setText("My Ticket : x" + ticket);
        Downloadlist.committime();
        Toast.makeText(ac, "Congratulation You Got One Ticket", Toast.LENGTH_SHORT).show();
    }

    public static void Feedback_Error() {
        network.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        mainrl.setVisibility(View.GONE);
        fullloading.setVisibility(View.GONE);
        mypg.setVisibility(View.GONE);
        spinbtn.setEnabled(true);
        Toast.makeText(ac, "Network Fail, So Sorry PLease Try again :'(", Toast.LENGTH_SHORT).show();
    }

    static String newticket;

    public static void Feedbackfaosdonoaphin(String s) {

        newticket = Jsonparser.getonestring(s, "ticket");
        remain = Jsonparser.getonestring(s, "remain");
        int ran = Integer.parseInt(Jsonparser.getonestring(s, "ran"));
        Fragment_menu.sentgold(remain);

        Spin(ran);

    }

    public static void Feedback_resume(String s) {


        mainrl.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        pg.hide();
        fullloading.setVisibility(View.GONE);
        ticket = Jsonparser.getonestring(s, "ticket");
        remain = Jsonparser.getonestring(s, "static");
        userid = Jsonparser.getonestring(s, "userid");

        ticktv.setText("Multiple : x" + ticket);
        remaintv.setText(calculate_st.format(Long.parseLong(remain)));

    }

    public static void start(Long remai) {


        try {
            cdt.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }


        cdt = new CountDownTimer(remai, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                long seconds = millisUntilFinished / 1000;
                mytimer.setText(String.format("%02d:%02d:%02d", seconds / 3600,
                        (seconds % 3600) / 60, (seconds % 60)));

            }

            @Override
            public void onFinish() {

                spinbtn.setEnabled(true);
                mytimer.setVisibility(View.GONE);
                tans_layout.setVisibility(View.GONE);
                price_tv.setVisibility(View.GONE);


            }
        }.start();
    }


}
