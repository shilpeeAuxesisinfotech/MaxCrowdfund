package com.auxesis.maxcrowdfund.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.auxesis.maxcrowdfund.R;

public class ChangeEmailActivity extends AppCompatActivity {
    private static final String TAG = "ChangeEmailActivity";
    TextView tv_back_arrow, tvHeaderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        init();
    }

    private void init() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.change_email);
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
