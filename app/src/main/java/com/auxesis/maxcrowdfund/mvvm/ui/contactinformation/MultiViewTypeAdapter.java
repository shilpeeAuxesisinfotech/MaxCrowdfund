/*
package com.auxesis.maxcrowdfund.mvvm.ui.contactinformation;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.model.MyListModel;
import java.util.ArrayList;
import java.util.List;

public class MultiViewTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MyListModel> dataSet;
    Context mContext;
    int total_types;
    public ArrayList<MyListModel> arrayList, filterList;
    Context mActivity;

    public MultiViewTypeAdapter(Context context, Activity mActivity, ArrayList<MyListModel> arrayList) {
        this.mContext = context;
        this.mActivity = mActivity;
        this.arrayList = arrayList;
    }


    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {
        TextView txtType;
        CardView cardView;

        public TextTypeViewHolder(View itemView) {
            super(itemView);
            this.txtType = (TextView) itemView.findViewById(R.id.tv_mTittle);
        }
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {
        TextView tv_raised_amount;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.tv_raised_amount = (TextView) itemView.findViewById(R.id.tv_raised_amount);
        }
    }

    public static class AudioTypeViewHolder extends RecyclerView.ViewHolder {
        ProgressBar mProgressbar ;
        public AudioTypeViewHolder(View itemView) {
            super(itemView);
            this.mProgressbar = itemView.findViewById(R.id.mProgressbar);
        }
    }

    public MultiViewTypeAdapter(ArrayList<MyListModel> data, Context context) {
        this.dataSet = data;
        this.mContext = context;
        total_types = dataSet.size();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case MyListModel.VIEW_TYPE_NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_home, parent, false);
                return new TextTypeViewHolder(view);
            case MyListModel.VIEW_TYPE_TOTAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_home_total, parent, false);
                return new ImageTypeViewHolder(view);
            case MyListModel.VIEW_TYPE_LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_loading, parent, false);
                return new AudioTypeViewHolder(view);
        }
        return null;

    }


    @Override
    public int getItemViewType(int position) {

        switch (dataSet.get(position).mViewHolderType) {
            case 0:
                return MyListModel.VIEW_TYPE_NORMAL;
            case 1:
                return MyListModel.VIEW_TYPE_TOTAL;
            case 2:
                return MyListModel.VIEW_TYPE_LOADING;
            default:
                return -1;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        MyListModel object = dataSet.get(listPosition);
        if (object != null) {
            switch (object.mViewHolderType) {
                case MyListModel.VIEW_TYPE_NORMAL:
                    ((TextTypeViewHolder) holder).txtType.setText(object.getmTitle());
                    break;
                case MyListModel.VIEW_TYPE_TOTAL:
                    ((ImageTypeViewHolder) holder).tv_raised_amount.setText(object.getTotal_raised());

                    break;
                case MyListModel.VIEW_TYPE_LOADING:
                    //((AudioTypeViewHolder) holder).mProgressbar.se;

                    break;
            }
        }

    }


    public void addItems(List<MyListModel> postItems) {
        arrayList.addAll(postItems);
        notifyDataSetChanged();
    }

    public void addLoading() {
       // isLoaderVisible = true;
        arrayList.add(new MyListModel());
        notifyItemInserted(arrayList.size() - 1);
    }

    public void removeLoading() {
     //   isLoaderVisible = false;
        int position = arrayList.size() - 1;
        MyListModel item = getItem(position);
        if (item != null) {
            arrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        arrayList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
*/
