package market.goldandgo.videonew1.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
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
import market.goldandgo.videonew1.Freegold;
import market.goldandgo.videonew1.MainActivity;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.SeriesDetail;
import market.goldandgo.videonew1.Utils.MySpinner;
import market.goldandgo.videonew1.Utils.calculate_st;

/**
 * Created by Go Goal on 9/26/2017.
 */

public class Fragment_Series extends Fragment {


    static  int count=1;

    public static Fragment_Series newInstance() {
        Bundle args = new Bundle();
        Fragment_Series fragment = new Fragment_Series();
        fragment.setArguments(args);
        return fragment;
    }

    static FragmentActivity ac;

    String reloadd="b";

    @Override
    public void onResume() {
        super.onResume();
        MyRequest.getseeallseries(count+"",reloadd);
        reloadd="a";
        MainActivity.Pagerenable(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reloadd="b";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ac = getActivity();


    }

    static ArrayList<get> list, list1,list2,list3,list4;
  //  static Spinneradapter adapteree;
    static RecyclerView rv;
    LinearLayoutManager llm;
    static Series_seealladapter adapter;
    static String cate = "0";
    static AVLoadingIndicatorView pg;
    static LinearLayout mainlayout;
    static RelativeLayout network, buylaout;
    static TextView priceinfo, totalseries;
    static Button buy, cancel, okay;
    static MySpinner sp;
    private TextView textView;
    private static int defaultSelect;
    private String[] arr=new String[5];
    static SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.series, container, false);

        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh_layout);
        swipeLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyRequest.getseeallseries(count+"","a");
            }
        });

        arr[0]="LATEST SERIES";
        arr[1]="ENGLISH SERIES";
        arr[2]="KOREA SERIES";
        arr[3]="CHINA SERIES";
        arr[4]="ANIME SERIES";

        textView = (TextView) v.findViewById(R.id.testingHelloseries);
        textView.setText("LATEST SERIES");
        defaultSelect=0;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yearOnclick(arr);
            }
        });

       /* sp = (MySpinner) v.findViewById(R.id.spinner2);
        adapteree = new Spinneradapter(ac, clist);
        sp.setAdapter(adapteree);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                changetype(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        pg = (AVLoadingIndicatorView) v.findViewById(R.id.avi);
        mainlayout = (LinearLayout) v.findViewById(R.id.mainrlayout);
        network = (RelativeLayout) v.findViewById(R.id.networkerro);
        buylaout = (RelativeLayout) v.findViewById(R.id.buyalert);
        priceinfo = (TextView) v.findViewById(R.id.price);
        totalseries = (TextView) v.findViewById(R.id.totalseries);
        buy = (Button) v.findViewById(R.id.buy);
        cancel = (Button) v.findViewById(R.id.cancel);
        okay = (Button) v.findViewById(R.id.okay);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buylaout.setVisibility(View.GONE);
                okay.setVisibility(View.GONE);
                Intent it=new Intent(ac, Freegold.class);
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
                MyRequest.buyseries(buymid);

            }
        });

        pg.show();

        Button reload = (Button) v.findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
                MyRequest.getseeallseries(count+"","b");
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
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int ydy = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = llm.getChildCount();
                int totalItemCount = llm.getItemCount();
                int pastVisibleItems = llm.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    count++;
                    adapter.load_pg(true);
                    MyRequest.getseeallseries(count+"","a");
                }


            }
        });

        return v;
    }

    private void changetype(int position) {

        if (position == 0) {

            adapter.refresh(list);

        }

        if (position == 1) {
            adapter.refresh(list1);

        }

        if (position == 2) {
            adapter.refresh(list2);

        }

        if (position == 3) {
            adapter.refresh(list3);

        }

        if (position == 4) {
            adapter.refresh(list4);

        }

    }


    static String progressimage = "0";

    public static void Feedback(String s) {
        swipeLayout.setRefreshing(false);
        network.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        pg.hide();
        rv.setVisibility(View.VISIBLE);

        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();

        String all=Jsonparser.getonestring(s,"all");

        list = Jsonparser.getseriesalllist(all);
        list1 = Jsonparser.getseriesalllist1(all,1+"");
        list2 = Jsonparser.getseriesalllist1(all,2+"");
        list3 = Jsonparser.getseriesalllist1(all,3+"");
        list4 = Jsonparser.getseriesalllist1(all,4+"");

        String tseries=Jsonparser.getonestring(s,"total");



        totalseries.setVisibility(View.VISIBLE);
        totalseries.setText(Html.fromHtml("<b>Total Series " + Integer.parseInt(tseries) + "</b>"));


        if (defaultSelect==0){
            adapter.refresh(list);
        }else if (defaultSelect==1){
            adapter.refresh(list1);
        }else if (defaultSelect==2){
            adapter.refresh(list2);
        }else if (defaultSelect==3){
            adapter.refresh(list3);
        }else if (defaultSelect==4){
            adapter.refresh(list4);
        }



        adapter.load_pg(false);
        Imagecondition();

    }

    private static void Imagecondition() {
        for (int i = 0; i < list.size(); i++) {


            File fi = new File(Constant.datalocation_scover + "s" + list.get(i).getMid() + ".fmovie");
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

    public static void Feedback_Error() {
        swipeLayout.setRefreshing(false);
        pg.setVisibility(View.GONE);
        pg.hide();
        network.setVisibility(View.VISIBLE);
        mainlayout.setVisibility(View.GONE);
        adapter.load_pg(false);
    }

    static String buymid;
    static int posti;

    public static void showbutalert(String price, String mid, String titlr, int i) {
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
        } else {
            cancel.setVisibility(View.GONE);
            buy.setVisibility(View.GONE);
            okay.setVisibility(View.VISIBLE);
            priceinfo.setText("ဤ ဇာတ္လမ္းအား ဝယ္ယူရန္ သင့္တြင္ ေရႊလံုေလာက္မႈ႕ မရွိပါ ။ေရႊ ရရွိရန္ ဂိမ္းမ်ား ကစားျခင္းျဖင့္ အခမဲ့ ရယူႏိုင္ပါသည္။");
        }

    }

    public static void Feedback_Error_SW() {
        swipeLayout.setRefreshing(false);
        adapter.load_pg(false);
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
                File file = new File(Constant.datalocation_scover + "s" + imagename + ".fmovie");

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

    private void yearOnclick(final String[] yearArr) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext(), R.style.MyAlertDialogTheme);
        builder.setTitle("Please select Movie Types");
        builder.setSingleChoiceItems(yearArr, defaultSelect, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                defaultSelect = which;
                textView.setText(arr[defaultSelect]);

                changetype(defaultSelect);
                dialog.dismiss();
            }
        });


        builder.show();
    }
}
