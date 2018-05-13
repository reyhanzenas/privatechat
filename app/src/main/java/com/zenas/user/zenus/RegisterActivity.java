package com.zenas.user.zenus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    private Toolbar mToolbar;
    private ProgressDialog loadingBar;
    private EditText RegisterUsername;
    private EditText RegisterEmail;
    private EditText RegisterPassword;
    private Button RegisterSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RegisterUsername = (EditText) findViewById(R.id.register_username);
        RegisterEmail = (EditText) findViewById(R.id.register_email);
        RegisterPassword = (EditText) findViewById(R.id.register_password);
        RegisterSignUpButton = (Button) findViewById(R.id.register_sign_up_button);
        loadingBar = new ProgressDialog(this);

        RegisterSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String username = RegisterUsername.getText().toString();
                String email = RegisterEmail.getText().toString();
                String password = RegisterPassword.getText().toString();

                RegisterAccount(username,email,password);
            }
        });
    }

    private void RegisterAccount(String username, String email, String password)
    {
        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(RegisterActivity.this, "Please write your username.", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(RegisterActivity.this, "Please write your email.", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(RegisterActivity.this, "Please write your username.", Toast.LENGTH_LONG).show();
        }
        else
        {
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please wait, creating account");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Error Occured, Try Again", Toast.LENGTH_SHORT).show();
                            }
                            loadingBar.dismiss();
                        }
                    });
        }
    }
}
