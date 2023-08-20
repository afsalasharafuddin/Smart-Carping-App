package com.example.scps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.Locale;

public class bookingPage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Button dateButton,startTime,endTime,totalTime,selectedSlot,vehicleNo,totalAmount,btnSearch;
    private DatePickerDialog datePickerDialog;
    int hour,minute;
    FirebaseAuth mAuth;
    FirebaseUser cUser;
    DatabaseReference reference,reference1,reservation_details,reservation_details1;
    String userId,userName;
    TextView hName,hEmail;
    AppCompatButton btn_success;

    //dialog varibles
    AlertDialog.Builder builderDialog;
    AlertDialog alertDialog;
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
        setContentView(R.layout.activity_booking_page);





        //Getting user name
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();


        //getting userid
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userId = currentFirebaseUser.getUid();
        updateNavHeader();



        //date picker
        dateButton=findViewById(R.id.datePicker);

        //time selector
        startTime=findViewById(R.id.startTime);
        endTime=findViewById(R.id.endTime);
        totalTime=findViewById(R.id.totalTime);
        selectedSlot=findViewById(R.id.selectedSlot);
        vehicleNo = findViewById(R.id.vehicleNo);
        totalAmount= findViewById(R.id.fee);
        btnSearch=findViewById(R.id.btnSearch);

        String date=getIntent().getStringExtra("keydate");
        String stime=getIntent().getStringExtra("keyStartTime");
        String etime=getIntent().getStringExtra("keyEndTime");
        String slot=getIntent().getStringExtra("keyslot");
        String vNumber=getIntent().getStringExtra("keyVehicleNumber");
        String tTime=getIntent().getStringExtra("keyTotalTime");
        String amount=getIntent().getStringExtra("keyAmount");
        dateButton.setText(date);
        startTime.setText(stime);
        endTime.setText(etime);
        selectedSlot.setText(slot);
        vehicleNo.setText(vNumber);
        totalTime.setText(tTime);
        totalAmount.setText(amount);

        btn_success=(AppCompatButton)findViewById(R.id.btnSearch) ;
        //inserting data into database
        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean a=insertReservationData();
                if(a==true){
                    showAlertDialog(R.layout.my_success_dialog);
                }
            }
        });

        //menu bar
        drawerLayout =findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                    {
                        Intent i=new Intent(bookingPage.this,mainIndex.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.about:
                    {
                        Intent i=new Intent(bookingPage.this,aboutPage.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.history:
                    {
                        Intent i=new Intent(bookingPage.this,HistoryPage.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.passChange:
                    {
                        Intent i=new Intent(bookingPage.this,ChangePass.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.logout:
                    {
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(bookingPage.this);
                        builder.setTitle("Are you sure?");
                        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(bookingPage.this, "Successfully Signed Out.", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(bookingPage.this, login_page.class));
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(bookingPage.this, "cancelled", Toast.LENGTH_SHORT).show();
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

    private void showAlertDialog(int myLayout) {
        builderDialog = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(myLayout,null);

        AppCompatButton dialogButton = layoutView.findViewById(R.id.btnok);
        builderDialog.setView(layoutView);
        alertDialog =builderDialog.create();
        alertDialog.show();
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(bookingPage.this,mainIndex.class));
                finish();
            }
        });
    }

    //inserting data
    private boolean insertReservationData() {
        String date=getIntent().getStringExtra("keydate");
        String stime=getIntent().getStringExtra("keyStartTime");
        String etime=getIntent().getStringExtra("keyEndTime");
        String Oetime=getIntent().getStringExtra("etimewithhalf");
        String slot=getIntent().getStringExtra("keyslot");
        String vNumber=getIntent().getStringExtra("keyVehicleNumber");

        String tTime=getIntent().getStringExtra("keyTotalTime");
        String amount=getIntent().getStringExtra("keyAmount");
        reservation_details =FirebaseDatabase.getInstance().getReference().child("reservation_details/"+date+"/");
        reservationDetails details= new reservationDetails(userId,vNumber,date,stime,Oetime,tTime,slot,amount);
        reservation_details.push().setValue(details);

        reservation_details1 =FirebaseDatabase.getInstance().getReference().child("reservation_history/");
        reservationDetails details1= new reservationDetails(userId,vNumber,date,stime,Oetime,tTime,slot,amount);
        reservation_details1.push().setValue(details1);
        return true;
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