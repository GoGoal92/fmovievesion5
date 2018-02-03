package market.goldandgo.videonew1.Object;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Go Goal on 5/24/2017.
 */

public class Downloadlist {

    public static ArrayList<String> list = new ArrayList<>();
    public static ArrayList<String> listsize = new ArrayList<>();
    public static ArrayList<String> listtaskToken = new ArrayList<>();
    public static ArrayList<String> liststatus = new ArrayList<>();
    public static ArrayList<String> listurl = new ArrayList<>();
    private static Context context;
    static SharedPreferences prefs;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public static void addlist(String dnfilename, String size, int taskToken,String stasus,String link) {


        listurl.add(link);
        list.add(dnfilename);
        listtaskToken.add(taskToken+"");
        liststatus.add(stasus);
        listsize.add(size);

        Set<String> setliststatus = new HashSet<String>();
        setliststatus.addAll(liststatus);

        Set<String> settaskToken = new HashSet<String>();
        settaskToken.addAll(listtaskToken);

        Set<String> set = new HashSet<String>();
        set.addAll(list);


        Set<String> setsize = new HashSet<String>();
        setsize.addAll(listsize);


        Set<String> seturl = new HashSet<String>();
        seturl.addAll(listurl);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("dlist", set);
        editor.putStringSet("sizelist", setsize);
        editor.putStringSet("taskTokenlist", settaskToken);
        editor.putStringSet("mystatus", setliststatus);
        editor.putStringSet("url", seturl);
        editor.commit();



    }

    public static ArrayList<String> getlisturl() {

        listurl = new ArrayList<>();
        try {

            Set<String> setsize = prefs.getStringSet("url", null);
            listurl.addAll(setsize);
        } catch (Exception e) {

        }

        return listurl;
    }

    public static ArrayList<String> getlistsize() {

        listsize = new ArrayList<>();
        try {

            Set<String> setsize = prefs.getStringSet("sizelist", null);
            listsize.addAll(setsize);
        } catch (Exception e) {

        }

        return listsize;
    }

    public static ArrayList<String> getlist() {

        list = new ArrayList<>();
        try {
            Set<String> set = prefs.getStringSet("dlist", null);
            list.addAll(set);
        } catch (Exception e) {

        }

        return list;
    }

    public static void removelist(ArrayList<String> mylist, ArrayList<String> sizelist,ArrayList<String> tokenlist,ArrayList<String> statusll,ArrayList<String> urllist) {
        //list.remove(position);
        list = mylist;
        Set<String> set = new HashSet<String>();
        set.addAll(list);

        listsize = sizelist;
        Set<String> setsize = new HashSet<String>();
        setsize.addAll(listsize);

        listtaskToken=tokenlist;
        Set<String> settokensize = new HashSet<String>();
        settokensize.addAll(listtaskToken);


        liststatus=statusll;
        Set<String> setliststatus = new HashSet<String>();
        setliststatus.addAll(liststatus);


        listurl=urllist;
        Set<String> seturl = new HashSet<String>();
        seturl.addAll(listurl);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("dlist", set);
        editor.putStringSet("sizelist", setsize);
        editor.putStringSet("taskTokenlist", settokensize);
        editor.putStringSet("mystatus", setliststatus);
        editor.putStringSet("url", seturl);
        editor.commit();

    }

    public static void setcontext(Context context) {
        Downloadlist.context = context;
        prefs = context.getSharedPreferences("dlist",
                Context.MODE_PRIVATE);
    }

    public static void committime() {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("time", System.currentTimeMillis());
        editor.commit();

    }


    public static long getremaintime() {



        long mytime=prefs.getLong("time",0);
        long nowtime=System.currentTimeMillis();



        long remain=nowtime-mytime;


        long fivemin=10*1000;

        long total=fivemin-remain;

        if (total<1){
            total=0;
        }

        return total;
    }

    public static ArrayList<String> gettaskTokenlist() {
        listtaskToken = new ArrayList<>();
        try {
            Set<String> set = prefs.getStringSet("taskTokenlist", null);
            listtaskToken.addAll(set);
        } catch (Exception e) {

        }

        return listtaskToken;
    }

    public static ArrayList<String> getstatuslist() {
        liststatus = new ArrayList<>();
        try {
            Set<String> set = prefs.getStringSet("mystatus", null);
            liststatus.addAll(set);
        } catch (Exception e) {

        }

        return liststatus;
    }

}
