package market.goldandgo.videonew1.Fragment;

/**
 * Created by Go Goal on 11/25/2016.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import market.goldandgo.videonew1.API.HorizontalListView;
import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Adapter.Adsadapter;
import market.goldandgo.videonew1.Adapter.Fragmentadapter_ADS;
import market.goldandgo.videonew1.Adapter.RecMoiveadapter;
import market.goldandgo.videonew1.Adapter.RecSeriesadapter;
import market.goldandgo.videonew1.Adapter.Recentadapter;
import market.goldandgo.videonew1.Adapter.TopMoiveadapter;
import market.goldandgo.videonew1.Adapter.TopSeriesadapter;
import market.goldandgo.videonew1.Detail;
import market.goldandgo.videonew1.Freegold;
import market.goldandgo.videonew1.MainActivity;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.SeriesDetail;
import market.goldandgo.videonew1.Utils.AutoScrollViewPager;
import market.goldandgo.videonew1.Utils.Download_data;
import market.goldandgo.videonew1.Utils.calculate_st;
import me.relex.circleindicator.CircleIndicator;


/**
 * Created by Go Goal on 11/20/2016.
 */

public class Fragment_Home extends Fragment implements View.OnClickListener {


    public static Fragment_Home newInstance() {

        Bundle args = new Bundle();

        Fragment_Home fragment = new Fragment_Home();
        fragment.setArguments(args);
        return fragment;
    }

    static FragmentActivity ac;




    @Override
    public void onResume() {
        super.onResume();

        //  MainActivity.Pagerenable(false);
    }

    static NestedScrollView sv;
    static AVLoadingIndicatorView pg;
    static RelativeLayout network;
    static ArrayList<get>  topratedlist, topserislist, recmovlist, recserieslist;
    static ArrayList<get> adlist=new ArrayList<>();

    static ImageView iv_recommdmovie[] = new ImageView[5];
    static Zawgyitextview tv_recommdmovie[] = new Zawgyitextview[5];


    static ImageView iv_topmovie[] = new ImageView[5];
    static Zawgyitextview tv_topmovie[] = new Zawgyitextview[5];


    static ImageView iv_topseries[] = new ImageView[5];
    static Zawgyitextview tv_topseries[] = new Zawgyitextview[5];

    static ImageView iv_recseries[] = new ImageView[5];
    static Zawgyitextview tv_recseries[] = new Zawgyitextview[5];


    static LinearLayout ly_recommdmovie[] = new LinearLayout[5];
    static LinearLayout ly_topmovie[] = new LinearLayout[5];

    static LinearLayout ly_topseries[] = new LinearLayout[5];
    static LinearLayout ly_recseries[] = new LinearLayout[5];




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ac = getActivity();
        MyRequest.Mainpage();
        adapter = new Adsadapter(ac, new ArrayList<get>());

        File moviepath = new File(Constant.datalocation_movie);
        if (!moviepath.exists()) {
            moviepath.mkdir();
        }

        File scoverpath = new File(Constant.datalocation_scover);
        if (!scoverpath.exists()) {
            scoverpath.mkdir();
        }

