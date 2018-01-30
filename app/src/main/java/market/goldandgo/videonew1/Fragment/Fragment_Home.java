package market.goldandgo.videonew1.Fragment;

/**
 * Created by Go Goal on 11/25/2016.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.ArrayList;

import market.goldandgo.videonew1.Detail;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.SeriesDetail;
import market.goldandgo.videonew1.Utils.Download_data;
import market.goldandgo.videonew1.Utils.Download_datascover;
import market.goldandgo.videonew1.Utils.calculate_st;


/**
 * Created by Go Goal on 11/20/2016.
 */

public class Fragment_Home extends Fragment {


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
        MyRequest.Mainpage();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ac = getActivity();
        //MyRequest.Mainpage();


    }


    static TextView[] titlemovie = new TextView[5];
    static TextView[] titleseries = new TextView[5];

    static RelativeLayout[] back = new RelativeLayout[5];
    static ProgressBar[] pm = new ProgressBar[5];

    static RelativeLayout[] backseries = new RelativeLayout[5];
    static ProgressBar[] ps = new ProgressBar[5];


    static TextView[] detailm = new TextView[5];
    static TextView[] details = new TextView[5];


    static ScrollView sv;
    static AVLoadingIndicatorView pg;
    static RelativeLayout network;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home, container, false);
        titlemovie[0] = (TextView) v.findViewById(R.id.mtitle1);
        titlemovie[1] = (TextView) v.findViewById(R.id.mtitle2);
        titlemovie[2] = (TextView) v.findViewById(R.id.mtitle3);
        titlemovie[3] = (TextView) v.findViewById(R.id.mtitle4);
        titlemovie[4] = (TextView) v.findViewById(R.id.mtitle5);

        titleseries[0] = (TextView) v.findViewById(R.id.seriesmtitle1);
        titleseries[1] = (TextView) v.findViewById(R.id.seriesmtitle2);
        titleseries[2] = (TextView) v.findViewById(R.id.seriesmtitle3);
        titleseries[3] = (TextView) v.findViewById(R.id.seriesmtitle4);
        titleseries[4] = (TextView) v.findViewById(R.id.seriesmtitle5);

        detailm[0] = (TextView) v.findViewById(R.id.mdetail1);
        detailm[1] = (TextView) v.findViewById(R.id.mdetail2);
        detailm[2] = (TextView) v.findViewById(R.id.mdetail3);
        detailm[3] = (TextView) v.findViewById(R.id.mdetail4);
        detailm[4] = (TextView) v.findViewById(R.id.mdetail5);


        details[0] = (TextView) v.findViewById(R.id.seriesmdetail1);
        details[1] = (TextView) v.findViewById(R.id.seriesmdetail2);
        details[2] = (TextView) v.findViewById(R.id.seriesmdetail3);
        details[3] = (TextView) v.findViewById(R.id.seriesmdetail4);
        details[4] = (TextView) v.findViewById(R.id.seriesmdetail5);

        back[0] = (RelativeLayout) v.findViewById(R.id.back1);
        back[1] = (RelativeLayout) v.findViewById(R.id.back2);
        back[2] = (RelativeLayout) v.findViewById(R.id.back3);
        back[3] = (RelativeLayout) v.findViewById(R.id.back4);
        back[4] = (RelativeLayout) v.findViewById(R.id.back5);

        pm[0] = (ProgressBar) v.findViewById(R.id.pg1);
        pm[1] = (ProgressBar) v.findViewById(R.id.pg2);
        pm[2] = (ProgressBar) v.findViewById(R.id.pg3);
        pm[3] = (ProgressBar) v.findViewById(R.id.pg4);
        pm[4] = (ProgressBar) v.findViewById(R.id.pg5);


        backseries[0] = (RelativeLayout) v.findViewById(R.id.seriesback1);
        backseries[1] = (RelativeLayout) v.findViewById(R.id.seriesback2);
        backseries[2] = (RelativeLayout) v.findViewById(R.id.seriesback3);
        backseries[3] = (RelativeLayout) v.findViewById(R.id.seriesback4);
        backseries[4] = (RelativeLayout) v.findViewById(R.id.seriesback5);

        ps[0] = (ProgressBar) v.findViewById(R.id.pg6);
        ps[1] = (ProgressBar) v.findViewById(R.id.pg7);
        ps[2] = (ProgressBar) v.findViewById(R.id.pg8);
        ps[3] = (ProgressBar) v.findViewById(R.id.pg9);
        ps[4] = (ProgressBar) v.findViewById(R.id.pg10);

        sv = (ScrollView) v.findViewById(R.id.sv);
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





        return v;
    }


    static ArrayList<get> listmovie, listseries;

    static int ab = 0;

    public static void Feedback(String s) {


        sv.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        network.setVisibility(View.GONE);
        pg.hide();


        listmovie = new ArrayList<>();
        listmovie = Jsonparser.getMovierated(s, "top");

        for (int k=0;k<back.length;k++){

            final int finalK = k;
            back[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it=new Intent(ac,Detail.class);
                    it.putExtra("mid",listmovie.get(finalK).getMid());
                    it.putExtra("type","movie");
                    it.putExtra("mname",listmovie.get(finalK).getTitle());
                    it.putExtra("image",listmovie.get(finalK).getImage());
                    ac.startActivity(it);

                }
            });

        }


        File moviepath = new File(Constant.datalocation_movie);
        if (!moviepath.exists()) {
            moviepath.mkdir();
        }

        File scoverpath = new File(Constant.datalocation_scover);
        if (!scoverpath.exists()) {
            scoverpath.mkdir();
        }


        ArrayList<get> movieloadinglist = new ArrayList<>();

        for (int i = 0; i < titlemovie.length; i++) {
            titlemovie[i].setText(listmovie.get(i).getTitle());
            detailm[i].setText("Like "+ calculate_st.format(Long.parseLong(listmovie.get(i).getLike()))+"\nView "+calculate_st.format(Long.parseLong(listmovie.get(i).getView())) );

            final String fpath = Constant.datalocation_movie + listmovie.get(i).getMid() + ".fmovie";
            File imgf = new File(fpath);
            if (!imgf.exists()) {

                get eg = new get();
                eg.setImage(listmovie.get(i).getImage());
                eg.setImagesdpath(fpath);
                eg.setBackrl(back[i]);
                eg.setImgpg(pm[i]);
                eg.setMid(listmovie.get(i).getMid());
                movieloadinglist.add(eg);


            } else {


                Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                back[i].setBackground(myDrawable);
                pm[i].setVisibility(View.GONE);

            }
        }

        if (movieloadinglist.size()>0){
            Download_data dn = new Download_data(ac, movieloadinglist);
            dn.startdownloadimg();
        }


        listseries = new ArrayList<>();
        listseries = Jsonparser.getseriesrated(s, "topseries");

        ArrayList<get> seriesloadinglist = new ArrayList<>();


        for (int k=0;k<backseries.length;k++){

            final int finalK = k;
            backseries[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it=new Intent(ac,SeriesDetail.class);
                    if (listseries.get(finalK).getMid().contains("s")){
                        String s=listseries.get(finalK).getMid().replace("s","");
                        listseries.get(finalK).setMid(s);
                    }

                    it.putExtra("mid",listseries.get(finalK).getMid());
                    it.putExtra("mname",listseries.get(finalK).getTitle());
                    it.putExtra("like",listseries.get(finalK).getLike() + " Like");
                    it.putExtra("view",listseries.get(finalK).getView() + " View");
                    it.putExtra("ep",listseries.get(finalK).getEpisode());
                    it.putExtra("price","Free");
                    it.putExtra("detail",listseries.get(finalK).getDetail());
                    ac.startActivity(it);

                }
            });

        }

        for (int i = 0; i < titleseries.length; i++) {
            titleseries[i].setText(listseries.get(i).getTitle());
            details[i].setText("Like "+ calculate_st.format(Long.parseLong(listseries.get(i).getLike()))+"\nView "+calculate_st.format(Long.parseLong(listseries.get(i).getView())) );
            final String fpath = Constant.datalocation_scover + listseries.get(i).getMid() + ".fmovie";
            File imgf = new File(fpath);
            if (!imgf.exists()) {

                get eg = new get();
                eg.setImage(listseries.get(i).getImage());
                eg.setImagesdpath(fpath);
                eg.setBackrl(backseries[i]);
                eg.setImgpg(ps[i]);
                eg.setMid(listseries.get(i).getMid());
                seriesloadinglist.add(eg);

            } else {

                Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                backseries[i].setBackground(myDrawable);
                ps[i].setVisibility(View.GONE);

            }
        }

        if (seriesloadinglist.size()>0){
            Download_datascover dnn = new Download_datascover(ac, seriesloadinglist);
            dnn.startdownloadimg();
        }


    }

    public static void Feedback_Error() {
        sv.setVisibility(View.GONE);
        pg.setVisibility(View.GONE);
        network.setVisibility(View.VISIBLE);
        pg.hide();
    }





}
