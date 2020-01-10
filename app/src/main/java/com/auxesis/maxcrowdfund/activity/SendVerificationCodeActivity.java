package com.auxesis.maxcrowdfund.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.auxesis.maxcrowdfund.R;

public class SendVerificationCodeActivity extends AppCompatActivity {
    private static final String TAG = "SendVerificationCodeAct";
    TextView tv_back_arrow, tvHeaderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_verification_code);
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


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}