        File adspath = new File(Constant.datalocation_ads);
        if (!adspath.exists()) {
            adspath.mkdir();
        }


    }

    public AutoScrollViewPager vpager;
    public CircleIndicator indicator;
    static Adsadapter adapter;


    static RelativeLayout buylaout;
    static TextView priceinfo;
    static Button buy, cancel, okay;
    static SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshlayoutmain);
        swipeLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyRequest.Mainpage();
            }
        });

        ly_recommdmovie[0] = (LinearLayout) v.findViewById(R.id.ly_recmovie1);
        ly_recommdmovie[1] = (LinearLayout) v.findViewById(R.id.ly_recmovie2);
        ly_recommdmovie[2] = (LinearLayout) v.findViewById(R.id.ly_recmovie3);
        ly_recommdmovie[3] = (LinearLayout) v.findViewById(R.id.ly_recmovie4);
        ly_recommdmovie[4] = (LinearLayout) v.findViewById(R.id.ly_recmovie5);

        ly_topmovie[0] = (LinearLayout) v.findViewById(R.id.ly_topmovie1);
        ly_topmovie[1] = (LinearLayout) v.findViewById(R.id.ly_topmovie2);
        ly_topmovie[2] = (LinearLayout) v.findViewById(R.id.ly_topmovie3);
        ly_topmovie[3] = (LinearLayout) v.findViewById(R.id.ly_topmovie4);
        ly_topmovie[4] = (LinearLayout) v.findViewById(R.id.ly_topmovie5);

        ly_topseries[0] = (LinearLayout) v.findViewById(R.id.ly_topseries1);
        ly_topseries[1] = (LinearLayout) v.findViewById(R.id.ly_topseries2);
        ly_topseries[2] = (LinearLayout) v.findViewById(R.id.ly_topseries3);
        ly_topseries[3] = (LinearLayout) v.findViewById(R.id.ly_topseries4);
        ly_topseries[4] = (LinearLayout) v.findViewById(R.id.ly_topseries5);

        ly_recseries[0] = (LinearLayout) v.findViewById(R.id.ly_recseries1);
        ly_recseries[1] = (LinearLayout) v.findViewById(R.id.ly_recseries2);
        ly_recseries[2] = (LinearLayout) v.findViewById(R.id.ly_recseries3);
        ly_recseries[3] = (LinearLayout) v.findViewById(R.id.ly_recseries4);
        ly_recseries[4] = (LinearLayout) v.findViewById(R.id.ly_recseries5);

        for (int i = 0; i < 5; i++) {
            ly_recommdmovie[i].setOnClickListener(this);
            ly_topmovie[i].setOnClickListener(this);
            ly_topseries[i].setOnClickListener(this);
            ly_recseries[i].setOnClickListener(this);

        }


        iv_recommdmovie[0] = (ImageView) v.findViewById(R.id.iv_recmovie1);
        iv_recommdmovie[1] = (ImageView) v.findViewById(R.id.iv_recmovie2);
        iv_recommdmovie[2] = (ImageView) v.findViewById(R.id.iv_recmovie3);
        iv_recommdmovie[3] = (ImageView) v.findViewById(R.id.iv_recmovie4);
        iv_recommdmovie[4] = (ImageView) v.findViewById(R.id.iv_recmovie5);
        tv_recommdmovie[0] = (Zawgyitextview) v.findViewById(R.id.tv_recmovie1);
        tv_recommdmovie[1] = (Zawgyitextview) v.findViewById(R.id.tv_recmovie2);
        tv_recommdmovie[2] = (Zawgyitextview) v.findViewById(R.id.tv_recmovie3);
        tv_recommdmovie[3] = (Zawgyitextview) v.findViewById(R.id.tv_recmovie4);
        tv_recommdmovie[4] = (Zawgyitextview) v.findViewById(R.id.tv_recmovie5);

        iv_topmovie[0] = (ImageView) v.findViewById(R.id.iv_topmovie1);
        iv_topmovie[1] = (ImageView) v.findViewById(R.id.iv_topmovie2);
        iv_topmovie[2] = (ImageView) v.findViewById(R.id.iv_topmovie3);
        iv_topmovie[3] = (ImageView) v.findViewById(R.id.iv_topmovie4);
        iv_topmovie[4] = (ImageView) v.findViewById(R.id.iv_topmovie5);
        tv_topmovie[0] = (Zawgyitextview) v.findViewById(R.id.tv_topmovie1);
        tv_topmovie[1] = (Zawgyitextview) v.findViewById(R.id.tv_topmovie2);
        tv_topmovie[2] = (Zawgyitextview) v.findViewById(R.id.tv_topmovie3);
        tv_topmovie[3] = (Zawgyitextview) v.findViewById(R.id.tv_topmovie4);
        tv_topmovie[4] = (Zawgyitextview) v.findViewById(R.id.tv_topmovie5);

        iv_topseries[0] = (ImageView) v.findViewById(R.id.iv_topseries1);
        iv_topseries[1] = (ImageView) v.findViewById(R.id.iv_topseries2);
        iv_topseries[2] = (ImageView) v.findViewById(R.id.iv_topseries3);
        iv_topseries[3] = (ImageView) v.findViewById(R.id.iv_topseries4);
        iv_topseries[4] = (ImageView) v.findViewById(R.id.iv_topseries5);
        tv_topseries[0] = (Zawgyitextview) v.findViewById(R.id.tv_topseries1);
        tv_topseries[1] = (Zawgyitextview) v.findViewById(R.id.tv_topseries2);
        tv_topseries[2] = (Zawgyitextview) v.findViewById(R.id.tv_topseries3);
        tv_topseries[3] = (Zawgyitextview) v.findViewById(R.id.tv_topseries4);
        tv_topseries[4] = (Zawgyitextview) v.findViewById(R.id.tv_topseries5);


        iv_recseries[0] = (ImageView) v.findViewById(R.id.iv_recseries1);
        iv_recseries[1] = (ImageView) v.findViewById(R.id.iv_recseries2);
        iv_recseries[2] = (ImageView) v.findViewById(R.id.iv_recseries3);
        iv_recseries[3] = (ImageView) v.findViewById(R.id.iv_recseries4);
        iv_recseries[4] = (ImageView) v.findViewById(R.id.iv_recseries5);
        tv_recseries[0] = (Zawgyitextview) v.findViewById(R.id.tv_recseries1);
        tv_recseries[1] = (Zawgyitextview) v.findViewById(R.id.tv_recseries2);
        tv_recseries[2] = (Zawgyitextview) v.findViewById(R.id.tv_recseries3);
        tv_recseries[3] = (Zawgyitextview) v.findViewById(R.id.tv_recseries4);
        tv_recseries[4] = (Zawgyitextview) v.findViewById(R.id.tv_recseries5);


        vpager = (AutoScrollViewPager) v.findViewById(R.id.viewpagermain);
        indicator = (CircleIndicator) v.findViewById(R.id.indicatormain);
        vpager.setAdapter(adapter);
        vpager.setCurrentItem(180);
        indicator.setViewPager(vpager);
        vpager.startAutoScroll(4000);


        sv = (NestedScrollView) v.findViewById(R.id.sv);
        pg = (AVLoadingIndicatorView) v.findViewById(R.id.avi);
        network = (RelativeLayout) v.findViewById(R.id.networkerro);
        pg.show();

        Button reload = (Button) v.findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRequest.Mainpage();
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
            }
        });


        buylaout = (RelativeLayout) v.findViewById(R.id.buyalert);
        priceinfo = (TextView) v.findViewById(R.id.price);
        buy = (Button) v.findViewById(R.id.buy);
        cancel = (Button) v.findViewById(R.id.cancel);
        okay = (Button) v.findViewById(R.id.okay);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buylaout.setVisibility(View.GONE);
                okay.setVisibility(View.GONE);
                Intent it = new Intent(ac, Freegold.class);
                ac.startActivity(it);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buylaout.setVisibility(View.GONE);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel.setEnabled(false);
                buy.setEnabled(false);
                cancel.setBackgroundColor(getResources().getColor(R.color.tethintcolor));
                buy.setBackgroundColor(getResources().getColor(R.color.tethintcolor));
                priceinfo.setText("Please Wait , Loading ....");
                MyRequest.buyserieshome(buymid);

            }
        });


        return v;
    }


    public static void Feedback(String s) {

        ClearIMage();

        swipeLayout.setRefreshing(false);
        sv.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        network.setVisibility(View.GONE);
        pg.hide();

        adlist = new ArrayList<>();
        adlist = Jsonparser.getadlist(s);
        adapter.refresh(adlist);

        topserislist = new ArrayList<>();
        topserislist = Jsonparser.getseriesrated(s, "topseries");

        recserieslist = new ArrayList<>();
        recserieslist = Jsonparser.getseriesrated(s, "recseries");

        recmovlist = new ArrayList<>();
        recmovlist = Jsonparser.getMovierated(s, "recmovie");

        topratedlist = new ArrayList<>();
        topratedlist = Jsonparser.getMovierated(s, "top");

        for (int i = 0; i < 5; i++) {
            tv_recommdmovie[i].setText(recmovlist.get(i).getTitle());
            tv_topmovie[i].setText(topratedlist.get(i).getTitle());
            tv_topseries[i].setText(topserislist.get(i).getTitle());
            tv_recseries[i].setText(recserieslist.get(i).getTitle());


        }



       // Imagecondition();
        Imagecondition_RM();
        Imagecondition_TM();
        Imagecondition_TS();
        Imagecondition_RS();
    }

    private static void ClearIMage() {
        for(int i=0;i<5;i++){
            iv_recseries[i].setBackgroundColor(R.color.tethintcolor);
            iv_topmovie[i].setBackgroundColor(R.color.tethintcolor);
            iv_topseries[i].setBackgroundColor(R.color.tethintcolor);
            iv_recommdmovie[i].setBackgroundColor(R.color.tethintcolor);

        }

    }


    /////////////////////RS

    static String progressadsimageRS = "0";

    private static void Imagecondition_RS() {
        for (int i = 0; i < recserieslist.size(); i++) {


            File fi = new File(Constant.datalocation_scover + recserieslist.get(i).getMid() + ".fmovie");
            Log.e("file", fi.toString());
            if (!fi.exists()) {

                if (progressadsimageRS.equals("0")) {
                    progressadsimageRS = recserieslist.get(i).getMid();
                    new Downloadads_RS(recserieslist.get(i).getImage(), recserieslist.get(i).getMid(), i).execute();
                    break;
                }

            } else {
                String fpath = Constant.datalocation_scover + recserieslist.get(i).getMid() + ".fmovie";
                Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                iv_recseries[i].setBackground(myDrawable);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v == ly_recommdmovie[0]) {
            Moiveclick(0, "rec");
        }
        if (v == ly_recommdmovie[1]) {
            Moiveclick(1, "rec");
        }
        if (v == ly_recommdmovie[2]) {
            Moiveclick(2, "rec");
        }
        if (v == ly_recommdmovie[3]) {
            Moiveclick(3, "rec");
        }
        if (v == ly_recommdmovie[4]) {
            Moiveclick(4, "rec");
        }

        if (v == ly_topmovie[0]) {
            Moiveclick(0, "top");
        }
        if (v == ly_topmovie[1]) {
            Moiveclick(1, "top");
        }
        if (v == ly_topmovie[2]) {
            Moiveclick(2, "top");
        }
        if (v == ly_topmovie[3]) {
            Moiveclick(3, "top");
        }
        if (v == ly_topmovie[4]) {
            Moiveclick(4, "top");
        }

        if (v == ly_topseries[0]) {
            Seriesclick(0, "top");
        }
        if (v == ly_topseries[1]) {
            Seriesclick(1, "top");
        }
        if (v == ly_topseries[2]) {
            Seriesclick(2, "top");
        }
        if (v == ly_topseries[3]) {
            Seriesclick(3, "top");
        }
        if (v == ly_topseries[4]) {
            Seriesclick(4, "top");
        }

        if (v == ly_recseries[0]) {
            Seriesclick(0, "rec");
        }
        if (v == ly_recseries[1]) {
            Seriesclick(1, "rec");
        }
        if (v == ly_recseries[2]) {
            Seriesclick(2, "rec");
        }
        if (v == ly_recseries[3]) {
            Seriesclick(3, "rec");
        }
        if (v == ly_recseries[4]) {
            Seriesclick(4, "rec");
        }

    }

    private void Seriesclick(int finalK, String rec) {
        ArrayList<get> clicklist = new ArrayList<>();
        if (rec.equals("rec")) {
            clicklist = recserieslist;
        }
        if (rec.equals("top")) {
            clicklist = topserislist;
        }


        if (clicklist.get(finalK).getPrice().equals("Free") || clicklist.get(finalK).getMine().equals("true")) {


            Intent it = new Intent(ac, SeriesDetail.class);
            String s = clicklist.get(finalK).getMid();
            if (s.contains("s")) {
                s = s.replace("s", "");

            }

            it.putExtra("mid", s);
            it.putExtra("mname", clicklist.get(finalK).getTitle());
            it.putExtra("like", clicklist.get(finalK).getLike() + " Like");
            it.putExtra("view", clicklist.get(finalK).getView() + " View");
            it.putExtra("ep", clicklist.get(finalK).getEpisode());
            it.putExtra("price", clicklist.get(finalK).getPrice());
            it.putExtra("detail", clicklist.get(finalK).getDetail());
            ac.startActivity(it);

        } else {

            showbutalert(clicklist.get(finalK).getPrice(), clicklist.get(finalK).getMid(), clicklist.get(finalK).getTitle(), finalK, rec);

        }


    }


    static String buymid;
    static int posti;
    static String buytype;

    public static void showbutalert(String price, String mid, String titlr, int i, String top) {
        buytype = top;
        posti = i;
        buymid = mid;
        cancel.setEnabled(true);
        buy.setEnabled(true);
        cancel.setVisibility(View.VISIBLE);
        buy.setVisibility(View.VISIBLE);
        buylaout.setVisibility(View.VISIBLE);
        cancel.setBackgroundColor(ac.getResources().getColor(R.color.colorAccent));
        buy.setBackgroundColor(ac.getResources().getColor(R.color.blue_inner));

        priceinfo.setText(Html.fromHtml("<b>" + titlr + "<b><br><br>ဤ ဇာတ္လမ္းအား ၾကည့္ရႈ႕ရန္ က်သင့္သည့္ တန္ဖိုးမွာ " + calculate_st.format(Long.parseLong(price)) + " Gold ျဖစ္သည္ <br>သင္ဝယ္ယူမည္မွာ ေသခ်ာပါသလား ?"));
    }

    public static void Feedback_buyError() {
        okay.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.GONE);
        buy.setVisibility(View.GONE);
        priceinfo.setText(Html.fromHtml("Network Fail. Please Try again"));

    }

    public static void Feedback_buy(String s) {

        String sta = Jsonparser.getonestring(s, "status");
        if (sta.equals("1")) {
            buylaout.setVisibility(View.GONE);
            if (buytype.equals("top")) {

                String sid = "";
                if (topserislist.get(posti).getMid().contains("s")) {
                    sid = topserislist.get(posti).getMid().replace("s", "");
                }

                Intent it = new Intent(ac, SeriesDetail.class);
                it.putExtra("mid", sid);
                it.putExtra("mname", topserislist.get(posti).getTitle());
                it.putExtra("like", topserislist.get(posti).getLike() + " Like");
                it.putExtra("view", topserislist.get(posti).getView() + " View");
                it.putExtra("ep", topserislist.get(posti).getEpisode());
                it.putExtra("price", topserislist.get(posti).getPrice());
                it.putExtra("detail", topserislist.get(posti).getDetail());
                ac.startActivity(it);

                topserislist.get(posti).setMine("true");
            } else {
                String sid = "";
                if (recserieslist.get(posti).getMid().contains("s")) {
                    sid = recserieslist.get(posti).getMid().replace("s", "");
                }

                Intent it = new Intent(ac, SeriesDetail.class);
                it.putExtra("mid", sid);
                it.putExtra("mname", recserieslist.get(posti).getTitle());
                it.putExtra("like", recserieslist.get(posti).getLike() + " Like");
                it.putExtra("view", recserieslist.get(posti).getView() + " View");
                it.putExtra("ep", recserieslist.get(posti).getEpisode());
                it.putExtra("price", recserieslist.get(posti).getPrice());
                it.putExtra("detail", recserieslist.get(posti).getDetail());
                ac.startActivity(it);

                recserieslist.get(posti).setMine("true");
            }

        } else {
            cancel.setVisibility(View.GONE);
            buy.setVisibility(View.GONE);
            okay.setVisibility(View.VISIBLE);
            priceinfo.setText("ဤ ဇာတ္လမ္းအား ဝယ္ယူရန္ သင့္တြင္ ေရႊလံုေလာက္မႈ႕ မရွိပါ ။ေရႊ ရရွိရန္ ဂိမ္းမ်ား ကစားျခင္းျဖင့္ အခမဲ့ ရယူႏိုင္ပါသည္။");
        }

    }


    private void Moiveclick(int i, String rec) {
        ArrayList<get> clicklist = new ArrayList<>();
        if (rec.equals("rec")) {
            clicklist = recmovlist;
        }
        if (rec.equals("top")) {
            clicklist = topratedlist;
        }
        Intent it = new Intent(ac, Detail.class);
        it.putExtra("mid", clicklist.get(i).getMid());
        it.putExtra("type", "movie");
        it.putExtra("mname", clicklist.get(i).getTitle());
        it.putExtra("image", clicklist.get(i).getImage());
        ac.startActivity(it);

    }

    static class Downloadads_RS extends AsyncTask<Void, Void, Void> {

        String imgurl, imgid;
        int possi;

        public Downloadads_RS(String link, String mid, int posii) {
            imgid = mid;
            imgurl = link;
            possi = posii;
            Log.e("urlTS", imgurl);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Bitmap bmp = null;
            URL url = null;
            try {
                url = new URL(imgurl);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                File file = new File(Constant.datalocation_scover + imgid + ".fmovie");

                FileOutputStream stream = null;
                try {
                    stream = new FileOutputStream(file);
                    ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outstream);
                    byte[] byteArray = outstream.toByteArray();

                    stream.write(byteArray);
                    stream.close();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressadsimageRS = "0";
            Imagecondition_RS();


        }


    }

    /////////////////////TS

    static String progressadsimageTS = "0";

    private static void Imagecondition_TS() {
        for (int i = 0; i < topserislist.size(); i++) {


            File fi = new File(Constant.datalocation_scover + topserislist.get(i).getMid() + ".fmovie");
            Log.e("file", fi.toString());
            if (!fi.exists()) {

                if (progressadsimageTS.equals("0")) {
                    progressadsimageTS = topserislist.get(i).getMid();
                    new Downloadads_TS(topserislist.get(i).getImage(), topserislist.get(i).getMid(), i).execute();
                    break;
                }

            } else {
                String fpath = Constant.datalocation_scover + topserislist.get(i).getMid() + ".fmovie";
                Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                iv_topseries[i].setBackground(myDrawable);
            }
        }
    }

    static class Downloadads_TS extends AsyncTask<Void, Void, Void> {

        String imgurl, imgid;
        int possi;

        public Downloadads_TS(String link, String mid, int posii) {
            imgid = mid;
            imgurl = link;
            possi = posii;
            Log.e("urlTS", imgurl);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Bitmap bmp = null;
            URL url = null;
            try {
                url = new URL(imgurl);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                File file = new File(Constant.datalocation_scover + imgid + ".fmovie");

                FileOutputStream stream = null;
                try {
                    stream = new FileOutputStream(file);
                    ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outstream);
                    byte[] byteArray = outstream.toByteArray();

                    stream.write(byteArray);
                    stream.close();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressadsimageTS = "0";
            Imagecondition_TS();


        }


    }

    /////////////////////TM

    static String progressadsimageTM = "0";

    private static void Imagecondition_TM() {
        for (int i = 0; i < topratedlist.size(); i++) {


            File fi = new File(Constant.datalocation_movie + topratedlist.get(i).getMid() + ".fmovie");
            Log.e("file", fi.toString());
            if (!fi.exists()) {

                if (progressadsimageTM.equals("0")) {
                    progressadsimageTM = topratedlist.get(i).getMid();
                    new Downloadads_TM(topratedlist.get(i).getImage(), topratedlist.get(i).getMid(), i).execute();
                    break;
                }

            } else {
                String fpath = Constant.datalocation_movie + topratedlist.get(i).getMid() + ".fmovie";
                Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                iv_topmovie[i].setBackground(myDrawable);
            }
        }
    }

    static class Downloadads_TM extends AsyncTask<Void, Void, Void> {

        String imgurl, imgid;
        int possi;

        public Downloadads_TM(String link, String mid, int posii) {
            imgid = mid;
            imgurl = link;
            possi = posii;
            Log.e("urlRM", imgurl);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Bitmap bmp = null;
            URL url = null;
            try {
                url = new URL(imgurl);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                File file = new File(Constant.datalocation_movie + imgid + ".fmovie");

                FileOutputStream stream = null;
                try {
                    stream = new FileOutputStream(file);
                    ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outstream);
                    byte[] byteArray = outstream.toByteArray();

                    stream.write(byteArray);
                    stream.close();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressadsimageTM = "0";
            Imagecondition_TM();


        }


    }


    /////////////////////RM

    static String progressadsimageRM = "0";

    private static void Imagecondition_RM() {
        for (int i = 0; i < recmovlist.size(); i++) {


            File fi = new File(Constant.datalocation_movie + recmovlist.get(i).getMid() + ".fmovie");
            Log.e("file", fi.toString());
            if (!fi.exists()) {

                if (progressadsimageRM.equals("0")) {
                    progressadsimageRM = recmovlist.get(i).getMid();
                    new Downloadads_RM(recmovlist.get(i).getImage(), recmovlist.get(i).getMid(), i).execute();
                    break;
                }

            } else {
                String fpath = Constant.datalocation_movie + recmovlist.get(i).getMid() + ".fmovie";
                Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                iv_recommdmovie[i].setBackground(myDrawable);
            }
        }
    }

    static class Downloadads_RM extends AsyncTask<Void, Void, Void> {

        String imgurl, imgid;
        int possi;

        public Downloadads_RM(String link, String mid, int posii) {
            imgid = mid;
            imgurl = link;
            possi = posii;
            Log.e("urlRM", imgurl);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Bitmap bmp = null;
            URL url = null;
            try {
                url = new URL(imgurl);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                File file = new File(Constant.datalocation_movie + imgid + ".fmovie");

                FileOutputStream stream = null;
                try {
                    stream = new FileOutputStream(file);
                    ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outstream);
                    byte[] byteArray = outstream.toByteArray();

                    stream.write(byteArray);
                    stream.close();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressadsimageRM = "0";
            Imagecondition_RM();


        }


    }



    public static void Feedback_Error() {
        swipeLayout.setRefreshing(false);
        sv.setVisibility(View.GONE);
        pg.setVisibility(View.GONE);
        network.setVisibility(View.VISIBLE);
        pg.hide();

    }
}
