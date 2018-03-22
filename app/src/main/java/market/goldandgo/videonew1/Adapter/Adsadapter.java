package market.goldandgo.videonew1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;

import static market.goldandgo.videonew1.R.id.iv;


public class Adsadapter extends PagerAdapter {
    LayoutInflater mLayoutInflater;
    Context con;
    Activity ac;
    int[] color = new int[]{Color.BLACK, Color.GRAY, Color.CYAN, Color.GREEN, Color.BLACK};
    ArrayList<get> list;

    public Adsadapter(Activity ac, ArrayList<get> list1) {
        // TODO Auto-generated constructor stub

        list = list1;
        con = ac;
        this.ac = ac;
        mLayoutInflater = (LayoutInflater) con
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 360;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.home_pager_layout,
                container, false);

        final ImageView back = (ImageView) itemView.findViewById(R.id.back);


        final String fpath=Constant.datalocation_ads + list.get(position%5).getAdsid() + ".fmovie";
        File ff=new File(fpath);
        if (ff.exists()){

            ac.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Bitmap myBitmap = BitmapFactory.decodeFile(fpath);
                    Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                    back.setBackground(myDrawable);
                }
            });

        }else{

            Picasso.with(ac).load(Constant.adsurl+list.get(position%5).getAdsid()+".png").into(new Target(){

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    back.setBackground(new BitmapDrawable(ac.getResources(), bitmap));
                    File file = new File(Constant.datalocation_ads + list.get(position%5).getAdsid() + ".fmovie");

                    FileOutputStream stream = null;
                    try {
                        stream = new FileOutputStream(file);
                        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outstream);
                        byte[] byteArray = outstream.toByteArray();

                        stream.write(byteArray);
                        stream.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onBitmapFailed(final Drawable errorDrawable) {
                    Log.d("TAG", "FAILED");
                }

                @Override
                public void onPrepareLoad(final Drawable placeHolderDrawable) {
                    Log.d("TAG", "Prepare Load");
                }
            });


        }





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse(list.get(position%5).getAdsurl()));
                ac.startActivity(intent);
            }
        });


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    public void refresh(ArrayList<get> list1) {
        list=list1;
        notifyDataSetChanged();
    }


    public void refresh1() {
        notifyDataSetChanged();
    }
}
