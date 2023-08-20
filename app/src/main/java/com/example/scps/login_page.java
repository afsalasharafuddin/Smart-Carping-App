package com.example.scps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends AppCompatActivity {
    private EditText email;
    private FirebaseAuth mAuth;
    private EditText password;
    private TextView forgetPass;
    private TextView signupBtn;
    private Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        final LoadingDialog loadingDialog=new LoadingDialog(login_page.this);

        mAuth = FirebaseAuth.getInstance();
        signupBtn = findViewById(R.id.signup);
        forgetPass=findViewById(R.id.forgotpass);

        loginBtn = findViewById(R.id.btnlogin);
        loginBtn.setBackgroundColor(Color.parseColor("#071480"));
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login_page.this,forgot_password.class));
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login_page.this,registration_page.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();
                if (TextUtils.isEmpty(emailTxt)) {
                    email.setError("please fill this email");
                    email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(passwordTxt)) {
                    password.setError("please fill this password");
                    password.requestFocus();
                    return;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
                    email.setError("please enter a valid email");
                    email.requestFocus();
                    return;
                }
                loadingDialog.startLoadingDialog();
                mAuth.signInWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(emailTxt.equals("carparkingadmin@gmail.com")) {
                                Toast.makeText(login_page.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(login_page.this,Admin_index.class));
                                finish();
                            } else {
                                Toast.makeText(login_page.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(login_page.this, mainIndex.class));
                                finish();
                            }
                        } else{
                            Toast.makeText(login_page.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}