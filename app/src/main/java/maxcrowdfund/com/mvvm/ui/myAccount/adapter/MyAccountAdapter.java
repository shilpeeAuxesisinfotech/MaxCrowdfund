package maxcrowdfund.com.mvvm.ui.myAccount.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import maxcrowdfund.com.R;
import maxcrowdfund.com.databinding.RowItemAccountBinding;
import maxcrowdfund.com.mvvm.ui.myAccount.model.AccountModel;

public class MyAccountAdapter extends RecyclerView.Adapter<MyAccountAdapter.MyHolder> {
    private List<AccountModel> arrayList;
    Context mContext;

    public MyAccountAdapter(Context mContext, List<AccountModel> arrayList) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyHolder(RowItemAccountBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.binding.rlHeaderLayout.setVisibility(View.VISIBLE);
        holder.binding.view.setVisibility(View.GONE);
        holder.binding.tvName.setText(arrayList.get(position).getTittle());
        if (arrayList.get(position).getType().equals("C")) {
            holder.binding.tvType.setText("+");
        } else if (arrayList.get(position).getType().equals("D")) {
            holder.binding.tvType.setText("-");
        }
        holder.binding.tvValue.setText("€ "+arrayList.get(position).getValue());
        if (position == arrayList.size() - 1) {
            holder.binding.rlHeaderLayout.setVisibility(View.VISIBLE);
            holder.binding.view.setVisibility(View.VISIBLE);
            holder.binding.tvName.setText(arrayList.get(position).getTittle());
            holder.binding.tvName.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            if (arrayList.get(position).getType().equals("C")) {
                holder.binding.tvType.setText("+");
            } else if (arrayList.get(position).getType().equals("D")) {
                holder.binding.tvType.setText("-");
            }
            holder.binding.tvValue.setText("€ "+arrayList.get(position).getValue());
            holder.binding.tvValue.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RowItemAccountBinding binding;
        public MyHolder(RowItemAccountBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
