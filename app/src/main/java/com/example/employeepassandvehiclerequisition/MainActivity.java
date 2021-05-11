package com.example.employeepassandvehiclerequisition;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NotificationManagerCompat notificationManagerCompat;

    final Context context = this;

    ListView listView;

    ClsCustomRequests clsCustomRequests;

    AlertDialog alert;

    AlertDialog.Builder altdial;

    String ControlNo, fullname;

    Button btnForDeparture, btnForArrival, btnAllFinished;

    ImageButton btnGetDate;

    EditText txtDate;

    LinearLayout linearDate;

    public String Stat, databaseId, filterval, newDate, selectedDriver;

    Spinner spinReasons, spinFilterBy, spinDriver;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    Calendar cal = Calendar.getInstance();


    public int recCounter = 0, recCounter_IN = 0, recCounter_Finished = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("ALL REQUESTS");

        //getSupportActionBar().show();

        fullname = getIntent().getExtras().getString("fullname");


        listView = (ListView) findViewById(R.id.listView);

        btnForArrival = (Button) findViewById(R.id.btnForArrival);

        btnForDeparture = (Button) findViewById(R.id.btnForDeparture);

        btnAllFinished = (Button) findViewById(R.id.btnAllFinished);

        btnGetDate = (ImageButton) findViewById(R.id.btnGetDate);

        spinReasons = (Spinner) findViewById(R.id.spinReasons);

        spinDriver = (Spinner) findViewById(R.id.spinDriver);

        spinFilterBy = (Spinner) findViewById(R.id.spinFilterBy);

        txtDate = (EditText) findViewById(R.id.txtDate);

        linearDate = (LinearLayout) findViewById(R.id.linearDate);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        Stat = "OUT";

        btnForDeparture.setEnabled(false);

        btnForDeparture.setBackgroundColor(Color.parseColor("#07B48C"));

        loadReasons();

        loadDrivers();

        loadFilter();

        databaseId = ((SpinnerObject) spinReasons.getSelectedItem()).getDatabaseValue().toString();

        filterval = ((SpinnerObject) spinFilterBy.getSelectedItem()).getDatabaseValue().toString();

        selectedDriver = ((SpinnerObject) spinDriver.getSelectedItem()).getDatabaseValue().toString();

        spinFilterBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this, , Toast.LENGTH_LONG).show();

                switch (((SpinnerObject) spinFilterBy.getSelectedItem()).getDatabaseValue().toString()) {
                    case "All":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.GONE);
                        switch (Stat) {
                            case "OUT":
                                GetAllRequests();

                                DBAccess dbAccess = new DBAccess();

                                dbAccess.GetAllRequests_Count();

                                recCounter = dbAccess.recCounter;
                                break;
                            case "IN":
                                GetAllRequests_IN();
                                DBAccess dbAccess2 = new DBAccess();

                                dbAccess2.GetAllRequests_IN_Count();

                                recCounter_IN = dbAccess2.recCounter_IN;
                                break;
                            case "FINISHED":
                                GetAllFinishedRequests();

                                DBAccess dbAccess3 = new DBAccess();

                                dbAccess3.GetAllFinishedRequests_Count();

                                recCounter_Finished = dbAccess3.recCounter_Finished;
                                break;
                        }
                        newDate = "";
                        break;
                    case "Date":
                        linearDate.setVisibility(View.VISIBLE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.GONE);

                        switch (Stat) {
                            case "OUT":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllRequestsByDate(newDate);
                                }
                                break;
                            case "IN":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllRequests_INByDate(newDate);
                                }
                                break;
                            case "FINISHED":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllFinishedRequestsByDate(newDate);
                                }
                                break;
                        }
                        break;
                    case "Reason":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.VISIBLE);
                        spinDriver.setVisibility(View.GONE);
                        spinReasons.setSelection(0);
                        newDate = "";
                        switch (Stat) {
                            case "OUT":
                                GetAllRequestsByReason(((SpinnerObject) spinReasons.getSelectedItem()).getDatabaseValue().toString());
                                break;
                            case "IN":
                                GetAllRequests_INByReason(((SpinnerObject) spinReasons.getSelectedItem()).getDatabaseValue().toString());
                                break;
                            case "FINISHED":
                                GetAllFinishedRequestsByReason(((SpinnerObject) spinReasons.getSelectedItem()).getDatabaseValue().toString());
                                break;
                        }
                        break;
                    case "Driver":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.VISIBLE);
                        newDate = "";
                        switch (Stat) {
                            case "OUT":
                                GetAllRequestsByDriver(((SpinnerObject) spinDriver.getSelectedItem()).getDatabaseValue().toString());
                                break;
                            case "IN":
                                GetAllRequests_INByDriver(((SpinnerObject) spinDriver.getSelectedItem()).getDatabaseValue().toString());
                                break;
                            case "FINISHED":
                                GetAllFinishedRequestsByDriver(((SpinnerObject) spinDriver.getSelectedItem()).getDatabaseValue().toString());
                                break;
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        switch (filterval) {
            case "All":
                linearDate.setVisibility(View.GONE);
                spinReasons.setVisibility(View.GONE);
                spinDriver.setVisibility(View.GONE);
                switch (Stat) {
                    case "OUT":
                        GetAllRequests();

                        DBAccess dbAccess = new DBAccess();

                        dbAccess.GetAllRequests_Count();

                        recCounter = dbAccess.recCounter;
                        break;
                    case "IN":
                        GetAllRequests_IN();
                        DBAccess dbAccess2 = new DBAccess();

                        dbAccess2.GetAllRequests_IN_Count();

                        recCounter_IN = dbAccess2.recCounter_IN;
                        break;
                    case "FINISHED":
                        GetAllFinishedRequests();

                        DBAccess dbAccess3 = new DBAccess();

                        dbAccess3.GetAllFinishedRequests_Count();

                        recCounter_Finished = dbAccess3.recCounter_Finished;
                        break;
                }
                newDate = "";
                break;
            case "Date":
                linearDate.setVisibility(View.VISIBLE);
                spinReasons.setVisibility(View.GONE);
                spinDriver.setVisibility(View.GONE);

                switch (Stat) {
                    case "OUT":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            GetAllRequestsByDate(newDate);
                        }
                        break;
                    case "IN":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            GetAllRequests_INByDate(newDate);
                        }
                        break;
                    case "FINISHED":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            GetAllFinishedRequestsByDate(newDate);
                        }
                        break;
                }
                break;
            case "Reason":
                linearDate.setVisibility(View.GONE);
                spinReasons.setVisibility(View.VISIBLE);
                spinDriver.setVisibility(View.GONE);
                spinReasons.setSelection(0);
                newDate = "";
                switch (Stat) {
                    case "OUT":
                        GetAllRequestsByReason(databaseId);
                        break;
                    case "IN":
                        GetAllRequests_INByReason(databaseId);
                        break;
                    case "FINISHED":
                        GetAllFinishedRequestsByReason(databaseId);
                        break;
                }
                break;
            case "Driver":
                linearDate.setVisibility(View.GONE);
                spinReasons.setVisibility(View.GONE);
                spinDriver.setVisibility(View.VISIBLE);
                newDate = "";
                switch (Stat) {
                    case "OUT":
                        GetAllRequestsByDriver(selectedDriver);
                        break;
                    case "IN":
                        GetAllRequests_INByDriver(selectedDriver);
                        break;
                    case "FINISHED":
                        GetAllFinishedRequestsByDriver(selectedDriver);
                        break;
                }
                break;
        }

        //handler.postDelayed(runnable, 30000);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ControlNo = view.getTag().toString();

                altdial = new AlertDialog.Builder(MainActivity.this);

                altdial.setMessage("View OB Request?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (Stat) {
                                    case "OUT":
                                        Intent io = new Intent(MainActivity.this, OutActivity.class);
                                        io.putExtra("ControlNo", ControlNo);
                                        io.putExtra("fullname", fullname);
                                        io.putExtra("stat", Stat);
                                        startActivity(io);
                                        break;
                                    case "IN":
                                        Intent iout = new Intent(MainActivity.this, InActivity.class);
                                        iout.putExtra("ControlNo", ControlNo);
                                        iout.putExtra("fullname", fullname);
                                        iout.putExtra("stat", Stat);
                                        startActivity(iout);
                                        break;
                                    case "FINISHED":
                                        Intent iall = new Intent(MainActivity.this, ViewDetailsActivity.class);
                                        iall.putExtra("ControlNo", ControlNo);
                                        iall.putExtra("fullname", fullname);
                                        iall.putExtra("stat", Stat);
                                        startActivity(iall);
                                        break;
                                    default:
                                        break;
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

        spinReasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);

                databaseId = ((SpinnerObject) spinReasons.getSelectedItem()).getDatabaseValue().toString();

                linearDate.setVisibility(View.GONE);

                spinReasons.setVisibility(View.VISIBLE);

                spinDriver.setVisibility(View.GONE);

                newDate = "";

                switch (Stat) {
                    case "OUT":
                        GetAllRequestsByReason(databaseId);
                        break;
                    case "IN":
                        GetAllRequests_INByReason(databaseId);
                        break;
                    case "FINISHED":
                        GetAllFinishedRequestsByReason(databaseId);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);

                selectedDriver = ((SpinnerObject) spinDriver.getSelectedItem()).getDatabaseValue().toString();

                linearDate.setVisibility(View.GONE);

                spinReasons.setVisibility(View.GONE);

                spinDriver.setVisibility(View.VISIBLE);

                newDate = "";

                switch (Stat) {
                    case "OUT":
                        GetAllRequestsByDriver(selectedDriver);
                        break;
                    case "IN":
                        GetAllRequests_INByDriver(selectedDriver);
                        break;
                    case "FINISHED":
                        GetAllFinishedRequestsByDriver(selectedDriver);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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

                txtDate.setText(date);

                newDate = year + "-" + newmonth + "-" + newday;

                //Toast.makeText(getApplicationContext(), newDate, Toast.LENGTH_LONG).show();

                switch (Stat) {
                    case "OUT":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            GetAllRequestsByDate(newDate);
                        }
                        break;
                    case "IN":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            GetAllRequests_INByDate(newDate);
                        }
                        break;
                    case "FINISHED":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            GetAllFinishedRequestsByDate(newDate);
                        }
                        break;
                }
            }
        };

        btnForDeparture.setOnClickListener(this);

        btnForArrival.setOnClickListener(this);

        btnAllFinished.setOnClickListener(this);

        btnGetDate.setOnClickListener(this);
    }

    private void loadReasons() {
        List<SpinnerObject> lables = getAllReasons();

        ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<SpinnerObject>(this,
                android.R.layout.simple_spinner_item, lables);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinReasons.setAdapter(dataAdapter);
    }

    private void loadDrivers() {
        List<SpinnerObject> lables = getAllDrivers();

        ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<SpinnerObject>(this,
                android.R.layout.simple_spinner_item, lables);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinDriver.setAdapter(dataAdapter);
    }

    private void loadFilter() {
        List<SpinnerObject> lables = getFilter();

        ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<SpinnerObject>(this,
                android.R.layout.simple_spinner_item, lables);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinFilterBy.setAdapter(dataAdapter);
    }

    public List<SpinnerObject> getAllReasons() {
        List<SpinnerObject> labels = new ArrayList<SpinnerObject>();

        try {
            ClsConnection connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "Select * from tblReasons";

            PreparedStatement preparedStatement = con.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                labels.add(new SpinnerObject(rs.getString("ReasonDesc"), rs.getString("ReasonType")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return labels;
    }

    public List<SpinnerObject> getAllDrivers() {
        List<SpinnerObject> labels = new ArrayList<SpinnerObject>();

        try {
            ClsConnection connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "Select * from tblDriver";

            PreparedStatement preparedStatement = con.prepareStatement(query);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                labels.add(new SpinnerObject(rs.getString("DriverName"), rs.getString("DriverName")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return labels;
    }

    public List<SpinnerObject> getFilter() {
        List<SpinnerObject> labels = new ArrayList<SpinnerObject>();

        labels.add(new SpinnerObject("All", "All"));

        labels.add(new SpinnerObject("Date", "Date"));

        labels.add(new SpinnerObject("Reason", "Reason"));

        labels.add(new SpinnerObject("Driver", "Driver"));

        return labels;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent launchNextActivity;
        launchNextActivity = new Intent(MainActivity.this, MainMenuActivity.class);
        launchNextActivity.putExtra("fullname", fullname);
        startActivity(launchNextActivity);
    }

    public void CreateNotifications() {
        String Title = "Employee Pass And Vehicle Requisition";

        String Message = "New Request Received";

        Notification notification = new NotificationCompat.Builder(this, App.Channel_1)
                .setSmallIcon(R.drawable.visitoricon)
                .setContentTitle(Title).setContentText(Message)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.FLAG_SHOW_LIGHTS)
                .build();

        notificationManagerCompat.notify(1, notification);

        PowerManager powerManager = (PowerManager) context.getSystemService(context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "appname::WakeLock");

        //acquire will turn on the display
        wakeLock.acquire();

        //release will release the lock from CPU, in case of that, screen will go back to sleep mode in defined time bt device settings
        wakeLock.release();
    }

    /*private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            databaseId = ((SpinnerObject) spinReasons.getSelectedItem()).getDatabaseValue().trim();

            filterval = ((SpinnerObject) spinFilterBy.getSelectedItem()).getDatabaseValue().trim();

            selectedDriver = ((SpinnerObject) spinDriver.getSelectedItem()).getDatabaseValue().toString();

            switch (filterval) {
                case "All":
                    linearDate.setVisibility(View.GONE);
                    spinReasons.setVisibility(View.GONE);
                    spinDriver.setVisibility(View.GONE);
                    switch (Stat) {
                        case "OUT":
                            GetAllRequests();
                            CountAllRequest();
                            break;
                        case "IN":
                            GetAllRequests_IN();
                            CountAllRequests_IN();
                            break;
                        case "FINISHED":
                            GetAllFinishedRequests();
                            //CountAllFinishedRequests();
                            break;
                    }
                    newDate = "";
                    break;
                case "Date":
                    linearDate.setVisibility(View.VISIBLE);
                    spinReasons.setVisibility(View.GONE);
                    spinDriver.setVisibility(View.GONE);

                    switch (Stat) {
                        case "OUT":
                            btnForDeparture.setEnabled(false);
                            btnForDeparture.setBackgroundColor(Color.parseColor("#07B48C"));
                            btnForArrival.setEnabled(true);
                            btnForArrival.setBackgroundColor(Color.parseColor("#008577"));
                            Stat = "OUT";

                            if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                GetAllRequestsByDate(newDate);
                            }
                            break;
                        case "IN":
                            btnForArrival.setEnabled(false);
                            btnForArrival.setBackgroundColor(Color.parseColor("#07B48C"));
                            btnForDeparture.setEnabled(true);
                            btnForDeparture.setBackgroundColor(Color.parseColor("#008577"));
                            Stat = "IN";
                            if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                GetAllRequests_INByDate(newDate);
                            }
                            break;
                        case "FINISHED":
                            btnAllFinished.setEnabled(false);
                            btnAllFinished.setBackgroundColor(Color.parseColor("#07B48C"));
                            btnForArrival.setEnabled(true);
                            btnForArrival.setBackgroundColor(Color.parseColor("#008577"));
                            btnForDeparture.setEnabled(true);
                            btnForDeparture.setBackgroundColor(Color.parseColor("#008577"));
                            Stat = "FINISHED";
                            if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                GetAllFinishedRequestsByDate(newDate);
                            }
                            break;
                    }
                    break;
                case "Reason":
                    linearDate.setVisibility(View.GONE);
                    spinDriver.setVisibility(View.GONE);
                    spinReasons.setVisibility(View.VISIBLE);
                    newDate = "";

                    switch (Stat) {
                        case "OUT":
                            btnForDeparture.setEnabled(false);
                            btnForDeparture.setBackgroundColor(Color.parseColor("#07B48C"));
                            btnForArrival.setEnabled(true);
                            btnForArrival.setBackgroundColor(Color.parseColor("#008577"));
                            btnAllFinished.setEnabled(true);
                            btnAllFinished.setBackgroundColor(Color.parseColor("#008577"));
                            Stat = "OUT";

                            GetAllRequestsByReason(databaseId);
                            break;
                        case "IN":
                            btnForArrival.setEnabled(false);
                            btnForArrival.setBackgroundColor(Color.parseColor("#07B48C"));
                            btnForDeparture.setEnabled(true);
                            btnForDeparture.setBackgroundColor(Color.parseColor("#008577"));
                            btnAllFinished.setEnabled(true);
                            btnAllFinished.setBackgroundColor(Color.parseColor("#008577"));
                            Stat = "IN";

                            GetAllRequests_INByReason(databaseId);
                            break;
                        case "FINISHED":
                            btnAllFinished.setEnabled(false);
                            btnAllFinished.setBackgroundColor(Color.parseColor("#07B48C"));
                            btnForArrival.setEnabled(true);
                            btnForArrival.setBackgroundColor(Color.parseColor("#008577"));
                            btnForDeparture.setEnabled(true);
                            btnForDeparture.setBackgroundColor(Color.parseColor("#008577"));
                            Stat = "FINISHED";
                            GetAllFinishedRequestsByReason(databaseId);
                            break;
                    }
                    break;
                case "Driver":
                    linearDate.setVisibility(View.GONE);
                    spinReasons.setVisibility(View.GONE);
                    spinDriver.setVisibility(View.VISIBLE);
                    newDate = "";
                    switch (Stat) {
                        case "OUT":
                            btnForDeparture.setEnabled(false);
                            btnForDeparture.setBackgroundColor(Color.parseColor("#07B48C"));
                            btnForArrival.setEnabled(true);
                            btnForArrival.setBackgroundColor(Color.parseColor("#008577"));
                            btnAllFinished.setEnabled(true);
                            btnAllFinished.setBackgroundColor(Color.parseColor("#008577"));
                            Stat = "OUT";
                            GetAllRequestsByDriver(selectedDriver);
                            break;
                        case "IN":
                            btnForArrival.setEnabled(false);
                            btnForArrival.setBackgroundColor(Color.parseColor("#07B48C"));
                            btnForDeparture.setEnabled(true);
                            btnForDeparture.setBackgroundColor(Color.parseColor("#008577"));
                            btnAllFinished.setEnabled(true);
                            btnAllFinished.setBackgroundColor(Color.parseColor("#008577"));
                            Stat = "IN";

                            GetAllRequests_INByDriver(selectedDriver);
                            break;
                        case "FINISHED":
                            btnAllFinished.setEnabled(false);
                            btnAllFinished.setBackgroundColor(Color.parseColor("#07B48C"));
                            btnForArrival.setEnabled(true);
                            btnForArrival.setBackgroundColor(Color.parseColor("#008577"));
                            btnForDeparture.setEnabled(true);
                            btnForDeparture.setBackgroundColor(Color.parseColor("#008577"));
                            Stat = "FINISHED";
                            GetAllFinishedRequestsByDriver(selectedDriver);
                            break;
                    }
                    break;
            }

            handler.postDelayed(this, 1000);
        }
    };
*/

    public void GetAllRequests() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequest();

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }

    public void CountAllRequest() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequests_Count();

        if (dbAccess.recCounter != recCounter && dbAccess.recCounter > 0) {

            CreateNotifications();
            recCounter = dbAccess.recCounter;
        }
    }


    public void GetAllRequestsByReason(String type) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequestsByReason(type);

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }

    public void GetAllRequestsByDriver(String driver) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequestsByDriver(driver);

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }

    public void GetAllRequestsByDate(String dates) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequestsByDate(dates);

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }


    public void GetAllFinishedRequests() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllFinishedRequests();

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }

    public void CountAllFinishedRequests() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllFinishedRequests_Count();

        if (dbAccess.recCounter_Finished != recCounter_Finished && dbAccess.recCounter_Finished > 0) {
            CreateNotifications();
            recCounter_Finished = dbAccess.recCounter_Finished;
        }
    }


    public void GetAllFinishedRequestsByReason(String type) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllFinishedRequestsByReason(type);

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }

    public void GetAllFinishedRequestsByDriver(String driver) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllFinishedRequestsByDriver(driver);

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }

    public void GetAllFinishedRequestsByDate(String dates) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllFinishedRequestsByDate(dates);

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }


    public void GetAllRequests_IN() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequests_IN();

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }

    public void CountAllRequests_IN() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequests_IN_Count();

        if (dbAccess.recCounter_IN != recCounter_IN && dbAccess.recCounter_IN > 0) {

            CreateNotifications();
            recCounter_IN = dbAccess.recCounter_IN;
        }
    }


    public void GetAllRequests_INByReason(String type) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequests_INByReason(type);

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }

    public void GetAllRequests_INByDriver(String driver) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequests_INByDriver(driver);

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }

    public void GetAllRequests_INByDate(String dates) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequests_INByDate(dates);

        clsCustomRequests = new ClsCustomRequests(this, dbAccess.clsDTRequests);

        listView.setAdapter(clsCustomRequests);

        clsCustomRequests.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
