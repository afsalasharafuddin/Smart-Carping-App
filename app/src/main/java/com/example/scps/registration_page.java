package com.example.scps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration_page extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    DatabaseReference reference;
    private EditText inputName,inputPhone,inputEmail,inputPassword,inputConPassword;
    private TextView login;
    private Button btnSignup;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        mDatabase = FirebaseDatabase.getInstance();
        reference=mDatabase.getReference("users");
        mAuth = FirebaseAuth.getInstance();

        final LoadingDialog loadingDialog=new LoadingDialog(registration_page.this);
        btnSignup = findViewById(R.id.btnregister);
        inputName = findViewById(R.id.name);
        inputPhone = findViewById(R.id.phone);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        inputConPassword = findViewById(R.id.conpassword);

        mAuth = FirebaseAuth.getInstance();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtName=inputName.getText().toString().trim();
                String txtPhone=inputPhone.getText().toString().trim();
                String txtEmail=inputEmail.getText().toString().trim();
                String txtPass=inputPassword.getText().toString().trim();
                String TxtConPass=inputConPassword.getText().toString().trim();

                if (txtEmail.isEmpty()){
                    inputEmail.setError("Enter Email Address");
                    inputEmail.requestFocus();
                } else if (txtEmail.length()<10) {
                    inputEmail.setError("Enter Valid Email");
                    inputEmail.requestFocus();
                }
                if(txtName.isEmpty()){
                    inputName.setError("Enter the Name");
                    inputName.requestFocus();
                }
                if(txtName.isEmpty()){
                    inputPhone.setError("Enter phone number");
                    inputPhone.requestFocus();
                }
                if(txtPass.isEmpty()){
                    inputPassword.setError("Enter Password");
                    inputPassword.requestFocus();
                } else if (txtPass.length()<7) {
                    inputPassword.setError("Password must contain atleast Seven Characters");
                    inputPassword.requestFocus();
                }
                if(TxtConPass.isEmpty()){
                    inputConPassword.setError("Enter Password");
                    inputConPassword.requestFocus();
                } else if (TxtConPass.length()<7) {
                    inputConPassword.setError("Password must contain atleast Seven Characters");
                    inputConPassword.requestFocus();
                } else if (!txtPass.equals(TxtConPass)) {
                    inputPassword.setError("Password Does not match");
                    inputPassword.requestFocus();
                    inputConPassword.setError("Password Does not match");
                    inputConPassword.requestFocus();
                    inputPassword.setText("");
                    inputConPassword.setText("");

                }else{
                    RegisterUser(txtName,txtEmail,txtPhone ,txtPass);
                    loadingDialog.startLoadingDialog();
                }
            }
            private void RegisterUser(String name,String email, String phone,String pass){
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(registration_page.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            HelperClass user=new HelperClass(name,email,phone,pass);
                            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(registration_page.this, "Registration Successfull.", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(registration_page.this,login_page.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(registration_page.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}