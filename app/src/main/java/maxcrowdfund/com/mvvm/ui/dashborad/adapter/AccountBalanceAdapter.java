package maxcrowdfund.com.mvvm.ui.dashborad.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import maxcrowdfund.com.R;
import maxcrowdfund.com.databinding.RowItemDashboardBinding;
import maxcrowdfund.com.mvvm.ui.commonmodel.CommonModel;
import java.util.List;

public class AccountBalanceAdapter extends RecyclerView.Adapter<AccountBalanceAdapter.MyHolder> {
    private List<CommonModel> arrayList;
    Context mContext;
    Activity mActivity;
    String mTotal = "";
    String type="";
    public AccountBalanceAdapter(Context mContext, Activity mActivity,String lType, List<CommonModel> arrayList) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.arrayList = arrayList;
        this.type=lType;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyHolder(RowItemDashboardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (type.equals("AccountBalance")){
            if (position == 0) {
                holder.binding.lLayoutHeader.setVisibility(View.VISIBLE);
                holder.binding.view.setVisibility(View.VISIBLE);
                holder.binding.rLayoutFooter.setVisibility(View.GONE);
                String mTittle = arrayList.get(position).getTittle();
                holder.binding.tvHeadingTittle.setText(mTittle.substring(0, 1).toUpperCase() + mTittle.substring(1).toLowerCase());
                holder.binding.tvHeadingType.setText(mContext.getResources().getString(R.string.euro));
                holder.binding.tvHeadingValue.setText(arrayList.get(position).getValue());
            } else {
                holder.binding.lLayoutHeader.setVisibility(View.GONE);
                holder.binding.view.setVisibility(View.GONE);
                holder.binding.rLayoutFooter.setVisibility(View.VISIBLE);
                holder.binding.tvName.setText(arrayList.get(position).getTittle());
                if (arrayList.get(position).getType().equals("C")) {
                    holder.binding.tvType.setText("+");
                    holder.binding.tvValue.setText(arrayList.get(position).getValue());
                    if ((arrayList.get(position).getTittle().equalsIgnoreCase("Total Balance"))) {
                        mTotal = arrayList.get(position).getValue();
                    }
                } else {
                    holder.binding.tvType.setText("-");
                    holder.binding.tvValue.setText(arrayList.get(position).getValue());
                }
                if (position == arrayList.size() - 1) {
                    holder.binding.rlLayout.setVisibility(View.GONE);
                    holder.binding.btnDeposited.setVisibility(View.VISIBLE);
                    holder.binding.btnDeposited.setOnClickListener(new View.OnClickListener() {
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
        }else if (type.equals("Portfolio")){
            if (position == 0) {
                holder.binding.lLayoutHeader.setVisibility(View.VISIBLE);
                holder.binding.view.setVisibility(View.VISIBLE);
                holder.binding.rLayoutFooter.setVisibility(View.GONE);
                String mTittle = arrayList.get(position).getTittle();
                holder.binding.tvHeadingTittle.setText(mTittle.substring(0, 1).toUpperCase() + mTittle.substring(1).toLowerCase());
                holder.binding.tvHeadingValue.setText(arrayList.get(position).getValue());
            } else {
                holder.binding.lLayoutHeader.setVisibility(View.GONE);
                holder.binding.view.setVisibility(View.GONE);
                holder.binding.rLayoutFooter.setVisibility(View.VISIBLE);
                holder.binding.btnDeposited.setVisibility(View.GONE);
                holder.binding.tvName.setText(arrayList.get(position).getTittle());
                holder.binding.tvValue.setText(arrayList.get(position).getValue());
            }
        }else if (type.equals("NetReturn")){
            if (position == 0) {
                holder.binding.lLayoutHeader.setVisibility(View.VISIBLE);
                holder.binding.view.setVisibility(View.VISIBLE);
                holder.binding.rLayoutFooter.setVisibility(View.GONE);
                String mTittle = arrayList.get(position).getTittle();
                holder.binding.tvHeadingTittle.setText(mTittle.substring(0, 1).toUpperCase() + mTittle.substring(1).toLowerCase());
                holder.binding.tvHeadingValue.setText(arrayList.get(position).getValue());
            } else {
                holder.binding.lLayoutHeader.setVisibility(View.GONE);
                holder.binding.view.setVisibility(View.GONE);
                holder.binding.rLayoutFooter.setVisibility(View.VISIBLE);
                holder.binding.btnDeposited.setVisibility(View.GONE);
                holder.binding.tvName.setText(arrayList.get(position).getTittle());
                holder.binding.tvValue.setText(arrayList.get(position).getValue());
            }
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RowItemDashboardBinding binding;
        public MyHolder(RowItemDashboardBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
