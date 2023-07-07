package com.stockmanagementsystem.managestock.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.stockmanagementsystem.managestock.MainActivity;
import com.stockmanagementsystem.managestock.R;
import com.stockmanagementsystem.managestock.adapters.CustomersAdapter;
import com.stockmanagementsystem.managestock.adapters.TodayBillAdapter;
import com.stockmanagementsystem.managestock.modelCLasses.GetBillsData;

import java.util.ArrayList;
import java.util.List;

public class CustomersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<GetBillsData> billsDataList = new ArrayList<>();
    public CustomersAdapter billsAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        readData();

    }

    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }



    private void readData() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            uid = currentUser.getUid();
            db.collection("Customers").document(uid).collection("data").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<GetBillsData> billsDataList = new ArrayList<>();
                                for (DocumentSnapshot document : task.getResult()) {
                                    String name = document.getString("name");
                                    String  age = document.getString("age");
                                    String email = document.getString("email");
                                    String phone = document.getString("phone");
                                    Object medicineObj = document.get("medicines");
                                    ArrayList<String> medicine = new ArrayList<>();
                                    if (medicineObj instanceof ArrayList) {
                                        medicine = (ArrayList<String>) medicineObj;
                                    } else if (medicineObj instanceof String) {
                                        medicine.add((String) medicineObj);
                                    }
                                    String manufactureDate = document.getString("manufacture_date");
                                    String expireDate = document.getString("expire_date");
                                    String quantity = document.getString("quantity");
                                    String  price = document.getString("price");
                                    String doctorName = document.getString("doctor_name");
                                    String date = document.getString("date");

                                    GetBillsData m = new GetBillsData(name, age, email, phone,
                                            medicine, manufactureDate, expireDate, quantity,
                                            price, doctorName, date);
                                    billsDataList.add(m);
                                }

                                recyclerView = findViewById(R.id.recyclerViewMedicine);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CustomersActivity.this));
                                billsAdapter = new CustomersAdapter(getApplicationContext(), billsDataList);
                                recyclerView.setAdapter(billsAdapter);
                            } else {
                                Toast.makeText(CustomersActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


}