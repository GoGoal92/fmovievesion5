package market.goldandgo.videonew1.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.File;
import java.util.HashMap;

import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Splash;

/**
 * Created by Go Goal on 2/1/2018.
 */

public class Networkreceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {


        //  Toast.makeText(context,"hello",Toast.LENGTH_SHORT).show();


        //   Toast.makeText(context,"useronline",Toast.LENGTH_SHORT).show();
        context.startService(new Intent(context, Firebaseservcie.class));


    }



}
