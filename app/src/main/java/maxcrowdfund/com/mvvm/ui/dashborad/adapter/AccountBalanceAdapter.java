package maxcrowdfund.com.mvvm.ui.dashborad.adapter;

import android.app.Activity;
import android.content.Context;
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

import maxcrowdfund.com.R;
import maxcrowdfund.com.mvvm.ui.commonmodel.CommonModel;
import java.util.List;

public class AccountBalanceAdapter extends RecyclerView.Adapter<AccountBalanceAdapter.MyHolder> {
    private List<CommonModel> arrayList;
    Context mContext;
    Activity mActivity;
    String mTotal = "";

    public AccountBalanceAdapter(Context mContext, Activity mActivity, List<CommonModel> arrayList) {
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
            holder.rLayout.setVisibility(View.GONE);
            String mTittle = arrayList.get(position).getTittle();
            holder.tvTittle.setText(mTittle.substring(0, 1).toUpperCase() + mTittle.substring(1).toLowerCase());
            holder.tvTittle.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.tvTittle.setVisibility(View.GONE);
            holder.rLayout.setVisibility(View.VISIBLE);
            holder.tvName.setText(arrayList.get(position).getTittle());
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.light_gray_text));

            if (arrayList.get(position).getType().equals("C")) {
                holder.tvType.setText("+");
                holder.tvValue.setText(arrayList.get(position).getValue());
                if ((arrayList.get(position).getTittle().equalsIgnoreCase("Total Balance"))) {
                    mTotal = arrayList.get(position).getValue();
                }
            } else {
                holder.tvType.setText("-");
                holder.tvValue.setText(arrayList.get(position).getValue());
            }

            if (position == arrayList.size() - 1) {
                holder.rlLayout.setVisibility(View.GONE);
                holder.btn_deposited.setVisibility(View.VISIBLE);
                holder.btn_deposited.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                        setPreference(mContext, "totalBalance", mTotal);
                        navController.navigate(R.id.action_nav_dashboard_to_dashboardDepositFragment); */
                        NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                        navController.navigate(R.id.action_nav_dashboard_to_bankTransferDetailFragment);
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
        TextView tvTittle, tvName, tvType, tvValue;
        RelativeLayout rLayout, rlLayout;
        Button btn_deposited;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tvTittle);
            tvName = itemView.findViewById(R.id.tvName);
            tvType = itemView.findViewById(R.id.tvType);
            tvValue = itemView.findViewById(R.id.tvValue);
            rLayout = itemView.findViewById(R.id.rLayout);
            rlLayout = itemView.findViewById(R.id.rlLayout);
            btn_deposited = itemView.findViewById(R.id.btn_deposited);
        }
    }
}
