package com.example.employeepassandvehiclerequisition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DBAccess dbAccess;

    EditText txtEmpID;

    Button btnSignin;

    String empid, fullname;

    ClsUtils clsUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().hide();

        txtEmpID = (EditText) findViewById(R.id.txtEmpID);

        btnSignin = (Button) findViewById(R.id.btnSignin);


        //if (!clsUtils.HasConnection(this)) {
           // clsUtils.NoInternetBuilDialog(this).show();

        //} else {
            btnSignin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckLogin checkLogin = new CheckLogin();
                    checkLogin.execute("");
                }
            });
        //}
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1000);
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";

        Boolean isSuccess = false;

        //String username = _autoCompleteTextUsername.getText().toString();

        String _empid = txtEmpID.getText().toString();

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != "") {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            if (isSuccess) {
                Intent i = new Intent(LoginActivity.this, MainMenuActivity.class);
                i.putExtra("fullname", fullname);
                startActivity(i);
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            if (_empid.trim().equals(""))
                z = "Username Cannot Be Empty. Username Required";
            else {
                dbAccess = new DBAccess();

                dbAccess.UserLogin(_empid);

                empid = dbAccess._empid;

                fullname = dbAccess.FullName_FnameFirst;

                z = dbAccess.z;

                isSuccess = dbAccess.isSuccess;
            }

            return z;
        }
    }
}
