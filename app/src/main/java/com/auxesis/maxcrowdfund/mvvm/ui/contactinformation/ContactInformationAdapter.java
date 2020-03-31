package com.auxesis.maxcrowdfund.mvvm.ui.contactinformation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.mvvm.ui.contactinformation.contactInformationModel.ContactInfoModel;
import java.util.List;

public class ContactInformationAdapter extends RecyclerView.Adapter<ContactInformationAdapter.MyHolder> {
    private static final String TAG = "DocumentAdapter";
    private List<ContactInfoModel> arrayList;
    Context mContext;

    public ContactInformationAdapter(Context mContext, List<ContactInfoModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_contact_information, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvTittle.setText(arrayList.get(position).getmTitle());
        holder.tvValue.setText(arrayList.get(position).getmValue());
        if (position == 1) {
            holder.tvTittle.setText(arrayList.get(position).getmTitle());
            holder.tvValue.setText(arrayList.get(position).getmValue());
            holder.tvValue.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (position==2){
            holder.tvTittle.setText(arrayList.get(position).getmTitle());
            holder.tvValue.setText(arrayList.get(position).getmValue());
            holder.tvValue.setTextColor(mContext.getResources().getColor(R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvTittle,tvValue;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTittle = itemView.findViewById(R.id.tvTittle);
            tvValue = itemView.findViewById(R.id.tvValue);
        }
    }
}
