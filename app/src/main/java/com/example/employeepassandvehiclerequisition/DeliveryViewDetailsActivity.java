package com.example.employeepassandvehiclerequisition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DeliveryViewDetailsActivity extends AppCompatActivity {

    TextView tViewDeliveryOBDate, tViewDeliveryRequestedBy, tViewDeliveryDriver, tViewDeliveryCrew1, tViewDeliveryCrew2, tViewDeliveryDestination, tViewDeliveryPlateNo, tViewDeliveryActDepartureDate, tViewDeliveryActDepartureTime, tViewDeliveryActArrivalDate, tViewDeliveryActArrivalTime, tViewDeliveryRemarks, tViewDeliveryGuardOnDuty_Departure, tViewDeliveryGuardOnDuty_Arrival;

    DBAccess dbAccess;

    public int requestID;

    public String fullname, stat, obDate;

    private static int convertStringToInt(String str){
        int x = 0;
        try{
            x = Integer.parseInt(str);
        }catch(NumberFormatException ex){
            //TODO: LOG or HANDLE
        }
        return x;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_view_details);

        requestID = convertStringToInt(getIntent().getExtras().getString("requestID"));

        fullname = getIntent().getExtras().getString("fullname");

        stat = getIntent().getExtras().getString("stat");

        tViewDeliveryOBDate = (TextView) findViewById(R.id.tViewDeliveryOBDate);

        tViewDeliveryRequestedBy = (TextView) findViewById(R.id.tViewDeliveryRequestedBy);

        tViewDeliveryPlateNo = (TextView) findViewById(R.id.tViewDeliveryPlateNo);

        tViewDeliveryDestination = (TextView) findViewById(R.id.tViewDeliveryDestination);

        tViewDeliveryDriver = (TextView) findViewById(R.id.tViewDeliveryDriver);

        tViewDeliveryCrew1 = (TextView) findViewById(R.id.tViewDeliveryCrew1);

        tViewDeliveryCrew2 = (TextView) findViewById(R.id.tViewDeliveryCrew2);

        tViewDeliveryActDepartureDate = (TextView) findViewById(R.id.tViewDeliveryActDepartureDate);

        tViewDeliveryActDepartureTime = (TextView) findViewById(R.id.tViewDeliveryActDepartureTime);

        tViewDeliveryActArrivalDate = (TextView) findViewById(R.id.tViewDeliveryActArrivalDate);

        tViewDeliveryActArrivalTime = (TextView) findViewById(R.id.tViewDeliveryActArrivalTime);

        tViewDeliveryRemarks = (TextView) findViewById(R.id.tViewDeliveryRemarks);

        tViewDeliveryGuardOnDuty_Departure = (TextView) findViewById(R.id.tViewDeliveryGuardOnDuty_Departure);

        tViewDeliveryGuardOnDuty_Arrival = (TextView) findViewById(R.id.tViewDeliveryGuardOnDuty_Arrival);

        ViewDetails();
    }

    @Override
    public void onBackPressed() {
        Intent launchNextActivity;
        launchNextActivity = new Intent(DeliveryViewDetailsActivity.this, DeliveryMainActivity.class);
        launchNextActivity.putExtra("fullname", fullname);
        startActivity(launchNextActivity);
    }

    public void ViewDetails() {
        dbAccess = new DBAccess();

        dbAccess.GetRequestDetails_Delivery(requestID);

        tViewDeliveryOBDate.setText(dbAccess.obDate);

        tViewDeliveryRequestedBy.setText(dbAccess.preparer);

        tViewDeliveryDriver.setText(dbAccess.driver);

        tViewDeliveryCrew1.setText(dbAccess.crew1);

        tViewDeliveryCrew2.setText(dbAccess.crew2);

        tViewDeliveryDestination.setText(dbAccess.destination);

        tViewDeliveryPlateNo.setText(dbAccess.plateno);

        tViewDeliveryActDepartureDate.setText(dbAccess.departuredate);

        tViewDeliveryActDepartureTime.setText(dbAccess.departuretime);

        tViewDeliveryGuardOnDuty_Departure.setText(dbAccess.guardOnDuty_Departure);

        tViewDeliveryGuardOnDuty_Arrival.setText(dbAccess.guardOnDuty_Arrival);

        tViewDeliveryActArrivalDate.setText(dbAccess.arrivaldate);

        tViewDeliveryActArrivalTime.setText(dbAccess.arrivaltime);

        tViewDeliveryRemarks.setText(dbAccess.remarks);

    }
}
