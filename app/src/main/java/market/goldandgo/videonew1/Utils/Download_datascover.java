package market.goldandgo.videonew1.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import market.goldandgo.videonew1.Object.get;

/**
 * Created by Go Goal on 9/25/2017.
 */

public class Download_datascover {

    static Activity ac;
    static ArrayList<get> list;

    public Download_datascover(FragmentActivity ac1, ArrayList<get> listi) {
        ac = ac1;
        list=listi;

    }

    static int count;

    public void startdownloadimg() {
        count=0;
        new downloadfileforscover().execute();
    }


    static class downloadfileforscover extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap bmp = null;
            URL url = null;
            try {
                url = new URL(list.get(count).getImage());
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                File file = new File(list.get(count).getImagesdpath());

                FileOutputStream stream = null;
                try {
                    stream = new FileOutputStream(file);
                    ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outstream);
                    byte[] byteArray = outstream.toByteArray();

                    stream.write(byteArray);
                    stream.close();



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);


            Bitmap myBitmap = BitmapFactory.decodeFile(list.get(count).getImagesdpath());
            Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
            list.get(count).getBackrl().setBackground(myDrawable);
            list.get(count).getImgpg().setVisibility(View.GONE);
            count++;

            if (count<list.size()){
                new downloadfileforscover().execute();
            }


        }
    }
}
