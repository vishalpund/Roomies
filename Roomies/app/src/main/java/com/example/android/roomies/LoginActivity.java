package com.example.android.roomies;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button mloginBtn;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;

    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private ProgressDialog mRegProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Toolbar
        mToolbar = (Toolbar) findViewById(R.id.register_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Roomies");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        mloginBtn = findViewById(R.id.login_btn);

        //progress
        mRegProgress = new ProgressDialog(this);

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if (!(TextUtils.isEmpty(password)) || !(TextUtils.isEmpty(email))) {

                    mRegProgress.setTitle("LOGGING IN...");
                    mRegProgress.setMessage("WELCOME BACK :)");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    login_user(email, password);
                }
                else
                {
                    mRegProgress.hide();
                    // If sign in fails, display a message to the user
                    Toast.makeText(LoginActivity.this, "!! OOPS SOMETHING WENT WRONG !!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void login_user(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        mRegProgress.dismiss();

                        Intent main_Intent =  new Intent(LoginActivity.this,MainActivity.class);
                        main_Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(main_Intent);
                        finish();
                    } else {

                        mRegProgress.hide();
                        // If sign in fails, display a message to the user
                        Toast.makeText(LoginActivity.this, "!! OOPS SOMETHING WENT WRONG !!",
                                Toast.LENGTH_LONG).show();
                    }

                    // ...
                }
            });
        }
    }
