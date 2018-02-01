package market.goldandgo.videonew1.Object;

/**
 * Created by Go Goal on 12/16/2016.
 */

import android.app.Application;

import com.firebase.client.Firebase;

public class MyApplication extends Application{

    public MyApplication() {
    }



    @Override
    public void onCreate() {
        super.onCreate();
        //Initializing firebase
        Firebase.setAndroidContext(getApplicationContext());

    }
}