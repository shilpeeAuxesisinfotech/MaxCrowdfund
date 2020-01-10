package com.auxesis.maxcrowdfund.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.model.InvestmentPlanModel;
import com.auxesis.maxcrowdfund.model.LoanTermModel;

import java.util.List;

public class InvestmentPlanAdapter extends RecyclerView.Adapter<InvestmentPlanAdapter.MyHolder> {
    private static final String TAG = "LoanTermAdapter";
    private List<InvestmentPlanModel> arrayList;
    Context mContext;

    public InvestmentPlanAdapter(Context mContext, List<InvestmentPlanModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_term_row_item, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tv_name_tittle.setText(arrayList.get(position).getmInvestmentPlanTitle());
        holder.tv_value.setText(arrayList.get(position).getmInvestmentPlanValue());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name_tittle,tv_value;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_tittle = itemView.findViewById(R.id.tv_name_tittle);
            tv_value = itemView.findViewById(R.id.tv_value);
        }
    }
}
