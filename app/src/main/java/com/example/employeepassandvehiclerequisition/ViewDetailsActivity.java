package com.example.employeepassandvehiclerequisition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ViewDetailsActivity extends AppCompatActivity {

    TextView tView_ControlNo, tView_OBDate, tView_RequestedBy, tView_Driver, tvHeader2,

    tView_EstimatedDepartureTime, tView_Destination, tView_EstimatedArrivalTime, tView_RequestType, tView_Reason, tView_Justification, tView_PlateNo, tView_AssignBy, tView_ActDepartureDate, tView_ActDepartureTime, tView_ActArrivalDate, tView_ActArrivalTime, tView_Remarks, tView_GuardOnDuty_Departure, tView_GuardOnDuty_Arrival;

    ListView lVPassengers;

    public String code, controlNo, fullname, stat, newDate, newTime;

    LinearLayout divDeliveryViewDetails, divDeliveryViewPassengers;

    ClsCustomPassengers clsCustomPassengers;

    Button btnDeliveryViewPassengers, btnDeliveryViewDetails;

    DBAccess dbAccess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        //setTitle("REQUEST DETAILS");

        controlNo = getIntent().getExtras().getString("ControlNo");

        fullname = getIntent().getExtras().getString("fullname");

        stat = getIntent().getExtras().getString("stat");

        tView_ControlNo = (TextView) findViewById(R.id.tView_ControlNo);

        tView_OBDate = (TextView) findViewById(R.id.tView_OBDate);

        tView_RequestedBy = (TextView) findViewById(R.id.tView_RequestedBy);

        tvHeader2 = (TextView) findViewById(R.id.tvHeader2);

        tView_Driver = (TextView) findViewById(R.id.tView_Driver);

        lVPassengers = (ListView) findViewById(R.id.lVPassengers);

        tView_EstimatedDepartureTime = (TextView) findViewById(R.id.tView_EstimatedDepartureTime);

        tView_Destination = (TextView) findViewById(R.id.tView_Destination);

        tView_EstimatedArrivalTime = (TextView) findViewById(R.id.tView_EstimatedArrivalTime);

        tView_RequestType = (TextView) findViewById(R.id.tView_RequestType);

        tView_Reason = (TextView) findViewById(R.id.tView_Reason);

        tView_Justification = (TextView) findViewById(R.id.tView_Justification);

        tView_PlateNo = (TextView) findViewById(R.id.tView_PlateNo);

        tView_AssignBy = (TextView) findViewById(R.id.tView_AssignBy);

        tView_ActDepartureDate = (TextView) findViewById(R.id.tView_ActDepartureDate);

        tView_ActDepartureTime = (TextView) findViewById(R.id.tView_ActDepartureTime);

        tView_ActArrivalDate = (TextView) findViewById(R.id.tView_ActArrivalDate);

        tView_ActArrivalTime = (TextView) findViewById(R.id.tView_ActArrivalTime);

        tView_Remarks = (TextView) findViewById(R.id.tView_Remarks);

        tView_GuardOnDuty_Departure = (TextView) findViewById(R.id.tView_GuardOnDuty_Departure);

        tView_GuardOnDuty_Arrival = (TextView) findViewById(R.id.tView_GuardOnDuty_Arrival);

        divDeliveryViewDetails = (LinearLayout) findViewById(R.id.divDeliveryViewDetails);

        divDeliveryViewPassengers = (LinearLayout) findViewById(R.id.divDeliveryViewPassengers);

        btnDeliveryViewPassengers = (Button) findViewById(R.id.btnDeliveryViewPassengers);

        btnDeliveryViewDetails = (Button) findViewById(R.id.btnDeliveryViewDetails);


        divDeliveryViewPassengers.setVisibility(View.GONE);

        divDeliveryViewDetails.setVisibility(View.VISIBLE);

        btnDeliveryViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                divDeliveryViewPassengers.setVisibility(View.GONE);
                divDeliveryViewDetails.setVisibility(View.VISIBLE);
            }
        });

        btnDeliveryViewPassengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                divDeliveryViewPassengers.setVisibility(View.VISIBLE);
                divDeliveryViewDetails.setVisibility(View.GONE);
            }
        });

        ViewDetails();

        GetPassengers();
    }

    @Override
    public void onBackPressed() {
        Intent launchNextActivity;
        launchNextActivity = new Intent(ViewDetailsActivity.this, MainActivity.class);
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
                launchNextActivity = new Intent(ViewDetailsActivity.this, MainActivity.class);
                launchNextActivity.putExtra("fullname", fullname);
                startActivity(launchNextActivity);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ViewDetails() {
        dbAccess = new DBAccess();

        dbAccess.GetRequestDetails(controlNo);

        tView_ControlNo.setText(dbAccess.controlNo);

        tView_OBDate.setText(dbAccess.obDate);

        tView_RequestedBy.setText(dbAccess.preparer);

        tView_Driver.setText(dbAccess.driver);

        tvHeader2.setText("Total No: " + dbAccess.totalPassenger + "  ");

        tView_EstimatedDepartureTime.setText(dbAccess.estdeparturetime);

        tView_Destination.setText(dbAccess.destination);

        tView_EstimatedArrivalTime.setText(dbAccess.estarrivaltime);

        tView_RequestType.setText(dbAccess.requesttype);

        tView_Reason.setText(dbAccess.reason);

        tView_Justification.setText(dbAccess.justification.replace("&nbsp;", " ").replace("<br />", "\n"));

        tView_PlateNo.setText(dbAccess.plateno);

        tView_AssignBy.setText(dbAccess.assignedby);

        tView_ActDepartureDate.setText(dbAccess.departuredate);

        tView_ActDepartureTime.setText(dbAccess.departuretime);

        tView_ActArrivalDate.setText(dbAccess.arrivaldate);

        tView_ActArrivalTime.setText(dbAccess.arrivaltime);

        tView_GuardOnDuty_Departure.setText(dbAccess.guardOnDuty_Departure);

        tView_GuardOnDuty_Arrival.setText(dbAccess.guardOnDuty_Arrival);

        tView_Remarks.setText(dbAccess.remarks);

        code = dbAccess.uc;
    }

    public void GetPassengers() {
        DBAccess dbUsers = new DBAccess();

        dbUsers.GetAllPassenger(code);

        clsCustomPassengers = new ClsCustomPassengers(this, dbUsers.clsDTPassengers);

        lVPassengers.setAdapter(clsCustomPassengers);

        clsCustomPassengers.notifyDataSetChanged();
    }
}
