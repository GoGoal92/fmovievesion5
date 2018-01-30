package market.goldandgo.videonew1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Jsonparser;

/**
 * Created by Go Goal on 5/9/2017.
 */
public class Readmore extends AppCompatActivity{

    static AppCompatActivity ac;
    static Zawgyitextview tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readmore);

        ac = this;

        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv= (Zawgyitextview) findViewById(R.id.vvb);
        String a=getIntent().getExtras().getString("a");
        MyRequest.getreadmore(a);


    }


    public static void Feedback(String s) {

        String detail= Jsonparser.getonestring(s,"detail");
        tv.setText(detail);

    }

    public static void Feedback_Error() {
        ac.finish();
        ac.overridePendingTransition(0, 0);

    }
}
