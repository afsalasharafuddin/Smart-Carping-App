package com.example.scps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {
    private EditText email;
    private Button btn;
    private ProgressBar p;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.forgot);
        btn = findViewById(R.id.frt);
        p=findViewById(R.id.progress);
        firebaseAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpassword();
            }
        });
    }

    private void resetpassword() {
        String em = email.getText().toString().trim();
        if (em.isEmpty()) {
            email.setError("Email reruired");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
            email.setError("please enter a valid email");
            email.requestFocus();
            return;
        }
        p.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(forgot_password.this, "please check email", Toast.LENGTH_SHORT).show();
                    p.setVisibility(View.GONE);
                } else {
                    Toast.makeText(forgot_password.this, "Entered email is not registered", Toast.LENGTH_SHORT).show();
                    p.setVisibility(View.GONE);
                }

            }

        });
    }
    @Override
    public  void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(forgot_password.this,login_page.class));

    }
}




