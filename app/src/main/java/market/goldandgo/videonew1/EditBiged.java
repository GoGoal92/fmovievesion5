package market.goldandgo.videonew1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Go Goal on 7/4/2017.
 */

public class EditBiged extends AppCompatActivity {

    EditText ed;
    TextView done;
    String type, text;
    String detail = "ရုပ္ရွင္ အညႊန္း (သို႔) ရုပ္ရွင္ အေၾကာင္း";
    String title = "ရုပ္ရွင္ အမည္";
    String url="ြFacebook ရုပ္ရွင္ Link";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biged);
        ed = (EditText) findViewById(R.id.ed);
        done = (TextView) findViewById(R.id.upload);

        ed.requestFocus();
        ed.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(ed, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 1000);


        text = getIntent().getExtras().getString("text");
        type = getIntent().getExtras().getString("type");

        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (type.equals("title")) {

            ed.setHint(title);

        } else if (type.equals("detail")){

            ed.setHint(detail);
        }else{
            ed.setHint(url);
        }


        if (!text.equals(title) && !text.equals(detail)&& !text.equals(url)){

            ed.setText(text);

        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed.getText().length() < 1) {
                    Toast.makeText(getApplicationContext(), "Need To Add Somewords", Toast.LENGTH_SHORT).show();
                } else {
                    if (type.equals("title")) {

                        EditMovies.settitle(ed.getText().toString());

                    } else if (type.equals("detail")){

                        EditMovies.setdetail(ed.getText().toString());
                    }else{
                        EditMovies.seturl(ed.getText().toString());
                    }
                    finish();
                }

            }
        });


    }
}
