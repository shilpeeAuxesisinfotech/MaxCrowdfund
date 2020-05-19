package maxcrowdfund.com.mvvm.ui.myWallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import maxcrowdfund.com.databinding.RowItemPurchaseOrderBinding;
import maxcrowdfund.com.mvvm.ui.myWallet.model.PurchaseOrderModel;

public class PurchaseOrderAdapter extends RecyclerView.Adapter<PurchaseOrderAdapter.MyHolder> {
    private List<PurchaseOrderModel> arrayList;
    Context mContext;

    public PurchaseOrderAdapter(Context mContext, List<PurchaseOrderModel> arrayList) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyHolder(RowItemPurchaseOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (arrayList.get(position).isDelete()==true) {
            holder.binding.tvDelete.setVisibility(View.VISIBLE);
            holder.binding.tvDate.setText(arrayList.get(position).getDate());
            holder.binding.tvType.setText(arrayList.get(position).getType());
            holder.binding.tvValue.setText(arrayList.get(position).getAmount());
        } else {
            holder.binding.tvDelete.setVisibility(View.INVISIBLE);
            holder.binding.tvType.setText(arrayList.get(position).getType());
            holder.binding.tvDate.setText(arrayList.get(position).getDate());
            holder.binding.tvValue.setText(arrayList.get(position).getAmount());
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
