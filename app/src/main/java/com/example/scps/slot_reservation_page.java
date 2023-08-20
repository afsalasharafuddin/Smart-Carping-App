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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class slot_reservation_page extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    ImageView s1,s2,s3,s4,s5,s6,s7,s8;
    //area dropdown
    FirebaseAuth mAuth;
    FirebaseUser cUser;
    DatabaseReference reference,reference1;
    String userId,userName;
    TextView hName,hEmail;

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
        setContentView(R.layout.activity_slot_reservation_page);


        s1=findViewById(R.id.s1);
        s1.setBackgroundResource(R.drawable.big_free_slot);
        s2=findViewById(R.id.s2);
        s2.setBackgroundResource(R.drawable.big_free_slot);
        s3=findViewById(R.id.s3);
        s3.setBackgroundResource(R.drawable.big_free_slot);
        s4=findViewById(R.id.s4);
        s4.setBackgroundResource(R.drawable.big_free_slot);
        s5=findViewById(R.id.s5);
        s5.setBackgroundResource(R.drawable.big_free_slot);
        s6=findViewById(R.id.s6);
        s6.setBackgroundResource(R.drawable.big_r_slot);
        s7=findViewById(R.id.s7);
        s7.setBackgroundResource(R.drawable.big_r_slot);
        s8=findViewById(R.id.s8);
        s8.setBackgroundResource(R.drawable.big_r_slot);


        //Getting user name
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();


        //getting userid
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userId = currentFirebaseUser.getUid();
        updateNavHeader();


        SimpleDateFormat timeFormat=new SimpleDateFormat("hh:mm", Locale.getDefault());
        String date=getIntent().getStringExtra("keydate");
        String stime=getIntent().getStringExtra("keyStartTime");
        String etime=getIntent().getStringExtra("keyEndTime");
        String Oetime=getIntent().getStringExtra("etimewithhalf");
        String[] splittedStime = stime.split(":");
        String[] splittedEtime = etime.split(":");
        int sHover=Integer.valueOf(splittedStime[0]);
        int sMint=Integer.valueOf(splittedStime[1]);
        int eHover=Integer.valueOf(splittedEtime[0]);
        int eMint=Integer.valueOf(splittedEtime[1]);
        String vNumber=getIntent().getStringExtra("keyVehicleNumber");
        String totalTime=getIntent().getStringExtra("keyTotalTime");
        String TotlMinute=getIntent().getStringExtra("keyTotalMinute");

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("reservation_details").child(date);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    String startTime = String.valueOf(snapshot.child("startTime").getValue());
                    String endTime = String.valueOf(snapshot.child("endTime").getValue());
                    String slot = String.valueOf(snapshot.child("slot").getValue());
                    try {
                        String string1 = startTime;
                        Date time1 = new SimpleDateFormat("HH:mm").parse(string1);

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.setTime(time1);
                        calendar1.add(Calendar.DATE, 1);


                        String string2 = endTime;
                        Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.setTime(time2);
                        calendar2.add(Calendar.DATE, 1);

                        String enteredStartTime = stime;
                        Date time4 = new SimpleDateFormat("HH:mm").parse(enteredStartTime);
                        Calendar calendar3 = Calendar.getInstance();
                        calendar3.setTime(time4);
                        calendar3.add(Calendar.DATE, 1);

                        String enteredEndTime = etime;
                        Date time5 = new SimpleDateFormat("HH:mm").parse(enteredEndTime);
                        Calendar calendar4 = Calendar.getInstance();
                        calendar4.setTime(time5);
                        calendar4.add(Calendar.DATE, 1);

                        Date x = calendar3.getTime();
                        Date y = calendar4.getTime();
                        if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                            if(slot.equals("s6")){
                                s6.setBackgroundResource(R.drawable.bigreserved_slot);
                                s6.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(slot_reservation_page.this, "This slot is aldrady reserved.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if(slot.equals("s7")){
                                s7.setBackgroundResource(R.drawable.bigreserved_slot);
                                s7.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(slot_reservation_page.this, "This slot is aldrady reserved.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else if(slot.equals("s8")){
                                s8.setBackgroundResource(R.drawable.bigreserved_slot);
                                s8.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(slot_reservation_page.this, "This slot is aldrady reserved.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else{
                                Toast.makeText(slot_reservation_page.this, "hy", Toast.LENGTH_SHORT).show();
                            }

                        } else if(y.after(calendar1.getTime()) && y.before(calendar2.getTime())) {
                            if(slot.equals("s6")){
                                s6.setBackgroundResource(R.drawable.bigreserved_slot);
                                s6.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(slot_reservation_page.this, "This slot is aldrady reserved.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else if(slot.equals("s7")){
                                s7.setBackgroundResource(R.drawable.bigreserved_slot);
                                s7.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(slot_reservation_page.this, "This slot is aldrady reserved.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else if(slot.equals("s8")){
                                s8.setBackgroundResource(R.drawable.bigreserved_slot);
                                s8.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(slot_reservation_page.this, "This slot is aldrady reserved.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else{
                                Toast.makeText(slot_reservation_page.this, "hy", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        int totalMinute=Integer.parseInt(TotlMinute);
        double amount=((double) totalMinute/60)*10;
        double amt1=Math.floor(amount);
        String amt=String.valueOf(amt1);

        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(slot_reservation_page.this, "This slot cannot be reserved.", Toast.LENGTH_SHORT).show();
            }
        });
        s2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(slot_reservation_page.this, "This slot cannot be reserved.", Toast.LENGTH_SHORT).show();
            }
        });
        s3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(slot_reservation_page.this, "This slot cannot be reserved.", Toast.LENGTH_SHORT).show();
            }
        });
        s4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(slot_reservation_page.this, "This slot cannot be reserved.", Toast.LENGTH_SHORT).show();
            }
        });
        s5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(slot_reservation_page.this, "This slot cannot be reserved.", Toast.LENGTH_SHORT).show();
            }
        });
        s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(slot_reservation_page.this, "This slot cannot be reserved.", Toast.LENGTH_SHORT).show();
            }
        });
        s7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(slot_reservation_page.this, "This slot cannot be reserved.", Toast.LENGTH_SHORT).show();
            }
        });


        s6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(slot_reservation_page.this, bookingPage.class);
                i.putExtra("keydate",date);
                i.putExtra("keyStartTime",stime);
                i.putExtra("keyEndTime",etime);
                i.putExtra("etimewithhalf",Oetime);
                i.putExtra("keyslot","s6");
                i.putExtra("keyVehicleNumber",vNumber);
                i.putExtra("keyTotalTime",totalTime);
                i.putExtra("keyAmount",amt);
                startActivity(i);
                }
        });
        s7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(slot_reservation_page.this, bookingPage.class);
                i.putExtra("keydate",date);
                i.putExtra("keyStartTime",stime);
                i.putExtra("keyEndTime",etime);
                i.putExtra("etimewithhalf",Oetime);
                i.putExtra("keyslot","s7");
                i.putExtra("keyVehicleNumber",vNumber);
                i.putExtra("keyTotalTime",totalTime);
                i.putExtra("keyAmount",amt);
                startActivity(i);
            }
        });
        s8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(slot_reservation_page.this, bookingPage.class);
                i.putExtra("keydate",date);
                i.putExtra("keyStartTime",stime);
                i.putExtra("keyEndTime",etime);
                i.putExtra("etimewithhalf",Oetime);
                i.putExtra("keyslot","s8");
                i.putExtra("keyVehicleNumber",vNumber);
                i.putExtra("keyTotalTime",totalTime);
                i.putExtra("keyAmount",amt);
                startActivity(i);
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
                        Intent i=new Intent(slot_reservation_page.this,mainIndex.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.about:
                    {
                        Intent i=new Intent(slot_reservation_page.this,aboutPage.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.history:
                    {
                        Intent i=new Intent(slot_reservation_page.this,HistoryPage.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.passChange:
                    {
                        Intent i=new Intent(slot_reservation_page.this,ChangePass.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.logout:
                    {
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(slot_reservation_page.this);
                        builder.setTitle("Are you sure?");
                        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(slot_reservation_page.this, "Successfully Signed Out.", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(slot_reservation_page.this, login_page.class));
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(slot_reservation_page.this, "cancelled", Toast.LENGTH_SHORT).show();
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