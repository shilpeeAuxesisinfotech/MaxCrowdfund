package maxcrowdfund.com.mvvm.ui.myWallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import maxcrowdfund.com.databinding.RowItemWalletBinding;
import maxcrowdfund.com.mvvm.ui.myWallet.model.WalletModel;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.MyHolder> {
    private List<WalletModel> arrayList;
    Context mContext;

    public WalletAdapter(Context mContext, List<WalletModel> arrayList) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyHolder(RowItemWalletBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (position == 0) {
            holder.binding.lLayoutHeader.setVisibility(View.VISIBLE);
            holder.binding.rLayoutFooter.setVisibility(View.GONE);
            holder.binding.tvHeadingTittle.setText(arrayList.get(position).getTittle());
        } else {
            holder.binding.lLayoutHeader.setVisibility(View.GONE);
            holder.binding.rLayoutFooter.setVisibility(View.VISIBLE);
            holder.binding.tvName.setText(arrayList.get(position).getTittle());
            holder.binding.tvValue.setText(arrayList.get(position).getValue());
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RowItemWalletBinding binding;
        public MyHolder(RowItemWalletBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
