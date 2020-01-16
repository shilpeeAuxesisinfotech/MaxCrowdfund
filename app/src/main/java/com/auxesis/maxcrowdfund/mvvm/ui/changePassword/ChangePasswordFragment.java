package com.auxesis.maxcrowdfund.mvvm.ui.changePassword;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;


public class ChangePasswordFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.fragment_change_password, container, false);
        // Inflate the layout for this fragment
        return root;
    }


    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.change_password));
    }
}
