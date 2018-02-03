package market.goldandgo.videonew1.Object;

/**
 * Created by Go Goal on 12/16/2016.
 */

import android.app.Application;

import com.firebase.client.Firebase;

public class MyApplication extends Application {
    private static MyApplication mInstance;

    public MyApplication() {
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Initializing firebase
        Firebase.setAndroidContext(getApplicationContext());
        mInstance = this;


    }
}