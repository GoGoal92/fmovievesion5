package market.goldandgo.videonew1.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Go Goal on 2/1/2018.
 */

public class rebotreceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {


      //  Toast.makeText(context,"hello",Toast.LENGTH_SHORT).show();


            context.startService(new Intent(context,Firebaseservcie.class));


    }



}
