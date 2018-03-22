package market.goldandgo.videonew1.Adapter;

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

import java.io.File;
import java.util.ArrayList;

import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;

/**
 * Created by Go Goal on 3/1/2018.
 */

public class TopSeriesadapter extends BaseAdapter {

    FragmentActivity ac;
    ArrayList<get> list;
    LayoutInflater in;

    public TopSeriesadapter(FragmentActivity ac1, ArrayList<get> gets) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=in.inflate(R.layout.mainvideorow,null);


        final ImageView iv= (ImageView) v.findViewById(R.id.iv);
        TextView tv= (TextView) v.findViewById(R.id.moviename);

        tv.setText(list.get(position).getTitle());
        final String fpath = Constant.datalocation_scover + list.get(position).getMid() + ".fmovie";

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

        }





        return v;
    }

    public void refresh(ArrayList<get> topratedlist) {
        list=topratedlist;
        notifyDataSetChanged();
    }
}