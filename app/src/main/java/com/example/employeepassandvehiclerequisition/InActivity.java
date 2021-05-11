package com.example.employeepassandvehiclerequisition;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class InActivity extends AppCompatActivity {

    AlertDialog alert;

    AlertDialog.Builder altdial;

    TextView tViewIn_ControlNo, tViewIn_OBDate, tViewIn_RequestedBy, tViewIn_Driver, tvHeader2, tViewIn_PlateNo, tViewIn_Destination, tViewIn_Justification;

    ListView listViewPassengersIn;

    Button btnMarkAsArrived, btnINDetails, btnInPassengers;

    ImageButton btnGetDate_In, btnGetTime_In;

    EditText txtRemarks, txtDate_In, txtTime_In;

    LinearLayout divDetailsIn, divPassengersIN;

    public String code, controlNo, fullname, stat, newDate, newTime;

    ClsCustomPassengers clsCustomPassengers;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    Calendar cal = Calendar.getInstance();

    DBAccess dbAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_in);

        setTitle("REQUEST DETAILS");

        controlNo = getIntent().getExtras().getString("ControlNo");

        fullname = getIntent().getExtras().getString("fullname");

        stat = getIntent().getExtras().getString("stat");

        tvHeader2 = (TextView) findViewById(R.id.tvHeader2);

        btnINDetails = (Button) findViewById(R.id.btnINDetails);

        btnInPassengers = (Button) findViewById(R.id.btnInPassengers);

        divDetailsIn = (LinearLayout) findViewById(R.id.divDetailsIn);

        divPassengersIN = (LinearLayout) findViewById(R.id.divPassengersIN);


        tViewIn_ControlNo = (TextView) findViewById(R.id.tViewIn_ControlNo);

        tViewIn_OBDate = (TextView) findViewById(R.id.tViewIn_OBDate);

        tViewIn_RequestedBy = (TextView) findViewById(R.id.tViewIn_RequestedBy);

        tViewIn_PlateNo = (TextView) findViewById(R.id.tViewIn_PlateNo);

        tViewIn_Destination = (TextView) findViewById(R.id.tViewIn_Destination);

        tViewIn_Justification = (TextView) findViewById(R.id.tViewIn_Justification);


        tViewIn_Driver = (TextView) findViewById(R.id.tViewIn_Driver);

        txtDate_In = (EditText) findViewById(R.id.txtDate_In);

        txtTime_In = (EditText) findViewById(R.id.txtTime_In);

        listViewPassengersIn = (ListView) findViewById(R.id.listViewPassengersIn);

        btnMarkAsArrived = (Button) findViewById(R.id.btnMarkAsArrived);

        btnGetDate_In = (ImageButton) findViewById(R.id.btnGetDate_In);

        btnGetTime_In = (ImageButton) findViewById(R.id.btnGetTime_In);

        txtRemarks = (EditText) findViewById(R.id.txtRemarks);


        divPassengersIN.setVisibility(View.GONE);
        divDetailsIn.setVisibility(View.VISIBLE);


        btnMarkAsArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                altdial = new AlertDialog.Builder(InActivity.this);

                altdial.setMessage("Confirm Action").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (newDate != "" && newTime != "") {
                                    UpdateDepartAndArrival();
                                } else {
                                    Toast.makeText(InActivity.this, "Must Select Date and Time", Toast.LENGTH_LONG).show();
                                }
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                alert = altdial.create();

                alert.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;

                String date = "", newmonth = "", newday = "", monthName = "";

                switch (month) {
                    case 1:
                        monthName = "January";
                        break;
                    case 2:
                        monthName = "February";
                        break;
                    case 3:
                        monthName = "March";
                        break;
                    case 4:
                        monthName = "April";
                        break;
                    case 5:
                        monthName = "May";
                        break;
                    case 6:
                        monthName = "June";
                        break;
                    case 7:
                        monthName = "July";
                        break;
                    case 8:
                        monthName = "August";
                        break;
                    case 9:
                        monthName = "September";
                        break;
                    case 10:
                        monthName = "October";
                        break;
                    case 11:
                        monthName = "November";
                        break;
                    case 12:
                        monthName = "December";
                        break;
                    default:
                        break;
                }

                if (month >= 1 && month <= 9) {
                    newmonth = "0" + month;
                } else {
                    newmonth = String.valueOf(month);
                }

                if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                    newday = "0" + dayOfMonth;
                } else {
                    newday = String.valueOf(dayOfMonth);
                }

                date = monthName + " " + newday + ", " + year;

                txtDate_In.setText(date);

                newDate = year + "-" + newmonth + "-" + newday;
            }
        };

        btnGetDate_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(
                        InActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);

                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd.show();
            }
        });

        btnGetTime_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hours = cal.get(Calendar.HOUR);

                int minss = cal.get(Calendar.MINUTE);

                TimePickerDialog tpd;

                tpd = new TimePickerDialog(InActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                String newtime = "", newhour = "", newminute = "", timezone = "", numhours = "";

                                if (hourOfDay >= 0 && hourOfDay <= 9) {
                                    newhour = "0" + hourOfDay;
                                    numhours = newhour;
                                    timezone = "am";
                                } else if (hourOfDay >= 10 && hourOfDay <= 11) {
                                    newhour = "" + hourOfDay;
                                    numhours = newhour;
                                    timezone = "am";
                                } else {
                                    switch (hourOfDay) {
                                        case 12:
                                            newhour = "12";
                                            break;
                                        case 13:
                                            newhour = "01";
                                            break;
                                        case 14:
                                            newhour = "02";
                                            break;
                                        case 15:
                                            newhour = "03";
                                            break;
                                        case 16:
                                            newhour = "04";
                                            break;
                                        case 17:
                                            newhour = "05";
                                            break;
                                        case 18:
                                            newhour = "06";
                                            break;
                                        case 19:
                                            newhour = "07";
                                            break;
                                        case 20:
                                            newhour = "08";
                                            break;
                                        case 21:
                                            newhour = "09";
                                            break;
                                        case 22:
                                            newhour = "10";
                                            break;
                                        case 23:
                                            newhour = "11";
                                            break;
                                    }
                                    numhours = String.valueOf(hourOfDay);
                                    timezone = "pm";
                                }

                                if (minute >= 0 && minute <= 9) {
                                    newminute = "0" + minute;
                                } else {
                                    newminute = String.valueOf(minute);
                                }

                                newtime = newhour + ":" + newminute + " " + timezone;

                                newTime = numhours + ":" + newminute;

                                txtTime_In.setText(newtime);

                            }
                        }, hours, minss, false);

                tpd.show();
            }
        });


        btnINDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                divPassengersIN.setVisibility(View.GONE);
                divDetailsIn.setVisibility(View.VISIBLE);
            }
        });

        btnInPassengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                divPassengersIN.setVisibility(View.VISIBLE);
                divDetailsIn.setVisibility(View.GONE);
            }
        });

        ViewDetails();

        GetPassengers();
    }

    @Override
    public void onBackPressed() {
        Intent launchNextActivity;
        launchNextActivity = new Intent(InActivity.this, MainActivity.class);
        launchNextActivity.putExtra("fullname", fullname);
        startActivity(launchNextActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.back_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent launchNextActivity;
                launchNextActivity = new Intent(InActivity.this, MainActivity.class);
                launchNextActivity.putExtra("fullname", fullname);
                startActivity(launchNextActivity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void UpdateDepartAndArrival() {

        dbAccess = new DBAccess();

        String msg = "";

        if (txtRemarks.getText().toString().trim() == "") {
            msg = "N/A";
        } else {
            msg = txtRemarks.getText().toString().trim();
        }

        dbAccess.UpdateDepartAndArrival(controlNo, fullname, stat, msg, "", "", newDate, newTime);

        Toast.makeText(InActivity.this, dbAccess.z, Toast.LENGTH_LONG).show();

        Intent frm = new Intent(InActivity.this, MainActivity.class);

        frm.putExtra("fullname", fullname);

        startActivity(frm);
    }

    public void ViewDetails() {
        dbAccess = new DBAccess();

        dbAccess.GetRequestDetails(controlNo);

        tViewIn_ControlNo.setText(controlNo);

        tViewIn_OBDate.setText(dbAccess.obDate);

        tViewIn_RequestedBy.setText(dbAccess.preparer);

        tViewIn_Destination.setText(dbAccess.destination);

        tViewIn_Justification.setText(dbAccess.justification.replace("&nbsp;", " ").replace("<br />", "\n"));
        ;

        tViewIn_PlateNo.setText(dbAccess.plateno);

        tViewIn_Driver.setText(dbAccess.driver);

        tvHeader2.setText("Total No: " + dbAccess.totalPassenger + "  ");

        code = dbAccess.uc;
    }

    public void GetPassengers() {
        DBAccess dbUsers = new DBAccess();

        dbUsers.GetAllPassenger(code);

        clsCustomPassengers = new ClsCustomPassengers(this, dbUsers.clsDTPassengers);

        listViewPassengersIn.setAdapter(clsCustomPassengers);

        clsCustomPassengers.notifyDataSetChanged();
    }
}
