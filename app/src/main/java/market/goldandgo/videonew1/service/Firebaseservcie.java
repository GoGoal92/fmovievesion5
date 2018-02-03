package market.goldandgo.videonew1.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.util.HashMap;

import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Mydownloadmanager;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Downloadlist;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Splash;

/**
 * Created by Go Goal on 2/1/2018.
 */

public class Firebaseservcie extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Downloadlist.setcontext(getApplicationContext());

       // showForegroundNotification("fmovie");

        Firebase firebase = new Firebase(Constant.FIREBASE_APP);
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> valuee = (HashMap) dataSnapshot.getValue();
                String msgid_firebase = valuee.get("msgid");
                String msg_firebase = valuee.get("msg");

                SharedPreferences prefs = getApplicationContext().getSharedPreferences("dlist",
                        Context.MODE_PRIVATE);


                File f = new File(Constant.downloadfolder);
                if (!f.exists()) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("msgid", msgid_firebase);
                    editor.commit();
                    f.mkdir();
                } else {

                    try{
                        String msgid = prefs.getString("msgid", null);
                        if (!msgid.equals(msgid_firebase)) {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("msgid", msgid_firebase);
                            editor.commit();
                            postNotif(msg_firebase);


                        }
                    }catch (Exception e){
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("msgid", msgid_firebase);
                        editor.commit();
                    }



                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    private void postNotif(String notifString) {

        Intent intent = new Intent(getApplicationContext(),Splash.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent, 0);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_push);
        contentView.setImageViewResource(R.id.image,R.drawable.appicon);
        contentView.setTextViewText(R.id.title, notifString);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.appicon)
                .setContent(contentView);

        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.contentIntent = pendingIntent;

        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);


    }

    private void showForegroundNotification(String contentText) {
        // Create intent that will bring our app to the front, as if it was tapped in the app
        // launcher
        Intent showTaskIntent = new Intent(getApplicationContext(), Splash.class);
        showTaskIntent.setAction(Intent.ACTION_MAIN);
        showTaskIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                showTaskIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle(getString(R.string.app_name))
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(contentIntent)
                .build();
        startForeground(1, notification);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        Intent intent = new Intent("com.android.gogoal");
        intent.putExtra("stop", "restart");
        sendBroadcast(intent);
        startService(new Intent(getApplicationContext(), Firebaseservcie.class));
        showForegroundNotification("fmovie");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent("com.android.gogoal");
        intent.putExtra("stop", "restart");
        sendBroadcast(intent);
        startService(new Intent(getApplicationContext(), Firebaseservcie.class));
        showForegroundNotification("fmovie");
    }
}
