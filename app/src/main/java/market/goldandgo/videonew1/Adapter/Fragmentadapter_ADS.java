package market.goldandgo.videonew1.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import market.goldandgo.videonew1.Fragment.Fragment_Home;
import market.goldandgo.videonew1.Fragment.Fragment_Movie;
import market.goldandgo.videonew1.Fragment.Fragment_Series;
import market.goldandgo.videonew1.Fragment.Fragment_menu;
import market.goldandgo.videonew1.Fragment.Fragmentads;
import market.goldandgo.videonew1.Object.get;


/**
 * Created by Go Goal on 11/25/2016.
 */

public class Fragmentadapter_ADS extends FragmentStatePagerAdapter {

    ArrayList<get> list;

    public Fragmentadapter_ADS(FragmentManager supportFragmentManager, ArrayList<get> list1) {
        super(supportFragmentManager);
        list=list1;
    }

    @Override
    public Fragment getItem(int position) {




        Bundle bundle = new Bundle();
        bundle.putString("id",list.get(position).getAdsid());
        bundle.putString("url",list.get(position).getAdsurl());


        final Fragmentads f = new Fragmentads();
        f.setArguments(bundle);
        return f;

    }

    @Override
    public int getCount() {
        return 5;
    }

    public void refresh(ArrayList<get> adlist) {
        list=adlist;
        notifyDataSetChanged();
    }
}
