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

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import market.goldandgo.videonew1.API.Zawgyitextview;
import market.goldandgo.videonew1.Detail;
import market.goldandgo.videonew1.Fragment.Fragment_Series;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Search;
import market.goldandgo.videonew1.SeriesDetail;
import market.goldandgo.videonew1.Utils.HFRecyclerView;
import market.goldandgo.videonew1.Utils.calculate_st;


/**
 * Created by Go Goal on 6/29/2017.
 */

public class SearchAdapter extends HFRecyclerView<get> {


    ArrayList<get> list;
    Activity ac;


    public SearchAdapter(Activity getactivity, ArrayList<get> asklist1) {
        super(asklist1, true, true);
        list = asklist1;
        ac = getactivity;

    }

    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.seeallmovierow, parent, false));
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
            if (!list.get(i - 1).getPrice().equals("Free") && list.get(i - 1).getMine().equals("true")) {

                holder1.prices.setText("Owned");

            } else if (list.get(i - 1).getPrice().equals("Free")) {
                holder1.prices.setText(list.get(i - 1).getPrice());
            } else {
                holder1.prices.setText(calculate_st.format(Long.parseLong(list.get(i - 1).getPrice())) + " Gold");
            }


            holder1.detail.setText(Html.fromHtml("<b>" + list.get(i - 1).getTitle() + "</b><br><br>" + list.get(i - 1).getDetail()));


            if (list.get(i - 1).getMid().startsWith("s")) {
                Bitmap myBitmap = BitmapFactory.decodeFile(Constant.datalocation_scover + list.get(i - 1).getMid() + ".fmovie");
                Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                holder1.movieimage.setBackground(myDrawable);
            } else {
                Bitmap myBitmap = BitmapFactory.decodeFile(Constant.datalocation_movie + list.get(i - 1).getMid() + ".fmovie");
                Drawable myDrawable = new BitmapDrawable(ac.getResources(), myBitmap);
                holder1.movieimage.setBackground(myDrawable);
            }


            holder1.seeallclick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (list.get(i - 1).getMid().startsWith("s")) {

                        String seriesid = list.get(i - 1).getMid();
                        seriesid = seriesid.replace("s", "");

                        if (list.get(i - 1).getPrice().equals("Free") || list.get(i - 1).getMine().equals("true")) {


                            Intent it = new Intent(ac, SeriesDetail.class);
                            it.putExtra("mid", seriesid);
                            it.putExtra("mname", list.get(i - 1).getTitle());
                            it.putExtra("like", list.get(i - 1).getLike() + " Like");
                            it.putExtra("view", list.get(i - 1).getView() + " View");
                            it.putExtra("ep", list.get(i - 1).getCategory());
                            it.putExtra("price", list.get(i - 1).getPrice());
                            it.putExtra("detail", list.get(i - 1).getDetail());
                            ac.startActivity(it);

                        } else {

                            Search.showbutalert(list.get(i - 1).getPrice(), seriesid, list.get(i - 1).getTitle(), i - 1);

                        }


                    } else {

                        Intent it = new Intent(ac, Detail.class);
                        it.putExtra("mid", list.get(i - 1).getMid());
                        it.putExtra("type", "movie");
                        it.putExtra("mname", list.get(i - 1).getTitle());
                        it.putExtra("image", list.get(i - 1).getImage());
                        ac.startActivity(it);

                    }


                }
            });

            int rp = Integer.parseInt(list.get(i - 1).getReportcount());
            if (rp > 4) {
                holder1.reportcon.setBackgroundResource(R.color.red);
            } else {
                holder1.reportcon.setBackgroundResource(R.color.blue_inner);
            }


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


        public LinearLayout reportcon;

        public Zawgyitextview likes, views, epis, prices, detail;
        public ImageView movieimage;
        public ProgressBar pg;
        public LinearLayout seeallclick;


        public ItemViewHolder(View v) {
            super(v);

            likes = (Zawgyitextview) v.findViewById(R.id.likeseries);
            views = (Zawgyitextview) v.findViewById(R.id.viewseries);
            epis = (Zawgyitextview) v.findViewById(R.id.episodeseries);
            prices = (Zawgyitextview) v.findViewById(R.id.priceseries);
            detail = (Zawgyitextview) v.findViewById(R.id.detail);

            movieimage = (ImageView) v.findViewById(R.id.iv);
            seeallclick = (LinearLayout) v.findViewById(R.id.seeallclick);
            reportcon = (LinearLayout) v.findViewById(R.id.reportcon);


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
