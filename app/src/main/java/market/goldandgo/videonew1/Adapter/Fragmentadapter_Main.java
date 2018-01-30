package market.goldandgo.videonew1.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import market.goldandgo.videonew1.Fragment.Fragment_Home;
import market.goldandgo.videonew1.Fragment.Fragment_Movie;
import market.goldandgo.videonew1.Fragment.Fragment_Series;
import market.goldandgo.videonew1.Fragment.Fragment_menu;


/**
 * Created by Go Goal on 11/25/2016.
 */

public class Fragmentadapter_Main extends FragmentStatePagerAdapter {


    public Fragmentadapter_Main(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return Fragment_Home.newInstance();
        } else if (position == 1) {
            return Fragment_Movie.newInstance();
        } else if (position == 2) {
            return Fragment_Series.newInstance();
        } else {
            return Fragment_menu.newInstance();
        }


    }

    @Override
    public int getCount() {
        return 4;
    }
}
