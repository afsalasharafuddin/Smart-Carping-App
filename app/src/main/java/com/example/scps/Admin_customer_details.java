package com.example.scps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class Admin_customer_details extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    private DatePickerDialog datePickerDialog;
    Button dateButton;


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
        setContentView(R.layout.activity_admin_view_user);



        //date picker
        initDatePicker();
        dateButton=findViewById(R.id.datePicker);
        dateButton.setText(getTodaysDate());


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
                        Intent i = new Intent(Admin_customer_details.this, Admin_index.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.viewuser: {
                        Intent i = new Intent(Admin_customer_details.this, Admin_view_user.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.viewcustomer: {
                        Intent i = new Intent(Admin_customer_details.this,Admin_customer_details.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.edit_user: {
                        Intent i = new Intent(Admin_customer_details.this, Edit_user.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.addarea: {
                        Intent i = new Intent(Admin_customer_details.this, Add_area.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.addslot: {
                        Intent i = new Intent(Admin_customer_details.this, Add_slot.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.passChange: {
                        Intent i = new Intent(Admin_customer_details.this, Admin_change.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.logout: {
                        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(Admin_customer_details.this);
                        builder.setTitle("Are you sure?");
                        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Admin_customer_details.this, "Successfully Signed Out.", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(Admin_customer_details.this, login_page.class));
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Admin_customer_details.this, "cancelled", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                        return true;
                    }
                }
                return false;
            }
        });

        //btn search

        Button search = (Button) findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String today = getTodaysDate();
                String Selecteddate = dateButton.getText().toString();
                String[] splitTodaysDate = today.split(" ");
                String TodaysMonth = splitTodaysDate[0];
                int TodayMnth = 0;
                if (TodaysMonth.equals("JAN")) {
                    TodayMnth = 1;
                }
                if (TodaysMonth.equals("FEB")) {
                    TodayMnth = 2;
                }
                if (TodaysMonth.equals("MAR")) {
                    TodayMnth = 3;
                }
                if (TodaysMonth.equals("APR")) {
                    TodayMnth = 4;
                }
                if (TodaysMonth.equals("MAY")) {
                    TodayMnth = 5;
                }
                if (TodaysMonth.equals("JUN")) {
                    TodayMnth = 6;
                }
                if (TodaysMonth.equals("JUL")) {
                    TodayMnth = 7;
                }
                if (TodaysMonth.equals("AUG")) {
                    TodayMnth = 8;
                }
                if (TodaysMonth.equals("SEP")) {
                    TodayMnth = 9;
                }
                if (TodaysMonth.equals("OCT")) {
                    TodayMnth = 10;
                }
                if (TodaysMonth.equals("NOV")) {
                    TodayMnth = 11;
                }
                if (TodaysMonth.equals("DEC")) {
                    TodayMnth = 12;
                }
                int todaysDay = Integer.valueOf(splitTodaysDate[1]);
                int todaysYear = Integer.valueOf(splitTodaysDate[2]);
                String[] splittedDate = Selecteddate.split(" ");
                String month = splittedDate[0];
                int mnth = 0;
                if (month.equals("JAN")) {
                    mnth = 1;
                }
                if (month.equals("FEB")) {
                    mnth = 2;
                }
                if (month.equals("MAR")) {
                    mnth = 3;
                }
                if (month.equals("APR")) {
                    mnth = 04;
                }
                if (month.equals("MAY")) {
                    mnth = 5;
                }
                if (month.equals("JUN")) {
                    mnth = 6;
                }
                if (month.equals("JUL")) {
                    mnth = 7;
                }
                if (month.equals("AUG")) {
                    mnth = 8;
                }
                if (month.equals("SEP")) {
                    mnth = 9;
                }
                if (month.equals("OCT")) {
                    mnth = 10;
                }
                if (month.equals("NOV")) {
                    mnth = 11;
                }
                if (month.equals("DEC")) {
                    mnth = 12;
                }
                int day = Integer.valueOf(splittedDate[1]);
                int year = Integer.valueOf(splittedDate[2]);
                if (year <= todaysYear) {
                    if(year == todaysYear){
                        if (mnth <= TodayMnth){
                            if(mnth == TodayMnth){
                                if(day<=todaysDay){
                                    //database
                                } else{
                                    Toast.makeText(Admin_customer_details.this, "Invalid Day", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                //database
                            }
                        } else{
                            Toast.makeText(Admin_customer_details.this, "Invalid month", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        //database
                    }
                }
                else {
                    Toast.makeText(Admin_customer_details.this, "Invalid Year", Toast.LENGTH_SHORT).show();
                }
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

    private String getTodaysDate() {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        month=month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                String date=makeDateString(day,month,year);
                dateButton.setText(date);

            }
        };
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month)+" "+day+" "+year;
        //return month+" "+day+" "+year;
    }

    private String getMonthFormat(int month) {
        if(month == 1){
            return "JAN";
        }if(month == 2){
            return "FEB";
        }if(month == 3){
            return "MAR";
        }if(month == 4){
            return "APR";
        }if(month == 5){
            return "MAY";
        }if(month == 6){
            return "JUN";
        }if(month == 7){
            return "JUL";
        }if(month == 8){
            return "AUG";
        }if(month == 9){
            return "SEP";
        }if(month == 10){
            return "OCT";
        }if(month == 11){
            return "NOV";
        }if(month == 12){
            return "DEC";
        }
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

}
