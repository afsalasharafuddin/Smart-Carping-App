package com.example.scps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_change extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    EditText pass;
    EditText newpass;
    Button updatepassword;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser cUser;
    //area dropdown


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_change);

        //Getting user name
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();
        pass=findViewById(R.id.Current);
        newpass=findViewById(R.id.newPass);
        updatepassword=findViewById(R.id.updatepassword);
        updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword=pass.getText().toString().trim();
                String newPassword=newpass.getText().toString().trim();
                if(TextUtils.isEmpty(oldPassword)){
                    Toast.makeText(Admin_change.this, "Enter Your current Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newPassword.length()<7){
                    Toast.makeText(Admin_change.this, "Password length must atleast 7 characters...", Toast.LENGTH_SHORT).show();
                    return;
                }
                UpdatePassword(oldPassword,newPassword);
                startActivity(new Intent(Admin_change.this,Admin_index.class));
                finish();

            }
        });
        //Nav bar
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                item.setChecked(true);
                switch (id)
                {
                    case R.id.home: {
                        Intent i = new Intent(Admin_change.this, Admin_index.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.viewuser: {
                        Intent i = new Intent(Admin_change.this, Admin_view_user.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.viewcustomer: {
                        Intent i = new Intent(Admin_change.this,Admin_customer_details.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.edit_user: {
                        Intent i = new Intent(Admin_change.this, Edit_user.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.addarea: {
                        Intent i = new Intent(Admin_change.this, Add_area.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.addslot: {
                        Intent i = new Intent(Admin_change.this, Add_slot.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.passChange: {
                        Intent i = new Intent(Admin_change.this, Admin_change.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.logout: {
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Admin_change.this);
                        builder.setTitle("Are you sure?");
                        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Admin_change.this, "Successfully Signed Out.", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(Admin_change.this, login_page.class));
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Admin_change.this, "cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void UpdatePassword(String oldPassword, String newPassword) {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential authCredential= EmailAuthProvider.getCredential(user.getEmail(),oldPassword);
        user.reauthenticate(authCredential)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user.updatePassword(newPassword)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Admin_change.this, "Password Updated...", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Admin_change.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Admin_change.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

}