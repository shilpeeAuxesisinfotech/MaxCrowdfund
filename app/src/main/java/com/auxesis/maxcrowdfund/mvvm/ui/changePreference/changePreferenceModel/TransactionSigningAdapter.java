package com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.auxesis.maxcrowdfund.R;
import java.util.List;

public class TransactionSigningAdapter extends BaseAdapter {
    Context mContext;
    List<Option__> modelList;

    public TransactionSigningAdapter(Context mContext, List<Option__> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Option__ getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, null);;
        TextView textView =view.findViewById(R.id.tvName);
        textView.setText(modelList.get(position).getVal());
        return view;
    }
}
