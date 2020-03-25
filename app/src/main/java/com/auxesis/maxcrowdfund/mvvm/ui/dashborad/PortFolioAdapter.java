package com.auxesis.maxcrowdfund.mvvm.ui.dashborad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboardmodel.PortfolioModel;
import java.util.List;

public class PortFolioAdapter extends RecyclerView.Adapter<PortFolioAdapter.MyHolder> {
    private List<PortfolioModel> arrayList;
    Context mContext;

    public PortFolioAdapter(Context mContext, List<PortfolioModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_portfolio, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (position == 0) {
            holder.tvCompanyName.setText(arrayList.get(position).getmPortfolioTitle());
            holder.tvCompanyName.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.tvCompanyName.setText(arrayList.get(position).getmPortfolioTitle());
            holder.tvCompanyName.setTextColor(mContext.getResources().getColor(R.color.light_gray_text));
            holder.tv_invested.setText(arrayList.get(position).getmPortfolioValue());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvCompanyName, tv_invested, tv_type;
        RelativeLayout rlMainLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tv_invested = itemView.findViewById(R.id.tv_invested);
            rlMainLayout = itemView.findViewById(R.id.rlMainLayout);
        }
    }
}
