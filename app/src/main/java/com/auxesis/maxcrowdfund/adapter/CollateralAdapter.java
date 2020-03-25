package com.auxesis.maxcrowdfund.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.mvvm.ui.MaxPropertyGroupDetail.model.CollateralModelP;
import java.util.ArrayList;
import java.util.List;

public class CollateralAdapter extends RecyclerView.Adapter<CollateralAdapter.MyHolder> {
    private static final String TAG = "LoanTermAdapter";
    Context mContext;
    private List<CollateralModelP> arrayListFirst = new ArrayList<>();

 public CollateralAdapter(Context mContext, List<CollateralModelP> arrayList) {
        this.mContext = mContext;
        this.arrayListFirst = arrayList;
    }

    @NonNull
    @Override
    public MyHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_collateral, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        CollateralModelP collateralModelP = arrayListFirst.get(position);
        holder.tv_Mheading.setText(collateralModelP.getmCollateralSubHeading());
        holder.tv_name_tittle1.setText(collateralModelP.getmCollateralTitle1());
        holder.tv_value1.setText(collateralModelP.getmCollateralValue1());

    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_Mheading,tv_name_tittle1,tv_value1;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_Mheading = itemView.findViewById(R.id.tv_Mheading);
            tv_name_tittle1 = itemView.findViewById(R.id.tv_name_tittle1);
            tv_value1 = itemView.findViewById(R.id.tv_value1);
        }
    }


    @Override
    public int getItemCount() {
       return arrayListFirst.size();
    }
}
