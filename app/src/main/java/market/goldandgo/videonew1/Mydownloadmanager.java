package market.goldandgo.videonew1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.golshadi.majid.core.DownloadManagerPro;

import java.util.ArrayList;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Adapter.Downloadadapter;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Downloadlist;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Utils.Mydatabase;
import market.goldandgo.videonew1.service.MydownloadService;


/**
 * Created by Go Goal on 5/24/2017.
 */

public class Mydownloadmanager extends AppCompatActivity implements View.OnClickListener {

    static ArrayList<get> list, comlist;

    static AppCompatActivity ac;

    RecyclerView rv;
    LinearLayoutManager llm;
    static Downloadadapter adpater;
    EditText ed;
    Button donbtn;
    static Mydatabase mdb;
    static RelativeLayout[] main = new RelativeLayout[5];
    static TextView[] name = new TextView[5];
    static TextView[] tpercent = new TextView[5];
    static TextView[] tsize = new TextView[5];
    static TextView[] tstatus = new TextView[5];
    static ProgressBar[] pg = new ProgressBar[5];
    static ImageView[] closebtn = new ImageView[5];
    static ImageView[] play_pause = new ImageView[5];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloadmgr);

        ac = this;
        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initl();


        rv = (RecyclerView) findViewById(R.id.rv);
        ed = (EditText) findViewById(R.id.ed);
        donbtn = (Button) findViewById(R.id.btn);


        donbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ed.getText().length() < 1) {

                    Toast.makeText(getApplicationContext(), "PLease Enter Your Download Video Link", Toast.LENGTH_SHORT).show();

                } else {

                    ArrayList<get> ddlist=new ArrayList<get>();
                    ddlist=mdb.getlist();
                    Log.e("ddlistsize",ddlist.size()+"");
                    if (ddlist.size()>4){

                        AlertDialog.Builder ab = new AlertDialog.Builder(ac);
                        ab.setTitle("ERROR");
                        View vc = ac.getLayoutInflater().inflate(R.layout.ontextzawwgyirow, null);
                        ab.setView(vc);
                        Zawgyitextview tv = (Zawgyitextview) vc.findViewById(R.id.ttv);
                        tv.setText("Maximum size of fmovie download manager is 5 . Cant download over 5 task at once.\n" +
                                "\n" +
                                "FMOVIE download manager ၏ တစ္ၾကိမ္တည္း အမ်ားဆံုး Download လုပ္ယူႏိုင္စြမ္းမွာ ၅ခုသာ ျဖစ္သည္။\n");

                        ab.setPositiveButton("Okay",null);

                        ab.setCancelable(false);
                        ab.show();


                    }else{
                        Intent it = new Intent(ac, webviewdownloader.class);
                        it.putExtra("url", ed.getText().toString());
                        it.putExtra("cla", "dm");
                        ac.startActivity(it);
                        ed.setText("");
                    }


                }


            }
        });


        mdb = new Mydatabase(ac);
        list = new ArrayList<>();
        comlist = new ArrayList<>();
        list = mdb.getlist();
        comlist = mdb.getcompletelist();

        for (int i = 0; i < list.size(); i++) {

            main[i].setVisibility(View.VISIBLE);
            name[i].setText(list.get(i).getDmname());
            if (list.get(i).getDmstatus().equals("1")) {
                play_pause[i].setBackgroundResource(R.drawable.pause);
            } else {
                play_pause[i].setBackgroundResource(R.drawable.play);
            }
            play_pause[i].setOnClickListener(this);
            closebtn[i].setOnClickListener(this);


        }

        stopService(new Intent(ac, MydownloadService.class));
        startService(new Intent(ac, MydownloadService.class));



        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(Mydownloadmanager.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        adpater=new Downloadadapter(ac,comlist);
        rv.setAdapter(adpater);

    }

    private void initl() {

        closebtn[0] = (ImageView) findViewById(R.id.close1);
        closebtn[1] = (ImageView) findViewById(R.id.close2);
        closebtn[2] = (ImageView) findViewById(R.id.close3);
        closebtn[3] = (ImageView) findViewById(R.id.close4);
        closebtn[4] = (ImageView) findViewById(R.id.close5);

        play_pause[0] = (ImageView) findViewById(R.id.play_pause1);
        play_pause[1] = (ImageView) findViewById(R.id.play_pause2);
        play_pause[2] = (ImageView) findViewById(R.id.play_pause3);
        play_pause[3] = (ImageView) findViewById(R.id.play_pause4);
        play_pause[4] = (ImageView) findViewById(R.id.play_pause5);

        main[0] = (RelativeLayout) findViewById(R.id.main1);
        main[1] = (RelativeLayout) findViewById(R.id.main2);
        main[2] = (RelativeLayout) findViewById(R.id.main3);
        main[3] = (RelativeLayout) findViewById(R.id.main4);
        main[4] = (RelativeLayout) findViewById(R.id.main5);

        name[0] = (TextView) findViewById(R.id.name1);
        name[1] = (TextView) findViewById(R.id.name2);
        name[2] = (TextView) findViewById(R.id.name3);
        name[3] = (TextView) findViewById(R.id.name4);
        name[4] = (TextView) findViewById(R.id.name5);

        tsize[0] = (TextView) findViewById(R.id.sizedown1);
        tsize[1] = (TextView) findViewById(R.id.sizedown2);
        tsize[2] = (TextView) findViewById(R.id.sizedown3);
        tsize[3] = (TextView) findViewById(R.id.sizedown4);
        tsize[4] = (TextView) findViewById(R.id.sizedown5);

        tstatus[0] = (TextView) findViewById(R.id.dlstatus1);
        tstatus[1] = (TextView) findViewById(R.id.dlstatus2);
        tstatus[2] = (TextView) findViewById(R.id.dlstatus3);
        tstatus[3] = (TextView) findViewById(R.id.dlstatus4);
        tstatus[4] = (TextView) findViewById(R.id.dlstatus5);

        tpercent[0] = (TextView) findViewById(R.id.percent1);
        tpercent[1] = (TextView) findViewById(R.id.percent2);
        tpercent[2] = (TextView) findViewById(R.id.percent3);
        tpercent[3] = (TextView) findViewById(R.id.percent4);
        tpercent[4] = (TextView) findViewById(R.id.percent5);

        pg[0] = (ProgressBar) findViewById(R.id.progressBar1);
        pg[1] = (ProgressBar) findViewById(R.id.progressBar2);
        pg[2] = (ProgressBar) findViewById(R.id.progressBar3);
        pg[3] = (ProgressBar) findViewById(R.id.progressBar4);
        pg[4] = (ProgressBar) findViewById(R.id.progressBar5);


    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(MydownloadService.Download_service));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {

            long taskid = intent.getLongExtra("taskId", 0);
            int percent = intent.getIntExtra("mypercent", 0);
            long downloadedLength = intent.getLongExtra("downloadedLength", 0);
            String status = intent.getStringExtra("status");

            for (int i = 0; i < list.size(); i++) {
                int dbtask = Integer.parseInt(list.get(i).getTasktoken());
                if (taskid == dbtask) {

                    pg[i].setProgress(percent);
                    tsize[i].setText(Constant.getmb(downloadedLength) + " / " + list.get(i).getDmsize());
                    tstatus[i].setText(status);
                    tpercent[i].setText(percent + " %");
                    if (status.equals("retry")) {

                        play_pause[i].setBackgroundResource(R.drawable.play);
                        list.get(i).setDmstatus("0");
                        mdb.update_row(list.get(i).getDmid(), "0");


                    }

                    if (status.equals("complete")) {
                        tsize[i].setText(list.get(i).getDmsize() + " / " + list.get(i).getDmsize());
                        tpercent[i].setText("100 %");
                        main[i].setVisibility(View.GONE);
                        mdb.update_row(list.get(i).getDmid(), "com");
                        list.get(i).setDmstatus("com");
                        stopService(new Intent(ac, MydownloadService.class));
                        startService(new Intent(ac, MydownloadService.class));
                        comlist=mdb.getcompletelist();
                        adpater.refresh(comlist);
                    }


                }

            }


        }
    }


    @Override
    public void onClick(View v) {
        //  stopService(new Intent(ac,MydownloadService.class));
        //  startService(new Intent(ac,MydownloadService.class));
        if (v == play_pause[0]) {

            String tasktt = list.get(0).getTasktoken();
            String dmstatus = list.get(0).getDmstatus();
            String dmid = list.get(0).getDmid();
            Playpause(tasktt, dmstatus, dmid, 0);

        }
        if (v == play_pause[1]) {
            String tasktt = list.get(1).getTasktoken();
            String dmstatus = list.get(1).getDmstatus();
            String dmid = list.get(1).getDmid();
            Playpause(tasktt, dmstatus, dmid, 1);

        }
        if (v == play_pause[2]) {
            String tasktt = list.get(2).getTasktoken();
            String dmstatus = list.get(2).getDmstatus();
            String dmid = list.get(2).getDmid();
            Playpause(tasktt, dmstatus, dmid, 2);

        }
        if (v == play_pause[3]) {
            String tasktt = list.get(3).getTasktoken();
            String dmstatus = list.get(3).getDmstatus();
            String dmid = list.get(3).getDmid();
            Playpause(tasktt, dmstatus, dmid, 3);

        }
        if (v == play_pause[4]) {

            String tasktt = list.get(4).getTasktoken();
            String dmstatus = list.get(4).getDmstatus();
            String dmid = list.get(4).getDmid();
            Playpause(tasktt, dmstatus, dmid, 4);
        }

        if (v == closebtn[0]) {

            MydownloadService.deletetask(list.get(0).getTasktoken());
            mdb.delete_row(list.get(0).getDmid());
            main[0].setVisibility(View.GONE);
        }

        if (v == closebtn[1]) {

            MydownloadService.deletetask(list.get(1).getTasktoken());
            mdb.delete_row(list.get(1).getDmid());
            main[1].setVisibility(View.GONE);
        }
        if (v == closebtn[2]) {
            MydownloadService.deletetask(list.get(2).getTasktoken());
            mdb.delete_row(list.get(2).getDmid());
            main[2].setVisibility(View.GONE);

        }
        if (v == closebtn[3]) {

            MydownloadService.deletetask(list.get(3).getTasktoken());
            mdb.delete_row(list.get(3).getDmid());
            main[3].setVisibility(View.GONE);
        }
        if (v == closebtn[4]) {

            MydownloadService.deletetask(list.get(4).getTasktoken());
            mdb.delete_row(list.get(4).getDmid());
            main[4].setVisibility(View.GONE);
        }

    }

    private void Playpause(String tasktt, String dmstatus, String dmid, int i) {
        if (dmstatus.equals("1")) {
            MydownloadService.pausetask(tasktt);
            play_pause[i].setBackgroundResource(R.drawable.play);
            tstatus[i].setText("Pause");
            mdb.update_row(dmid, "0");
            list.get(i).setDmstatus("0");
        } else {
            MydownloadService.startdownload(tasktt);
            play_pause[i].setBackgroundResource(R.drawable.pause);
            tstatus[i].setText("Waiting Network");
            mdb.update_row(dmid, "1");
            list.get(i).setDmstatus("1");
        }


    }

    public static void reload() {
        try {
            ac.stopService(new Intent(ac, MydownloadService.class));
            ac.startService(new Intent(ac, MydownloadService.class));
        } catch (Exception e) {

        }

    }

    public static void finishactivity() {
        ac.finish();
    }
}
