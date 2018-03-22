package market.goldandgo.videonew1.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.entity.AppInfo;
import market.goldandgo.videonew1.listener.OnItemClickListener;

/**
 * Created by Aspsine on 2015/7/8.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AppInfo> mAppInfos;

    private OnItemClickListener mListener;
    AppCompatActivity ac;

    public RecyclerViewAdapter(AppCompatActivity ac1) {
        this.mAppInfos = new ArrayList<AppInfo>();
        ac=ac1;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setData(List<AppInfo> appInfos) {
        //this.mAppInfos.clear();
        mAppInfos=appInfos;
        //this.mAppInfos.addAll(appInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent,false);
        final AppViewHolder holder = new AppViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(v.getContext(), AppDetailActivity.class);
                intent.putExtra("EXTRA_APPINFO", mAppInfos.get(holder.getLayoutPosition()));
                v.getContext().startActivity(intent);*/
            }
        });



        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindData((AppViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return mAppInfos.size();
    }

    private void bindData(AppViewHolder holder, final int position) {
        final AppInfo appInfo = mAppInfos.get(position);
        holder.tvName.setText(appInfo.getName());
        holder.tvDownloadPerSize.setText(appInfo.getDownloadPerSize());
        holder.tvstatus.setText(appInfo.getStatusText());
        holder.progressBar.setProgress(appInfo.getProgress());
        holder.btnDownload.setText(appInfo.getButtonText());

        Bitmap myBitmap = BitmapFactory.decodeFile(appInfo.getImage());
        Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
        holder.ivIcon.setBackground(myDrawable);


        Picasso.with(holder.itemView.getContext()).load(appInfo.getImage()).into(holder.ivIcon);
        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position, appInfo);
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position, appInfo);
                }
            }
        });

        if (mAppInfos.get(position).getStatus()==AppInfo.STATUS_INSTALLED){
            holder.hide.setVisibility(View.GONE);
        }

    }

    public static final class AppViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivIcon)
        public ImageView ivIcon;

        @Bind(R.id.tvName)
        public TextView tvName;

        @Bind(R.id.btnDownload)
        public Button btnDownload;

        @Bind(R.id.tvDownloadPerSize)
        public TextView tvDownloadPerSize;

        @Bind(R.id.tvstatus)
        public TextView tvstatus;

        @Bind(R.id.remove)
        public Button remove;

        @Bind(R.id.progressBar)
        public ProgressBar progressBar;

        @Bind(R.id.hiderl)
        public RelativeLayout hide;

        public AppViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
