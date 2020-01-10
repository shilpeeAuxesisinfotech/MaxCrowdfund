package com.auxesis.maxcrowdfund.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.auxesis.maxcrowdfund.R;

public class ContactInformationActivity extends AppCompatActivity {
    private static final String TAG = "ContactInformationActiv";
    TextView tv_back_arrow, tvHeaderTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_information);
        init();
    }

    private void init() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.menu_contact_information);
        tv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
