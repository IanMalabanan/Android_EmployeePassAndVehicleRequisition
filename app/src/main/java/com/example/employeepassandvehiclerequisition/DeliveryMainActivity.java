package com.example.employeepassandvehiclerequisition;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class DeliveryMainActivity extends AppCompatActivity implements View.OnClickListener {

    private NotificationManagerCompat notificationManagerCompat;

    final Context context = this;

    ListView listViewDelivery;

    ClsCustomDeliveryRequests clsCustomDeliveryRequests;

    //ClsCustomRequests clsCustomRequests;

    AlertDialog alert;

    AlertDialog.Builder altdial;

    String ControlNo, fullname;

    Button btnDeliveryForDeparture, btnDeliveryForArrival, btnDeliveryAllFinished;

    ImageButton btnDeliverySearchDestination, btnDeliveryRefresh;

    EditText txtDeliverySearchDestination;

    LinearLayout linearDeliveryDate;

    public String Stat, databaseId, filterval, newDate, selectedDriver;

    public int requestID = 0;

    //Spinner spinReasons, spinFilterBy, spinDriver;

    DatePickerDialog.OnDateSetListener mDateSetListener;

    Calendar cal = Calendar.getInstance();

    private static int convertStringToInt(String str) {
        int x = 0;
        try {
            x = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            //TODO: LOG or HANDLE
        }
        return x;
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }


    public int recCounter = 0, recCounter_IN = 0, recCounter_Finished = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_main);

        fullname = getIntent().getExtras().getString("fullname");

        listViewDelivery = (ListView) findViewById(R.id.listViewDelivery);

        btnDeliveryForArrival = (Button) findViewById(R.id.btnDeliveryForArrival);

        btnDeliveryForDeparture = (Button) findViewById(R.id.btnDeliveryForDeparture);

        btnDeliveryAllFinished = (Button) findViewById(R.id.btnDeliveryAllFinished);

        btnDeliveryRefresh = (ImageButton) findViewById(R.id.btnDeliveryRefresh);

        btnDeliverySearchDestination = (ImageButton) findViewById(R.id.btnDeliverySearchDestination);

        txtDeliverySearchDestination = (EditText) findViewById(R.id.txtDeliverySearchDestination);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        btnDeliveryForDeparture.setEnabled(false);
        btnDeliveryForDeparture.setBackgroundColor(Color.parseColor("#07B48C"));
        btnDeliveryForArrival.setEnabled(true);
        btnDeliveryForArrival.setBackgroundColor(Color.parseColor("#008577"));
        btnDeliveryAllFinished.setEnabled(true);
        btnDeliveryAllFinished.setBackgroundColor(Color.parseColor("#008577"));

        Stat = "OUT";

        switch (Stat) {
            case "OUT":
                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                    //GetAllRequestsByDate_Delivery(newDate);
                } else if (isEmpty(txtDeliverySearchDestination) == false) {
                    GetAllRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                } else {
                    GetAllRequests_Delivery();
                }
                break;
            case "IN":
                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                    //GetAllRequests_INByDate_Delivery(newDate);
                } else if (isEmpty(txtDeliverySearchDestination) == false) {
                    GetAllRequests_INByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                } else {
                    GetAllRequests_IN_Delivery();
                }
                break;
            case "FINISHED":
                if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                    //GetAllFinishedRequestsByDate_Delivery(newDate);
                } else if (isEmpty(txtDeliverySearchDestination) == false) {
                    GetAllFinishedRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                } else {
                    GetAllFinishedRequests_Delivery();
                }
                break;
        }

        listViewDelivery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                databaseId = view.getTag().toString();

                altdial = new AlertDialog.Builder(DeliveryMainActivity.this);

                altdial.setMessage("View Delivery Request?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (Stat) {
                                    case "OUT":

                                        //Toast.makeText(DeliveryMainActivity.this, databaseId, Toast.LENGTH_LONG).show();
                                        Intent io = new Intent(DeliveryMainActivity.this, DeliveryOutActivity.class);
                                        io.putExtra("requestID", databaseId);
                                        io.putExtra("fullname", fullname);
                                        io.putExtra("stat", Stat);
                                        startActivity(io);
                                        break;
                                    case "IN":
                                        Intent iout = new Intent(DeliveryMainActivity.this, DeliveryInActivity.class);
                                        iout.putExtra("requestID", databaseId);
                                        iout.putExtra("fullname", fullname);
                                        iout.putExtra("stat", Stat);
                                        startActivity(iout);
                                        break;
                                    case "FINISHED":
                                        Intent iall = new Intent(DeliveryMainActivity.this, DeliveryViewDetailsActivity.class);
                                        iall.putExtra("requestID", databaseId);
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

                //txtDeliveryDate.setText(date);

                newDate = year + "-" + newmonth + "-" + newday;

                //Toast.makeText(getApplicationContext(), newDate, Toast.LENGTH_LONG).show();

                switch (Stat) {
                    case "OUT":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllRequestsByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllRequests_Delivery();
                        }
                        break;
                    case "IN":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllRequests_INByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllRequests_INByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllRequests_IN_Delivery();
                        }
                        break;
                    case "FINISHED":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllFinishedRequestsByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllFinishedRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllFinishedRequests_Delivery();
                        }
                        break;
                }
            }
        };


        btnDeliveryForDeparture.setOnClickListener(this);

        btnDeliveryForArrival.setOnClickListener(this);

        btnDeliveryAllFinished.setOnClickListener(this);

        btnDeliverySearchDestination.setOnClickListener(this);

        btnDeliveryRefresh.setOnClickListener(this);

        //btnGetDeliveryDate.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent launchNextActivity;
        launchNextActivity = new Intent(DeliveryMainActivity.this, MainMenuActivity.class);
        launchNextActivity.putExtra("fullname", fullname);
        startActivity(launchNextActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDeliveryForDeparture:

                btnDeliveryForDeparture.setEnabled(false);
                btnDeliveryForDeparture.setBackgroundColor(Color.parseColor("#07B48C"));
                btnDeliveryForArrival.setEnabled(true);
                btnDeliveryForArrival.setBackgroundColor(Color.parseColor("#008577"));
                btnDeliveryAllFinished.setEnabled(true);
                btnDeliveryAllFinished.setBackgroundColor(Color.parseColor("#008577"));

                Stat = "OUT";

                switch (Stat) {
                    case "OUT":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllRequestsByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllRequests_Delivery();
                        }
                        break;
                    case "IN":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllRequests_INByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllRequests_INByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllRequests_IN_Delivery();
                        }
                        break;
                    case "FINISHED":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllFinishedRequestsByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllFinishedRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllFinishedRequests_Delivery();
                        }
                        break;
                }
                break;
            case R.id.btnDeliveryForArrival:

                btnDeliveryForArrival.setEnabled(false);
                btnDeliveryForArrival.setBackgroundColor(Color.parseColor("#07B48C"));
                btnDeliveryForDeparture.setEnabled(true);
                btnDeliveryForDeparture.setBackgroundColor(Color.parseColor("#008577"));
                btnDeliveryAllFinished.setEnabled(true);
                btnDeliveryAllFinished.setBackgroundColor(Color.parseColor("#008577"));
                Stat = "IN";

                switch (Stat) {
                    case "OUT":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllRequestsByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllRequests_Delivery();
                        }
                        break;
                    case "IN":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllRequests_INByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllRequests_INByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllRequests_IN_Delivery();
                        }
                        break;
                    case "FINISHED":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllFinishedRequestsByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllFinishedRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllFinishedRequests_Delivery();
                        }
                        break;
                }
                break;
            case R.id.btnDeliveryAllFinished:

                btnDeliveryAllFinished.setEnabled(false);
                btnDeliveryAllFinished.setBackgroundColor(Color.parseColor("#07B48C"));
                btnDeliveryForDeparture.setEnabled(true);
                btnDeliveryForDeparture.setBackgroundColor(Color.parseColor("#008577"));
                btnDeliveryForArrival.setEnabled(true);
                btnDeliveryForArrival.setBackgroundColor(Color.parseColor("#008577"));

                Stat = "FINISHED";

                switch (Stat) {
                    case "OUT":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllRequestsByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllRequests_Delivery();
                        }
                        break;
                    case "IN":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllRequests_INByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllRequests_INByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllRequests_IN_Delivery();
                        }
                        break;
                    case "FINISHED":
                        if (newDate != null && !newDate.isEmpty() && !newDate.equals("null")) {
                            //GetAllFinishedRequestsByDate_Delivery(newDate);
                        } else if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllFinishedRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllFinishedRequests_Delivery();
                        }
                        break;
                }
                break;

            case R.id.btnDeliverySearchDestination:
                /*int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(
                        DeliveryMainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);

                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd.show();*/

                switch (Stat) {
                    case "OUT":
                        if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllRequests_Delivery();
                        }
                        break;
                    case "IN":
                        if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllRequests_INByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllRequests_IN_Delivery();
                        }
                        break;
                    case "FINISHED":
                        if (isEmpty(txtDeliverySearchDestination) == false) {
                            GetAllFinishedRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                        } else {
                            GetAllFinishedRequests_Delivery();
                        }
                        break;
                }
                break;
            case R.id.btnDeliveryRefresh:
                /*int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(
                        DeliveryMainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);

                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd.show();*/

                switch (Stat) {
                    case "OUT":
                        if (isEmpty(txtDeliverySearchDestination) == false) {
                            //GetAllRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                            txtDeliverySearchDestination.setText("");
                        }

                        GetAllRequests_Delivery();

                        break;
                    case "IN":
                        if (isEmpty(txtDeliverySearchDestination) == false) {
                            //GetAllRequests_INByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                            txtDeliverySearchDestination.setText("");
                        }

                        GetAllRequests_IN_Delivery();

                        break;
                    case "FINISHED":
                        if (isEmpty(txtDeliverySearchDestination) == false) {
                            //GetAllFinishedRequestsByDestination_Delivery(txtDeliverySearchDestination.getText().toString().trim());
                            txtDeliverySearchDestination.setText("");
                        }
                        GetAllFinishedRequests_Delivery();
                        break;
                }
                break;
        }
    }


    public void GetAllRequests_Delivery() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequests_Delivery();

        clsCustomDeliveryRequests = new ClsCustomDeliveryRequests(this, dbAccess.clsDTRequests);

        listViewDelivery.setAdapter(clsCustomDeliveryRequests);

        clsCustomDeliveryRequests.notifyDataSetChanged();
    }

    public void GetAllRequestsByDate_Delivery(String dates) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequestsByDate_Delivery(dates);

        clsCustomDeliveryRequests = new ClsCustomDeliveryRequests(this, dbAccess.clsDTRequests);

        listViewDelivery.setAdapter(clsCustomDeliveryRequests);

        clsCustomDeliveryRequests.notifyDataSetChanged();
    }

    public void GetAllRequestsByDestination_Delivery(String destination) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequestsByDestination_Delivery(destination);

        clsCustomDeliveryRequests = new ClsCustomDeliveryRequests(this, dbAccess.clsDTRequests);

        listViewDelivery.setAdapter(clsCustomDeliveryRequests);

        clsCustomDeliveryRequests.notifyDataSetChanged();
    }

    public void GetAllFinishedRequests_Delivery() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllFinishedRequests_Delivery();

        clsCustomDeliveryRequests = new ClsCustomDeliveryRequests(this, dbAccess.clsDTRequests);

        listViewDelivery.setAdapter(clsCustomDeliveryRequests);

        clsCustomDeliveryRequests.notifyDataSetChanged();
    }

    public void GetAllFinishedRequestsByDate_Delivery(String dates) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllFinishedRequestsByDate_Delivery(dates);

        clsCustomDeliveryRequests = new ClsCustomDeliveryRequests(this, dbAccess.clsDTRequests);

        listViewDelivery.setAdapter(clsCustomDeliveryRequests);

        clsCustomDeliveryRequests.notifyDataSetChanged();
    }

    public void GetAllFinishedRequestsByDestination_Delivery(String destination) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllFinishedRequestsByDestination_Delivery(destination);

        clsCustomDeliveryRequests = new ClsCustomDeliveryRequests(this, dbAccess.clsDTRequests);

        listViewDelivery.setAdapter(clsCustomDeliveryRequests);

        clsCustomDeliveryRequests.notifyDataSetChanged();
    }


    public void GetAllRequests_IN_Delivery() {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequests_IN_Delivery();

        clsCustomDeliveryRequests = new ClsCustomDeliveryRequests(this, dbAccess.clsDTRequests);

        listViewDelivery.setAdapter(clsCustomDeliveryRequests);

        clsCustomDeliveryRequests.notifyDataSetChanged();
    }

    public void GetAllRequests_INByDate_Delivery(String dates) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequests_INByDate_Delivery(dates);

        clsCustomDeliveryRequests = new ClsCustomDeliveryRequests(this, dbAccess.clsDTRequests);

        listViewDelivery.setAdapter(clsCustomDeliveryRequests);

        clsCustomDeliveryRequests.notifyDataSetChanged();
    }

    public void GetAllRequests_INByDestination_Delivery(String destination) {
        DBAccess dbAccess = new DBAccess();

        dbAccess.GetAllRequests_INByDestination_Delivery(destination);

        clsCustomDeliveryRequests = new ClsCustomDeliveryRequests(this, dbAccess.clsDTRequests);

        listViewDelivery.setAdapter(clsCustomDeliveryRequests);

        clsCustomDeliveryRequests.notifyDataSetChanged();
    }

}
