package market.goldandgo.videonew1.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.golshadi.majid.core.DownloadManagerPro;
import com.golshadi.majid.core.enums.QueueSort;
import com.golshadi.majid.report.exceptions.QueueDownloadInProgressException;
import com.golshadi.majid.report.listener.DownloadManagerListener;

import java.io.IOException;
import java.util.ArrayList;

import market.goldandgo.videonew1.Mydownloadmanager;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Utils.Mydatabase;

/**
 * Created by Go Goal on 1/28/2018.
 */

public class MydownloadService extends Service {


    static DownloadManagerPro dm;
    public static final String Download_service = "market.goldandgo.videonew1";
    Intent bi = new Intent(Download_service);
    Mydatabase mdb;
    ArrayList<get> list;
    static Context con;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {

      /*  for (int i=0;i<list.size();i++){
            mdb.update_row(list.get(i).getDmid(),"0");
            try{
                dm.pauseDownload(Integer.parseInt(list.get(i).getTasktoken()));
            }catch (Exception e){
            }


        }*/
        Intent background = new Intent(getApplicationContext(), Firebaseservcie.class);
        startService(background);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();


        con = this;
        mdb = new Mydatabase(getApplicationContext());
        list = new ArrayList<>();
        list = mdb.getlist();


        dm = new DownloadManagerPro(getApplicationContext());


        dm.init(Constant.DM_downloadfolder, 10, new DownloadManagerListener() {
            @Override
            public void OnDownloadStarted(long taskId) {

            }

            @Override
            public void OnDownloadPaused(long taskId) {

            }

            @Override
            public void onDownloadProcess(long taskId, double percent, final long downloadedLength) {


                bi.putExtra("taskId", taskId);
                int pp = (int) percent;
                bi.putExtra("mypercent", pp);
                bi.putExtra("status", "downloaing");
                bi.putExtra("downloadedLength", downloadedLength);
                sendBroadcast(bi);
                String dnmae = "fmovie";
                try {
                    dnmae = mdb.getdetailname(taskId);
                } catch (Exception e) {
                    dnmae = "fmovie";

                }

                noti((int) percent, taskId, "downloading", dnmae);


            }

            @Override
            public void OnDownloadFinished(long taskId) {

            }

            @Override
            public void OnDownloadRebuildStart(long taskId) {
                bi.putExtra("taskId", taskId);
                bi.putExtra("mypercent", 99);
                bi.putExtra("status", "rebuilding");
                bi.putExtra("downloadedLength", 0);
                sendBroadcast(bi);

                String dnmae = "fmovie";
                try {
                    dnmae = mdb.getdetailname(taskId);
                } catch (Exception e) {
                    dnmae = "fmovie";

                }

                noti(99, taskId, "rebuilding", dnmae);
            }

            @Override
            public void OnDownloadRebuildFinished(long taskId) {

            }

            @Override
            public void OnDownloadCompleted(long taskId) {
                bi.putExtra("taskId", taskId);
                bi.putExtra("mypercent", 100);
                bi.putExtra("status", "complete");
                bi.putExtra("downloadedLength", 0);
                sendBroadcast(bi);
                mdb.update_complete(taskId);
                String dnmae = "fmovie";
                try {
                    dnmae = mdb.getdetailname(taskId);
                } catch (Exception e) {
                    dnmae = "fmovie";

                }

                noti(100, taskId, "complete", dnmae);

            }

            @Override
            public void connectionLost(long taskId) {
                bi.putExtra("taskId", taskId);
                bi.putExtra("mypercent", 99);
                bi.putExtra("status", "retry");
                bi.putExtra("downloadedLength", 0);
                sendBroadcast(bi);
                dm.pauseDownload((int) taskId);
                String dnmae = "fmovie";
                try {
                    dnmae = mdb.getdetailname(taskId);
                } catch (Exception e) {
                    dnmae = "fmovie";

                }
                noti(0, taskId, "retry", dnmae);


            }
        });


        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getDmstatus().equals("1")) {
                try {

                    dm.startDownload(Integer.parseInt(list.get(i).getTasktoken()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }


    }

    static NotificationManager notificationManager;

    public void noti(int percent, long taskidnoti, String status, String name) {
        Intent intent = new Intent(con, Mydownloadmanager.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent, 0);
        Notification notification = new Notification(R.drawable.appicon,
                "Downloading", System.currentTimeMillis());
        notification.flags = notification.flags
                | Notification.FLAG_ONGOING_EVENT;
        notification.contentView = new RemoteViews(getApplicationContext()
                .getPackageName(), R.layout.upload_progress_bar);
        notification.contentIntent = pendingIntent;

        notification.contentView.setTextViewText(R.id.percent_text,
                percent + " %");
        notification.contentView.setTextViewText(R.id.download_text,
                name);
        notification.contentView.setProgressBar(R.id.progressBar1, 100,
                percent, false);
        getApplicationContext();
        notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) taskidnoti, notification);
        if (status.equals("complete")) {
            notificationManager.cancel((int) taskidnoti);
        }


    }

    public static void deletetask(String tasktoken) {
        int taskid = Integer.parseInt(tasktoken);
        try {
            notificationManager.cancel((int) taskid);
            dm.delete(taskid, true);
        } catch (Exception e) {
            Mydownloadmanager.reload();
        }


    }

    public static void pausetask(String tasktoken) {
        int taskid = Integer.parseInt(tasktoken);
        try {
            dm.pauseDownload(taskid);
        } catch (Exception e) {
            Mydownloadmanager.reload();
        }

    }

    public static void startdownload(String tasktoken) {
        int taskid = Integer.parseInt(tasktoken);
        try {
            dm.startDownload(taskid);
        } catch (Exception e) {
            e.printStackTrace();
            Mydownloadmanager.reload();
        }
    }
}
