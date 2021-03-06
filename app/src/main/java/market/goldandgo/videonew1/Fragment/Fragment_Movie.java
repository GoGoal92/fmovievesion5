package market.goldandgo.videonew1.Fragment;

import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.LinearLayout;
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

import market.goldandgo.videonew1.Adapter.Movie_seealladapter;
import market.goldandgo.videonew1.Adapter.Spinneradapter;
import market.goldandgo.videonew1.MainActivity;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Utils.MySpinner;

/**
 * Created by Go Goal on 9/26/2017.
 */

public class Fragment_Movie extends Fragment {

    private static Object[] objDays;
    private int defaultSelect;
    private static ArrayList<get> catelist;
    private TextView textView;
    private String[] arr;

    public static Fragment_Movie newInstance() {
        Bundle args = new Bundle();
        Fragment_Movie fragment = new Fragment_Movie();
        fragment.setArguments(args);
        return fragment;
    }

    static FragmentActivity ac;
    static int count = 1;

    public static ArrayList<get> getcatelist() {
        return clist;
    }

    String reloadd="b";

    @Override
    public void onResume() {
        super.onResume();
        MyRequest.getseeallMovie(count + "", cate,reloadd);
        reloadd="a";
        MainActivity.Pagerenable(true);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ac = getActivity();
        //MyRequest.getseeallMovie(count + "","0");


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = (TextView) getActivity().findViewById(R.id.testingHello);
        textView.setText("LATEST MOVIES");
        defaultSelect=0;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 arr = new String[clist.size()];
                for (int i = 0; i < clist.size(); i++) {
                    arr[i] = clist.get(i).getTitle();
                }
                yearOnclick(arr);
            }
        });

    }

    static MySpinner sp;
    static ArrayList<get> clist, list;
    static RecyclerView rv;
    LinearLayoutManager llm;
    static Movie_seealladapter adapter;
    static Spinneradapter adapteree;
    static String cate = "0";
    static AVLoadingIndicatorView pg;
    static LinearLayout mainlayout;
    static RelativeLayout network;
    static TextView totalmovie;

    static SwipeRefreshLayout swipeLayout;

    @Override
    public void onDestroy() {
        super.onDestroy();
        cate = "0";
        reloadd="b";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie, container, false);
        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh_layout);
        swipeLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyRequest.getseeallMovie(count + "", cate,"a");
            }
        });


        clist = new ArrayList<>();
        get eg = new get();
        eg.setTitle("LATEST MOVIES");
        clist.add(eg);

//        sp = (MySpinner) v.findViewById(R.id.spinner2);
//           adapteree = new Spinneradapter(ac, clist);
//         sp.setAdapter(adapteree);
//        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                Log.e("spposition",position+"");
//
//
//                if (position == 0) {
//                    cate = 0 + "";
//                } else {
//                    cate = clist.get(position).getMid() + "";
//                }
//
//
//                MyRequest.getseeallMoviespinner(count + "", cate);
//                rv.setVisibility(View.GONE);
//                pg.setVisibility(View.VISIBLE);
//                pg.show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Log.e("onNothingSelected","true");
//            }
//        });
        pg = (AVLoadingIndicatorView) v.findViewById(R.id.avi);
        mainlayout = (LinearLayout) v.findViewById(R.id.mainrlayout);
        network = (RelativeLayout) v.findViewById(R.id.networkerro);
        totalmovie = (TextView) v.findViewById(R.id.totalmovie);

        pg.show();

        Button reload = (Button) v.findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
                MyRequest.getseeallMovie(count + "", cate,"b");
            }
        });

        rv = (RecyclerView) v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(ac);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        list = new ArrayList<get>();
        adapter = new Movie_seealladapter(ac, list);
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
                    MyRequest.getseeallMoviespinner(count + "", cate);
                }


            }
        });


        return v;
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
        list = Jsonparser.getMoviealllist(s);

        totalmovie.setText(Html.fromHtml("<b>Total Movies " + Jsonparser.getonestring(s, "moviecount") + "</b>"));


        clist = Jsonparser.getcatelist(s);
        objDays = clist.toArray();


        //adapteree.refresh(clist);


        adapter.refresh(list);
        adapter.load_pg(false);
        Imagecondition();

    }



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

    public static void Feedback_Error() {
        swipeLayout.setRefreshing(false);
        pg.setVisibility(View.GONE);
        pg.hide();
        network.setVisibility(View.VISIBLE);
        mainlayout.setVisibility(View.GONE);
        adapter.load_pg(false);
    }

    public static void Feedbackwithoutspinner(String s) {
        swipeLayout.setRefreshing(false);
        network.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        pg.hide();
        rv.setVisibility(View.VISIBLE);

        list = new ArrayList<>();
        list = Jsonparser.getMoviealllist(s);


        /*clist = Jsonparser.getcatelist(s);
        adapteree = new Spinneradapter(ac, clist);
        sp.setAdapter(adapteree);*/

      /*  sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.e("spposition",position+"");


                if (position == 0) {
                    cate = 0 + "";
                } else {
                    cate = clist.get(position).getMid() + "";
                }



                MyRequest.getseeallMoviespinner(count + "", cate);
                rv.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        adapter.refresh(list);
        adapter.load_pg(false);
        Imagecondition();
    }

    public static void Feedback_ErrorSW() {

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



    private void yearOnclick(final String[] yearArr) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext(), R.style.MyAlertDialogTheme);
        builder.setTitle("Please select Movie Types");
        builder.setSingleChoiceItems(yearArr, defaultSelect, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                defaultSelect = which;
                textView.setText(arr[defaultSelect]);

                if (defaultSelect == 0) {
                    cate = 0 + "";
                } else {
                    cate = clist.get(defaultSelect).getMid() + "";
                }


                MyRequest.getseeallMoviespinner(count + "", cate);
                rv.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
                dialog.dismiss();
            }
        });


        builder.show();
    }
}
