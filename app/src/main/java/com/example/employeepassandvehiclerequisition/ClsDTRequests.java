package com.example.employeepassandvehiclerequisition;

import java.util.ArrayList;

public class ClsDTRequests {

    ArrayList<ClsModelRequests> RequestsList;

    ArrayList<ClsModelRequests> DeliveryRequestsList;

    public ArrayList<ClsModelRequests> getRequestsList() {
        return RequestsList;
    }

    public void setRequestsList(ArrayList<ClsModelRequests> requestsList) {
        RequestsList = requestsList;
    }

    public ArrayList<ClsModelRequests> getDeliveryRequestsList() {
        return DeliveryRequestsList;
    }

    public void setDeliveryRequestsList(ArrayList<ClsModelRequests> deliveryRequestsList) {
        DeliveryRequestsList = deliveryRequestsList;
    }
}
