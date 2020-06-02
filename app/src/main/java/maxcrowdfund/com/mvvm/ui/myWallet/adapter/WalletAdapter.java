package maxcrowdfund.com.mvvm.ui.myWallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import maxcrowdfund.com.R;
import maxcrowdfund.com.databinding.RowItemCommonBinding;
import maxcrowdfund.com.mvvm.ui.commonmodel.CommonModel;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyHolder> {
    private List<CommonModel> arrayList;
    Context mContext;
    String type = "";

    public WalletAdapter(Context mContext, String mType, List<CommonModel> arrayList) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.type = mType;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyHolder(RowItemCommonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (type.equals("Wallet")) {
            if (position == 0) {
                holder.binding.lLayHeaderCommon.setVisibility(View.VISIBLE);
                holder.binding.rLayoutWallet.setVisibility(View.GONE);
                holder.binding.tvHeaderTitle.setText(arrayList.get(position).getTittle());
            } else {
                holder.binding.lLayHeaderCommon.setVisibility(View.GONE);
                holder.binding.rLayoutWallet.setVisibility(View.VISIBLE);
                holder.binding.tvTitleName.setText(arrayList.get(position).getTittle());
                holder.binding.tvTitleValue.setText(arrayList.get(position).getValue());
            }
        } else if (type.equals("Purchase")) {
            holder.binding.rLayTransaction.setVisibility(View.VISIBLE);
            /*Resources res = mContext.getResources();
            Drawable background = res.getDrawable(R.drawable.ic_delete_green);
            int primaryColor = res.getColor(R.color.light_gray_text);
            background.setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);*/
            if (position == 0) {
               /* holder.binding.tvDelete.setVisibility(View.INVISIBLE);
                holder.binding.tvDelete.setBackgroundDrawable(background);*/
                holder.binding.tvTitle.setText(arrayList.get(position).getTittle());
                holder.binding.tvType.setText(arrayList.get(position).getType());
                holder.binding.tvValue.setText(arrayList.get(position).getValue());
                holder.binding.tvType.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.binding.tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.binding.tvValue.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                holder.binding.tvTitle.setText(arrayList.get(position).getTittle());
                holder.binding.tvType.setText(arrayList.get(position).getType());
                holder.binding.tvValue.setText(arrayList.get(position).getValue());
            }
        } else if (type.equals("Transactions")) {
            holder.binding.rLayTransaction.setVisibility(View.VISIBLE);
            if (position == 0) {
                holder.binding.tvType.setText(arrayList.get(position).getType());
                holder.binding.tvTitle.setText(arrayList.get(position).getTittle());
                holder.binding.tvValue.setText(arrayList.get(position).getValue());
                holder.binding.tvType.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.binding.tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                holder.binding.tvValue.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                holder.binding.tvType.setText(arrayList.get(position).getType());
                holder.binding.tvTitle.setText(arrayList.get(position).getTittle());
                holder.binding.tvValue.setText(arrayList.get(position).getValue());
            }
        }else if (type.equals("ContactInfo")){
            if (position == 0) {
                holder.binding.lLayHeaderCommon.setVisibility(View.VISIBLE);
                holder.binding.lLayoutContactInfo.setVisibility(View.GONE);
                holder.binding.tvHeaderTitle.setText(arrayList.get(position).getTittle());
            } else {
                holder.binding.lLayHeaderCommon.setVisibility(View.GONE);
                holder.binding.lLayoutContactInfo.setVisibility(View.VISIBLE);
                holder.binding.tvContactTittle.setText(arrayList.get(position).getTittle());
                holder.binding.tvContactValue.setText(arrayList.get(position).getValue());
                if (position == 2 ||position == 3) {
                    holder.binding.tvContactTittle.setText(arrayList.get(position).getTittle());
                    holder.binding.tvContactValue.setText(arrayList.get(position).getValue());
                    holder.binding.tvContactValue.setTextColor(mContext.getResources().getColor(R.color.green));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class MyHolder extends RecyclerView.ViewHolder {
        RowItemCommonBinding binding;
        public MyHolder(RowItemCommonBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
