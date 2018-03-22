package market.goldandgo.videonew1.Object;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Locale;

import market.goldandgo.videonew1.Utils.Encode_Decoder;

/**
 * Created by Go Goal on 6/29/2017.
 */

public class Constant {

    public static final String INFO1 = "ဤ app ၏ သေဘာတရားမွာ FB မွာတင္ထားတဲ့ ဇာတ္ကားမ်ားကို ျပန္ျပတာျဖစ္တဲ့ အတြက္ Operator ေတြက FB Package ထဲက ျဖတ္သြားတာပါ ။\n" +
            "\n" +
            "ဒါေၾကာင့္ App အတြက္ အသံုးျပဳရတဲ့ Data ဟာ Mobile Data နဲ႔ျဖစ္ျပီး ဇာတ္ကားၾကည့္တာ ေဒါင္းတာဘဲ FB plan နဲ႔ပါ ။";

    public static final String INFO2 ="ဇာတ္ကားေတြဟာ FB ေပၚတင္ထားတာျဖစ္လို႔ တခ်ိဳ႕ဇာတ္ကားေတြဟာ ပ်က္က်ႏိုင္ပါတယ္ ။\n" +
            "\n" +
            "ပ်က္က်ေနတဲ့ ဇာတ္ကားမ်ားကို Report ျပဳလုပ္ျခင္းျဖင့္ Admin မ်ားကို အသိေပးႏိုင္ပါသည္ ။";
    public static final String INFO3 ="တစ္ခ်ိဳ႔ ဇာတ္ကားေတြဟာ Gold ျဖင့္ေရာင္းပါသည္ ။ \n" +
            "\n" +
            "Gold ကို Free Gold ဆိုေသာ Game ေဆာ့ျခင္းျဖင့္ အခမဲ့ရယူႏိုင္ပါသည္ ။";
    public static final String INFO4 ="သင့္တြင္ဖုန္းေဘလ္ (သို႔) အင္တာနက္ Plan မရွိပါက ဤ app အားဖြင့္၍ ရမည္မဟုတ္ ။\n" +
            "\n" +
            "FB plan သည္ ဇာတ္ကား ၾကည့္ရာ ေဒါင္းရာတြင္ဘဲ အၾကံဳးဝင္မည္ျဖစ္သည္။\n";
    public static final String INFO5 ="Rate Us ခလုပ္ကို ႏိုပ္၍ PlayStore ေပၚတြင္ ၾကယ္ ၅ လံုး ေပးျခင္း၊ လိုအပ္ခ်က္မ်ားကို ေျပာဆိုျခင္းမ်ားျဖင့္ Fmovie ကို ေကာင္းသထက္ေကာင္းလာေအာင္ အၾကံဥာဏ္မ်ား ေပးႏိုင္ပါသည္ ။";
    public static final String INFO6 ="Like F Movie Facebook Page အားႏိုပ္ပါက FMOVIE Facebook Page သို႔ေရာက္ရွိသြားမယ္ျဖစ္သည္ ။\n" +
            "\n" +
            "Page အား Like လုပ္ျပီး See First လုပ္ထားျခင္းအားျဖင့္ FMOVIE ၏ ေနာက္ဆံုးရ သတင္းမ်ားကို သိရွိလိုက္ပါ ။";



    public static final String FIREBASE_APP = "https://fmovie-14887.firebaseio.com";


    public static String host1, hostfile, host, Banner, adlink, Inter, Bannercon, Intercon, Adflycon;

    public static String moviephoto;
    public static String seriesrcoverphoto;
    public static String seriesrphoto = hostfile + "api/series/";
    public static String adsurl = hostfile + "api/ads/";

    public static String apikey = "aa";

    public static String appplaystroe = "https://play.google.com/store/apps/details?id=market.goldandgo.videonew1"; //rate me
    public static String facebookpage = "762314377270893";
    public static String usermovie = "base64fmgogoal";

    public static String datalocation_movie = Environment.getExternalStorageDirectory() + "/fmovie/movie/";
    public static String datalocation_scover = Environment.getExternalStorageDirectory() + "/fmovie/scover/";
    public static String datalocation_ads = Environment.getExternalStorageDirectory() + "/fmovie/ads/";
    public static String datalocation_series = Environment.getExternalStorageDirectory() + "/fmovie/series/";
    public static String Offialfolder = Environment.getExternalStorageDirectory() + "/fmovie/";
    public static String downloadfolder = Environment.getExternalStorageDirectory() + "/fmovie/downloadManager/";
    public static String DM_downloadfolder = "/fmovie/downloadManager/";
    public static String versionanme = "7.0";


    public static String cleanstring(String title, String status) {

        if (status.equals("1")) {

            title = Encode_Decoder.decoding_string(title);
        }


        return title;
    }

    public static void generateapi(AppCompatActivity ac) {


        try {

            PackageInfo info = ac.getPackageManager().getPackageInfo(ac.getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                apikey = sign;

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


    }

    public static void seturl(String hostv, String host1v, String hostfilev, String ban, String adfly) {
        host = hostv;
        host1 = host1v;
        hostfile = hostfilev;
        Banner = ban;
        adlink = adfly;

        moviephoto = hostfile + "api/Movie/";
        seriesrcoverphoto = hostfile + "api/scover/";
        adsurl=hostfile + "api/ads/";

    }

    public static void setads(String banner, String inter, String bannercon, String intercon, String adflycon) {

        Banner = banner;
        Inter = inter;
        Bannercon = bannercon;
        Intercon = intercon;
        Adflycon = adflycon;

    }

    public static boolean bannershow() {

        if (Bannercon.equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean intershow() {

        if (Intercon.equals("1")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean adflyshow() {

        if (Adflycon.equals("1")) {
            return true;
        } else {
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
            for (int i = 0; i < messageDigest.length; i++)
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


    private static String getBytesToMBString(long bytes) {
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }


    public static String getmb(long downloadedLength) {
        return getBytesToMBString(downloadedLength);
    }

    private static final DecimalFormat DF = new DecimalFormat("0.00");

    public static String getDownloadPerSize(long finished, long total) {
        return DF.format((float) finished / (1024 * 1024)) + "M/" + DF.format((float) total / (1024 * 1024)) + "M";
    }
}