//                altdial = new AlertDialog.Builder(MainActivity.this);
//
//                altdial.setMessage("Do you want to signout?").setCancelable(false)
//                        .setPositiveButton("Signout", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent launchNextActivity;
//                                launchNextActivity = new Intent(MainActivity.this, MainMenuActivity.class);
//                                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                                startActivity(launchNextActivity);
//                            }
//                        })
//
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.cancel();
//                            }
//                        });
//
//                alert = altdial.create();
//
//                alert.show();

                Intent launchNextActivity;
                launchNextActivity = new Intent(MainActivity.this, MainMenuActivity.class);
                launchNextActivity.putExtra("fullname", fullname);
                startActivity(launchNextActivity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnForDeparture:

                databaseId = ((SpinnerObject) spinReasons.getSelectedItem()).getDatabaseValue().toString();

                filterval = ((SpinnerObject) spinFilterBy.getSelectedItem()).getDatabaseValue().toString();

                selectedDriver = ((SpinnerObject) spinDriver.getSelectedItem()).getDatabaseValue().toString();

                btnForDeparture.setEnabled(false);
                btnForDeparture.setBackgroundColor(Color.parseColor("#07B48C"));
                btnForArrival.setEnabled(true);
                btnForArrival.setBackgroundColor(Color.parseColor("#008577"));
                btnAllFinished.setEnabled(true);
                btnAllFinished.setBackgroundColor(Color.parseColor("#008577"));
                Stat = "OUT";

                switch (filterval) {
                    case "All":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.GONE);
                        switch (Stat) {
                            case "OUT":
                                GetAllRequests();
                                break;
                            case "IN":
                                GetAllRequests_IN();
                                break;
                            case "FINISHED":
                                GetAllFinishedRequests();
                                break;
                        }
                        newDate = "";
                        break;
                    case "Date":
                        linearDate.setVisibility(View.VISIBLE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.GONE);

                        switch (Stat) {
                            case "OUT":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllRequestsByDate(newDate);
                                }
                                break;
                            case "IN":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllRequests_INByDate(newDate);
                                }
                                break;
                            case "FINISHED":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllFinishedRequestsByDate(newDate);
                                }
                                break;
                        }
                        break;
                    case "Reason":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.VISIBLE);
                        spinDriver.setVisibility(View.GONE);
                        spinReasons.setSelection(0);
                        newDate = "";
                        switch (Stat) {
                            case "OUT":
                                GetAllRequestsByReason(databaseId);
                                break;
                            case "IN":
                                GetAllRequests_INByReason(databaseId);
                                break;
                            case "FINISHED":
                                GetAllFinishedRequestsByReason(databaseId);
                                break;
                        }
                        break;
                    case "Driver":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.VISIBLE);
                        newDate = "";
                        switch (Stat) {
                            case "OUT":
                                GetAllRequestsByDriver(selectedDriver);
                                break;
                            case "IN":
                                GetAllRequests_INByDriver(selectedDriver);
                                break;
                            case "FINISHED":
                                GetAllFinishedRequestsByDriver(selectedDriver);
                                break;
                        }
                        break;
                }

                break;
            case R.id.btnForArrival:

                databaseId = ((SpinnerObject) spinReasons.getSelectedItem()).getDatabaseValue().toString();

                filterval = ((SpinnerObject) spinFilterBy.getSelectedItem()).getDatabaseValue().toString();

                selectedDriver = ((SpinnerObject) spinDriver.getSelectedItem()).getDatabaseValue().toString();

                btnForArrival.setEnabled(false);
                btnForArrival.setBackgroundColor(Color.parseColor("#07B48C"));
                btnForDeparture.setEnabled(true);
                btnForDeparture.setBackgroundColor(Color.parseColor("#008577"));
                btnAllFinished.setEnabled(true);
                btnAllFinished.setBackgroundColor(Color.parseColor("#008577"));
                Stat = "IN";

                switch (filterval) {
                    case "All":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.GONE);
                        switch (Stat) {
                            case "OUT":
                                GetAllRequests();
                                break;
                            case "IN":
                                GetAllRequests_IN();
                                break;
                            case "FINISHED":
                                GetAllFinishedRequests();
                                break;
                        }
                        newDate = "";
                        break;

                    case "Date":
                        linearDate.setVisibility(View.VISIBLE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.GONE);
                        switch (Stat) {
                            case "OUT":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllRequestsByDate(newDate);
                                }
                                break;
                            case "IN":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllRequests_INByDate(newDate);
                                }
                                break;
                            case "FINISHED":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllFinishedRequestsByDate(newDate);
                                }
                                break;
                        }
                        break;

                    case "Reason":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.VISIBLE);
                        spinDriver.setVisibility(View.GONE);
                        spinReasons.setSelection(0);
                        switch (Stat) {
                            case "OUT":
                                GetAllRequestsByReason(databaseId);
                                break;
                            case "IN":
                                GetAllRequests_INByReason(databaseId);
                                break;
                            case "FINISHED":
                                GetAllFinishedRequestsByReason(databaseId);
                                break;
                        }
                        newDate = "";
                        break;
                    case "Driver":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.VISIBLE);
                        newDate = "";
                        switch (Stat) {
                            case "OUT":
                                GetAllRequestsByDriver(selectedDriver);
                                break;
                            case "IN":
                                GetAllRequests_INByDriver(selectedDriver);
                                break;
                            case "FINISHED":
                                GetAllFinishedRequestsByDriver(selectedDriver);
                                break;
                        }
                        break;
                }
                break;
            case R.id.btnAllFinished:

                databaseId = ((SpinnerObject) spinReasons.getSelectedItem()).getDatabaseValue().toString();

                filterval = ((SpinnerObject) spinFilterBy.getSelectedItem()).getDatabaseValue().toString();

                selectedDriver = ((SpinnerObject) spinDriver.getSelectedItem()).getDatabaseValue().toString();

                btnAllFinished.setEnabled(false);
                btnAllFinished.setBackgroundColor(Color.parseColor("#07B48C"));
                btnForDeparture.setEnabled(true);
                btnForDeparture.setBackgroundColor(Color.parseColor("#008577"));
                btnForArrival.setEnabled(true);
                btnForArrival.setBackgroundColor(Color.parseColor("#008577"));
                Stat = "FINISHED";

                switch (filterval) {
                    case "All":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.GONE);
                        switch (Stat) {
                            case "OUT":
                                GetAllRequests();
                                break;
                            case "IN":
                                GetAllRequests_IN();
                                break;
                            case "FINISHED":
                                GetAllFinishedRequests();
                                break;
                        }
                        newDate = "";
                        break;

                    case "Date":
                        linearDate.setVisibility(View.VISIBLE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.GONE);
                        switch (Stat) {
                            case "OUT":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllRequestsByDate(newDate);
                                }
                                break;
                            case "IN":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllRequests_INByDate(newDate);
                                }
                                break;
                            case "FINISHED":
                                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                                    GetAllFinishedRequestsByDate(newDate);
                                }
                                break;
                        }
                        break;

                    case "Reason":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.VISIBLE);
                        spinDriver.setVisibility(View.GONE);
                        spinReasons.setSelection(0);
                        switch (Stat) {
                            case "OUT":
                                GetAllRequestsByReason(databaseId);
                                break;
                            case "IN":
                                GetAllRequests_INByReason(databaseId);
                                break;
                            case "FINISHED":
                                GetAllFinishedRequestsByReason(databaseId);
                                break;
                        }
                        newDate = "";
                        break;
                    case "Driver":
                        linearDate.setVisibility(View.GONE);
                        spinReasons.setVisibility(View.GONE);
                        spinDriver.setVisibility(View.VISIBLE);
                        newDate = "";
                        switch (Stat) {
                            case "OUT":
                                GetAllRequestsByDriver(selectedDriver);
                                break;
                            case "IN":
                                GetAllRequests_INByDriver(selectedDriver);
                                break;
                            case "FINISHED":
                                GetAllFinishedRequestsByDriver(selectedDriver);
                                break;
                        }
                        break;
                }
                break;

            case R.id.btnGetDate:
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);

                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd.show();
                break;
        }
    }
}
