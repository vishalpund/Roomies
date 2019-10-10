package com.example.android.roomies;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    private Button mRegBtn;
    private Button mloginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mRegBtn = findViewById(R.id.start_reg_btn);
        mloginBtn = findViewById(R.id.start_login_btn);

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg_Intent = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(reg_Intent);
            }
        });

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_Intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(login_Intent);
            }
        });
    }
}
