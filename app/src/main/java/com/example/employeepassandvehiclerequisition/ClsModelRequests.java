package com.example.employeepassandvehiclerequisition;

public class ClsModelRequests {
    private String rowNum;
    private String controlNo;
    private String oBDate;
    private String requestedBy;
    private String reason;
    private String uniqueCode;
    private String driver;
    private String crew1;

    public String getGuardOnDuty_Departure() {
        return guardOnDuty_Departure;
    }

    public void setGuardOnDuty_Departure(String guardOnDuty_Departure) {
        this.guardOnDuty_Departure = guardOnDuty_Departure;
    }

    public String getGuardOnDuty_Arrival() {
        return guardOnDuty_Arrival;
    }

    public void setGuardOnDuty_Arrival(String guardOnDuty_Arrival) {
        this.guardOnDuty_Arrival = guardOnDuty_Arrival;
    }

    private String crew2;
    private String destination;
    private String requestid;
    private String guardOnDuty_Departure;
    private String guardOnDuty_Arrival;

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCrew1() {
        return crew1;
    }

    public void setCrew1(String crew1) {
        this.crew1 = crew1;
    }

    public String getCrew2() {
        return crew2;
    }

    public void setCrew2(String crew2) {
        this.crew2 = crew2;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getControlNo() {
        return controlNo;
    }

    public void setControlNo(String controlNo) {
        this.controlNo = controlNo;
    }

    public String getoBDate() {
        return oBDate;
    }

    public void setoBDate(String oBDate) {
        this.oBDate = oBDate;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
