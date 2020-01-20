package com.auxesis.maxcrowdfund.mvvm.ui.changePreference;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel.Option_;
import java.util.List;

public class ActiveAccountAdapter extends BaseAdapter {
    Context mContext;
    List<Option_> modelList;

    public ActiveAccountAdapter(Context mContext, List<Option_> modelList) {
        this.mContext = mContext;
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Option_ getItem(int position) {
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
