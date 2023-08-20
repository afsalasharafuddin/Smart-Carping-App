package com.example.scps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class mainIndex extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth mAuth;
    FirebaseUser cUser;
    DatabaseReference reference,reference1;
     String userId,userName,item,location;
    TextView hName,hEmail;
    List<String> items;
    private ListView list_view;
    Spinner spinner;
    ImageButton locButton;
    ImageView s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;

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
        setContentView(R.layout.activity_main_index);

        //getting slots from xml
        s1=findViewById(R.id.s1);
        s2=findViewById(R.id.s2);
        s3=findViewById(R.id.s3);
        s4=findViewById(R.id.s4);
        s5=findViewById(R.id.s5);
        s6=findViewById(R.id.s6);
        s7=findViewById(R.id.s7);
        s8=findViewById(R.id.s8);


        //Getting user name
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();


        //getting userid
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userId = currentFirebaseUser.getUid();
        updateNavHeader();

        //Dropdown area
        spinner = findViewById(R.id.spinner);
        items = new ArrayList<>();
        items.add("Thodupuzha");
        items.add("Cheruthoni");
        spinner.setAdapter(new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                item = items.get(position);
                item = spinner.getSelectedItem().toString();
                reference1 = FirebaseDatabase.getInstance().getReference().child("area_details");
                reference1.child(item).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        location= dataSnapshot.child("location").getValue().toString();
                        //Toast.makeText(mainIndex.this, "loc="+location, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        locButton=findViewById(R.id.imgLocation);
        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(location);
            }
        });

        //getting slot details from firebase
        DatabaseReference reference3=FirebaseDatabase.getInstance().getReference().child("slot_details").child("Thodupuzha");
        int i=1;
        while(i<=10) {
            String slot="s"+ String.valueOf(i);
            int slotno=i;
            reference3.child(slot).addValueEventListener(new ValueEventListener() {
                String status;
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                    for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                        status = String.valueOf(snapshot.getValue());
                    }
                    if(status.equals("free")){
                        if(slotno==1){
                            s1.setBackgroundResource(R.drawable.big_free_slot);
                        } else if(slotno ==2){
                            s2.setBackgroundResource(R.drawable.big_free_slot);
                        } else if(slotno ==3){
                            s3.setBackgroundResource(R.drawable.big_free_slot);
                        } else if(slotno ==4){
                            s4.setBackgroundResource(R.drawable.big_free_slot);
                        } else if(slotno ==5){
                            s5.setBackgroundResource(R.drawable.big_free_slot);
                        } else if(slotno ==6){
                            s6.setBackgroundResource(R.drawable.big_free_slot);
                        } else if(slotno ==7) {
                            s7.setBackgroundResource(R.drawable.big_free_slot);
                        } else if(slotno ==8){
                            s8.setBackgroundResource(R.drawable.big_free_slot);
                        }
                    } else if(status.equals("occupied")){
                        if(slotno==1){
                            s1.setBackgroundResource(R.drawable.big_occupied_slot);
                        } else if(slotno ==2){
                            s2.setBackgroundResource(R.drawable.big_occupied_slot);
                        } else if(slotno ==3){
                            s3.setBackgroundResource(R.drawable.big_occupied_slot);
                        } else if(slotno ==4){
                            s4.setBackgroundResource(R.drawable.big_occupied_slot);
                        } else if(slotno ==5){
                            s5.setBackgroundResource(R.drawable.big_occupied_slot);
                        } else if(slotno ==6){
                            s6.setBackgroundResource(R.drawable.big_occupied_slot);
                        } else if(slotno ==7) {
                            s7.setBackgroundResource(R.drawable.big_occupied_slot);
                        } else if(slotno ==8){
                            s8.setBackgroundResource(R.drawable.big_occupied_slot);
                        }
                    } else if(status.equals("reserved")){
                        if(slotno==1){
                            s1.setBackgroundResource(R.drawable.bigreserved_slot);
                        } else if(slotno ==2){
                            s2.setBackgroundResource(R.drawable.bigreserved_slot);
                        } else if(slotno ==3){
                            s3.setBackgroundResource(R.drawable.bigreserved_slot);
                        } else if(slotno ==4){
                            s4.setBackgroundResource(R.drawable.bigreserved_slot);
                        } else if(slotno ==5){
                            s5.setBackgroundResource(R.drawable.bigreserved_slot);
                        } else if(slotno ==6){
                            s6.setBackgroundResource(R.drawable.bigreserved_slot);
                        } else if(slotno ==7) {
                            s7.setBackgroundResource(R.drawable.bigreserved_slot);
                        } else if(slotno ==8){
                            s8.setBackgroundResource(R.drawable.bigreserved_slot);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            i=i+1;
        }



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
                        Intent i = new Intent(mainIndex.this, mainIndex.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.about: {
                        Intent i = new Intent(mainIndex.this, aboutPage.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.history: {
                        Intent i = new Intent(mainIndex.this, HistoryPage.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.passChange: {
                        Intent i = new Intent(mainIndex.this, ChangePass.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.logout: {
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(mainIndex.this);
                        builder.setTitle("Are you sure?");
                        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(mainIndex.this, "Successfully Signed Out.", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(mainIndex.this, login_page.class));
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(mainIndex.this, "cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                        return true;
                    }
                }
                return false;
            }
        });


        //Search btn
        Button search = (Button) findViewById(R.id.btnReserve);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mainIndex.this, date_selection_page.class);
                startActivity(i);
            }
        });
    }



    //url for location
    private void gotoUrl(String location) {
        Uri uri=Uri.parse((location));
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        if(firebaseUser!=null){

        }else{
            startActivity(new Intent(mainIndex.this,login_page.class));
            finish();
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