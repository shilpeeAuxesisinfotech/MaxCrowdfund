package com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.mvvm.ui.myinvestments.myinvestmentdetail.myinvestmentdetailmodel.Datum__;

import java.util.List;

public class MyInvestmentDetailRepayAdapter extends RecyclerView.Adapter<MyInvestmentDetailRepayAdapter.MyHolder> {
    private static final String TAG = "MyInvestmentAdapter";
    private List<Datum__> arrayList;
    Context mContext;

    public MyInvestmentDetailRepayAdapter(Context mContext, List<Datum__> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_investment_detail, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.tvName.setText(String.valueOf(arrayList.get(position).getData().getPeriod()));
        holder.tv_invested.setText(arrayList.get(position).getData().getAmount());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvName, tv_invested;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tv_invested = itemView.findViewById(R.id.tv_invested);
        }
    }
}
