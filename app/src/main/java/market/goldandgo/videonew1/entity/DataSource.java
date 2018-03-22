package market.goldandgo.videonew1.entity;


import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import market.goldandgo.videonew1.Utils.Mydatabase;

/**
 * Created by Aspsine on 2015/7/8.
 */
public class DataSource {

    private static DataSource sDataSource = new DataSource();

    private static final String[] NAMES = {
            "网易云音乐",
            "优酷",
            "腾讯视频",
            "UC浏览器",
            "360手机卫士",
            "前程无忧51job",
            "搜狐视频",
            "微信电话本",
            "淘宝",
            "聚美优品",
            "搜房网"
    };

    private static final String[] IMAGES = {
            "http://img.wdjimg.com/mms/icon/v1/d/f1/1c8ebc9ca51390cf67d1c3c3d3298f1d_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/3/2d/dc14dd1e40b8e561eae91584432262d3_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/8/10/1b26d9f0a258255b0431c03a21c0d108_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/3/89/9f5f869c0b6a14d5132550176c761893_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/d/29/dc596253e9e80f28ddc84fe6e52b929d_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/e/d0/03a49009c73496fb8ba6f779fec99d0e_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/2/bf/939a67b179e75326aa932fc476cbdbf2_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/b/fe/718d7c213ce633fd4e25c278c19acfeb_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/f/29/cf90d1294ac84da3b49561a6f304029f_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/4/43/0318ce32731600bfa66cbb5018e1a434_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/7/08/2b3858e31efdee8a7f28b06bdb83a087_512_512.png"
    };

    private static final String[] URLS = {
            "http://www.myfmovie.com/downloads/f64.apk",
            "http://www.myfmovie.com/downloads/f64.apk",
            "http://www.myfmovie.com/downloads/f64.apk",
            "http://www.myfmovie.com/downloads/f64.apk",
            "http://www.myfmovie.com/downloads/f64.apk",
            "http://www.myfmovie.com/downloads/f64.apk",
            "http://www.myfmovie.com/downloads/f64.apk",
            "http://www.myfmovie.com/downloads/f64.apk",
            "http://www.myfmovie.com/downloads/f64.apk",
            "http://www.myfmovie.com/downloads/f64.apk",
            "http://www.myfmovie.com/downloads/f64.apk"
    };

    public static DataSource getInstance() {
        return sDataSource;
    }

    public static List<AppInfo> getData() {
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        for (int i = 0; i < NAMES.length; i++) {
            AppInfo appInfo = new AppInfo();
            appInfo.setId(String.valueOf(i));
            appInfo.setName(NAMES[i]);
            appInfo.setImage(IMAGES[i]);
            appInfo.setUrl(URLS[i]);

      //      AppInfo appInfo = new AppInfo(String.valueOf(i), NAMES[i], IMAGES[i], URLS[i]);
            appInfos.add(appInfo);
        }
        return appInfos;
    }

    public static List<AppInfo> getmyData(AppCompatActivity ac) {
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        Mydatabase mdb=new Mydatabase(ac);
        appInfos=mdb.getdata();

        return appInfos;
    }
}
