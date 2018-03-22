package market.goldandgo.videonew1.Adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;

/**
 * Created by Go Goal on 2/28/2018.
 */

public class Recentadapter extends BaseAdapter {

    FragmentActivity ac;
    ArrayList<get> list;
    LayoutInflater in;

    public Recentadapter(FragmentActivity ac1, ArrayList<get> gets) {
        ac=ac1;
        list=gets;
        in= (LayoutInflater) ac.getSystemService(ac.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 5;
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

        return v;
    }
}
