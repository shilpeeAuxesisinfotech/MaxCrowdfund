package com.auxesis.maxcrowdfund.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.model.RiskModel;
import java.util.List;

public class RiskAdapter extends RecyclerView.Adapter<RiskAdapter.MyHolder> {
    private static final String TAG = "RiskAdapter";
    private List<RiskModel> arrayList;
    Context mContext;

    public RiskAdapter(Context mContext, List<RiskModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.risk_row_item, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tv_name_tittle.setText(arrayList.get(position).getmRiskTitle());
        holder.tv_value.setText(arrayList.get(position).getmRiskValue());
        holder.tv_sname_tittle.setText(arrayList.get(position).getmScoreTitle());
        holder.tv_svalue.setText(arrayList.get(position).getmScoreValue());
        holder.tv_mname_tittle.setText(arrayList.get(position).getmMeasureTitle());
        holder.tv_mvalue.setText(arrayList.get(position).getmMeasureValue());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name_tittle, tv_value, tv_sname_tittle, tv_svalue, tv_mname_tittle, tv_mvalue;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_tittle = itemView.findViewById(R.id.tv_name_tittle);
            tv_value = itemView.findViewById(R.id.tv_value);
            tv_sname_tittle = itemView.findViewById(R.id.tv_sname_tittle);
            tv_svalue = itemView.findViewById(R.id.tv_svalue);
            tv_mname_tittle = itemView.findViewById(R.id.tv_mname_tittle);
            tv_mvalue = itemView.findViewById(R.id.tv_mvalue);
        }
    }
}
