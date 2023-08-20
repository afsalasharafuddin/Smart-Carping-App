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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePass extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    EditText pass;
    EditText newpass;
    Button updatepassword;
    FirebaseAuth mAuth;
    FirebaseUser cUser;
    DatabaseReference reference,reference1;
    String userId,userName;
    TextView hName,hEmail;

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
        setContentView(R.layout.activity_change_pass);

        //Getting user name
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();


        //getting userid
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userId = currentFirebaseUser.getUid();
        updateNavHeader();



        pass=findViewById(R.id.Current);
        newpass=findViewById(R.id.newPass);
        updatepassword=findViewById(R.id.updatepassword);
        updatepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword=pass.getText().toString().trim();
                String newPassword=newpass.getText().toString().trim();
                if(TextUtils.isEmpty(oldPassword)){
                    Toast.makeText(ChangePass.this, "Enter Your current Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newPassword.length()<7){
                    Toast.makeText(ChangePass.this, "Password length must atleast 7 characters...", Toast.LENGTH_SHORT).show();
                    return;
                }
                UpdatePassword(oldPassword,newPassword);
                startActivity(new Intent(ChangePass.this,mainIndex.class));
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
                    case R.id.home:
                    {
                        Intent i=new Intent(ChangePass.this,mainIndex.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.about:
                    {
                        Intent i=new Intent(ChangePass.this,aboutPage.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.history:
                    {
                        Intent i=new Intent(ChangePass.this,HistoryPage.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.passChange:
                    {
                        Intent i=new Intent(ChangePass.this,ChangePass.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.logout:
                    {
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(ChangePass.this);
                        builder.setTitle("Are you sure?");
                        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(ChangePass.this, "Successfully Signed Out.", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(ChangePass.this, login_page.class));
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(ChangePass.this, "cancelled", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(ChangePass.this, "Password Updated...", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ChangePass.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChangePass.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public  void updateNavHeader(){
        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView1.getHeaderView(0);
        hName=headerView.findViewById(R.id.headerName);
        hEmail=headerView.findViewById(R.id.headerEmail);
        hEmail.setText(cUser.getEmail());
        reference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName= dataSnapshot.child("name").getValue().toString();
                hName.setText(userName);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}