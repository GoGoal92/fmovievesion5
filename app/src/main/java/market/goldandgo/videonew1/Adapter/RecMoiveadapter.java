package market.goldandgo.videonew1.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import market.goldandgo.videonew1.Detail;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;

/**
 * Created by Go Goal on 3/1/2018.
 */

public class RecMoiveadapter extends BaseAdapter {

    FragmentActivity ac;
    ArrayList<get> list;
    LayoutInflater in;

    public RecMoiveadapter(FragmentActivity ac1, ArrayList<get> gets) {
        ac=ac1;
        list=gets;
        in= (LayoutInflater) ac.getSystemService(ac.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v=in.inflate(R.layout.mainvideorow,null);


        final ImageView iv= (ImageView) v.findViewById(R.id.iv);
        TextView tv= (TextView) v.findViewById(R.id.moviename);

        tv.setText(list.get(position).getTitle());
        final String fpath = Constant.datalocation_movie + list.get(position).getMid() + ".fmovie";
        File ff=new File(fpath);
        if (ff.exists()){

            ac.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                    Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                    iv.setBackground(myDrawable);
                }
            });

        }else{

            Picasso.with(ac).load(list.get(position).getImage()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    File file = new File(Constant.datalocation_movie + list.get(position).getMid() + ".fmovie");

                    FileOutputStream stream = null;
                    try {
                        stream = new FileOutputStream(file);
                        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outstream);
                        byte[] byteArray = outstream.toByteArray();

                        stream.write(byteArray);
                        stream.close();

                        Drawable myDrawable = new BitmapDrawable(ac.getResources(), bitmap);
                        iv.setBackground(myDrawable);
                        notifyDataSetChanged();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

        }






        return v;
    }

    public void refresh(ArrayList<get> topratedlist) {
        list=topratedlist;
        notifyDataSetChanged();
    }
}