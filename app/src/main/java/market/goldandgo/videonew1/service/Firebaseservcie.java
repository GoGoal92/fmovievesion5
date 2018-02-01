package market.goldandgo.videonew1.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
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
                    String msgid = prefs.getString("msgid", null);
                 //   Toast.makeText(getApplicationContext(), msgid, Toast.LENGTH_SHORT).show();
                    if (!msgid.equals(msgid_firebase)) {

               //         Toast.makeText(getApplicationContext(), msg_firebase, Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("msgid", msgid_firebase);
                        editor.commit();
                        postNotif(msg_firebase);


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
}
