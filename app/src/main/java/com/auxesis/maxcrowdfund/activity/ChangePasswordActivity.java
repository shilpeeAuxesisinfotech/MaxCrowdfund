package com.auxesis.maxcrowdfund.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.auxesis.maxcrowdfund.R;

public class ChangePasswordActivity extends AppCompatActivity {
    private static final String TAG = "ChangePasswordActivity";
    TextView tv_back_arrow, tvHeaderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    private void init() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.change_password);
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
