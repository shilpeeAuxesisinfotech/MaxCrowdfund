package com.auxesis.maxcrowdfund.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.activity.MyInvestmentDetailActivity;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.Datum;
import java.util.List;

import static com.auxesis.maxcrowdfund.constant.Utils.getCustomReplaceFormat;

public class MyInvestmentAdapter extends RecyclerView.Adapter<MyInvestmentAdapter.MyHolder> {
    private static final String TAG = "MyInvestmentAdapter";
    private List<Datum> arrayList;
    Context mContext;

    public MyInvestmentAdapter(Context mContext, List<Datum> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_item_invested, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvCompanyName.setText(arrayList.get(position).getLoan());
        holder.tv_invested.setText(String.valueOf(getCustomReplaceFormat(arrayList.get(position).getInvested())));

        holder.rlMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyInvestmentDetailActivity.class);
               /* Bundle bundle = new Bundle();
                bundle.putString("name", arrayList.get(position).getName());
                bundle.putString("cate_name", arrayList.get(position).getCategory_name());
                bundle.putString("material_Name", arrayList.get(position).getMaterial_name());
                bundle.putString("size_name", arrayList.get(position).getSize_name());
                bundle.putString("description", arrayList.get(position).getDescription());
                bundle.putString("banner_path", banner_path);
                intent.putExtras(bundle);*/
                mContext.startActivity(intent);
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
