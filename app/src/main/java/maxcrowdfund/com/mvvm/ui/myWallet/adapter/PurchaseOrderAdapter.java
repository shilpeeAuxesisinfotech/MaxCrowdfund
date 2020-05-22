package maxcrowdfund.com.mvvm.ui.myWallet.adapter;

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
import maxcrowdfund.com.mvvm.ui.myWallet.model.PurchaseOrderModel;

public class PurchaseOrderAdapter extends RecyclerView.Adapter<PurchaseOrderAdapter.MyHolder> {
    private List<PurchaseOrderModel> arrayList;
    Context mContext;
    String type = "";

    public PurchaseOrderAdapter(Context mContext, String mType, List<PurchaseOrderModel> arrayList) {
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
        if (type.equals("Purchase")){
            Resources res = mContext.getResources();
            Drawable background = res.getDrawable(R.drawable.ic_delete_green);
            int primaryColor = res.getColor(R.color.light_gray_text);
            background.setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
            if (position==0){
                holder.binding.tvDelete.setVisibility(View.INVISIBLE);
                holder.binding.tvDelete.setBackgroundDrawable(background);
                holder.binding.tvDate.setText(arrayList.get(position).getDate());
                holder.binding.tvType.setText(arrayList.get(position).getType());
                holder.binding.tvValue.setText(arrayList.get(position).getAmount());
                holder.binding.tvType.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.binding.tvDate.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.binding.tvValue.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            }else {
                if (arrayList.get(position).isDelete() == true) {
                    holder.binding.tvDelete.setVisibility(View.VISIBLE);
                    holder.binding.tvDate.setText(arrayList.get(position).getDate());
                    holder.binding.tvType.setText(arrayList.get(position).getType());
                    holder.binding.tvValue.setText(arrayList.get(position).getAmount());
                    holder.binding.tvDelete.setBackgroundDrawable(background);
                } else {
                    holder.binding.tvDelete.setVisibility(View.INVISIBLE);
                    holder.binding.tvType.setText(arrayList.get(position).getType());
                    holder.binding.tvDate.setText(arrayList.get(position).getDate());
                    holder.binding.tvValue.setText(arrayList.get(position).getAmount());
                }
            }
        }else if (type.equals("Transactions")){
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

    /*public void removeItem(int position) {
        arrayList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(PurchaseOrderModel item, int position) {
        arrayList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }*/
}
