package market.goldandgo.videonew1.Fragment;

/**
 * Created by Go Goal on 11/25/2016.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import market.goldandgo.videonew1.Coingame;
import market.goldandgo.videonew1.Freegold;
import market.goldandgo.videonew1.MainActivity;
import market.goldandgo.videonew1.Mydownloadmanager;
import market.goldandgo.videonew1.Mymovies;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Rule;
import market.goldandgo.videonew1.Splash;
import market.goldandgo.videonew1.Utils.calculate_st;


/**
 * Created by Go Goal on 11/20/2016.
 */

public class Fragment_menu extends Fragment {


    public static Fragment_menu newInstance() {

        Bundle args = new Bundle();

        Fragment_menu fragment = new Fragment_menu();
        fragment.setArguments(args);
        return fragment;
    }


    FragmentActivity ac;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ac = getActivity();


    }


    static TextView usergold;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.mymenu, container, false);


        LinearLayout rateus = (LinearLayout) v.findViewById(R.id.rateus);
        LinearLayout likepage = (LinearLayout) v.findViewById(R.id.likepage);
        LinearLayout uploadmovies = (LinearLayout) v.findViewById(R.id.uploadmovies);
        LinearLayout mymovies = (LinearLayout) v.findViewById(R.id.mymovies);
        LinearLayout downloadmgr = (LinearLayout) v.findViewById(R.id.downloadmgr);
        LinearLayout freespin = (LinearLayout) v.findViewById(R.id.wheeloffortune);
        LinearLayout coingame = (LinearLayout) v.findViewById(R.id.coingame);

        usergold = (TextView) v.findViewById(R.id.usergold);
        usergold.setText(calculate_st.format(Long.parseLong(Splash.getgold())) + " Gold");

        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse(Constant.appplaystroe));
                startActivity(intent);
            }
        });

        likepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("fb://page/" + Constant.facebookpage));
                startActivity(intent);
            }
        });

        uploadmovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ac, Rule.class);
                startActivity(intent);
            }
        });

        mymovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ac, Mymovies.class);
                startActivity(intent);
            }
        });

        downloadmgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ac, Mydownloadmanager.class);
                startActivity(intent);
            }
        });

        freespin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ac, Freegold.class);
                startActivity(intent);
            }
        });

        coingame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ac, Coingame.class);
                startActivity(intent);
            }
        });


        return v;
    }


    public static void sentgold(String remain) {
        usergold.setText(calculate_st.format(Long.parseLong(remain)) + " Gold");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.Pagerenable(true);
    }
}
