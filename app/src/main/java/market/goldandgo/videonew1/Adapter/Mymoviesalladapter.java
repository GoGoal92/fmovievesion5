package market.goldandgo.videonew1.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Detail;
import market.goldandgo.videonew1.EditMovies;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Object.ratio;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Utils.HFRecyclerView;


/**
 * Created by Go Goal on 6/29/2017.
 */

public class Mymoviesalladapter extends HFRecyclerView<get> {


    ArrayList<get> list;
    Activity ac;


    public Mymoviesalladapter(Activity getactivity, ArrayList<get> asklist1) {
        super(asklist1, true, true);
        list = asklist1;
        ac = getactivity;

    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.seeallseriesrow, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.nothing, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return new FooterViewHolder(inflater.inflate(R.layout.footer_pg, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ItemViewHolder) {
            final ItemViewHolder holder1 = (ItemViewHolder) holder;

            holder1.likes.setText(list.get(i - 1).getLike() + " Like");
            holder1.views.setText(list.get(i - 1).getView() + " View");
            holder1.epis.setText(list.get(i - 1).getCategory());

            holder1.detail.setText(Html.fromHtml("<b>"+list.get(i - 1).getTitle() + "</b><br><br>" + list.get(i - 1).getDetail()));


            Bitmap myBitmap = BitmapFactory.decodeFile(Constant.datalocation_movie + list.get(i - 1).getMid() + ".fmovie");
            Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
            holder1.movieimage.setBackground(myDrawable);

            holder1.seeallclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it=new Intent(ac,EditMovies.class);
                    it.putExtra("mid",list.get(i-1).getMid());
                    it.putExtra("mname",list.get(i-1).getTitle());
                    it.putExtra("image",list.get(i-1).getImage());
                    it.putExtra("url",list.get(i-1).getMurl());
                    it.putExtra("detail",list.get(i-1).getDetail());
                    it.putExtra("cate",list.get(i-1).getCategory());
                    ac.startActivity(it);

                }
            });


        } else if (holder instanceof FooterViewHolder) {

            FooterViewHolder footholder = (FooterViewHolder) holder;
            if (showpg) {
                footholder.progressBar.setVisibility(View.VISIBLE);
                footholder.progressBar.show();
            } else {
                footholder.progressBar.setVisibility(View.GONE);
                footholder.progressBar.hide();
            }


        }
    }

    static boolean showpg = false;

    @Override
    public int getItemCount() {
        return list.size() + 2;
    }

    public void load_pg(boolean b) {
        showpg = b;
        notifyDataSetChanged();
    }

    public void refresh(ArrayList<get> list1) {
        list = list1;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public Zawgyitextview likes, views, epis, prices, detail;
        public ImageView movieimage;
        public ProgressBar pg;
        public LinearLayout seeallclick;
        public RelativeLayout rowivrl;
        public RelativeLayout rowtvrl[]=new RelativeLayout[4];

        public ItemViewHolder(View v) {
            super(v);

            likes = (Zawgyitextview) v.findViewById(R.id.likeseries);
            views = (Zawgyitextview) v.findViewById(R.id.viewseries);
            epis = (Zawgyitextview) v.findViewById(R.id.episodeseries);
            prices = (Zawgyitextview) v.findViewById(R.id.priceseries);
            detail = (Zawgyitextview) v.findViewById(R.id.detail);

            movieimage = (ImageView) v.findViewById(R.id.iv);
            seeallclick = (LinearLayout) v.findViewById(R.id.seeallclick);
            rowivrl= (RelativeLayout) v.findViewById(R.id.rowiv_rl);
            rowtvrl[0]= (RelativeLayout) v.findViewById(R.id.rowtv_rl1);
            rowtvrl[1]= (RelativeLayout) v.findViewById(R.id.rowtv_rl2);
            rowtvrl[2]= (RelativeLayout) v.findViewById(R.id.rowtv_rl3);
            rowtvrl[3]= (RelativeLayout) v.findViewById(R.id.rowtv_rl4);

            ViewGroup.LayoutParams robot_speechsize = rowivrl.getLayoutParams();
            robot_speechsize.width = ratio.getwidth(487);
            robot_speechsize.height = ratio.gethetight(314);
            rowivrl.setLayoutParams(robot_speechsize);

            for (int i=0;i<rowtvrl.length;i++){
                ViewGroup.LayoutParams rr = rowtvrl[i].getLayoutParams();
                rr.height = ratio.gethetight(65);
                rowtvrl[i].setLayoutParams(rr);
            }

        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public AVLoadingIndicatorView progressBar;

        public FooterViewHolder(View v) {
            super(v);

            progressBar = (AVLoadingIndicatorView) v.findViewById(R.id.ppgg);
        }
    }
}
