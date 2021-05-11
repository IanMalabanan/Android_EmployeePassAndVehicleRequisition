package com.example.employeepassandvehiclerequisition;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class DeliveryInActivity extends AppCompatActivity {

    AlertDialog alert;

    AlertDialog.Builder altdial;

    TextView tViewDeliveryIn_OBDate, tViewDeliveryIn_RequestedBy, tViewDeliveryIn_Driver, tViewDeliveryIn_Crew1, tViewDeliveryIn_Crew2, tvDeliveryInHeader2, tViewDeliveryIn_PlateNo, tViewDeliveryIn_Destination;

    Button btnDeliveryMarkAsArrived;

    ImageButton btnDeliveryGetDate_In, btnDeliveryGetTime_In;

    EditText txtDeliveryRemarks, txtDeliveryDate_In, txtDeliveryTime_In;

    public String code, controlNo, fullname, stat, newDate, newTime, obDate;

    public int requestID;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    Calendar cal = Calendar.getInstance();

    DBAccess dbAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delivery_in);

        requestID = Integer.parseInt(getIntent().getExtras().getString("requestID"));

        fullname = getIntent().getExtras().getString("fullname");

        stat = getIntent().getExtras().getString("stat");

        tViewDeliveryIn_OBDate = (TextView) findViewById(R.id.tViewDeliveryIn_OBDate);

        tViewDeliveryIn_RequestedBy = (TextView) findViewById(R.id.tViewDeliveryIn_RequestedBy);

        tViewDeliveryIn_PlateNo = (TextView) findViewById(R.id.tViewDeliveryIn_PlateNo);

        tViewDeliveryIn_Destination = (TextView) findViewById(R.id.tViewDeliveryIn_Destination);

        tViewDeliveryIn_Driver = (TextView) findViewById(R.id.tViewDeliveryIn_Driver);

        tViewDeliveryIn_Crew1 = (TextView) findViewById(R.id.tViewDeliveryIn_Crew1);

        tViewDeliveryIn_Crew2 = (TextView) findViewById(R.id.tViewDeliveryIn_Crew2);

        txtDeliveryDate_In = (EditText) findViewById(R.id.txtDeliveryDate_In);

        txtDeliveryTime_In = (EditText) findViewById(R.id.txtDeliveryTime_In);

        btnDeliveryMarkAsArrived = (Button) findViewById(R.id.btnDeliveryMarkAsArrived);

        btnDeliveryGetDate_In = (ImageButton) findViewById(R.id.btnGetDeliveryDate_In);

        btnDeliveryGetTime_In = (ImageButton) findViewById(R.id.btnGetDeliveryTime_In);

        txtDeliveryRemarks = (EditText) findViewById(R.id.txtDeliveryRemarks);

        btnDeliveryMarkAsArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                altdial = new AlertDialog.Builder(DeliveryInActivity.this);

                altdial.setMessage("Confirm Action").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (newDate != "" && newTime != "") {
                                    UpdateDepartAndArrival();
                                } else {
                                    Toast.makeText(DeliveryInActivity.this, "Must Select Date and Time", Toast.LENGTH_LONG).show();
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

                txtDeliveryDate_In.setText(date);

                newDate = year + "-" + newmonth + "-" + newday;
            }
        };

        btnDeliveryGetDate_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(
                        DeliveryInActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);

                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd.show();
            }
        });

        btnDeliveryGetTime_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hours = cal.get(Calendar.HOUR);

                int minss = cal.get(Calendar.MINUTE);

                TimePickerDialog tpd;

                tpd = new TimePickerDialog(DeliveryInActivity.this,
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

                                txtDeliveryTime_In.setText(newtime);

                            }
                        }, hours, minss, false);

                tpd.show();
            }
        });

        ViewDetails();

    }

    @Override
    public void onBackPressed() {
        Intent launchNextActivity;
        launchNextActivity = new Intent(DeliveryInActivity.this, DeliveryMainActivity.class);
        launchNextActivity.putExtra("fullname", fullname);
        startActivity(launchNextActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.back_menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent launchNextActivity;
                launchNextActivity = new Intent(DeliveryInActivity.this, DeliveryMainActivity.class);
                launchNextActivity.putExtra("fullname", fullname);
                startActivity(launchNextActivity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ViewDetails() {
        dbAccess = new DBAccess();

        dbAccess.GetRequestDetails_Delivery(requestID);

        tViewDeliveryIn_OBDate.setText(dbAccess.obDate);

        tViewDeliveryIn_RequestedBy.setText(dbAccess.preparer);

        tViewDeliveryIn_Driver.setText(dbAccess.driver);

        tViewDeliveryIn_Crew1.setText(dbAccess.crew1);

        tViewDeliveryIn_Crew2.setText(dbAccess.crew2);

        tViewDeliveryIn_Destination.setText(dbAccess.destination);

        tViewDeliveryIn_PlateNo.setText(dbAccess.plateno);

        btnDeliveryMarkAsArrived.setEnabled(true);
    }

    public void UpdateDepartAndArrival() {

        dbAccess = new DBAccess();

        String msg = "";

        if (txtDeliveryRemarks.getText().toString().trim() == "") {
            msg = "N/A";
        } else {
            msg = txtDeliveryRemarks.getText().toString().trim();
        }

        dbAccess.UpdateDepartAndArrival_Delivery(requestID, fullname, stat, msg,"","",newDate,newTime);

        Toast.makeText(DeliveryInActivity.this, dbAccess.z, Toast.LENGTH_LONG).show();

        Intent frm = new Intent(DeliveryInActivity.this, DeliveryMainActivity.class);

        frm.putExtra("fullname", fullname);

        startActivity(frm);
    }
}
