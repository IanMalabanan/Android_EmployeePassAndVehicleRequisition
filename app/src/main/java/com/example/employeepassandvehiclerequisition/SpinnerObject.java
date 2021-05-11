package com.example.employeepassandvehiclerequisition;

public class SpinnerObject {
    private  String databaseText;
    private String databaseValue;

    public SpinnerObject ( String databaseText , String databaseValue ) {
        this.databaseText = databaseText;
        this.databaseValue = databaseValue;
    }

    public String getDatabaseText() {
        return databaseText;
    }

    public void setDatabaseText(String databaseText) {
        this.databaseText = databaseText;
    }

    public String getDatabaseValue() {
        return databaseValue;
    }

    public void setDatabaseValue(String databaseValue) {
        this.databaseValue = databaseValue;
    }


    @Override
    public String toString () {
        return databaseText;
    }
}
