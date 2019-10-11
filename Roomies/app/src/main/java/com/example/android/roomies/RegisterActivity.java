package com.example.android.roomies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private TextInputLayout mName;
    private Button mCreateBtn;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

    //Progress Dialog
    private ProgressDialog mRegProgress;

    //DatabaseReference
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Toolbar Set
        mToolbar = (Toolbar) findViewById(R.id.register_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Roomies");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRegProgress = new ProgressDialog(this);

        //Firebase auth
        mAuth = FirebaseAuth.getInstance();
        mName =  findViewById(R.id.reg_username);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        mCreateBtn = findViewById(R.id.login_btn);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_name = mName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if(!(TextUtils.isEmpty(display_name)) || !(TextUtils.isEmpty(password)) || !(TextUtils.isEmpty(email))) {

                    mRegProgress.setTitle("CREATING ACCOUNT....");
                    mRegProgress.setMessage("Keep Calm While U r Getting Registered :)");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(display_name, email, password);
                }
            }
        });
    }
    private void register_user(final String display_name, String email, String password)
    {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String, String> userMap= new HashMap<>();
                            userMap.put("name", display_name);

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        mRegProgress.dismiss();

                                        Intent main_Intent =  new Intent(RegisterActivity.this,MainActivity.class);
                                        main_Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(main_Intent);
                                        finish();
                                    }

                                }
                            });
                        } else {

                            mRegProgress.hide();
                            // If sign in fails, display a message to the user
                            Toast.makeText(RegisterActivity.this, "!! OOPS SOMETHING WENT WRONG !!",
                                    Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });

    }
}
