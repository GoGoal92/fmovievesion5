package market.goldandgo.videonew1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.golshadi.majid.core.DownloadManagerPro;
import com.golshadi.majid.report.listener.DownloadManagerListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Downloadlist;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.Phonesize;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Utils.Mydatabase;

/**
 * Created by Go Goal on 6/30/2017.
 */

public class Detail extends AppCompatActivity {

    static String mname, mid, type, image, sid;
    static RelativeLayout[] rl = new RelativeLayout[3];
    static AppCompatActivity ac;
    static ImageView iv, userlikeiv, viewiv;
    static AVLoadingIndicatorView pg;
    static RelativeLayout network;
    static ScrollView sv;
    static TextView liketv, viewtv, downloadtv, alerttv;
    static Zawgyitextview mdetial;
    static String filecover = "";
    WebView wb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        ac = this;


        mname = getIntent().getExtras().getString("mname");
        mid = getIntent().getExtras().getString("mid");
        type = getIntent().getExtras().getString("type");
        image = getIntent().getExtras().getString("image");

        wb = (WebView) findViewById(R.id.mywebview);

        if (Constant.adflyshow()) {
            wb.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (!url.equals("http://52.14.88.50/fbvideo/api/form/fads.html") && !url.equals("http://www.fmovie.com/")) {
                        // button1 was clicked inside webview html
                        Intent it = new Intent(ac, Ads.class);
                        it.putExtra("a", url);
                        startActivity(it);

                    } else {

                        if (url.equals("http://www.fmovie.com/")) {
                            wb.setVisibility(View.GONE);
                        } else {
                            wb.loadUrl(url);

                        }


                    }
                    return true;

                }

            });


            wb.getSettings().setJavaScriptEnabled(true);
            wb.getSettings().setDomStorageEnabled(true);
            wb.addJavascriptInterface(new Object() {

                @JavascriptInterface
                public void performClick() {
                    wb.setVisibility(View.GONE);
                }
            }, "valid");
            wb.loadUrl(Constant.adlink);
        } else {
            wb.setVisibility(View.GONE);
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


        sv = (ScrollView) findViewById(R.id.sv);
        mdetial = (Zawgyitextview) findViewById(R.id.mdetail);
        liketv = (TextView) findViewById(R.id.liketv);
        viewtv = (TextView) findViewById(R.id.viewt);
        downloadtv = (TextView) findViewById(R.id.downloadtv);
        userlikeiv = (ImageView) findViewById(R.id.likeiv);
        viewiv = (ImageView) findViewById(R.id.viewiv);
        alerttv = (TextView) findViewById(R.id.tvalert);

        TextView toolbarname = (TextView) findViewById(R.id.toolbartitle);
        rl[0] = (RelativeLayout) findViewById(R.id.likerl);
        rl[1] = (RelativeLayout) findViewById(R.id.viewrl);
        rl[2] = (RelativeLayout) findViewById(R.id.downloadrl);
        iv = (ImageView) findViewById(R.id.iv);


        toolbarname.setText(mname);

        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        int pwidth = Phonesize.getwidth();

        for (int i = 0; i < rl.length; i++) {

            ViewGroup.LayoutParams robot_speechsize = rl[i].getLayoutParams();
            robot_speechsize.width = (pwidth / 3) - 30;
            rl[i].setLayoutParams(robot_speechsize);

        }


        if (type.equals("series")) {

            sid = getIntent().getExtras().getString("sid");
            filecover = Constant.datalocation_scover + "s" + sid + ".fmovie";
            Bitmap myBitmap = BitmapFactory.decodeFile(filecover);
            Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
            iv.setBackground(myDrawable);

        } else {
            filecover = Constant.datalocation_movie + mid + ".fmovie";
            Bitmap myBitmap = BitmapFactory.decodeFile(filecover);
            Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
            iv.setBackground(myDrawable);
        }


        pg = (AVLoadingIndicatorView) findViewById(R.id.avi);
        network = (RelativeLayout) findViewById(R.id.networkerro);
        pg.show();

        Button reload = (Button) findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("series")) {
                    MyRequest.getseriesdetail(sid, mid);
                } else {
                    MyRequest.getMoviedetail(mid);
                }
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
            }
        });


        if (type.equals("series")) {
            MyRequest.getseriesdetail(sid, mid);
        } else {
            MyRequest.getMoviedetail(mid);
        }

    }

    static boolean userlikecon = false, userreportcon = false;
    static int likeint = 0, reportint = 0;
    static String url, sdurl, view, acctype;

    public static void Feedback(String s) {

        network.setVisibility(View.GONE);
        pg.setVisibility(View.GONE);
        pg.hide();
        sv.setVisibility(View.VISIBLE);

        String vstatus = Jsonparser.getonestring(s, "vstatus");
        String detail = Jsonparser.getonestring(s, "detail");
        String like = Jsonparser.getonestring(s, "like");
        view = Jsonparser.getonestring(s, "view");
        final String userlike = Jsonparser.getonestring(s, "userlike");
        url = Jsonparser.getonestring(s, "url");
        sdurl = Jsonparser.getonestring(s, "sdurl");


        final String userreport = Jsonparser.getonestring(s, "userreport");
        String report = Jsonparser.getonestring(s, "report");
        acctype = Jsonparser.getonestring(s, "acctype");

        if (vstatus.equals("1")) {
            detail = Constant.cleanstring(detail, "1");
            url = Constant.cleanstring(url, "1");

        }


        likeint = Integer.parseInt(like);
        reportint = Integer.parseInt(report);

        mdetial.setMovementMethod(LinkMovementMethod.getInstance());
        mdetial.setText(detail);


        liketv.setText(like + " Like");
        viewtv.setText(view + " view" + " " + reportint + " Report");
        if (userlike.equals("true")) {

            userlikecon = true;
            userlikeiv.setBackgroundResource(R.drawable.ic_like1);

        } else {
            userlikecon = false;
            userlikeiv.setBackgroundResource(R.drawable.like);
        }

        if (userreport.equals("true")) {

            userreportcon = true;
            viewiv.setBackgroundResource(R.drawable.ic_view1);

        } else {
            userreportcon = false;
            viewiv.setBackgroundResource(R.drawable.ic_view);
        }


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (acctype.equals("0")) {
                    Intent it = new Intent(ac, webviewdownloader.class);
                    it.putExtra("url", url);
                    it.putExtra("cla", "mdv");
                    it.putExtra("name", mname);
                    it.putExtra("img", filecover);
                    ac.startActivity(it);

//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri
//                            .parse(url));
//                    ac.startActivity(intent);
                } else {

                    Intent it = new Intent(ac, Blockvideochoice.class);
                    it.putExtra("url", url);
                    it.putExtra("sdurl", sdurl);
                    it.putExtra("img", filecover);
                    it.putExtra("name", mname);
                    ac.startActivity(it);


                }


            }
        });


        rl[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userlikecon) {
                    alerttv.setVisibility(View.VISIBLE);
                    alerttv.setText("You have already rated this video");
                } else {
                    likeint = likeint + 1;
                    liketv.setText(likeint + " Like");
                    userlikecon = true;
                    userlikeiv.setBackgroundResource(R.drawable.ic_like1);


                    if (type.equals("series")) {
                        MyRequest.userlike("s" + sid);
                    } else {
                        MyRequest.userlike(mid);
                    }

                }


            }
        });

        rl[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder ab = new AlertDialog.Builder(ac);
                ab.setTitle("REPORT");
                View vc = ac.getLayoutInflater().inflate(R.layout.ontextzawwgyirow, null);
                ab.setView(vc);
                Zawgyitextview tv = (Zawgyitextview) vc.findViewById(R.id.ttv);
                tv.setText("ေက်းဇူျပဳ၍ လင့္မ်ား ပ်က္ေနမွ သာ ရီပို႕ ျပဳလုပ္ေပးပါရန္။\n" +
                        "သင္ ရီပို႔ ျပဳလုပ္လိုသည္မွာ ေသခ်ာပါသလား?");

                ab.setPositiveButton("Report", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (userreportcon) {
                            alerttv.setVisibility(View.VISIBLE);
                            alerttv.setText("You have already reported this video");
                        } else {
                            reportint = reportint + 1;
                            viewtv.setText(view + " view" + " " + reportint + " Report");
                            userreportcon = true;
                            viewiv.setBackgroundResource(R.drawable.ic_view1);


                            if (type.equals("series")) {
                                MyRequest.userreprt("s" + mid);
                            } else {
                                MyRequest.userreprt(mid);
                            }

                        }

                    }
                }).setNegativeButton("Cancel", null);
                ab.setCancelable(false);
                ab.show();


            }
        });

        rl[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Mydatabase mdb=new Mydatabase(ac);
                ArrayList<get> ddlist=new ArrayList<get>();
                ddlist=mdb.getlist();
                if (ddlist.size()>4){

                    AlertDialog.Builder ab = new AlertDialog.Builder(ac);
                    ab.setTitle("ERROR");
                    View vc = ac.getLayoutInflater().inflate(R.layout.ontextzawwgyirow, null);
                    ab.setView(vc);
                    Zawgyitextview tv = (Zawgyitextview) vc.findViewById(R.id.ttv);
                    tv.setText("Maximum size of fmovie download manager is 5 . Cant download over 5 task at once.\n" +
                            "\n" +
                            "FMOVIE download manager ၏ တစ္ၾကိမ္တည္း အမ်ားဆံုး Download လုပ္ယူႏိုင္စြမ္းမွာ ၅ခုသာ ျဖစ္သည္။\n");

                    ab.setPositiveButton("Okay",null);

                    ab.setCancelable(false);
                    ab.show();


                }else{
                    if (acctype.equals("0")) {

                        Intent it = new Intent(ac, webviewdownloader.class);
                        it.putExtra("url", url);
                        it.putExtra("cla", "md");
                        it.putExtra("img", filecover);
                        ac.startActivity(it);

                    } else {


                        Intent it = new Intent(ac, Blockdownloader.class);
                        it.putExtra("url", url);
                        it.putExtra("sdurl", sdurl);
                        it.putExtra("img", filecover);
                        ac.startActivity(it);

                    }

                }



            }
        });


    }


    public static void Feedback_Error() {
        network.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        pg.hide();
        sv.setVisibility(View.GONE);

    }


    public static void Facebookfeedback(final String hDlink, final String mbsize) {

        AlertDialog.Builder ab = new AlertDialog.Builder(ac);
        ab.setTitle("Confirmation");
        View vv = ac.getLayoutInflater().inflate(R.layout.ontextzawwgyirow, null);
        Zawgyitextview tv = (Zawgyitextview) vv.findViewById(R.id.ttv);
        tv.setText("Are you sure to download this video?\n" +
                "ဤ video ကို သင္ Download ျပဳလုပ္မည္မွာေသခ်ာပါသလား  ?");
        ab.setView(vv);
        ab.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // rl[2].setClickable(false);
                // rl[2].setEnabled(false);

                if (mname.contains("'")) {
                    mname = mname.replace("'", "");
                }
                DownloadManagerPro dm = new DownloadManagerPro(ac);
                dm.init(Constant.DM_downloadfolder, 10, null);
                int  taskToken = dm.addTask(mname, hDlink, false, false);


              /*  Intent it=new Intent(ac, SecondActivity.class);
                it.putExtra("a",taskToken);
                ac.startActivity(it);*/

                Mydatabase mdb=new Mydatabase(ac);
                mdb.insertdata(taskToken+"",mname,mbsize,"1",System.currentTimeMillis()+"");

               // Downloadlist.addlist(mname, mbsize, taskToken ,"true", hDlink);
                Intent it=new Intent(ac,Mydownloadmanager.class);
                ac.startActivity(it);


            }
        }).setNegativeButton("Cancel", null).show();


    }




}
