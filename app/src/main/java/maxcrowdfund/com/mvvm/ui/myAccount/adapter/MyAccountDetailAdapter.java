package maxcrowdfund.com.mvvm.ui.myAccount.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import maxcrowdfund.com.R;
import maxcrowdfund.com.databinding.RowItemPurchaseOrderBinding;
import maxcrowdfund.com.mvvm.ui.myAccount.model.AccountModel;
import maxcrowdfund.com.mvvm.ui.myWallet.model.PurchaseOrderModel;

public class MyAccountDetailAdapter extends RecyclerView.Adapter<MyAccountDetailAdapter.MyHolder> {
    private List<PurchaseOrderModel> arrayList;
    Context mContext;
    String type = "";

    public MyAccountDetailAdapter(Context mContext, String mType, List<PurchaseOrderModel> arrayList) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.type=mType;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyHolder(RowItemPurchaseOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.binding.tvDelete.setVisibility(View.GONE);
        holder.binding.tvValue.setVisibility(View.GONE);
        holder.binding.tvTransValue.setVisibility(View.VISIBLE);
        if (position==0){
            holder.binding.tvType.setText(arrayList.get(position).getType());
            holder.binding.tvDate.setText(arrayList.get(position).getDate());
            holder.binding.tvTransValue.setText(arrayList.get(position).getAmount());
            holder.binding.tvType.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.binding.tvDate.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            holder.binding.tvTransValue.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        }else {
            holder.binding.tvType.setText(arrayList.get(position).getType());
            holder.binding.tvDate.setText(arrayList.get(position).getDate());
            holder.binding.tvTransValue.setText(arrayList.get(position).getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RowItemPurchaseOrderBinding binding;
        public MyHolder(RowItemPurchaseOrderBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
