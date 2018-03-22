package market.goldandgo.videonew1;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import market.goldandgo.videonew1.Adapter.Fragmentadapter_Main;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Downloadlist;
import market.goldandgo.videonew1.Object.Phonesize;
import market.goldandgo.videonew1.Object.phoneid;
import market.goldandgo.videonew1.Utils.CustomViewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout[] indicatorrl = new RelativeLayout[4];
    Fragmentadapter_Main adapter;
    static CustomViewPager pager;

    ImageView homeiv,movieiv,seriesiv,menuiv;
    AppCompatActivity ac;
    LinearLayout searchlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ac=this;
    //    phoneid pid=new phoneid(getApplicationContext());
     //   Downloadlist.setcontext(ac);

        searchlayout= (LinearLayout) findViewById(R.id.serachlayout);
        searchlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(getApplicationContext(),Search.class);
                startActivity(it);
                overridePendingTransition(0, 0);
            }
        });

        Constant.generateapi(MainActivity.this);
        indicatorrl[0] = (RelativeLayout) findViewById(R.id.homerrl);
        indicatorrl[1] = (RelativeLayout) findViewById(R.id.movierl);
        indicatorrl[2] = (RelativeLayout) findViewById(R.id.seriesrl);
        indicatorrl[3] = (RelativeLayout) findViewById(R.id.menurl);

        homeiv= (ImageView) findViewById(R.id.homeiv);
        movieiv= (ImageView) findViewById(R.id.movieiv);
        seriesiv= (ImageView) findViewById(R.id.seriesiv);
        menuiv= (ImageView) findViewById(R.id.menuiv);

        Phonesize psize = new Phonesize(MainActivity.this);

        int pwidth = psize.getwidth();

        for (int i = 0; i < indicatorrl.length; i++) {

            ViewGroup.LayoutParams robot_speechsize = indicatorrl[i].getLayoutParams();
            robot_speechsize.width = pwidth / 4;
            indicatorrl[i].setLayoutParams(robot_speechsize);
            indicatorrl[i].setOnClickListener(this);
        }


        pager= (CustomViewPager) findViewById(R.id.viewpager);
        adapter=new Fragmentadapter_Main(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(4);



        changeui(0);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                changeui(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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


        try{
            adView.loadAd(adRequest);
            adView.setAdListener(new AdListener() {

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    fm.setVisibility(View.VISIBLE);
                }
            });
        }catch (Exception e){

        }






    }

    @Override
    public void onClick(View v) {
        if (v == indicatorrl[0]) {

            changeui(0);

        } else if (v == indicatorrl[1]) {
            changeui(1);

        } else if (v == indicatorrl[2]) {

            changeui(2);
        } else if (v == indicatorrl[3]) {

            changeui(3);
        }
    }

    private void changeui(int l) {

        pager.setCurrentItem(l);

        if (l == 0) {

            homeiv.setBackgroundResource(R.drawable.ic_home);
            movieiv.setBackgroundResource(R.drawable.ic_movieicon1);
            seriesiv.setBackgroundResource(R.drawable.ic_tvicon1);
            menuiv.setBackgroundResource(R.drawable.ic_menu);

        } else if (l == 1) {
            homeiv.setBackgroundResource(R.drawable.ic_home1);
            movieiv.setBackgroundResource(R.drawable.ic_movieicon);
            seriesiv.setBackgroundResource(R.drawable.ic_tvicon1);
            menuiv.setBackgroundResource(R.drawable.ic_menu);

        } else if (l == 2) {
            homeiv.setBackgroundResource(R.drawable.ic_home1);
            movieiv.setBackgroundResource(R.drawable.ic_movieicon1);
            seriesiv.setBackgroundResource(R.drawable.ic_tvicon);
            menuiv.setBackgroundResource(R.drawable.ic_menu);

        }else if (l == 3) {
            homeiv.setBackgroundResource(R.drawable.ic_home1);
            movieiv.setBackgroundResource(R.drawable.ic_movieicon1);
            seriesiv.setBackgroundResource(R.drawable.ic_tvicon1);
            menuiv.setBackgroundResource(R.drawable.ic_menu1);

        }
    }

    public static void Pagerenable(boolean b) {
      //  pager.setPagingEnabled(false);
    }
}
