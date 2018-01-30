package market.goldandgo.videonew1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;


/**
 * Created by Go Goal on 2/13/2017.
 */

public class Spinneradapter1 extends ArrayAdapter {

    Context con;
    ArrayList<get> list;

    public Spinneradapter1(Context context, ArrayList<get> list1) {
        super(context, 0);
        con = context;
        list = list1;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.one_text_row_cate, parent,
                false);
        TextView make = (TextView) row.findViewById(R.id.ttv);

        make.setText(list.get(position).getTitle());
        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.one_text_row_cate, parent,
                false);
        TextView make = (TextView) row.findViewById(R.id.ttv);

        make.setText(list.get(position).getTitle());
        return row;
    }

    public void refresh(ArrayList<get> clist) {
        list = clist;
        notifyDataSetChanged();
    }
}
