package com.auxesis.maxcrowdfund.mvvm.ui.changePreference;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;

public class ChangePreferenceFragment extends Fragment {
    private static final String TAG = "ChangePreferenceFragmen";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_change_preference, container, false);

        return root;
    }



    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.change_preference));
    }
}
