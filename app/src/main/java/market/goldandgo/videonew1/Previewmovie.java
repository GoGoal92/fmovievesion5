package market.goldandgo.videonew1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Object.Phonesize;
import market.goldandgo.videonew1.Utils.Myalertdialog;
import market.goldandgo.videonew1.Utils.UploadVideo;


/**
 * Created by Go Goal on 7/5/2017.
 */

public class Previewmovie extends AppCompatActivity{


    static Zawgyitextview mdetial;
    static TextView upload,pgtv;
    Boolean uploadcon=false;
    static ImageView iv;
    static RelativeLayout[] rl = new RelativeLayout[3];
    static RelativeLayout pgrl;
    String title,detail,url,filepath,category;
    static AppCompatActivity ac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);

        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ac=this;

        TextView toolbarname = (TextView) findViewById(R.id.toolbartitle);
        iv = (ImageView) findViewById(R.id.iv);
        mdetial = (Zawgyitextview) findViewById(R.id.mdetail);
        upload= (TextView) findViewById(R.id.upload);
        rl[0] = (RelativeLayout) findViewById(R.id.likerl);
        rl[1] = (RelativeLayout) findViewById(R.id.viewrl);
        rl[2] = (RelativeLayout) findViewById(R.id.downloadrl);

        pgrl = (RelativeLayout) findViewById(R.id.pgrl);
        pgtv = (TextView) findViewById(R.id.pgtv);

        title=getIntent().getExtras().getString("a");
        detail=getIntent().getExtras().getString("b");
        url=getIntent().getExtras().getString("c");
        filepath=getIntent().getExtras().getString("d");
        category=getIntent().getExtras().getString("e");

        toolbarname.setText(title);
        mdetial.setText(detail);

        Bitmap b= BitmapFactory.decodeFile(filepath);
        BitmapDrawable ob = new BitmapDrawable(getResources(), b);
        iv.setBackgroundDrawable(ob);

        int pwidth = Phonesize.getwidth();

        for (int i = 0; i < rl.length; i++) {

            ViewGroup.LayoutParams robot_speechsize = rl[i].getLayoutParams();
            robot_speechsize.width = (pwidth / 3) - 30;
            rl[i].setLayoutParams(robot_speechsize);

        }



        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse(url));
                startActivity(intent);
                uploadcon=true;
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadcon){

                    upload.setEnabled(false);
                    pgrl.setVisibility(View.VISIBLE);
                    new UploadVideo(title,detail,url,category,filepath,pgtv).execute();

                }else{
                    Toast.makeText(getApplicationContext(),"Please Test Your Video Link",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public static void feedback(String msg) {
        upload.setEnabled(true);
        pgrl.setVisibility(View.GONE);
        Myalertdialog.showresultupload(ac,msg);
        Upload.clost();
    }
}
