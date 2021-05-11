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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public class DeliveryOutActivity extends AppCompatActivity {

    AlertDialog alert;

    AlertDialog.Builder altdial;

    TextView tViewDeliveryOut_OBDate, tViewDeliveryOut_RequestedBy, tViewDeliveryOut_Driver, tvDeliveryHeader2Out, tViewDeliveryOut_PlateNo, tViewDeliveryOut_Destination, tViewDeliveryOut_Crew1, tViewDeliveryOut_Crew2;

    Button btnDeliveryMarkAsDepart;

    ImageButton btnGetDeliveryDate_Out, btnGetDeliveryTime_Out;

    EditText txtDeliveryDate_Out, txtDeliveryTime_Out, txtDeliveryDriver_Update, txtDeliveryCrew1_Update, txtDeliveryCrew2_Update;

    CheckBox chkUpdateDriver_Crew;

    LinearLayout linearNewDriver_Crew;

    public String code, fullname, stat, newDate, newTime;

    public int requestID = 0;

    public boolean res = false;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    Calendar cal = Calendar.getInstance();

    DBAccess dbAccess;

    private static int convertStringToInt(String str) {
        int x = 0;
        try {
            x = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            //TODO: LOG or HANDLE
        }
        return x;
    }

    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_out);

        requestID = convertStringToInt(getIntent().getExtras().getString("requestID"));

        fullname = getIntent().getExtras().getString("fullname");

        stat = getIntent().getExtras().getString("stat");


        txtDeliveryDriver_Update = (EditText) findViewById(R.id.txtDeliveryDriver_Update);

        txtDeliveryCrew1_Update = (EditText) findViewById(R.id.txtDeliveryCrew1_Update);

        txtDeliveryCrew2_Update = (EditText) findViewById(R.id.txtDeliveryCrew2_Update);

        linearNewDriver_Crew = (LinearLayout) findViewById(R.id.linearNewDriver_Crew);

        chkUpdateDriver_Crew = (CheckBox) findViewById(R.id.chkUpdateDriver_Crew);

        tViewDeliveryOut_OBDate = (TextView) findViewById(R.id.tViewDeliveryOut_OBDate);

        tViewDeliveryOut_RequestedBy = (TextView) findViewById(R.id.tViewDeliveryOut_RequestedBy);

        tViewDeliveryOut_Driver = (TextView) findViewById(R.id.tViewDeliveryOut_Driver);

        tViewDeliveryOut_Crew1 = (TextView) findViewById(R.id.tViewDeliveryOut_Crew1);

        tViewDeliveryOut_Crew2 = (TextView) findViewById(R.id.tViewDeliveryOut_Crew2);

        tViewDeliveryOut_PlateNo = (TextView) findViewById(R.id.tViewDeliveryOut_PlateNo);

        tViewDeliveryOut_Destination = (TextView) findViewById(R.id.tViewDeliveryOut_Destination);

        btnDeliveryMarkAsDepart = (Button) findViewById(R.id.btnDeliveryMarkAsDepart);

        btnGetDeliveryDate_Out = (ImageButton) findViewById(R.id.btnGetDeliveryDate_Out);

        btnGetDeliveryTime_Out = (ImageButton) findViewById(R.id.btnGetDeliveryTime_Out);

        txtDeliveryDate_Out = (EditText) findViewById(R.id.txtDeliveryDate_Out);

        txtDeliveryTime_Out = (EditText) findViewById(R.id.txtDeliveryTime_Out);


        enableDisableView(linearNewDriver_Crew, false);
        txtDeliveryDriver_Update.setText("");
        txtDeliveryCrew1_Update.setText("");
        txtDeliveryCrew2_Update.setText("");
        res = false;

        chkUpdateDriver_Crew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //Toast.makeText(DeliveryOutActivity.this, String.valueOf(b), Toast.LENGTH_LONG).show();
                enableDisableView(linearNewDriver_Crew, b);
                txtDeliveryDriver_Update.setText("");
                txtDeliveryCrew1_Update.setText("");
                txtDeliveryCrew2_Update.setText("");
                res = b;
            }
        });

        btnDeliveryMarkAsDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                altdial = new AlertDialog.Builder(DeliveryOutActivity.this);

                altdial.setMessage("Confirm Action").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (newDate != "" && newTime != "") {

                                    if (res == false) {
                                        UpdateDepartAndArrival();
                                    } else {
                                        UpdateDepartAndArrival2();
                                    }

                                } else {
                                    Toast.makeText(DeliveryOutActivity.this, "Must Select Date and Time", Toast.LENGTH_LONG).show();
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

                txtDeliveryDate_Out.setText(date);

                newDate = year + "-" + newmonth + "-" + newday;
            }
        };

        btnGetDeliveryDate_Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(
                        DeliveryOutActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);

                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd.show();
            }
        });

        btnGetDeliveryTime_Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hours = cal.get(Calendar.HOUR);

                int minss = cal.get(Calendar.MINUTE);

                TimePickerDialog tpd;

                tpd = new TimePickerDialog(DeliveryOutActivity.this,
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

                                txtDeliveryTime_Out.setText(newtime);

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
        launchNextActivity = new Intent(DeliveryOutActivity.this, DeliveryMainActivity.class);
        launchNextActivity.putExtra("fullname", fullname);
        startActivity(launchNextActivity);
    }

    public void UpdateDepartAndArrival() {

        dbAccess = new DBAccess();

        dbAccess.UpdateDepartAndArrival_Delivery(requestID, fullname, stat, "N/A", newDate, newTime, newDate, newTime);

        Toast.makeText(DeliveryOutActivity.this, dbAccess.z, Toast.LENGTH_LONG).show();

        Intent frm = new Intent(DeliveryOutActivity.this, DeliveryMainActivity.class);

        frm.putExtra("fullname", fullname);

        startActivity(frm);
    }

    public void UpdateDepartAndArrival2() {

        dbAccess = new DBAccess();

        dbAccess.UpdateDepartAndArrival_Delivery2(requestID, fullname, stat, "N/A"
                , newDate, newTime, newDate, newTime
                , txtDeliveryDriver_Update.getText().toString()
                , txtDeliveryCrew1_Update.getText().toString()
                , txtDeliveryCrew2_Update.getText().toString());

        Toast.makeText(DeliveryOutActivity.this, dbAccess.z, Toast.LENGTH_LONG).show();

        Intent frm = new Intent(DeliveryOutActivity.this, DeliveryMainActivity.class);

        frm.putExtra("fullname", fullname);

        startActivity(frm);
    }

    public void ViewDetails() {
        dbAccess = new DBAccess();

        dbAccess.GetRequestDetails_Delivery(requestID);

        tViewDeliveryOut_OBDate.setText(dbAccess.obDate);

        tViewDeliveryOut_RequestedBy.setText(dbAccess.preparer);

        tViewDeliveryOut_Driver.setText(dbAccess.driver);

        tViewDeliveryOut_Crew1.setText(dbAccess.crew1);

        tViewDeliveryOut_Crew2.setText(dbAccess.crew2);

        tViewDeliveryOut_Destination.setText(dbAccess.destination);

        tViewDeliveryOut_PlateNo.setText(dbAccess.plateno);

        btnDeliveryMarkAsDepart.setEnabled(true);
    }
}
