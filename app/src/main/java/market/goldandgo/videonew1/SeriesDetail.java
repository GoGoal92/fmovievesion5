package market.goldandgo.videonew1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;

import market.goldandgo.videonew1.Adapter.Series_episodeadapter;
import market.goldandgo.videonew1.Adapter.Series_seealladapter;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;

/**
 * Created by Go Goal on 6/30/2017.
 */

public class SeriesDetail extends AppCompatActivity {

    static AVLoadingIndicatorView pg;
    static RelativeLayout network;

    static String mid, like, view, episode, price, detail, title;
    static ArrayList<get> list;
    static RecyclerView rv;
    LinearLayoutManager llm;
    static AppCompatActivity ac;
    static Series_episodeadapter adapter;
    static TextView readmore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seriesepisodelist);
        ac = this;

        mid = getIntent().getExtras().getString("mid");
        like = getIntent().getExtras().getString("like");
        view = getIntent().getExtras().getString("view");
        episode = getIntent().getExtras().getString("ep");
        price = getIntent().getExtras().getString("price");
        detail = getIntent().getExtras().getString("detail");
        title = getIntent().getExtras().getString("mname");


        pg = (AVLoadingIndicatorView) findViewById(R.id.avi);
        network = (RelativeLayout) findViewById(R.id.networkerro);
        pg.show();

        Button reload = (Button) findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRequest.getcategory_series(mid, "1");
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
            }
        });

        MyRequest.getcategory_series(mid, "1");


        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(ac);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        list = new ArrayList<get>();
        adapter = new Series_episodeadapter(ac, list, mid, like, view, episode, price, detail);
        rv.setAdapter(adapter);

        TextView toolbarname = (TextView) findViewById(R.id.toolbartitle);
        toolbarname.setText(title);

        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        prefs = getSharedPreferences("dlist",
                Context.MODE_PRIVATE);



    }

    static SharedPreferences prefs;
    static String json;

    public static void Feedback(String s) {

        json = s;
        rv.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        network.setVisibility(View.GONE);
        pg.hide();


        String scheck = "0";
        if (prefs.contains(mid)) {
            scheck = prefs.getString(mid, null);
        }


        list = new ArrayList<>();
        list = Jsonparser.getserieslistrow(s, scheck);
        adapter.refresh(list);


    }

    public static void Feedback_Error() {
        rv.setVisibility(View.GONE);
        pg.setVisibility(View.GONE);
        network.setVisibility(View.VISIBLE);
        pg.hide();
    }


}
