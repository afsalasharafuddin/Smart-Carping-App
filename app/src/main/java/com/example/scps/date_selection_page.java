package com.example.scps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class date_selection_page extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    private DatePickerDialog datePickerDialog;
    Button dateButton,stime,etime;
    int hour,minute;
    FirebaseAuth mAuth;
    FirebaseUser cUser;
    DatabaseReference reference,reference1;
    String userId,userName;
    TextView hName,hEmail;
    EditText vehicle_num;
    int Mint,Hvr,TotalMinute;
    String mint,hvr,comTime,tmint;
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
        setContentView(R.layout.activity_date_selection_page);

        //Getting user name
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();


        //getting userid
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userId = currentFirebaseUser.getUid();
        updateNavHeader();


        //date picker
        initDatePicker();
        dateButton=findViewById(R.id.datePicker);
        dateButton.setText(getTodaysDate());

        //time selector
        Date dateAndTime = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm",Locale.getDefault());
        String currentTime= timeFormat.format(dateAndTime);
        stime=findViewById(R.id.startTime);
        stime.setText(currentTime);
        etime=findViewById(R.id.endTime);
        etime.setText(currentTime);


        vehicle_num=findViewById(R.id.vNo);
        vehicle_num.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

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
                        Intent i=new Intent(date_selection_page.this,mainIndex.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.about:
                    {
                        Intent i=new Intent(date_selection_page.this,aboutPage.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.history:
                    {
                        Intent i=new Intent(date_selection_page.this,HistoryPage.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.passChange:
                    {
                        Intent i=new Intent(date_selection_page.this,ChangePass.class);
                        startActivity(i);
                        finish();
                        break;
                    }
                    case R.id.logout:
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(date_selection_page.this);
                        builder.setTitle("Are you sure?");
                        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(date_selection_page.this, "Successfully Signed Out.", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(date_selection_page.this, login_page.class));
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(date_selection_page.this, "cancelled", Toast.LENGTH_SHORT).show();
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
        Button search = (Button) findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vnum=vehicle_num.getText().toString();
                if (vnum.isEmpty()){
                    vehicle_num.setError("Enter Vehicle number");
                    vehicle_num.requestFocus();
                } else{
                    String today=getTodaysDate();
                    String Selecteddate= dateButton.getText().toString();
                    String[] splitTodaysDate = today.split(" ");
                    String TodaysMonth=splitTodaysDate[0];
                    int TodayMnth=0;
                    if(TodaysMonth.equals("JAN")){
                        TodayMnth= 1;
                    }if(TodaysMonth.equals("FEB")){
                        TodayMnth=2;
                    }if(TodaysMonth.equals("MAR")){
                        TodayMnth=3;
                    }if(TodaysMonth.equals("APR")){
                        TodayMnth=04;
                    }if(TodaysMonth.equals("MAY")){
                        TodayMnth=5;
                    }if(TodaysMonth.equals("JUN")){
                        TodayMnth=6;
                    }if(TodaysMonth.equals("JUL")){
                        TodayMnth=7;
                    }if(TodaysMonth.equals("AUG")){
                        TodayMnth= 8;
                    }if(TodaysMonth.equals("SEP")){
                        TodayMnth= 9;
                    }if(TodaysMonth.equals("OCT")){
                        TodayMnth=10;
                    }if(TodaysMonth.equals("NOV")){
                        TodayMnth=11;
                    }if(TodaysMonth.equals("DEC")){
                        TodayMnth=12;
                    }
                    int todaysDay=Integer.valueOf(splitTodaysDate[1]);
                    int todaysYear=Integer.valueOf(splitTodaysDate[2]);
                    String[] splittedDate = Selecteddate.split(" ");
                    String month=splittedDate[0];
                    int mnth=0;
                    if(month.equals("JAN")){
                        mnth= 1;
                    }if(month.equals("FEB")){
                        mnth=2;
                    }if(month.equals("MAR")){
                        mnth=3;
                    }if(month.equals("APR")){
                        mnth=04;
                    }if(month.equals("MAY")){
                        mnth=5;
                    }if(month.equals("JUN")){
                        mnth=6;
                    }if(month.equals("JUL")){
                        mnth=7;
                    }if(month.equals("AUG")){
                        mnth= 8;
                    }if(month.equals("SEP")){
                        mnth= 9;
                    }if(month.equals("OCT")){
                        mnth=10;
                    }if(month.equals("NOV")){
                        mnth=11;
                    }if(month.equals("DEC")){
                        mnth=12;
                    }
                    int day=Integer.valueOf(splittedDate[1]);
                    int year = Integer.valueOf(splittedDate[2]);
                    if(year==todaysYear){
                        if(mnth>=TodayMnth){
                            if(mnth==TodayMnth){
                                if(day==todaysDay){
                                    String t1="00:30";
                                    String startTime= stime.getText().toString();
                                    String endTime= etime.getText().toString();


                                    SimpleDateFormat timeformat= new SimpleDateFormat("HH:mm");
                                    timeformat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                    String d3;
                                    try {
                                        Date d1=timeformat.parse(endTime);
                                        Date d2=timeformat.parse(t1);
                                        long sum= d1.getTime()+d2.getTime();
                                        d3=timeformat.format(new Date(sum));
                                    } catch (ParseException e) {
                                        throw new RuntimeException(e);
                                    }


                                    String[] splittedStime = startTime.split(":");
                                    String[] splittedEtime = endTime.split(":");
                                    int sHover=Integer.valueOf(splittedStime[0]);
                                    int sMint=Integer.valueOf(splittedStime[1]);
                                    int eHover=Integer.valueOf(splittedEtime[0]);
                                    int eMint=Integer.valueOf(splittedEtime[1]);

                                    String[] splittedCtime = currentTime.split(":");
                                    int cHover=Integer.valueOf(splittedCtime[0]);
                                    int cMint=Integer.valueOf(splittedCtime[1]);
                                    if(sHover>=cHover){
                                        if(sHover==cHover){
                                            if(sMint<cMint){
                                                Toast.makeText(date_selection_page.this, "Select a valid start time.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if(eHover>=sHover){
                                                    Date d1=null;
                                                    Date d2=null;
                                                    try {
                                                        d1=timeFormat.parse(startTime);
                                                        d2=timeFormat.parse(endTime);
                                                    } catch (ParseException e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                    long diff=d2.getTime() - d1.getTime();
                                                    Long diffMint=diff/(60*1000)%60;
                                                    Long diffHvr=diff/(60*60*1000)%12;
                                                    Mint=diffMint.intValue();
                                                    Hvr=diffHvr.intValue();
                                                    TotalMinute=(Hvr*60)+Mint;
                                                    tmint=String.valueOf(TotalMinute);
                                                    mint=String.valueOf(diffMint);
                                                    hvr=String.valueOf(diffHvr);
                                                    comTime=hvr+" Hover "+mint+" Minute";
                                                    if(diffMint.longValue()>=30 || diffHvr.longValue()>0){
                                                        Intent i = new Intent(date_selection_page.this, slot_reservation_page.class);
                                                        i.putExtra("keydate",Selecteddate);
                                                        i.putExtra("keyStartTime",startTime);
                                                        i.putExtra("keyEndTime",endTime);
                                                        i.putExtra("etimewithhalf",d3);
                                                        i.putExtra("keyVehicleNumber",vnum);
                                                        i.putExtra("keyTotalMinute",tmint);
                                                        i.putExtra("keyTotalTime",comTime);
                                                        startActivity(i);
                                                    }else{
                                                        Toast.makeText(date_selection_page.this, "30 minute is the minimum reservation time.", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else{
                                                    Toast.makeText(date_selection_page.this, "Enter a valid end time.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } else{
                                            if(eHover>=sHover){
                                                Date d1=null;
                                                Date d2=null;
                                                try {
                                                    d1=timeFormat.parse(startTime);
                                                    d2=timeFormat.parse(endTime);
                                                } catch (ParseException e) {
                                                    throw new RuntimeException(e);
                                                }
                                                long diff=d2.getTime() - d1.getTime();
                                                Long diffMint=diff/(60*1000)%60;
                                                Long diffHvr=diff/(60*60*1000)%12;
                                                Mint=diffMint.intValue();
                                                Hvr=diffHvr.intValue();
                                                TotalMinute=(Hvr*60)+Mint;
                                                tmint=String.valueOf(TotalMinute);
                                                mint=String.valueOf(diffMint);
                                                hvr=String.valueOf(diffHvr);
                                                comTime=hvr+" Hover "+mint+" Minute";
                                                if(diffMint.longValue()>=30 || diffHvr.longValue()>0){
                                                    Intent i = new Intent(date_selection_page.this, slot_reservation_page.class);
                                                    i.putExtra("keydate",Selecteddate);
                                                    i.putExtra("keyStartTime",startTime);
                                                    i.putExtra("keyEndTime",endTime);
                                                    i.putExtra("etimewithhalf",d3);
                                                    i.putExtra("keyVehicleNumber",vnum);
                                                    i.putExtra("keyTotalMinute",tmint);
                                                    i.putExtra("keyTotalTime",comTime);
                                                    startActivity(i);
                                                }else{
                                                    Toast.makeText(date_selection_page.this, "30 minute is the minimum reservation time.", Toast.LENGTH_SHORT).show();
                                                }
                                            } else{
                                                Toast.makeText(date_selection_page.this, "Enter a valid end time.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } else{
                                        Toast.makeText(date_selection_page.this, "Select a valid start time.", Toast.LENGTH_SHORT).show();
                                        stime.setError("Error");
                                    }
                                }else if(day>todaysDay) {
                                    String t1="00:30";
                                    String startTime= stime.getText().toString();
                                    String endTime= etime.getText().toString();
                                    SimpleDateFormat timeformat= new SimpleDateFormat("HH:mm");
                                    timeformat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                    String d3;
                                    try {
                                        Date d1=timeformat.parse(endTime);
                                        Date d2=timeformat.parse(t1);
                                        long sum= d1.getTime()+d2.getTime();
                                        d3=timeformat.format(new Date(sum));
                                    } catch (ParseException e) {
                                        throw new RuntimeException(e);
                                    }
                                    String[] splittedStime = startTime.split(":");
                                    String[] splittedEtime = endTime.split(":");
                                    int sHover=Integer.valueOf(splittedStime[0]);
                                    int sMint=Integer.valueOf(splittedStime[1]);
                                    int eHover=Integer.valueOf(splittedEtime[0]);
                                    int eMint=Integer.valueOf(splittedEtime[1]);

                                    String[] splittedCtime = currentTime.split(":");
                                    int cHover=Integer.valueOf(splittedCtime[0]);
                                    int cMint=Integer.valueOf(splittedCtime[1]);

                                    if(eHover>=sHover){
                                        Date d1=null;
                                        Date d2=null;
                                        try {
                                            d1=timeFormat.parse(startTime);
                                            d2=timeFormat.parse(endTime);
                                        } catch (ParseException e) {
                                            throw new RuntimeException(e);
                                        }
                                        long diff=d2.getTime() - d1.getTime();
                                        Long diffMint=diff/(60*1000)%60;
                                        Long diffHvr=diff/(60*60*1000)%12;
                                        Mint=diffMint.intValue();
                                        Hvr=diffHvr.intValue();
                                        TotalMinute=(Hvr*60)+Mint;
                                        tmint=String.valueOf(TotalMinute);
                                        mint=String.valueOf(diffMint);
                                        hvr=String.valueOf(diffHvr);
                                        comTime=hvr+" Hover "+mint+" Minute";
                                        if(diffMint.longValue()>=30 || diffHvr.longValue()>0){
                                            Intent i = new Intent(date_selection_page.this, slot_reservation_page.class);
                                            i.putExtra("keydate",Selecteddate);
                                            i.putExtra("keyStartTime",startTime);
                                            i.putExtra("keyEndTime",endTime);
                                            i.putExtra("etimewithhalf",d3);
                                            i.putExtra("etimewithhalf",d3);
                                            i.putExtra("keyVehicleNumber",vnum);
                                            i.putExtra("keyTotalMinute",tmint);
                                            i.putExtra("keyTotalTime",comTime);
                                            startActivity(i);
                                        }else{
                                            Toast.makeText(date_selection_page.this, "30 minute is the minimum reservation time.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else{
                                        Toast.makeText(date_selection_page.this, "Enter a valid end time.", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(date_selection_page.this, "Select a valid day. ", Toast.LENGTH_SHORT).show();
                                    dateButton.setError("error");
                                    dateButton.requestFocus();
                                }
                            } else{
                                String t1="00:30";
                                String startTime= stime.getText().toString();
                                String endTime= etime.getText().toString();

                                SimpleDateFormat timeformat= new SimpleDateFormat("HH:mm");
                                timeformat.setTimeZone(TimeZone.getTimeZone("UTC"));
                                String d3;
                                try {
                                    Date d1=timeformat.parse(endTime);
                                    Date d2=timeformat.parse(t1);
                                    long sum= d1.getTime()+d2.getTime();
                                    d3=timeformat.format(new Date(sum));
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }

                                String[] splittedStime = startTime.split(":");
                                String[] splittedEtime = endTime.split(":");
                                int sHover=Integer.valueOf(splittedStime[0]);
                                int sMint=Integer.valueOf(splittedStime[1]);
                                int eHover=Integer.valueOf(splittedEtime[0]);
                                int eMint=Integer.valueOf(splittedEtime[1]);

                                String[] splittedCtime = currentTime.split(":");
                                int cHover=Integer.valueOf(splittedCtime[0]);
                                int cMint=Integer.valueOf(splittedCtime[1]);
                                if(eHover>=sHover){
                                    Date d1=null;
                                    Date d2=null;
                                    try {
                                        d1=timeFormat.parse(startTime);
                                        d2=timeFormat.parse(endTime);
                                    } catch (ParseException e) {
                                        throw new RuntimeException(e);
                                    }
                                    long diff=d2.getTime() - d1.getTime();
                                    Long diffMint=diff/(60*1000)%60;
                                    Long diffHvr=diff/(60*60*1000)%12;
                                    Mint=diffMint.intValue();
                                    Hvr=diffHvr.intValue();
                                    TotalMinute=(Hvr*60)+Mint;
                                    tmint=String.valueOf(TotalMinute);
                                    mint=String.valueOf(diffMint);
                                    hvr=String.valueOf(diffHvr);
                                    comTime=hvr+" Hover "+mint+" Minute";
                                    if(diffMint.longValue()>=30 || diffHvr.longValue()>0){
                                        Intent i = new Intent(date_selection_page.this, slot_reservation_page.class);
                                        i.putExtra("keydate",Selecteddate);
                                        i.putExtra("keyStartTime",startTime);
                                        i.putExtra("keyEndTime",endTime);
                                        i.putExtra("etimewithhalf",d3);
                                        i.putExtra("keyVehicleNumber",vnum);
                                        i.putExtra("keyTotalMinute",tmint);
                                        i.putExtra("keyTotalTime",comTime);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(date_selection_page.this, "30 minute is the minimum reservation time.", Toast.LENGTH_SHORT).show();
                                    }
                                } else{
                                    Toast.makeText(date_selection_page.this, "Enter a valid end time.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else{
                            Toast.makeText(date_selection_page.this, "Select valid month", Toast.LENGTH_SHORT).show();
                        }

                    }else if(year>todaysYear){
                        String t1="00:30";
                        String startTime= stime.getText().toString();
                        String endTime= etime.getText().toString();

                        SimpleDateFormat timeformat= new SimpleDateFormat("HH:mm");
                        timeformat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        String d3;
                        try {
                            Date d1=timeformat.parse(endTime);
                            Date d2=timeformat.parse(t1);
                            long sum= d1.getTime()+d2.getTime();
                            d3=timeformat.format(new Date(sum));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                                    String[] splittedStime = startTime.split(":");
                                    String[] splittedEtime = endTime.split(":");
                                    int sHover=Integer.valueOf(splittedStime[0]);
                                    int sMint=Integer.valueOf(splittedStime[1]);
                                    int eHover=Integer.valueOf(splittedEtime[0]);
                                    int eMint=Integer.valueOf(splittedEtime[1]);

                                    String[] splittedCtime = currentTime.split(":");
                                    int cHover=Integer.valueOf(splittedCtime[0]);
                                    int cMint=Integer.valueOf(splittedCtime[1]);
                                    if(eHover>=sHover){
                                        Date d1=null;
                                        Date d2=null;
                                        try {
                                            d1=timeFormat.parse(startTime);
                                            d2=timeFormat.parse(endTime);
                                        } catch (ParseException e) {
                                            throw new RuntimeException(e);
                                        }
                                        long diff=d2.getTime() - d1.getTime();
                                        Long diffMint=diff/(60*1000)%60;
                                        Long diffHvr=diff/(60*60*1000)%12;
                                        Mint=diffMint.intValue();
                                        Hvr=diffHvr.intValue();
                                        TotalMinute=(Hvr*60)+Mint;
                                        tmint=String.valueOf(TotalMinute);
                                        mint=String.valueOf(diffMint);
                                        hvr=String.valueOf(diffHvr);
                                        comTime=hvr+" Hover "+mint+" Minute";
                                        if(diffMint.longValue()>=30 || diffHvr.longValue()>0){
                                            Intent i = new Intent(date_selection_page.this, slot_reservation_page.class);
                                            i.putExtra("keydate",Selecteddate);
                                            i.putExtra("keyStartTime",startTime);
                                            i.putExtra("keyEndTime",endTime);
                                            i.putExtra("etimewithhalf",d3);
                                            i.putExtra("keyVehicleNumber",vnum);
                                            i.putExtra("keyTotalMinute",tmint);
                                            i.putExtra("keyTotalTime",comTime);
                                            startActivity(i);
                                        }else{
                                            Toast.makeText(date_selection_page.this, "30 minute is the minimum reservation time.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else{
                                        Toast.makeText(date_selection_page.this, "Enter a valid end time.", Toast.LENGTH_SHORT).show();
                                    }
                    }

                    else{
                        Toast.makeText(date_selection_page.this, "Select valid Year.", Toast.LENGTH_SHORT).show();
                    }
                }
                }

        });

    }


    //date picker
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



    //time picker

    public void startTimePopTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour =selectedHour;
                minute=selectedMinute;
                stime.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("select time");
        timePickerDialog.show();
    }
    public void endTimePopTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour =selectedHour;
                minute=selectedMinute;
                etime.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, onTimeSetListener,hour,minute,true);
        timePickerDialog.setTitle("select time");
        timePickerDialog.show();
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