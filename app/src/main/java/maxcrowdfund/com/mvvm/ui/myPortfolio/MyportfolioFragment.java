package maxcrowdfund.com.mvvm.ui.myPortfolio;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import maxcrowdfund.com.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyportfolioFragment extends Fragment {

    public MyportfolioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_myportfolio, container, false);
    }
}
