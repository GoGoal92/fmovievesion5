package market.goldandgo.videonew1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Adapter.Mymoviesalladapter;
import market.goldandgo.videonew1.Fragment.Fragment_Movie;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;


/**
 * Created by Go Goal on 7/5/2017.
 */

public class Mymovies extends AppCompatActivity {

    static SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView rv;
    LinearLayoutManager llm;
    static Mymoviesalladapter adapter;
    static ArrayList<get> list;
    static AppCompatActivity ac;
    static AVLoadingIndicatorView pg;
    static RelativeLayout network;
    static int count = 1;
    static Zawgyitextview myinfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);

        ac = this;
        rv = (RecyclerView) findViewById(R.id.rv);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);


        myinfo = (Zawgyitextview) findViewById(R.id.mymovieinfo);

        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        list = new ArrayList<get>();
        adapter = new Mymoviesalladapter(ac, list);
        rv.setAdapter(adapter);

        MyRequest.getMyallmovies();

        TextView toolbarname = (TextView) findViewById(R.id.toolbartitle);
        toolbarname.setText("My Movies");

        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyRequest.getMyallmovies();
            }
        });


        pg = (AVLoadingIndicatorView) findViewById(R.id.avi);
        network = (RelativeLayout) findViewById(R.id.networkerro);
        pg.show();

        Button reload = (Button) findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
                MyRequest.getMyallmovies();
            }
        });

    }

    public static void Feedback_Error() {
        pg.setVisibility(View.GONE);
        pg.hide();
        network.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        adapter.load_pg(false);
    }

    public static void Feedback(String s) {


        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        pg.hide();
        list = new ArrayList<>();
        list = Jsonparser.getmymovies(s);
        if (list.size()<1){

            myinfo.setVisibility(View.VISIBLE);
        }else{
            adapter.refresh(list);
            adapter.load_pg(false);
            Imagecondition();
        }



    }

    public static void finished() {
        ac.finish();
    }

    static String progressimage = "0";

    private static void Imagecondition() {
        for (int i = 0; i < list.size(); i++) {


            File fi = new File(Constant.datalocation_movie + list.get(i).getMid() + ".fmovie");
            if (!fi.exists()) {

                if (progressimage.equals("0")) {
                    Log.e("current", list.get(i).getMid());
                    progressimage = list.get(i).getMid();
                    new Downloadseeallmovie(list.get(i).getImage(), list.get(i).getMid()).execute();
                    break;
                }

            }
        }
    }

    static class Downloadseeallmovie extends AsyncTask<Void, Void, Void> {

        String imgurl, imagename;

        public Downloadseeallmovie(String link, String imagename1) {

            imgurl = link;
            imagename = imagename1;
        }

        @Override
        protected Void doInBackground(Void... params) {

            Bitmap bmp = null;
            URL url = null;
            try {
                url = new URL(imgurl);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                File file = new File(Constant.datalocation_movie + imagename + ".fmovie");

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
            adapter.refresh(list);
            adapter.load_pg(false);
            progressimage = "0";
            Imagecondition();
        }
    }
}
