package market.goldandgo.videonew1.Object;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by Go Goal on 10/15/2016.
 */

public class ratio {

    static int myuiheight=1280;
    static int myuiwidth=720;



    static int screenWidth,screenheight;



    public static int getwidth(int i) {

        float res = (float) screenWidth/myuiwidth;


        int width= (int) (res*i);

        return width;
    }

    public static int gethetight(int i) {

        float res = (float) screenheight/myuiheight;
        int height= (int) (res*i);
        return height;
    }


    public static void setactivity(AppCompatActivity ac) {
        DisplayMetrics metrics = new DisplayMetrics();
        ac.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenheight = metrics.heightPixels;
    }
}
