package market.goldandgo.videonew1.Object;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import market.goldandgo.videonew1.Utils.Encode_Decoder;

/**
 * Created by Go Goal on 6/29/2017.
 */

public class Constant {

    public static final String FIREBASE_APP = "https://fmovie-14887.firebaseio.com";


    public  static  String host1,hostfile,host,Banner,adlink,Inter,Bannercon,Intercon,Adflycon;

    public static String moviephoto;
    public static String seriesrcoverphoto;
    public static String seriesrphoto=hostfile+"api/series/";
    public static String cartegories=host+"api/cartegories/";

    public static String apikey="aa";

    public  static  String appplaystroe="https://play.google.com/store/apps/details?id=market.goldandgo.videonew"; //rate me
    public static String facebookpage="762314377270893";
    public static String usermovie="base64fmgogoal";

    public static String datalocation_movie= Environment.getExternalStorageDirectory()+"/fmovie/movie/";
    public static String datalocation_scover= Environment.getExternalStorageDirectory()+"/fmovie/scover/";
    public static String datalocation_series= Environment.getExternalStorageDirectory()+"/fmovie/series/";
    public static String Offialfolder= Environment.getExternalStorageDirectory()+"/fmovie/";
    public static String downloadfolder=  Environment.getExternalStorageDirectory()+"/fmovie/downloadManager/";
    public static String DM_downloadfolder= "fmovie/downloadManager/";



    public static String cleanstring(String title,String status) {

        if (status.equals("1")){

            title= Encode_Decoder.decoding_string(title);
        }



        return  title;
    }

    public static void generateapi(AppCompatActivity ac) {



        try {

            PackageInfo info = ac.getPackageManager().getPackageInfo("market.goldandgo.videonew", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                apikey=sign;

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {
            
        }
        apikey = "YDkqHcl3/XYGDVemFr/Ztl3UN3s=\n";
    }

    public static void seturl(String hostv, String host1v, String hostfilev,String ban,String adfly) {
        host=hostv;
        host1=host1v;
        hostfile=hostfilev;
        Banner=ban;
        adlink=adfly;

        moviephoto=hostfile+"api/Movie/";
        seriesrcoverphoto=hostfile+"api/scover/";

    }

    public static void setads(String banner, String inter, String bannercon, String intercon, String adflycon) {

        Banner=banner;
        Inter=inter;
        Bannercon=bannercon;
        Intercon=intercon;
        Adflycon=adflycon;

    }

    public static boolean bannershow() {

        if (Bannercon.equals("1")){
            return true;
        }else{
            return false;
        }
    }

    public static boolean intershow() {

        if (Intercon.equals("1")){
            return true;
        }else{
            return false;
        }
    }

    public static boolean adflyshow() {

        if (Adflycon.equals("1")){
            return true;
        }else{
            return false;
        }
    }



    public String md5(String s) {



        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }



    private static String getBytesToMBString(long bytes){
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }


    public static String getmb(long downloadedLength) {
        return getBytesToMBString(downloadedLength);
    }
}
