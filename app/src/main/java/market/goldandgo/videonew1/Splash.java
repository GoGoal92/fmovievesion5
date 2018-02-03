package market.goldandgo.videonew1;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Downloadlist;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.phoneid;
import market.goldandgo.videonew1.Utils.Myalertdialog;
import market.goldandgo.videonew1.service.Firebaseservcie;
import market.goldandgo.videonew1.service.Networkreceiver;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by Go Goal on 6/30/2017.
 */

public class Splash extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    TextView loading;
    static AppCompatActivity ac;
    static  boolean firsttime=false;

    @Override
    protected void onResume() {
        super.onResume();
        firsttime=false;
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ac = this;
        Constant.generateapi(ac);


        Intent ll24 = new Intent(ac, Networkreceiver.class);
        PendingIntent recurringLl24 = PendingIntent.getBroadcast(ac, 0, ll24, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) getSystemService(ac.ALARM_SERVICE);
        alarms.setRepeating(AlarmManager.RTC_WAKEUP, 1000, AlarmManager.INTERVAL_HOUR, recurringLl24); // Log repetition

        startService(new Intent(ac,Firebaseservcie.class));





        phoneid pd = new phoneid(getApplicationContext());
        Downloadlist.setcontext(ac);

        loading = (TextView) findViewById(R.id.loading);





      /*  if(appInstalledOrNot("market.goldandgo.videonew")){

            AlertDialog.Builder ab = new AlertDialog.Builder(ac);
            ab.setTitle("Information");
            View vc = ac.getLayoutInflater().inflate(R.layout.ontextzawwgyirow, null);
            ab.setView(vc);
            Zawgyitextview tv = (Zawgyitextview) vc.findViewById(R.id.ttv);
            tv.setText("ေက်းဇူးျပဳ၍ Version 6.3 ေအာက္ version ေတြကို Unistall လုပ္ေပးပါ။");

            ab.setPositiveButton("CLEAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Uri uri = Uri.parse(get"market.goldandgo.videonew");
                    Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, uri);
                    startActivity(intent);

                }
            });
            ab.setCancelable(false);
            ab.show();


        }else{

        }*/

        checkStoragePermission();

        /*int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                checkStoragePermission();

            }
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyRequest.checkversion();
                    loading.setText("Checking App Version");
                }
            },2000);
        }*/


    }

    private void checkStoragePermission() {
        String[] per = new String[]{android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(ac, per)) {
            // Already have permission, do the thing
            File f=new File(Constant.Offialfolder);
            if (!f.exists()){
                f.mkdir();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Firebase firebase = new Firebase(Constant.FIREBASE_APP );
                    firebase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (!firsttime){
                            HashMap<String, String> valuee = (HashMap) dataSnapshot.getValue();
                            String host = valuee.get("Url");
                            String host1 = valuee.get("host1");
                            String hostfile = valuee.get("hostfile");
                            String banner = valuee.get("banner");
                            String adlink = valuee.get("adfly");
                            Constant.seturl(host,host1,hostfile,banner,adlink);



                                MyRequest.checkversion();
                                loading.setText("Checking App Version (6.4)");
                                firsttime=true;
                            }


                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });



                }
            },2000);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "All Permission Must Grant", 200, per);
        }
    }



    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED ) {
            return true;
        } else {
            return false;
        }
    }



    static String gold="0";

    public static String getgold () {
        return gold;
    }

    public static void Feedback(String s) {


        gold = Jsonparser.getonestring(s, "gold");



            String status = Jsonparser.getonestring(s, "status");
            if (status.equals("0")) {

                //ads
                String banner = Jsonparser.getonestring(s, "banner");
                String inter = Jsonparser.getonestring(s, "inter");
                String bannercon = Jsonparser.getonestring(s, "bannercon");
                String intercon = Jsonparser.getonestring(s, "intercon");
                String adflycon = Jsonparser.getonestring(s, "adflycon");

                Constant.setads(banner,inter,bannercon,intercon,adflycon);
                Intent it = new Intent(ac, MainActivity.class);
                ac.startActivity(it);
                ac.finish();

            } else if (status.equals("1")) {
                String msg = Jsonparser.getonestring(s, "msg");
                Myalertdialog.show_exit(msg, ac, "Come back Later", "Server Matainance");

            } else if (status.equals("2")) {

                //ads
                String banner = Jsonparser.getonestring(s, "banner");
                String inter = Jsonparser.getonestring(s, "inter");
                String bannercon = Jsonparser.getonestring(s, "bannercon");
                String intercon = Jsonparser.getonestring(s, "intercon");
                String adflycon = Jsonparser.getonestring(s, "adflycon");

                Constant.setads(banner,inter,bannercon,intercon,adflycon);

                String msg = Jsonparser.getonestring(s, "msg");
                String url = Jsonparser.getonestring(s, "url");
                Myalertdialog.show_updateremind(msg, ac, "Update", "Cancel", "Update Remind", url);

            } else if (status.equals("3")) {

                String msg = Jsonparser.getonestring(s, "msg");
                String url = Jsonparser.getonestring(s, "url");

                Myalertdialog.show_update(msg, ac, "Update", "Cancel", "Need To Update", url);

            }



    }

    public static void Feedback_Error() {

        AlertDialog.Builder ab = new AlertDialog.Builder(ac);
        ab.setTitle("Connection Problem");
        View v = ac.getLayoutInflater().inflate(R.layout.ontextzawwgyirow, null);
        ab.setView(v);
        Zawgyitextview tv = (Zawgyitextview) v.findViewById(R.id.ttv);
        tv.setText("Network Fail ! Please Check Your Network");

        ab.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                MyRequest.checkversion();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ac.finish();
            }
        });

        ab.show();

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

        File f=new File(Constant.Offialfolder);
        if (!f.exists()){
            f.mkdir();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MyRequest.checkversion();
                loading.setText("Checking App Version");
            }
        },2000);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        Toast.makeText(ac,"Permission Needed",Toast.LENGTH_SHORT).show();
        ac.finish();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==200) {
            File f = new File(Constant.Offialfolder);
            if (!f.exists()) {
                f.mkdir();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Firebase firebase = new Firebase(Constant.FIREBASE_APP);
                    firebase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            HashMap<String, String> valuee = (HashMap) dataSnapshot.getValue();
                            String host = valuee.get("Url");
                            String host1 = valuee.get("host1");
                            String hostfile = valuee.get("hostfile");
                            String banner = valuee.get("banner");
                            String adlink = valuee.get("adfly");
                            Constant.seturl(host, host1, hostfile, banner, adlink);


                            MyRequest.checkversion();
                            loading.setText("Checking App Version (6.3)");

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });



                }
            }, 2000);
        }

    }
}
