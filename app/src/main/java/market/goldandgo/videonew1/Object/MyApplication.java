package market.goldandgo.videonew1.Object;

/**
 * Created by Go Goal on 12/16/2016.
 */

import android.app.Application;
import android.content.Context;

import com.aspsine.multithreaddownload.DownloadConfiguration;
import com.aspsine.multithreaddownload.DownloadManager;
import com.firebase.client.Firebase;

import market.goldandgo.videonew1.Utils.CrashHandler;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    private static Context sContext;

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

        sContext = getApplicationContext();
        initDownloader();
        CrashHandler.getInstance(sContext);

    }

    private void initDownloader() {
        DownloadConfiguration configuration = new DownloadConfiguration();
        configuration.setMaxThreadNum(10);
        configuration.setThreadNum(3);
        DownloadManager.getInstance().init(getApplicationContext(), configuration);
    }

    public static Context getContext() {
        return sContext;
    }

}