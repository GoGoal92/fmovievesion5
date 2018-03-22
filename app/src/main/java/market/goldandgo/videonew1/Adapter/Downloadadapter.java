package market.goldandgo.videonew1.Adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;

import br.com.bemobi.medescope.Medescope;
import br.com.bemobi.medescope.callback.DownloadStatusCallback;
import market.goldandgo.videonew1.Object.Downloadlist;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.google.android.gms.internal.zzahn.runOnUiThread;

/**
 * Created by Go Goal on 12/3/2016.
 */
public class Downloadadapter extends RecyclerView.Adapter<Downloadadapter.ContactViewHolder> {

    static Activity ac;
    static ArrayList<get> list;
    Medescope mMedescope;
    DownloadManager downloadManager;
    public Downloadadapter(Activity getactivity, ArrayList<get> list1) {

        list = list1;

        ac = getactivity;

        downloadManager = (DownloadManager)ac.getSystemService(DOWNLOAD_SERVICE);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dm_progress_row, parent, false));
    }


    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {


        holder.mtitle.setText(list.get(position).getDmname());
        holder.mclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                downloadManager.remove(Integer.parseInt(list.get(position).getTasktoken()));
            }
        });



        new Thread(new Runnable() {

            @Override
            public void run() {

                boolean downloading = true;

                while (downloading) {

                    int downloadid=Integer.parseInt(list.get(position).getTasktoken());
                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadid);

                    Cursor cursor = downloadManager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                    }

                    final double dl_progress = (bytes_downloaded / bytes_total) * 100;

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            holder.pg.setProgress((int) dl_progress);

                        }
                    });

                    cursor.close();
                }

            }
        }).start();








    }

    private void stopdownload(String s) {
        mMedescope.cancel(s);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        public TextView mtitle,mprogress;
        public ImageView mclose;
        public ProgressBar pg;

        public ContactViewHolder(View v) {
            super(v);

            mtitle= (TextView) v.findViewById(R.id.textView3);
            mprogress= (TextView) v.findViewById(R.id.percent);
            mclose= (ImageView) v.findViewById(R.id.close);
            pg= (ProgressBar) v.findViewById(R.id.progressBar2);



        }

    }
}
