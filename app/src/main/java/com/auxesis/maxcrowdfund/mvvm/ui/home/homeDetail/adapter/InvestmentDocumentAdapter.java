package com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.customClickListener.OnDownloadClickListener;
import com.auxesis.maxcrowdfund.mvvm.ui.myinvestments.myinvestmentdetail.myinvestmentdetailmodel.Datum_;

import java.util.ArrayList;
import java.util.List;

public class InvestmentDocumentAdapter extends RecyclerView.Adapter<InvestmentDocumentAdapter.MyHolder> {
    private static final String TAG = "DocumentAdapter";
    private List<Datum_> arrayList;
    Context mContext;
    private DownloadManager downloadManager;
    private long refid;
    private Uri Download_Uri;
    Activity mActivity;
    ArrayList<Long> list = new ArrayList<>();

    public OnDownloadClickListener onDownloadClickListener;

    public InvestmentDocumentAdapter(Context mContext, OnDownloadClickListener onDownloadClickListener, List<Datum_> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.onDownloadClickListener = onDownloadClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_document, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tv_name_tittle.setText(arrayList.get(position).getTitle());
        holder.tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String downloadUrl =arrayList.get(position).getUrl();
                if (downloadUrl!=null && !downloadUrl.isEmpty()) {
                    Log.d(TAG, "onClick: "+"><><"+downloadUrl);
                    onDownloadClickListener.onMyDownloadClick(downloadUrl);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name_tittle, tv_download;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_tittle = itemView.findViewById(R.id.tv_name_tittle);
            tv_download = itemView.findViewById(R.id.tv_download);
        }
    }
}
