package com.auxesis.maxcrowdfund.mvvm.ui.homeDetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.mvvm.ui.homeDetail.model.LastInvestmentModel;

import java.util.List;

public class LastInvestmentAdapter extends RecyclerView.Adapter<LastInvestmentAdapter.MyHolder> {
    private static final String TAG = "LastInvestmentAdapter";
    private List<LastInvestmentModel> arrayList;
    Context mContext;

    public LastInvestmentAdapter(Context mContext, List<LastInvestmentModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_last_investment, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tv_name_tittle.setText(arrayList.get(position).getmLastInvestmentData());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name_tittle;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_tittle = itemView.findViewById(R.id.tv_name_tittle);

        }
    }
}
