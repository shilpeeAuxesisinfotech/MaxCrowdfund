package com.auxesis.maxcrowdfund.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.Datum;
import java.util.List;
import static com.auxesis.maxcrowdfund.constant.Utils.getCustomReplaceFormat;

public class MyInvestmentAdapter extends RecyclerView.Adapter<MyInvestmentAdapter.MyHolder> {
    private static final String TAG = "MyInvestmentAdapter";
    private List<Datum> arrayList;
    Context mContext;
    Activity mActivity;

    public MyInvestmentAdapter(Context mContext,Activity mActivity, List<Datum> arrayList) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_invested, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvCompanyName.setText(arrayList.get(position).getLoan());
        holder.tv_invested.setText(String.valueOf(getCustomReplaceFormat(arrayList.get(position).getInvested())));

        holder.rlMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_my_investments_to_nav_send);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvCompanyName, tv_invested;
        RelativeLayout rlMainLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tv_invested = itemView.findViewById(R.id.tv_invested);
            rlMainLayout = itemView.findViewById(R.id.rlMainLayout);
        }
    }
}
