package com.stockmanagementsystem.managestock.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.stockmanagementsystem.managestock.MainActivity;
import com.stockmanagementsystem.managestock.R;
import com.stockmanagementsystem.managestock.modelCLasses.AddMedicineModelView;

import java.util.Calendar;
import java.util.HashMap;
@IgnoreExtraProperties
public class AddMedicineActivity extends AppCompatActivity {
    EditText medicine, company, quantity, price;
    TextView manufactureDate, expireDate;
    Button add;
    String uid = FirebaseAuth.getInstance().getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        medicine = findViewById(R.id.enterMedicine2);
        company = findViewById(R.id.enterCompany2);
        manufactureDate = findViewById(R.id.enterManufactureDate2);
        expireDate = findViewById(R.id.enterExpireDate2);
        quantity = findViewById(R.id.enterQuantity2);
        price = findViewById(R.id.enterPrice2);
        add = findViewById(R.id.btnSave2);


        manufactureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                long currentTime = Calendar.getInstance().getTimeInMillis();
                // Create a new DatePickerDialog and show it
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMedicineActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Set the selected date on the EditText
                                manufactureDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(currentTime - 24 * 60 * 60 * 1000); // Set minimum date to yesterday
                datePickerDialog.show();
            }
        });

        expireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                long currentTime = Calendar.getInstance().getTimeInMillis();

                // Create a new DatePickerDialog and show it
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMedicineActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Set the selected date on the EditText
                                expireDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(currentTime + 365 * 24 * 60 * 60 * 1000); // Set maximum date to one year from today
                datePickerDialog.show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean info = true;

                if (medicine.getText().toString().equals("")) {
                    medicine.setError("invalid");
                    info = false;



                }
                if (manufactureDate.getText().toString().equals("")) {
                    manufactureDate.setError("invalid");
                    info = false;
                }
                if (expireDate.getText().toString().equals("")) {
                    expireDate.setError("invalid");
                    info = false;
                }
                if (quantity.getText().toString().equals("")) {
                    quantity.setError("invalid");
                    info = false;
                }
                if (company.getText().toString().equals("")) {
                    company.setError("invalid");
                    info = false;
                }
                if (price.getText().toString().equals("")) {
                    price.setError("invalid");
                    info = false;
                }
                if (info) {


// Check for network connectivity
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    if (activeNetwork == null || !activeNetwork.isConnected()) {
                        // Handle network connectivity issues
                        Toast.makeText(AddMedicineActivity.this, "No network connectivity", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                        DatabaseReference uidRef2 = database2.getReference("AllMedicineList").child(uid).child(medicine.getText().toString());
                        AddMedicineModelView medicine1 = new AddMedicineModelView( medicine.getText().toString(), company.getText().toString(), manufactureDate.getText().toString(), expireDate.getText().toString(), quantity.getText().toString(), price.getText().toString());
                        uidRef2.setValue(medicine1);
                    }
                    finish();
                    startActivity(new Intent(AddMedicineActivity.this, AddMedicineActivity.class));

                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

}

