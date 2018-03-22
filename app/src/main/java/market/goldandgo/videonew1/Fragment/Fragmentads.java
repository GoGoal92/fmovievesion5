package market.goldandgo.videonew1.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.R;

/**
 * Created by Go Goal on 3/4/2018.
 */

public class Fragmentads extends Fragment {

    public static Fragmentads newInstance() {

        //   Bundle args = new Bundle();

        Fragmentads fragment = new Fragmentads();
        //    fragment.setArguments(args);
        return fragment;
    }

    String id, url;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        final Bundle args = getArguments();
        id = args.getString("id");
        url = args.getString("url");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v= getView();

        final ImageView back = (ImageView) v.findViewById(R.id.back);
        Picasso.with(getActivity()).load(Constant.adsurl+id+".png").into(new Target(){

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                back.setBackground(new BitmapDrawable(getActivity().getResources(), bitmap));
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse(url));
                getActivity().startActivity(intent);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_pager_layout, container, false);
    }


}
