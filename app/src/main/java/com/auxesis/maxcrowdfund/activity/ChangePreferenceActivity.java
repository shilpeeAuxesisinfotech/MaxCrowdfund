package com.auxesis.maxcrowdfund.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.auxesis.maxcrowdfund.R;

public class ChangePreferenceActivity extends AppCompatActivity {
    private static final String TAG = "ChangePreferenceActivit";
    TextView tv_back_arrow, tvHeaderTitle;
    RadioGroup radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_preference);
        init();
    }

    private void init() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.change_preference);
        tv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        radioButton =findViewById(R.id.radioButton);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
