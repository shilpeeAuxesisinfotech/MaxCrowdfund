package com.auxesis.maxcrowdfund.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.customClickListener.OnCustomClickListener;
import com.auxesis.maxcrowdfund.mvvm.ui.MaxPropertyGroupDetail.model.PhotosVideosModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import java.util.List;

public class MyPhotoAdapter extends RecyclerView.Adapter<MyPhotoAdapter.MyHolder> {
    private static final String TAG = "MyPhotoAdapter";
    private List<PhotosVideosModel> arrayList;
    Context mContext;
    public OnCustomClickListener onCustomClickListener;

    public MyPhotoAdapter(Context mContext, List<PhotosVideosModel> arrayList, OnCustomClickListener onCustomClickListener) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.onCustomClickListener = onCustomClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_photo, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String photo = arrayList.get(position).getType();
        String mUrl = arrayList.get(position).getUrl();
      //  String mphotoName = photo.substring(0, 1).toUpperCase() + photo.substring(1);

        try {
            if (mUrl != null && !mUrl.isEmpty() && !mUrl.equals("null")) {
                if (photo.equals("video")) {
                    holder.tvType.setText(photo);
                    holder.iv_photo.setVisibility(View.GONE);
                    holder.iv_videoView.setVisibility(View.VISIBLE);

                   /* MediaController mediaController = new MediaController(mContext);
                    mediaController.setAnchorView(holder.iv_videoView);
                    holder.iv_videoView.setMediaController(mediaController);
                    holder.iv_videoView.setVideoURI(Uri.parse(mUrl));
                    holder.iv_videoView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onImageClickListener.onImageClick(mUrl);
                        }
                    });*/

                } else {
                    holder.tvType.setText(photo);
                    holder.iv_videoView.setVisibility(View.GONE);
                    holder.iv_photo.setVisibility(View.VISIBLE);
                    Glide.with(mContext).load(mUrl)
                            .thumbnail(0.5f)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.iv_photo);

                    holder.iv_photo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onCustomClickListener.onCustomClick(mUrl);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView iv_photo;
        VideoView iv_videoView;
        TextView tvType;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            iv_videoView = itemView.findViewById(R.id.iv_videoView);
            tvType = itemView.findViewById(R.id.tvType);
        }
    }
}
