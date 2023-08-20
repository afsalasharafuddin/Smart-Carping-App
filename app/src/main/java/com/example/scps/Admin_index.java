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
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_index extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_index);

        //Nav bar
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                switch (id) {
                    case R.id.home: {
                        Intent i = new Intent(Admin_index.this, Admin_index.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.viewuser: {
                        Intent i = new Intent(Admin_index.this, Admin_view_user.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.viewcustomer: {
                        Intent i = new Intent(Admin_index.this,Admin_customer_details.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.edit_user: {
                        Intent i = new Intent(Admin_index.this, Edit_user.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.addarea: {
                        Intent i = new Intent(Admin_index.this, Add_area.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.addslot: {
                        Intent i = new Intent(Admin_index.this, Add_slot.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.passChange: {
                        Intent i = new Intent(Admin_index.this, Admin_change.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.logout: {
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Admin_index.this);
                        builder.setTitle("Are you sure?");
                        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Admin_index.this, "Successfully Signed Out.", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(Admin_index.this, login_page.class));
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Admin_index.this, "cancelled", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}