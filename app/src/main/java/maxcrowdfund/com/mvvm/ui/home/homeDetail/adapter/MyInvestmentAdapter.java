package maxcrowdfund.com.mvvm.ui.home.homeDetail.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import maxcrowdfund.com.R;
import maxcrowdfund.com.mvvm.ui.myinvestments.model.Datum;
import java.util.List;
import maxcrowdfund.com.constant.Utils;

public class MyInvestmentAdapter extends RecyclerView.Adapter<MyInvestmentAdapter.MyHolder> {
    private static final String TAG = "MyInvestmentAdapter";
    private List<Datum> arrayList;
    Context mContext;
    Activity mActivity;

    public MyInvestmentAdapter(Context mContext, Activity mActivity, List<Datum> arrayList) {
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
        holder.tv_invested.setText(String.valueOf(Utils.getCustomReplaceFormat(arrayList.get(position).getInvested())));

        holder.rlMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                Log.d("><><><",arrayList.get(position).getInvestmentId());
                Utils.setPreference(mContext, "investment_id", arrayList.get(position).getInvestmentId());
                Utils.setPreference(mContext, "loan_id", arrayList.get(position).getLoanid());
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
