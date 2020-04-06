package com.auxesis.maxcrowdfund.mvvm.ui.dashborad;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboardmodel.AccountBalanceModel;

import java.util.List;

import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;

public class AccountBalanceAdapter extends RecyclerView.Adapter<AccountBalanceAdapter.MyHolder> {
    private List<AccountBalanceModel> arrayList;
    Context mContext;
    Activity mActivity;
    String mTotal = "";

    public AccountBalanceAdapter(Context mContext, Activity mActivity, List<AccountBalanceModel> arrayList) {
        this.mContext = mContext;
        this.mActivity = mActivity;
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
            holder.tvTittle.setVisibility(View.VISIBLE);
            holder.rlBalanceAccount.setVisibility(View.GONE);
            holder.tvTittle.setText(arrayList.get(position).getmTitle());
            holder.tvTittle.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.tvTittle.setVisibility(View.GONE);
            holder.rlBalanceAccount.setVisibility(View.VISIBLE);
            holder.tvCompanyName.setText(arrayList.get(position).getmTitle());
            holder.tvCompanyName.setTextColor(mContext.getResources().getColor(R.color.light_gray_text));

            if (arrayList.get(position).getmType().equals("C")) {
                holder.tv_type.setText("+");
                holder.tv_invested.setText(arrayList.get(position).getmValue());
                if ((arrayList.get(position).getmTitle().equalsIgnoreCase("Total Balance"))){
                    mTotal=arrayList.get(position).getmValue();
                }
            } else {
                holder.tv_type.setText("-");
                holder.tv_invested.setText(arrayList.get(position).getmValue());
            }

            if (position == arrayList.size() - 1) {
                holder.rlLayout.setVisibility(View.GONE);
                holder.btn_deposited.setVisibility(View.VISIBLE);
                holder.btn_deposited.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                        setPreference(mContext, "totalBalance", mTotal);
                        navController.navigate(R.id.action_nav_dashboard_to_dashboardDepositFragment);

                        /*Intent intent = new Intent(mContext, DashboardDepositActivity.class);
                        mContext.startActivity(intent);*/
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvTittle, tvCompanyName, tv_invested, tv_type;
        RelativeLayout rlBalanceAccount, rlLayout;
        Button btn_deposited;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tvTittle);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tv_invested = itemView.findViewById(R.id.tv_invested);
            tv_type = itemView.findViewById(R.id.tv_type);
            rlBalanceAccount = itemView.findViewById(R.id.rlBalanceAccount);
            rlLayout = itemView.findViewById(R.id.rlLayout);
            btn_deposited = itemView.findViewById(R.id.btn_deposited);
        }
    }
}
