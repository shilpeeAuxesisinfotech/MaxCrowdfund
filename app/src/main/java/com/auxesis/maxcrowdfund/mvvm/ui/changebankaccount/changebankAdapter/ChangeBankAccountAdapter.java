package com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankAdapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.customClickListener.OnCustomClickListener;
import com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountmodel.Datum;

import java.util.List;

public class ChangeBankAccountAdapter extends RecyclerView.Adapter<ChangeBankAccountAdapter.MyHolder> {
    private List<Datum> arrayList;
    Context mContext;
    String account = "";
    OnCustomClickListener onCustomClickListener;
    Activity mActivity;

    public ChangeBankAccountAdapter(Context mContext, OnCustomClickListener onCustomClickListener, List<Datum> arrayList) {
        this.mContext = mContext;
        this.mActivity =mActivity;
        this.arrayList = arrayList;
        this.onCustomClickListener = onCustomClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_bank_account_, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.radioAccount.setText(arrayList.get(position).getAccount());
        if (arrayList.get(position).getActive() == 1) {
            holder.radioAccount.setChecked(true);
            account = arrayList.get(position).getAccount();
            Log.d(">>>>>>>>>>", "onCheckedChanged: " +"account====="+account+"pos---"+String.valueOf(position));
        } else {
            holder.radioAccount.setChecked(false);
            account = arrayList.get(position).getAccount();
        }
        holder.radioAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // holder.radioAccount.setChecked(false);
                Toast.makeText(mContext, "Selected Bank Account is " + arrayList.get(position).getAccount(), Toast.LENGTH_SHORT).show();
                account = arrayList.get(position).getAccount();
            }
        });

       /* holder.radioAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               // holder.radioAccount.setChecked(false);
                account = arrayList.get(position).getAccount();
                Log.d(">>>>>>>>>>", "onCheckedChanged: " + isChecked+"account====="+account+"pos---"+String.valueOf(position));
            }
        });
      */

        if (position == arrayList.size() - 1) {
            holder.btn_saveChanges.setVisibility(View.VISIBLE);
            holder.btn_saveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (account != null) {
                        onCustomClickListener.onCustomClick(account);
                    } else {
                        Toast.makeText(mContext, "Please select bank account", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            holder.btn_saveChanges.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void addItems(List<Datum> postItems) {
        arrayList.addAll(postItems);
        notifyDataSetChanged();
    }

    public void clear() {
        arrayList.clear();
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        Button btn_saveChanges;
        RadioButton radioAccount;
        RelativeLayout llChangebank;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            radioAccount = itemView.findViewById(R.id.radioAccount);
            btn_saveChanges = itemView.findViewById(R.id.btn_saveChanges);
            llChangebank = itemView.findViewById(R.id.llChangebank);
        }
    }
}
