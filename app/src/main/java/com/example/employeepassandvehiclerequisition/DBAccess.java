package com.example.employeepassandvehiclerequisition;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class DBAccess {
    ClsConnection connectionClass;

    CallableStatement _callableStatement;

    public Boolean isSuccess = false;

    public String z = "";

    public String[] array;

    public String _empid = "", fname = "", FullName_FnameFirst = "", deptcode = ""
            , sectioncode = "", obDate = "", rowNum = "", controlNo = "", preparer = ""
            , uc = "", driver = "" ,totalPassenger = "", destination="",estdeparturetime = ""
            , estdeparturedate = "", estarrivaltime = "", estarrivaldate = "", requesttype = ""
            , reason = "", justification = "",plateno="",assignedby="",departuredate="", departuretime = ""
            ,arrivaldate="",arrivaltime="",remarks="",requesttypecode = "", crew1 = "", crew2 = ""
            ,guardOnDuty_Departure = "",guardOnDuty_Arrival = "";

    public int recCounter = 0,recCounter_IN = 0, recCounter_Finished = 0, requestID = 0;

    ClsDTRequests clsDTRequests = new ClsDTRequests();

    ClsDTPassengers clsDTPassengers = new ClsDTPassengers();

    ArrayList<ClsModelRequests> data2 = new ArrayList<ClsModelRequests>();

    ArrayList<ClsModelPassengers> data = new ArrayList<ClsModelPassengers>();


    public boolean UserLogin(String empid) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            if (con == null) {
                z = "Error in Network Connection";
            } else {
                String SP = "{call GuardLogin(?)}";

                _callableStatement = con.prepareCall(SP);

                _callableStatement.setString(1, empid);

                ResultSet rs = _callableStatement.executeQuery();

                if (rs.next()) {

                    z = "";
                    _empid = rs.getString("EmpID");

                    FullName_FnameFirst = rs.getString("Name");
                    isSuccess = true;

                } else {
                    z = "Access Denied";
                    isSuccess = false;
                }
            }
        } catch (Exception ex) {
            isSuccess = false;
            z = ex.toString();
        }

        return isSuccess;
    }

    public boolean GetAllRequest() {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequests()}";

            CallableStatement callableStatement = con.prepareCall(query);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequests_Delivery() {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequests_Delivery()}";

            CallableStatement callableStatement = con.prepareCall(query);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setDriver(rs.getString("DriverName"));

                itemPanel.setCrew1((rs.getString("Crew1")));

                itemPanel.setCrew2((rs.getString("Crew2")));

                itemPanel.setDestination(rs.getString("Delivery_PickupLocation"));

                itemPanel.setRequestid(rs.getString("RequestID"));

                data2.add(itemPanel);

                clsDTRequests.setDeliveryRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequests_Count() {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "call GetAllRequests_Count";

            CallableStatement callableStatement = con.prepareCall(query);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                recCounter = rs.getInt("ReqCounter");

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequestsByReason(String type) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequestsByReason(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1,type);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequestsByDriver(String driver) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequestsByDriver(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1,driver);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequestsByDate(String date) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequestsByDate(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1, date);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequestsByDate_Delivery(String date) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequestsByDate_Delivery(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1, date);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setDriver(rs.getString("DriverName"));

                itemPanel.setCrew1((rs.getString("Crew1")));

                itemPanel.setCrew2((rs.getString("Crew2")));

                itemPanel.setDestination(rs.getString("Delivery_PickupLocation"));

                itemPanel.setRequestid(rs.getString("RequestID"));

                data2.add(itemPanel);

                clsDTRequests.setDeliveryRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequestsByDestination_Delivery(String destination) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequestsByDestination_Delivery(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1, destination);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setDriver(rs.getString("DriverName"));

                itemPanel.setCrew1((rs.getString("Crew1")));

                itemPanel.setCrew2((rs.getString("Crew2")));

                itemPanel.setDestination(rs.getString("Delivery_PickupLocation"));

                itemPanel.setRequestid(rs.getString("RequestID"));

                data2.add(itemPanel);

                clsDTRequests.setDeliveryRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }




    public boolean GetAllFinishedRequests() {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllFinishedRequests()}";

            CallableStatement callableStatement = con.prepareCall(query);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllFinishedRequests_Delivery() {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllFinishedRequests_Delivery()}";

            CallableStatement callableStatement = con.prepareCall(query);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setDriver(rs.getString("DriverName"));

                itemPanel.setCrew1((rs.getString("Crew1")));

                itemPanel.setCrew2((rs.getString("Crew2")));

                itemPanel.setDestination(rs.getString("Delivery_PickupLocation"));

                itemPanel.setRequestid(rs.getString("RequestID"));

                data2.add(itemPanel);

                clsDTRequests.setDeliveryRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllFinishedRequests_Count() {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "call GetAllFinishedRequests_Count";

            CallableStatement callableStatement = con.prepareCall(query);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                recCounter_Finished = rs.getInt("ReqCounter");

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllFinishedRequestsByReason(String type) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllFinishedRequestsByReason(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1,type);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllFinishedRequestsByDriver(String driver) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllFinishedRequestsByDriver(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1,driver);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllFinishedRequestsByDate(String date) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequestsByDate(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1, date);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllFinishedRequestsByDate_Delivery(String date) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllFinishedRequestsByDate_Delivery(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1, date);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setDriver(rs.getString("DriverName"));

                itemPanel.setCrew1((rs.getString("Crew1")));

                itemPanel.setCrew2((rs.getString("Crew2")));

                itemPanel.setDestination(rs.getString("Delivery_PickupLocation"));

                itemPanel.setRequestid(rs.getString("RequestID"));

                data2.add(itemPanel);

                clsDTRequests.setDeliveryRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllFinishedRequestsByDestination_Delivery(String destination) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllFinishedRequestsByDestination_Delivery(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1, destination);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setDriver(rs.getString("DriverName"));

                itemPanel.setCrew1((rs.getString("Crew1")));

                itemPanel.setCrew2((rs.getString("Crew2")));

                itemPanel.setDestination(rs.getString("Delivery_PickupLocation"));

                itemPanel.setRequestid(rs.getString("RequestID"));

                data2.add(itemPanel);

                clsDTRequests.setDeliveryRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }


    public boolean GetAllRequests_IN() {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequests_IN()}";

            CallableStatement callableStatement = con.prepareCall(query);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequests_IN_Delivery() {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequests_IN_Delivery()}";

            CallableStatement callableStatement = con.prepareCall(query);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setDriver(rs.getString("DriverName"));

                itemPanel.setCrew1((rs.getString("Crew1")));

                itemPanel.setCrew2((rs.getString("Crew2")));

                itemPanel.setDestination(rs.getString("Delivery_PickupLocation"));

                itemPanel.setRequestid(rs.getString("RequestID"));

                data2.add(itemPanel);

                clsDTRequests.setDeliveryRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequests_IN_Count() {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "call GetAllRequests_IN_Count";

            CallableStatement callableStatement = con.prepareCall(query);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                recCounter_IN = rs.getInt("ReqCounter");

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequests_INByReason(String type) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequests_INByReason(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1,type);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequests_INByDriver(String driver) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequests_INByDriver(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1,driver);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequests_INByDate(String date) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequests_INByDate(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1,  date);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequests_INByDate_Delivery(String date) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequests_INByDate_Delivery(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1,  date);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllRequests_INByDestination_Delivery(String destination) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllRequests_INByDestination_Delivery(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1,  destination);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelRequests itemPanel = new ClsModelRequests();

                itemPanel.setRowNum(rs.getString("RowNum"));

                itemPanel.setControlNo(rs.getString("ControlNo"));

                itemPanel.setoBDate(rs.getString("NewOBDate"));

                itemPanel.setRequestedBy(rs.getString("Preparer"));

                itemPanel.setReason(rs.getString("ReasonDesc"));

                itemPanel.setDriver(rs.getString("DriverName"));

                data2.add(itemPanel);

                clsDTRequests.setRequestsList(data2);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }






    public boolean GetRequestDetails(String ControlNo) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetRequestDetails(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1, ControlNo);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                rowNum = rs.getString("RowNum");

                controlNo = rs.getString("ControlNo");

                obDate = rs.getString("NewOBDate");

                preparer = rs.getString("Preparer");

                uc = rs.getString("UniqueCode");

                driver = rs.getString("DriverName");

                totalPassenger = rs.getString("TotalPassenger");

                destination = rs.getString("AddressLocation");

                estdeparturetime = rs.getString("EstDepartTime");

                estarrivaltime = rs.getString("EstArrTime");

                requesttype = rs.getString("RequestDesc");

                requesttypecode = rs.getString("RequestType");

                reason = rs.getString("ReasonDesc");

                justification = rs.getString("Justification");

                plateno = rs.getString("PlateNo");

                assignedby = rs.getString("AssignedBy");

                departuredate = rs.getString("NewActDeptDate");

                departuretime = rs.getString("ActDepartTime");

                arrivaldate = rs.getString("NewActArrDate");

                arrivaltime = rs.getString("ActArrTime");

                guardOnDuty_Departure = rs.getString("GuardOnDuty_Departure");

                guardOnDuty_Arrival = rs.getString("GuardOnDuty_Arrival");

                remarks = rs.getString("GuardRemarks");

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetRequestDetails_Delivery(int id) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetRequestDetails_Delivery(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setInt(1, id);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                rowNum = rs.getString("RowNum");

                preparer = rs.getString("Preparer");

                obDate = rs.getString("NewOBDate");

                driver = rs.getString("DriverName");

                crew1 = rs.getString("Crew1");

                crew2 = rs.getString("Crew2");

                destination = rs.getString("Delivery_PickupLocation");

                plateno = rs.getString("PlateNo");

                departuredate = rs.getString("NewActDeptDate");

                departuretime = rs.getString("ActDepartTime");

                guardOnDuty_Departure = rs.getString("GuardOnDuty_Departure");

                arrivaldate = rs.getString("NewActArrDate");

                arrivaltime = rs.getString("ActArrTime");

                guardOnDuty_Arrival = rs.getString("GuardOnDuty_Arrival");

                remarks = rs.getString("GuardRemarks");

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean GetAllPassenger(String code) {
        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            String query = "{call GetAllPassengers(?)}";

            CallableStatement callableStatement = con.prepareCall(query);

            callableStatement.setString(1, code);

            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {

                ClsModelPassengers itemPanels = new ClsModelPassengers();

                itemPanels.setRowNum(rs.getString("RowNum"));

                itemPanels.setPassengerName(rs.getString("PassengerName"));

                data.add(itemPanels);

                clsDTPassengers.setPassengersArrayList(data);

                isSuccess = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean UpdateDepartAndArrival(String ControlNo, String Guard, String Stat,String Remarks
            ,String dateDepart, String timeDepart, String dateArrive, String timeArrive) {

        String query = "{call UpdateDepartAndArrival(?,?,?,?,?,?,?,?)}";

        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            CallableStatement callableStatement;

            callableStatement = con.prepareCall(query);

            callableStatement.setString(1, ControlNo);

            callableStatement.setString(2, Guard);

            callableStatement.setString(3, Stat);

            callableStatement.setString(4, Remarks);

            callableStatement.setString(5, dateDepart);

            callableStatement.setString(6, timeDepart);

            callableStatement.setString(7, dateArrive);

            callableStatement.setString(8, timeArrive);

            callableStatement.executeUpdate();

            con.setAutoCommit(true);

            isSuccess = true;

            z = "Record Has Been Updated";

        } catch (Exception ex) {
            z = ex.getMessage().toString();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean UpdateDepartAndArrival_Delivery(int requestID, String Guard, String Stat,String Remarks,String dateDepart, String timeDepart
            , String dateArrive, String timeArrive) {

        String query = "{call UpdateDepartAndArrival_Delivery(?,?,?,?,?,?,?,?)}";

        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            CallableStatement callableStatement;

            callableStatement = con.prepareCall(query);

            callableStatement.setInt(1, requestID);

            callableStatement.setString(2, Guard);

            callableStatement.setString(3, Stat);

            callableStatement.setString(4, Remarks);

            callableStatement.setString(5, dateDepart);

            callableStatement.setString(6, timeDepart);

            callableStatement.setString(7, dateArrive);

            callableStatement.setString(8, timeArrive);

            callableStatement.executeUpdate();

            con.setAutoCommit(true);

            isSuccess = true;

            z = "Record Has Been Updated";

        } catch (Exception ex) {
            z = ex.getMessage().toString();
            isSuccess = false;
        }

        return isSuccess;
    }

    public boolean UpdateDepartAndArrival_Delivery2(int requestID, String Guard, String Stat,String Remarks,String dateDepart, String timeDepart
            , String dateArrive, String timeArrive,String driver,String crew1,String crew2) {

        String query = "{call UpdateDepartAndArrival_Delivery2(?,?,?,?,?,?,?,?,?,?,?)}";

        try {
            connectionClass = new ClsConnection();

            Connection con = connectionClass.Conn();

            CallableStatement callableStatement;

            callableStatement = con.prepareCall(query);

            callableStatement.setInt(1, requestID);

            callableStatement.setString(2, Guard);

            callableStatement.setString(3, Stat);

            callableStatement.setString(4, Remarks);

            callableStatement.setString(5, dateDepart);

            callableStatement.setString(6, timeDepart);

            callableStatement.setString(7, dateArrive);

            callableStatement.setString(8, timeArrive);

            callableStatement.setString(9, driver);

            callableStatement.setString(10, crew1);

            callableStatement.setString(11, crew2);

            callableStatement.executeUpdate();

            con.setAutoCommit(true);

            isSuccess = true;

            z = "Record Has Been Updated";

        } catch (Exception ex) {
            z = ex.getMessage().toString();
            isSuccess = false;
        }

        return isSuccess;
    }



}
