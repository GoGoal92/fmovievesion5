package market.goldandgo.videonew1.Object;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by Go Goal on 11/25/2016.
 */

public class Phonesize {

    static DisplayMetrics metrics;

    public Phonesize(Activity mainActivity) {
        metrics = new DisplayMetrics();
        mainActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    public static int getwidth_two() {
        return  metrics.widthPixels;
    }


    public static int getwidth() {
        int screenWidth = metrics.widthPixels;
        return screenWidth;
    }

    public static int getheight() {

        int screenheight = metrics.heightPixels;

        return screenheight;
    }
}
