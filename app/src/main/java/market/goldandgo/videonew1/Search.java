package market.goldandgo.videonew1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import market.goldandgo.videonew1.Adapter.SearchAdapter;
import market.goldandgo.videonew1.Fragment.Fragment_Movie;
import market.goldandgo.videonew1.Fragment.Fragment_Series;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Utils.calculate_st;


/**
 * Created by Go Goal on 7/6/2017.
 */

public class Search extends AppCompatActivity {

    EditText ed;
    static RecyclerView rv;
    LinearLayoutManager llm;
    static SearchAdapter adapter;
    static ArrayList<get> list;
    static AppCompatActivity ac;
    static int count = 1;
    static AVLoadingIndicatorView pg;
    static RelativeLayout network, buylaout;
    static Button buy, cancel, okay;
    static TextView priceinfo, tt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchvideo);

        ac = this;
        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        ed = (EditText) findViewById(R.id.ed);
        // InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.showSoftInput(ed, InputMethodManager.SHOW_IMPLICIT);

        new Handler().postDelayed(new Runnable() {

            public void run() {
//        ((EditText) findViewById(R.id.et_find)).requestFocus();
//

//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(yourEditText, InputMethodManager.SHOW_IMPLICIT);

                ed.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                ed.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            }
        }, 200);


      /*  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);*/

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        list = new ArrayList<get>();
        adapter = new SearchAdapter(ac, list);
        rv.setAdapter(adapter);

        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int bb) {
                count = 1;
                if (ed.getText().length() > 2) {
                    pg.setVisibility(View.VISIBLE);
                    pg.show();
                    MyRequest.getsearchmovie(count + "", ed.getText().toString());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int ydy = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (list.size() > 9) {
                    int visibleItemCount = llm.getChildCount();
                    int totalItemCount = llm.getItemCount();
                    int pastVisibleItems = llm.findFirstVisibleItemPosition();
                    if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                        count++;
                        adapter.load_pg(true);
                        MyRequest.getsearchmovie(count + "", ed.getText().toString());
                    }
                }


            }
        });


        pg = (AVLoadingIndicatorView) findViewById(R.id.avi);
        pg.setVisibility(View.GONE);
        pg.hide();

        network = (RelativeLayout) findViewById(R.id.networkerro);

        Button reload = (Button) findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRequest.getsearchmovie(count + "", ed.getText().toString());
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();

            }
        });


        buylaout = (RelativeLayout) findViewById(R.id.buyalert);
        cancel = (Button) findViewById(R.id.cancel);
        okay = (Button) findViewById(R.id.okay);
        priceinfo = (TextView) findViewById(R.id.price);

        tt = (TextView) findViewById(R.id.tt);
        buy = (Button) findViewById(R.id.buy);

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
                MyRequest.buyseriessearch(buymid);

            }
        });


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

    public static void Feedback_Error() {

        pg.setVisibility(View.GONE);
        pg.hide();
        rv.setVisibility(View.GONE);
        adapter.load_pg(false);
        network.setVisibility(View.VISIBLE);

    }


    public static void Feedback(String s) {

        tt.setText(s);
        rv.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        network.setVisibility(View.GONE);
        pg.hide();
        list = new ArrayList<>();
        list = Jsonparser.getsearchalllist(s);
        adapter.refresh(list);
        adapter.load_pg(false);
        Imagecondition();

    }


    static String progressimage = "0";

    private static void Imagecondition() {
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getMid().startsWith("s")) {
                File fi = new File(Constant.datalocation_scover + "s" + list.get(i).getMid() + ".fmovie");
                if (!fi.exists()) {

                    if (progressimage.equals("0")) {
                        Log.e("current", list.get(i).getMid());
                        progressimage = list.get(i).getMid();
                        new Downloadseeallmovie1(list.get(i).getImage(), list.get(i).getMid()).execute();
                        break;
                    }

                }
            }else{
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

            new Processforadapter().execute();


        }
    }

    static class Processforadapter extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            try {

                adapter.refresh(list);

            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.load_pg(false);
            progressimage = "0";
            Imagecondition();
        }
    }

    static class Downloadseeallmovie1 extends AsyncTask<Void, Void, Void> {

        String imgurl, imagename;

        public Downloadseeallmovie1(String link, String imagename1) {

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

}
