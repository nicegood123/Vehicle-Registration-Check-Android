package com.example.vehicleregistrationchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCheck, btnClose;
    EditText editTxtPlateNumber;

    TextView lblVehicleType;
    TextView lblMonthsDelayed;
    TextView lblTotalFines;
    TextView lblRegistrationDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTxtPlateNumber = findViewById(R.id.editTxtPlateNumber);
        btnCheck = findViewById(R.id.btnCheck);
        btnClose = findViewById(R.id.btnClose);

        lblVehicleType = findViewById(R.id.lblVehicleType);
        lblMonthsDelayed = findViewById(R.id.lblMonthsDelayed);
        lblTotalFines = findViewById(R.id.lblTotalFines);
        lblRegistrationDate = findViewById(R.id.lblRegistrationDate);

        btnCheck.setOnClickListener(this);
        btnClose.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        //Get current day and month
        String current_day = new SimpleDateFormat("dd").format(new Date().getTime());
        String current_month = new SimpleDateFormat("MMMM").format(new Date().getTime());

        //initialize
        double total_fines;
        int no_of_delayed_months = 0;
        String month = "", week = "";
        String plate_number = editTxtPlateNumber.getText().toString();
        String last = "" + plate_number.charAt(plate_number.length() - 1);
        int second = Integer.parseInt("" + plate_number.charAt(plate_number.length() - 2));
        String [] months = {"October", "January", "February", "March", "April", "May", "June", "July", "August", "September"};

        //Get vehicle type
        String vehicle_type = (plate_number.charAt(2) == 32) ? "Two-wheeled vehicle" : "Four-wheeled vehicle";

        //Get no of months delayed registration/renew
        for(int i = 0; i <= 9; i++) {
            if(Integer.parseInt(last) == i) {
                month = months[i];
                if(!month.equals("October")) {
                    for(int a = Integer.parseInt(last); a < 9; a++) {
                        no_of_delayed_months++;
                    }
                }

                if(current_month.equals("November")) {
                    no_of_delayed_months++;
                } else if(current_month.equals("December")) {
                    no_of_delayed_months += 2;
                }
                break;
            }
        }

        //Calculate total fines
        if(no_of_delayed_months > 5) {
            total_fines = 100000.00;
        } else {
            if(vehicle_type.length() == 19) {
                total_fines = 1500.00 * no_of_delayed_months;
            } else {
                total_fines = 3000.00 * no_of_delayed_months;
            }
        }

        if(second >=1 && second <= 3) {
                week = "1st to the 7th day of the month";
        } else if(second >=4 && second <= 6) {
            week = "8th to the 14th day of the month";
        } else if(second >=7 && second <= 8) {
            week = "15th to the 21st day of the month";
        } else if(second == 9 && second == 0) {
            week = "22nd to the last day of the month";
        }

        if(v.getId() == R.id.btnCheck) {
                lblVehicleType.setText("" + vehicle_type);
                lblMonthsDelayed.setText("" + no_of_delayed_months);
                lblTotalFines.setText("PHP " + total_fines);
                lblRegistrationDate.setText("The vehicle should be registered on or before " + month + " between " + week);
        } else if(v.getId() == R.id.btnClose) {
            finish();
            System.exit(0);
        }
    }
}