package com.auxesis.maxcrowdfund.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.custommvvm.DashboardDepositActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboardmodel.AccountBalanceModel;
import java.util.List;

public class AccountBalanceAdapter extends RecyclerView.Adapter<AccountBalanceAdapter.MyHolder> {
    private List<AccountBalanceModel> arrayList;
    Context mContext;

    public AccountBalanceAdapter(Context mContext, List<AccountBalanceModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_account_balance, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (position == 0) {
            holder.tvCompanyName.setText(arrayList.get(position).getmTitle());
            holder.tvCompanyName.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.tvCompanyName.setText(arrayList.get(position).getmTitle());
            holder.tvCompanyName.setTextColor(mContext.getResources().getColor(R.color.light_gray_text));
            if (arrayList.get(position).getmType().equals("C")) {
                holder.tv_type.setText("+");
                holder.tv_invested.setText(arrayList.get(position).getmValue());
            } else {
                holder.tv_type.setText("-");
                holder.tv_invested.setText(arrayList.get(position).getmValue());
            }
        }
        if (position==arrayList.size()-1){
            holder.btn_deposited.setVisibility(View.VISIBLE);
            Log.d("mSize", "onBindViewHolder: " + String.valueOf(position)+"><><"+String.valueOf(arrayList.size()-1));
            holder.btn_deposited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DashboardDepositActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }else {
            holder.btn_deposited.setVisibility(View.GONE);
            Log.d("mSize", "Else: " + String.valueOf(position)+"><><"+String.valueOf(arrayList.size()-1));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvCompanyName, tv_invested, tv_type;
        RelativeLayout rlMainLayout;
        Button btn_deposited;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tv_invested = itemView.findViewById(R.id.tv_invested);
            tv_type = itemView.findViewById(R.id.tv_type);
            rlMainLayout = itemView.findViewById(R.id.rlMainLayout);
            btn_deposited = itemView.findViewById(R.id.btn_deposited);
        }
    }
}
