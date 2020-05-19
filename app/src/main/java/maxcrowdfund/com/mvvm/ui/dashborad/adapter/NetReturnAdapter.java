package maxcrowdfund.com.mvvm.ui.dashborad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import maxcrowdfund.com.R;
import maxcrowdfund.com.mvvm.ui.dashborad.netreturnmodel.NetReturnModel;
import java.util.List;

public class NetReturnAdapter extends RecyclerView.Adapter<NetReturnAdapter.MyHolder> {
    private List<NetReturnModel> arrayList;
    Context mContext;

    public NetReturnAdapter(Context mContext, List<NetReturnModel> arrayList) {
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
            holder.tvTittle.setVisibility(View.VISIBLE);
            holder.rLayout.setVisibility(View.GONE);
            holder.tvTittle.setText(arrayList.get(position).getTittle());
            holder.tvTittle.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.tvTittle.setVisibility(View.GONE);
            holder.rLayout.setVisibility(View.VISIBLE);
            holder.tvName.setText(arrayList.get(position).getTittle());
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.light_gray_text));

            if (arrayList.get(position).getType().equals("C")) {
                holder.tvType.setText("+");
                holder.tvValue.setText(arrayList.get(position).getValue() + "%");
            } else {
                holder.tvType.setText("-");
                holder.tvValue.setText(arrayList.get(position).getValue()+ "%");
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvTittle, tvName, tvType, tvValue;
        RelativeLayout rLayout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tvTittle);
            tvName = itemView.findViewById(R.id.tvName);
            tvType = itemView.findViewById(R.id.tvType);
            tvValue = itemView.findViewById(R.id.tvValue);
            rLayout = itemView.findViewById(R.id.rLayout);
        }
    }
}
