package com.auxesis.maxcrowdfund.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.auxesis.maxcrowdfund.R;

public class ChangeMobileNumberActivity extends AppCompatActivity {
    private static final String TAG = "ChangeMobileNumberActiv";
    TextView tv_back_arrow, tvHeaderTitle;
    Button btn_send_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mobile_number);
        init();
    }

    private void init() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.change_mobile_no);
        tv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_send_verify =findViewById(R.id.btn_send_verify);
        btn_send_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangeMobileNumberActivity.this, SendVerificationCodeActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
