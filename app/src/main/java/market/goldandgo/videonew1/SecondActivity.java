package market.goldandgo.videonew1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.golshadi.majid.core.DownloadManagerPro;
import com.golshadi.majid.report.listener.DownloadManagerListener;

import java.io.IOException;

import market.goldandgo.videonew1.Object.Constant;


/**
 * Created by Go Goal on 1/26/2018.
 */

public class SecondActivity extends AppCompatActivity implements DownloadManagerListener {


    ProgressBar pg;
    Button download, pause, resume, delete,next;
    TextView ppp;

    int taskToken;
    DownloadManagerPro dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec_main);
        pg = (ProgressBar) findViewById(R.id.progressBar);
        download = (Button) findViewById(R.id.download);
        pause = (Button) findViewById(R.id.pause);
        resume = (Button) findViewById(R.id.resume);
        delete = (Button) findViewById(R.id.delete);
        next = (Button) findViewById(R.id.next);
        ppp = (TextView) findViewById(R.id.ppp);


        dm = new DownloadManagerPro(getApplicationContext());
        dm.init(Constant.DM_downloadfolder, 10, this);
        taskToken=getIntent().getExtras().getInt("a");
        try {
            dm.startDownload(taskToken);
        } catch (IOException e) {
            e.printStackTrace();
        }



        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dm.pauseDownload(taskToken);

            }
        });

        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    dm.startDownload(taskToken);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dm.delete(taskToken,true);
                pg.setProgress(0);
                ppp.setText(0+"% / "+0);
            }
        });






    }


    @Override
    public void OnDownloadStarted(long taskId) {
        pg.setProgress(0);
    }

    @Override
    public void OnDownloadPaused(long taskId) {

    }

    @Override
    public void onDownloadProcess(long taskId, double percent, final long downloadedLength) {

        final int proint= (int) percent;
        pg.setProgress(proint);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ppp.setText(pg.getProgress()+" % / "+downloadedLength);

            }
        });



    }

    @Override
    public void OnDownloadFinished(long taskId) {

    }

    @Override
    public void OnDownloadRebuildStart(long taskId) {

    }

    @Override
    public void OnDownloadRebuildFinished(long taskId) {

    }

    @Override
    public void OnDownloadCompleted(long taskId) {

    }

    @Override
    public void connectionLost(long taskId) {

    }
}