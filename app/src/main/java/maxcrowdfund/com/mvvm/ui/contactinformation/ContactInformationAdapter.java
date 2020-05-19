package maxcrowdfund.com.mvvm.ui.contactinformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import maxcrowdfund.com.R;
import maxcrowdfund.com.databinding.RowItemContactInformationBinding;
import maxcrowdfund.com.mvvm.ui.contactinformation.contactInformationModel.ContactInfoModel;
import java.util.List;

public class ContactInformationAdapter extends RecyclerView.Adapter<ContactInformationAdapter.MyHolder> {
    private List<ContactInfoModel> arrayList;
    Context mContext;

    public ContactInformationAdapter(Context mContext, List<ContactInfoModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyHolder(RowItemContactInformationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.binding.tvTittle.setText(arrayList.get(position).getmTitle());
        holder.binding.tvValue.setText(arrayList.get(position).getmValue());
        if (position == 1) {
            holder.binding.tvTittle.setText(arrayList.get(position).getmTitle());
            holder.binding.tvValue.setText(arrayList.get(position).getmValue());
            holder.binding.tvValue.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (position == 2) {
            holder.binding.tvTittle.setText(arrayList.get(position).getmTitle());
            holder.binding.tvValue.setText(arrayList.get(position).getmValue());
            holder.binding.tvValue.setTextColor(mContext.getResources().getColor(R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RowItemContactInformationBinding binding;
        public MyHolder(RowItemContactInformationBinding b) {
            super(b.getRoot());
            binding =b;
        }
    }
}
