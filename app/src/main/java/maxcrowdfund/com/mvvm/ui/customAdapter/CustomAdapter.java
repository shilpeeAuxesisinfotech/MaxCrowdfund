package maxcrowdfund.com.mvvm.ui.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import maxcrowdfund.com.R;
import maxcrowdfund.com.mvvm.ui.customModels.CustomSpinnerModel;

public class CustomAdapter extends BaseAdapter {
    Context mContext;
    List<CustomSpinnerModel> arrayList;

    public CustomAdapter(Context mContext, List<CustomSpinnerModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public CustomSpinnerModel getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, null);;
        TextView textView =view.findViewById(R.id.tvName);
        textView.setText(arrayList.get(position).getVal());
        return view;
    }
}
