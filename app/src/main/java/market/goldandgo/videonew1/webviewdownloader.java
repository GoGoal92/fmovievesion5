package market.goldandgo.videonew1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.wang.avi.AVLoadingIndicatorView;


/**
 * Created by Go Goal on 5/26/2017.
 */

public class webviewdownloader extends AppCompatActivity {


    WebView wb;
    AVLoadingIndicatorView pg;
    String url, clas,imgpath;
    static AppCompatActivity ac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mywebview);
        ac = this;

        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        url = getIntent().getExtras().getString("url");
        clas = getIntent().getExtras().getString("cla");
        imgpath = getIntent().getExtras().getString("img");
        wb = (WebView) findViewById(R.id.mywebview);
        pg = (AVLoadingIndicatorView) findViewById(R.id.pg);
        pg.show();

        //wb.requestFocus();
        //wb.requestFocusFromTouch();

        wb.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pg.setVisibility(View.GONE);
                wb.setVisibility(View.VISIBLE);
                pg.hide();

                wb.loadUrl("javascript:(function() { "
                        + "var el = document.querySelectorAll('div[data-sigil]');"
                        + "for(var i=0;i<el.length; i++)"
                        + "{"
                        + "var sigil = el[i].dataset.sigil;"
                        + "if(sigil.indexOf('inlineVideo') > -1){"
                        + "delete el[i].dataset.sigil;"
                        + "var jsonData = JSON.parse(el[i].dataset.store);"
                        + "el[i].setAttribute('onClick', 'webviewdownloader.processVideo(\"'+jsonData['src']+'\");');"
                        + "}" + "}" + "})()");


            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);

                wb.loadUrl("javascript:(function prepareVideo() { "
                        + "var el = document.querySelectorAll('div[data-sigil]');"
                        + "for(var i=0;i<el.length; i++)"
                        + "{"
                        + "var sigil = el[i].dataset.sigil;"
                        + "if(sigil.indexOf('inlineVideo') > -1){"
                        + "delete el[i].dataset.sigil;"
                        + "console.log(i);"
                        + "var jsonData = JSON.parse(el[i].dataset.store);"
                        +"var someObjStr = JSON.stringify(jsonData);"
                        + "console.log( someObjStr  );"
                        + "el[i].setAttribute('onClick', 'webviewdownloader.processVideo(\"'+jsonData['src']+'\",\"'+jsonData['videoID']+'\");');"
                        + "}" + "}" + "})()");
                wb.loadUrl("javascript:( window.onload=prepareVideo;"
                        + ")()");


            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                pg.setVisibility(View.GONE);
                pg.hide();
                if (errorCode == -2) {
                    ac.finish();
                }

            }
        });


        wb.addJavascriptInterface(ac, "webviewdownloader");
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        CookieSyncManager.getInstance().startSync();

        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setDomStorageEnabled(true);
        wb.loadUrl(url);


    }


    @JavascriptInterface
    public void processVideo(final String vidData, final String vidID) {


   //     Log.e("Video",vidData);
     //   Log.e("JSON",vidID);

        if (clas.equals("mdv")){
            Intent newintent = new Intent(ac, Choicetoview.class);
            newintent.putExtra("url", vidData);
            newintent.putExtra("type", clas);
            newintent.putExtra("vid", vidID);
            newintent.putExtra("img", imgpath);
            newintent.putExtra("name", getIntent().getExtras().getString("name") );
            startActivity(newintent);
            overridePendingTransition(0, 0);

        }else{
            Intent newintent = new Intent(ac, FakeDialog.class);
            newintent.putExtra("url", vidData);
            newintent.putExtra("type", clas);
            newintent.putExtra("vid", vidID);
            newintent.putExtra("img", imgpath);
            startActivity(newintent);
            overridePendingTransition(0, 0);
        }

       // finish();

    }



    public static void finishactivity() {
        ac.finish();
    }
}
