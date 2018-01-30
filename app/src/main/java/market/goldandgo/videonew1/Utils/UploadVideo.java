package market.goldandgo.videonew1.Utils;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.phoneid;
import market.goldandgo.videonew1.Previewmovie;


/**
 * Created by Go Goal on 7/5/2017.
 */

public class UploadVideo extends AsyncTask<Void, Integer, Boolean> implements UploadProgressListener {

    String title2, detail2, url2, filepath2, category2;
    long imageSize;
    String stringResponse;
    TextView pg;

    public UploadVideo(String title1, String detail1, String url1, String category1, String filepath1, TextView pgtv) {

        title2 = title1;
        detail2 = detail1;
        url2 = url1;
        filepath2 = filepath1;
        category2 = category1;

        File file = new File(filepath2);
        imageSize = file.length();
        imageSize=imageSize/1024;
        if (imageSize < 1){
            imageSize=1;
        }

        pg=pgtv;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (values[0]<101){
            pg.setText(values[0]+" %");
        }

       // progressDialog.setProgress(values[0]);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {

            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
            HttpPost httpPost = new HttpPost(Constant.host+"userupload.php");

            title2 = Encode_Decoder.encoding_string(title2);
            detail2 = Encode_Decoder.encoding_string(detail2);
            url2 = Encode_Decoder.encoding_string(url2);

            StringBody api = new StringBody(Constant.apikey);
            StringBody title = new StringBody(title2);
            StringBody detail = new StringBody(detail2);
            StringBody url = new StringBody(url2);
            StringBody cate = new StringBody(category2);
            StringBody pid = new StringBody(phoneid.getid());


            CustomMultiPartEntity multipartEntity = new CustomMultiPartEntity();

            multipartEntity.addPart("api", api);
            multipartEntity.addPart("pid", pid);
            multipartEntity.addPart("title", title);
            multipartEntity.addPart("detail", detail);
            multipartEntity.addPart("url", url);
            multipartEntity.addPart("car", cate);


            File fil = new File(filepath2);
            FileBody file = new FileBody(fil);
            multipartEntity.addPart("file", file);


            multipartEntity.setUploadProgressListener(this);


            httpPost.setEntity(multipartEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            stringResponse = EntityUtils.toString(httpResponse.getEntity());
            //  Log.e("data from server", stringResponse);


        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return false;

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public void transferred(long num) {
        long dataUploaded = ((num / 1024) * 100) / imageSize;
        this.publishProgress((int) dataUploaded);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        String msg= Jsonparser.getonestring(stringResponse,"msg");
        Previewmovie.feedback(msg);

    }
}
