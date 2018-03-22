package market.goldandgo.videonew1.Fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.multithreaddownload.CallBack;
import com.aspsine.multithreaddownload.DownloadException;
import com.aspsine.multithreaddownload.DownloadInfo;
import com.aspsine.multithreaddownload.DownloadManager;
import com.aspsine.multithreaddownload.DownloadRequest;


import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.goldandgo.videonew1.Adapter.RecyclerViewAdapter;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.entity.AppInfo;
import market.goldandgo.videonew1.entity.DataSource;
import market.goldandgo.videonew1.listener.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragment extends Fragment implements OnItemClickListener<AppInfo> {
    private List<AppInfo> mAppInfos;
    private RecyclerViewAdapter mAdapter;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mAdapter = new RecyclerViewAdapter(ac);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setData(mAppInfos);
    }

    @Override
    public void onPause() {
        super.onPause();
        DownloadManager.getInstance().pauseAll();
    }

    private static final DecimalFormat DF = new DecimalFormat("0.00");

    /**
     * Dir: /Download
     */
    private final File mDownloadDir = new File(Environment.getExternalStorageDirectory(), "Download");

    @Override
    public void onItemClick(View v, int position, AppInfo appInfo) {
        if (appInfo.getStatus() == AppInfo.STATUS_DOWNLOADING || appInfo.getStatus() == AppInfo.STATUS_CONNECTING) {
            pause(appInfo.getUrl());
        } else if (appInfo.getStatus() == AppInfo.STATUS_COMPLETE) {
          //  install(appInfo);
        } else if (appInfo.getStatus() == AppInfo.STATUS_INSTALLED) {
          //  unInstall(appInfo);
        } else {
            download(position, appInfo.getUrl(), appInfo);
        }
    }

    private void download(final int position, String tag, final AppInfo appInfo) {
        final DownloadRequest request = new DownloadRequest.Builder()
                .setName(appInfo.getName() + ".apk")
                .setUri(appInfo.getUrl())
                .setFolder(mDownloadDir)
                .build();

       // DownloadManager.getInstance().download(request, tag, new DownloadCallback(position, appInfo));
    }

    private void pause(String tag) {
        DownloadManager.getInstance().pause(tag);
    }

  /*  private void install(AppInfo appInfo) {
        Utils.installApp(getActivity(), new File(mDownloadDir, appInfo.getName() + ".apk"));
    }

    private void unInstall(AppInfo appInfo) {
        Utils.unInstallApp(getActivity(), appInfo.getPackageName());
    }*/


}
