package market.goldandgo.videonew1.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import market.goldandgo.videonew1.Adapter.Movie_seealladapter;
import market.goldandgo.videonew1.Adapter.Series_seealladapter;
import market.goldandgo.videonew1.Adapter.Spinneradapter;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.SeriesDetail;
import market.goldandgo.videonew1.Utils.calculate_st;

/**
 * Created by Go Goal on 9/26/2017.
 */

public class Fragment_Series extends Fragment {


    public static Fragment_Series newInstance() {
        Bundle args = new Bundle();
        Fragment_Series fragment = new Fragment_Series();
        fragment.setArguments(args);
        return fragment;
    }

    static FragmentActivity ac;

    @Override
    public void onResume() {
        super.onResume();
        MyRequest.getseeallseries();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ac = getActivity();
        //MyRequest.getseeallseries();

    }

    static ArrayList<get>  list;
    static RecyclerView rv;
    LinearLayoutManager llm;
    static Series_seealladapter adapter;
    static  String cate="0";
    static AVLoadingIndicatorView pg;
    static LinearLayout mainlayout;
    static RelativeLayout network,buylaout;
    static TextView priceinfo,totalseries;
    static Button buy,cancel,okay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.series, container, false);

        pg= (AVLoadingIndicatorView) v.findViewById(R.id.avi);
        mainlayout= (LinearLayout) v.findViewById(R.id.mainrlayout);
        network= (RelativeLayout) v.findViewById(R.id.networkerro);
        buylaout= (RelativeLayout) v.findViewById(R.id.buyalert);
        priceinfo= (TextView) v.findViewById(R.id.price);
        totalseries= (TextView) v.findViewById(R.id.totalseries);
        buy= (Button) v.findViewById(R.id.buy);
        cancel= (Button) v.findViewById(R.id.cancel);
        okay= (Button) v.findViewById(R.id.okay);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buylaout.setVisibility(View.GONE);
                okay.setVisibility(View.GONE);
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
                MyRequest.buyseries(buymid);

            }
        });

        pg.show();

        Button reload= (Button) v.findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
                MyRequest.getseeallseries();
            }
        });

        rv = (RecyclerView) v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(ac);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        list = new ArrayList<get>();
        adapter = new Series_seealladapter(ac, list);
        rv.setAdapter(adapter);



        return v;
    }


    static String progressimage = "0";

    public static void Feedback(String s) {

        network.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        pg.hide();
        rv.setVisibility(View.VISIBLE);

        list = new ArrayList<>();
        list = Jsonparser.getseriesalllist(s);
        totalseries.setVisibility(View.VISIBLE);
        totalseries.setText(Html.fromHtml("<b>Total Series "+list.size()+"</b>"));


        adapter.refresh(list);
        adapter.load_pg(false);
        Imagecondition();

    }

    private static void Imagecondition() {
        for (int i = 0; i < list.size(); i++) {


            File fi = new File(Constant.datalocation_scover + "s"+list.get(i).getMid() + ".fmovie");
            if (!fi.exists()) {

                if (progressimage.equals("0")){
                    Log.e("current",list.get(i).getMid());
                    progressimage=list.get(i).getMid();
                    new Downloadseeallmovie(list.get(i).getImage(), list.get(i).getMid()).execute();
                    break;
                }

            }
        }
    }

    public static void Feedback_Error() {

        pg.setVisibility(View.GONE);
        pg.hide();
        network.setVisibility(View.VISIBLE);
        mainlayout.setVisibility(View.GONE);
        adapter.load_pg(false);
    }

    static String buymid;
    static int posti;

    public static void showbutalert(String price, String mid, String titlr, int i) {
        posti=i;
        buymid=mid;
        cancel.setEnabled(true);
        buy.setEnabled(true);
        cancel.setVisibility(View.VISIBLE);
        buy.setVisibility(View.VISIBLE);
        buylaout.setVisibility(View.VISIBLE);
        cancel.setBackgroundColor(ac.getResources().getColor(R.color.colorAccent));
        buy.setBackgroundColor(ac.getResources().getColor(R.color.blue_inner));

        priceinfo.setText(Html.fromHtml("<b>"+titlr+"<b><br><br>ဤ ဇာတ္လမ္းအား ၾကည့္ရႈ႕ရန္ က်သင့္သည့္ တန္ဖိုးမွာ "+ calculate_st.format(Long.parseLong(price))+" Gold ျဖစ္သည္ <br>သင္ဝယ္ယူမည္မွာ ေသခ်ာပါသလား ?"));
    }

    public static void Feedback_buyError() {
        okay.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.GONE);
        buy.setVisibility(View.GONE);
        priceinfo.setText(Html.fromHtml("Network Fail. Please Try again"));

    }

    public static void Feedback_buy(String s) {

        String sta=Jsonparser.getonestring(s,"status");
        if (sta.equals("1")){
            buylaout.setVisibility(View.GONE);
            Intent it = new Intent(ac, SeriesDetail.class);
            it.putExtra("mid", list.get(posti).getMid());
            it.putExtra("mname", list.get(posti).getTitle());
            it.putExtra("like", list.get(posti).getLike() + " Like");
            it.putExtra("view", list.get(posti).getView() + " View");
            it.putExtra("ep", list.get(posti).getCategory());
            it.putExtra("price", list.get(posti).getPrice());
            it.putExtra("detail", list.get(posti).getDetail());
            ac.startActivity(it);

            list.get(posti).setMine("true");
            adapter.refresh(list);
        }else{
            cancel.setVisibility(View.GONE);
            buy.setVisibility(View.GONE);
            okay.setVisibility(View.VISIBLE);
            priceinfo.setText("ဤ ဇာတ္လမ္းအား ဝယ္ယူရန္ သင့္တြင္ ေရႊလံုေလာက္မႈ႕ မရွိပါ ။ေရႊ ရရွိရန္ ဂိမ္းမ်ား ကစားျခင္းျဖင့္ အခမဲ့ ရယူႏိုင္ပါသည္။");
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
                File file = new File(Constant.datalocation_scover +"s"+ imagename + ".fmovie");

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
            progressimage="0";
            Imagecondition();
        }
    }
}
