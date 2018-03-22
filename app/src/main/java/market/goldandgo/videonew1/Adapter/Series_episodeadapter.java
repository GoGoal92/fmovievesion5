package market.goldandgo.videonew1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Object.ratio;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Readmore;
import market.goldandgo.videonew1.SeriesDetail;
import market.goldandgo.videonew1.Utils.HFRecyclerView;


/**
 * Created by Go Goal on 6/29/2017.
 */

public class Series_episodeadapter extends HFRecyclerView<get> {


    ArrayList<get> list;
    Activity ac;
    String mid, like, view, episode, price, detail;


    public Series_episodeadapter(Activity getactivity, ArrayList<get> asklist1, String mid1, String like1, String view1, String episode1, String price1, String detail1) {
        super(asklist1, true, true);
        list = asklist1;
        ac = getactivity;
        mid = mid1;
        like = like1;
        view = view1;
        episode = episode1;
        price = price1;
        detail = detail1;


    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.episoderow, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.seealldetailheader, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return new FooterViewHolder(inflater.inflate(R.layout.footer_pg, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {
        if (holder instanceof HeaderViewHolder) {

            HeaderViewHolder hholder = (HeaderViewHolder) holder;


            hholder.readmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(ac, Readmore.class);
                    it.putExtra("a", mid);
                    ac.startActivity(it);
                    ac.overridePendingTransition(0, 0);
                }
            });


            hholder.likes.setText(like);
            hholder.views.setText(view);
            hholder.epis.setText(episode);

            hholder.detail.setText(Html.fromHtml(detail));


            Bitmap myBitmap = BitmapFactory.decodeFile(Constant.datalocation_scover + "s" + mid + ".fmovie");
            Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
            hholder.movieimage.setBackground(myDrawable);

        } else if (holder instanceof ItemViewHolder) {
            final ItemViewHolder holder1 = (ItemViewHolder) holder;

            holder1.epdetail.setText(list.get(i - 1).getTitle());
            holder1.episode.setText("အပိုင္း - " + list.get(i - 1).getEpisode());
            holder1.clicker.setBackgroundResource(list.get(i - 1).getBaclgrouund());
            int rr = Integer.parseInt(list.get(i-1).getReportcount());

            if (rr>4){
                holder1.reporticon.setVisibility(View.VISIBLE);
            }else{
                holder1.reporticon.setVisibility(View.GONE);
            }

            holder1.clicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder1.clicker.setBackgroundResource(R.color.backcolorsolid);


                    SharedPreferences prefs = ac.getSharedPreferences("dlist",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(mid, list.get(i - 1).getMid());
                    editor.commit();

                    for (int j = 0; j < list.size(); j++) {
                        if (j == i - 1) {
                            list.get(j).setBaclgrouund(R.color.backcolorsolid);
                        } else {
                            list.get(j).setBaclgrouund(R.color.white);
                        }

                    }
                    notifyDataSetChanged();
                    Intent it = new Intent(ac, Detail.class);
                    it.putExtra("mid", list.get(i - 1).getMid());
                    it.putExtra("type", "series");
                    it.putExtra("mname", list.get(i - 1).getTitle());
                    it.putExtra("image", "ii");
                    it.putExtra("sid", mid);
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

        public Zawgyitextview episode, epdetail;
        public LinearLayout clicker;
        public ImageView reporticon;

        public ItemViewHolder(View v) {
            super(v);

            episode = (Zawgyitextview) v.findViewById(R.id.epno);
            epdetail = (Zawgyitextview) v.findViewById(R.id.epname);
            clicker = (LinearLayout) v.findViewById(R.id.epclicker);
            reporticon= (ImageView) v.findViewById(R.id.reporticon);

        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public Zawgyitextview likes, views, epis, prices, detail;
        public ImageView movieimage;
        public TextView readmore;
        public RelativeLayout rowivrl;
        public RelativeLayout rowtvrl[]=new RelativeLayout[4];

        public HeaderViewHolder(View v) {
            super(v);
            likes = (Zawgyitextview) v.findViewById(R.id.likeseries);
            views = (Zawgyitextview) v.findViewById(R.id.viewseries);
            epis = (Zawgyitextview) v.findViewById(R.id.episodeseries);
            prices = (Zawgyitextview) v.findViewById(R.id.priceseries);
            detail = (Zawgyitextview) v.findViewById(R.id.detail);
            movieimage = (ImageView) v.findViewById(R.id.iv);
            readmore = (TextView) v.findViewById(R.id.tv);


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

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public AVLoadingIndicatorView progressBar;

        public FooterViewHolder(View v) {
            super(v);

            progressBar = (AVLoadingIndicatorView) v.findViewById(R.id.ppgg);
        }
    }
}
