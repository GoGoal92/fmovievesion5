package market.goldandgo.videonew1.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.golshadi.majid.core.DownloadManagerPro;
import com.golshadi.majid.report.listener.DownloadManagerListener;

import java.io.IOException;
import java.util.ArrayList;

import market.goldandgo.videonew1.Mydownloadmanager;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.Downloadlist;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Utils.Mydatabase;

/**
 * Created by Go Goal on 12/3/2016.
 */
public class Downloadadapter extends RecyclerView.Adapter<Downloadadapter.ContactViewHolder> {

    static Activity ac;
    static ArrayList<get> list;
    Mydatabase mdb;

    public Downloadadapter(Activity getactivity, ArrayList<get> list1) {

        mdb = new Mydatabase(getactivity);
        list = new ArrayList<>();
        list = list1;
        ac = getactivity;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dm_progress_row, parent, false));
    }


    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {


        holder.tsize.setText(list.get(position).getDmsize()+" / "+list.get(position).getDmsize());
        holder.mtitle.setText(list.get(position).getDmname());
        holder.pg.setProgress(100);

        holder.mclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdb.delete_row(list.get(position).getDmid());
                list=mdb.getcompletelist();
                notifyDataSetChanged();
              //  Mydownloadmanager.deletedownload(position);
            }
        });



    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void refresh(ArrayList<get> list1) {
        list = list1;
        notifyDataSetChanged();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        public TextView mtitle,  tsize;
        public ImageView mclose;

        public ProgressBar pg;

        public ContactViewHolder(View v) {
            super(v);

            mtitle = (TextView) v.findViewById(R.id.textView3);
            tsize = (TextView) v.findViewById(R.id.sizedown);
            mclose = (ImageView) v.findViewById(R.id.close);
            pg = (ProgressBar) v.findViewById(R.id.progressBar2);


        }

    }


}
