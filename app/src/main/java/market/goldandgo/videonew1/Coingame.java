package market.goldandgo.videonew1;

import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.FlipHorizontalAnimation;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.wang.avi.AVLoadingIndicatorView;

import market.goldandgo.videonew1.Fragment.Fragment_menu;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Utils.calculate_st;

/**
 * Created by Go Goal on 10/23/2017.
 */



public class Coingame extends AppCompatActivity {

    static AVLoadingIndicatorView pg;
    static RelativeLayout network, mainrl;
    static TextView  remaintv;
    static AppCompatActivity ac;
    static String userid,remain;
    static Button tail_btn, head_btn;
    static EditText ed;
    static int userbet,userchoose;
    static ProgressBar mypg;
    static ImageView coin;
    static long myrealtoken;
    RelativeLayout screenmainrl;
    LinearLayout titlelayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin);
        ac = this;
        remaintv = (TextView) findViewById(R.id.remaintimetv);
        mypg = (ProgressBar) findViewById(R.id.mypg);

        coin = (ImageView) findViewById(R.id.coin);

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
        screenmainrl= (RelativeLayout) findViewById(R.id.screenmainrl);
        titlelayout= (LinearLayout) findViewById(R.id.linearLayout2);

        pg.show();

        Button reload = (Button) findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyRequest.getuserinfocoin();
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
            }
        });
        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MyRequest.getuserinfocoin();

        tail_btn = (Button) findViewById(R.id.tail_btn);
        head_btn = (Button) findViewById(R.id.head_btn);
        ed = (EditText) findViewById(R.id.eedd);


        head_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed.getText().length() < 1) {
                    Toast.makeText(getApplicationContext(), "Enter your bet amount", Toast.LENGTH_SHORT).show();
                } else {
                    userbet = Integer.parseInt(ed.getText().toString());
                    if (userbet > myrealtoken) {
                        Toast.makeText(getApplicationContext(), "Insufficent Token", Toast.LENGTH_SHORT).show();
                    } else if (userbet < 1000) {
                        Toast.makeText(getApplicationContext(), "Minimum Token must have 1000", Toast.LENGTH_SHORT).show();
                    } else {
                        userchoose = 1;
                        StartRandom();
                    }
                }


            }
        });


        tail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed.getText().length() < 1) {
                    Toast.makeText(getApplicationContext(), "Enter your bet amount", Toast.LENGTH_SHORT).show();
                } else {
                    userbet = Integer.parseInt(ed.getText().toString());
                    if (userbet > myrealtoken) {
                        Toast.makeText(getApplicationContext(), "Insufficent Token", Toast.LENGTH_SHORT).show();
                    } else if (userbet < 1000) {
                        Toast.makeText(getApplicationContext(), "Minimum Token must have 1000", Toast.LENGTH_SHORT).show();
                    } else {
                        userchoose = 0;
                        StartRandom();
                    }
                }
            }
        });


        screenmainrl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                screenmainrl.getWindowVisibleDisplayFrame(r);
                int screenHeight = screenmainrl.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;


                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    titlelayout.setVisibility(View.GONE);
                } else {
                    // keyboard is closed
                    titlelayout.setVisibility(View.VISIBLE);
                }
            }
        });




    }

    private void StartRandom() {
        disablebutton();
        mypg.setVisibility(View.VISIBLE);
        MyRequest.coin(userid,userchoose,userbet);

    }


    public void disablebutton() {
        ed.setEnabled(false);
        head_btn.setEnabled(false);
        tail_btn.setEnabled(false);
    }

    public static void enablebutton() {
        head_btn.setEnabled(true);
        tail_btn.setEnabled(true);
        ed.setEnabled(true);
        mypg.setVisibility(View.GONE);
    }

    public static void Feedback_Error() {
        network.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        mainrl.setVisibility(View.GONE);
        mypg.setVisibility(View.GONE);
        enablebutton();
    }

    public static void Feedback_resume(String s) {
        mainrl.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        pg.hide();
        remain = Jsonparser.getonestring(s, "static");
        userid = Jsonparser.getonestring(s, "userid");
        myrealtoken=Long.parseLong(remain);
        enablebutton();
        remaintv.setText(calculate_st.format(Long.parseLong(remain)));
    }

    public static void Feedbackfaosdonoaphin(String s) {

        Log.e("s",s);

        final String status=Jsonparser.getonestring(s,"status");
        final String randrom=Jsonparser.getonestring(s,"randrom");
        myrealtoken=Long.parseLong(Jsonparser.getonestring(s,"static"));

        FlipHorizontalAnimation anim = new FlipHorizontalAnimation(coin);
        anim.setDuration(150);
        anim.setListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(com.easyandroidanimations.library.Animation animation) {
                if (randrom.equals("1")) {
                    coin.setImageResource(R.drawable.head_coin);
                } else {
                    coin.setImageResource(R.drawable.tail_coin);
                }


                if (status.equals("1")){

                    MediaPlayer mp = MediaPlayer.create(ac, R.raw.getgold);
                    mp.start();

                    Toast.makeText(ac,"You Win",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ac,"You Loose",Toast.LENGTH_SHORT).show();

                }

                remaintv.setText(calculate_st.format(myrealtoken));

            }
        });
        anim.animate();

        enablebutton();
        Fragment_menu.sentgold(myrealtoken+"");

    }
}
